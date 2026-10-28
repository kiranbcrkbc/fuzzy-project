package edu.fuzzy;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import java.nio.file.*;
import java.io.*;
import java.util.UUID;

/** Ciphertext only. Reference manifests and wrapping keys are not read by this component. */
@Service
public class ObjectStore {
 private final Db db;private final CryptoService crypto;private final boolean sql;
 public ObjectStore(Db db,CryptoService crypto,@Value("${fuzzy.object-store:disk}")String mode,@Value("${fuzzy.hosted:false}")boolean hosted){
  if(!mode.equals("disk")&&!mode.equals("sql"))throw new IllegalStateException("Unknown object store mode");
  this.db=db;this.crypto=crypto;sql=mode.equals("sql");
  if(sql&&hosted&&!crypto.externalMaster())throw new IllegalStateException("Hosted SQL object storage requires a persistent FUZZY_MASTER_KEY host secret.");
 }
 private void valid(String id){UUID.fromString(id);}
 public void put(String id,byte[]ciphertext)throws IOException{valid(id);if(sql)db.jdbc.update("insert into cloud_object(id,ciphertext) values(?,?)",id,ciphertext);else Files.write(crypto.object(id),ciphertext,StandardOpenOption.CREATE_NEW);}
 private byte[] readSql(String id,boolean lock)throws IOException{
  try{return db.jdbc.queryForObject("select ciphertext from cloud_object where id=?"+(lock?" for update":""),(rs,n)->rs.getBytes(1),id);}
  catch(EmptyResultDataAccessException e){throw new FileNotFoundException("Current ciphertext object is missing");}
 }
 public byte[] read(String id)throws IOException{valid(id);return sql?readSql(id,false):Files.readAllBytes(crypto.object(id));}
 public void delete(String id)throws IOException{valid(id);if(sql)db.jdbc.update("delete from cloud_object where id=?",id);else Files.deleteIfExists(crypto.object(id));}
 public AuditProtocol.Response respond(AuditProtocol protocol,AuditProtocol.Challenge c)throws IOException{
  valid(c.fileId());return sql?protocol.respond(c,read(c.fileId())):protocol.respond(c,crypto.object(c.fileId()));
 }
 /** Caller supplies the demo/role/ownership checks and transaction. */
 public void flip(String id,long offset)throws IOException{
  valid(id);
  if(sql){byte[] current=readSql(id,true);if(offset<0||offset>=current.length)throw new IllegalArgumentException("Object is truncated; cannot flip that byte.");current[(int)offset]^=1;db.jdbc.update("update cloud_object set ciphertext=? where id=?",current,id);}
  else try(RandomAccessFile f=new RandomAccessFile(crypto.object(id).toFile(),"rw")){if(offset<0||offset>=f.length())throw new IllegalArgumentException("Object is truncated; cannot flip that byte.");f.seek(offset);int b=f.read();f.seek(offset);f.write(b^1);}
 }
}
