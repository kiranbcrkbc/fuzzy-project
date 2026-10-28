package edu.fuzzy;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import java.security.SecureRandom;
import org.springframework.stereotype.Service;
@Service
public class AuditProtocol {
 public static final int BLOCK_SIZE=65536;
 public record Block(int index,int length,String digest){}
 public record Manifest(String fileId,int version,long length,int blockSize,List<Block> blocks){}
 public record Challenge(String auditId,String nonce,String fileId,int version,List<Integer> indices,long issuedAt,long expiresAt,String mode){}
 public record ResponseBlock(int index,byte[] bytes){}
 public record Response(String auditId,String nonce,String fileId,int version,long objectLength,List<ResponseBlock> blocks){}
 public record Comparison(int index,int expectedLength,int actualLength,String expectedDigest,String actualDigest,boolean passed){}
 public record Result(boolean passed,String reason,List<Comparison> comparisons){}
 public Manifest manifest(String id,int version,byte[] envelope){List<Block>b=new ArrayList<>();for(int i=0,offset=0;offset<envelope.length;i++,offset+=BLOCK_SIZE){byte[] part=Arrays.copyOfRange(envelope,offset,Math.min(offset+BLOCK_SIZE,envelope.length));b.add(new Block(i,part.length,CryptoService.hash(part)));}return new Manifest(id,version,envelope.length,BLOCK_SIZE,List.copyOf(b));}
 public Challenge challenge(String audit,Manifest m,String mode,long now){if(!Set.of("FULL","SAMPLED").contains(mode))throw new IllegalArgumentException("Invalid audit mode");List<Integer>idx=new ArrayList<>();for(Block b:m.blocks())idx.add(b.index());if(mode.equals("SAMPLED")){Collections.shuffle(idx,new SecureRandom());idx=new ArrayList<>(idx.subList(0,Math.min(3,idx.size())));Collections.sort(idx);}return new Challenge(audit,UUID.randomUUID().toString(),m.fileId(),m.version(),List.copyOf(idx),now,now+600000,mode);}
 public Response respond(Challenge c,Path path) throws IOException {List<ResponseBlock>blocks=new ArrayList<>();try(RandomAccessFile f=new RandomAccessFile(path.toFile(),"r")){long len=f.length();for(int i:c.indices()){long offset=(long)i*BLOCK_SIZE;f.seek(Math.min(offset,len));int size=(int)Math.max(0,Math.min(BLOCK_SIZE,len-offset));byte[]b=new byte[size];f.readFully(b);blocks.add(new ResponseBlock(i,b));}return new Response(c.auditId(),c.nonce(),c.fileId(),c.version(),len,List.copyOf(blocks));}}
 public Result verify(Challenge c,Response r,Manifest m,long now){
  if(c==null||r==null||m==null||c.auditId()==null||c.nonce()==null||c.fileId()==null||m.fileId()==null||m.blocks()==null||c.indices()==null||c.indices().isEmpty()||c.indices().stream().anyMatch(Objects::isNull)||c.issuedAt()>now||c.expiresAt()<c.issuedAt())return fail("Malformed response, manifest or challenge metadata");
  if(now>c.expiresAt()) return fail("Challenge expired");
  if(!c.auditId().equals(r.auditId())||!c.nonce().equals(r.nonce())||!c.fileId().equals(r.fileId())||c.version()!=r.version()||!m.fileId().equals(c.fileId())||m.version()!=c.version())return fail("Challenge binding mismatch: audit, nonce, file or version");
  if(r.objectLength()!=m.length())return fail("Object length differs from trusted upload-time length");
  if(c.indices()==null||new HashSet<>(c.indices()).size()!=c.indices().size()||r.blocks()==null||r.blocks().size()!=c.indices().size())return fail("Missing, duplicate or extra blocks");
  Set<Integer>seen=new HashSet<>();List<Comparison>checks=new ArrayList<>();boolean pass=true;
  for(ResponseBlock b:r.blocks()){
   if(b==null||!c.indices().contains(b.index())||!seen.add(b.index())||b.index()<0||b.index()>=m.blocks().size()||b.bytes()==null)return fail("Missing, duplicate, extra or invalid block");
   Block expected=m.blocks().get(b.index());String hash=CryptoService.hash(b.bytes());boolean ok=expected.length()==b.bytes().length&&expected.digest().equals(hash);pass&=ok;checks.add(new Comparison(b.index(),expected.length(),b.bytes().length,expected.digest(),hash,ok));
  }
  if(!seen.equals(new HashSet<>(c.indices())))return fail("Requested index set mismatch");
  return new Result(pass,pass?"Selected current ciphertext blocks match trusted upload-time metadata":"Ciphertext digest or block length mismatch",checks);
 }
 private Result fail(String message){return new Result(false,message,List.of());}
 /** The supplied bytes must be read from the current durable ciphertext object for this response. */
 public Response respond(Challenge c,byte[] current){List<ResponseBlock>blocks=new ArrayList<>();for(int i:c.indices()){long offset=(long)i*BLOCK_SIZE;int start=(int)Math.min(offset,current.length);int end=(int)Math.min(offset+BLOCK_SIZE,current.length);blocks.add(new ResponseBlock(i,Arrays.copyOfRange(current,start,end)));}return new Response(c.auditId(),c.nonce(),c.fileId(),c.version(),current.length,List.copyOf(blocks));}
}
