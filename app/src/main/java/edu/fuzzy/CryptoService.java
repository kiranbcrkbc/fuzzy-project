package edu.fuzzy;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class CryptoService {
 private final byte[] master;
 public final Path root;
 private final SecureRandom random=new SecureRandom();
 public CryptoService(@Value("${fuzzy.data-root}") String dataRoot) throws Exception {
  root=Path.of(dataRoot).toAbsolutePath().normalize(); Files.createDirectories(root.resolve("cloud")); Files.createDirectories(root.resolve("trusted"));
  Path p=root.resolve("trusted/master.key");
  if(Files.exists(p)) master=Files.readAllBytes(p); else {master=random(32); Files.write(p,master,StandardOpenOption.CREATE_NEW);}
  if(master.length!=32) throw new IllegalStateException("Invalid master key file. Restore the trusted key backup.");
 }
 public byte[] random(int n){byte[] b=new byte[n];random.nextBytes(b);return b;}
 public byte[] encrypt(byte[] plain,byte[] key,String aad) throws Exception {
  byte[] iv=random(12); Cipher c=Cipher.getInstance("AES/GCM/NoPadding"); c.init(Cipher.ENCRYPT_MODE,new SecretKeySpec(key,"AES"),new GCMParameterSpec(128,iv)); c.updateAAD(aad.getBytes(StandardCharsets.UTF_8));
  byte[] out=c.doFinal(plain); byte[] envelope=new byte[12+out.length]; System.arraycopy(iv,0,envelope,0,12);System.arraycopy(out,0,envelope,12,out.length);return envelope;
 }
 public byte[] decrypt(byte[] envelope,byte[] key,String aad) throws Exception {
  if(envelope.length<28) throw new IllegalArgumentException("Encrypted object is truncated.");
  Cipher c=Cipher.getInstance("AES/GCM/NoPadding");c.init(Cipher.DECRYPT_MODE,new SecretKeySpec(key,"AES"),new GCMParameterSpec(128,Arrays.copyOf(envelope,12)));c.updateAAD(aad.getBytes(StandardCharsets.UTF_8));return c.doFinal(envelope,12,envelope.length-12);
 }
 public String wrap(byte[] key,String context) throws Exception{return Base64.getEncoder().encodeToString(encrypt(key,master,context));}
 public byte[] unwrap(String wrapped,String context) throws Exception{return decrypt(Base64.getDecoder().decode(wrapped),master,context);}
 public Path object(String id){UUID.fromString(id); Path p=root.resolve("cloud").resolve(id+".bin").normalize();if(!p.startsWith(root.resolve("cloud")))throw new SecurityException("Invalid object path");return p;}
 public static String hash(byte[] bytes){try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(bytes));}catch(Exception e){throw new IllegalStateException(e);}}
}
