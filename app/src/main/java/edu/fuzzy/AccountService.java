package edu.fuzzy;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
@Service
public class AccountService {
 private final Db db;private final PasswordEncoder passwords;private final CryptoService crypto;
 public AccountService(Db db,PasswordEncoder passwords,CryptoService crypto){this.db=db;this.passwords=passwords;this.crypto=crypto;}
 @Transactional public String register(Map<String,String>form,MultipartFile signature)throws Exception{
  String email=clean(form.get("email"),190).toLowerCase();String name=clean(form.get("name"),100);String pw=form.getOrDefault("password","");
  if(!email.matches("[A-Za-z0-9.!#$%&'*+/=?^_`{|}~-]+@[A-Za-z0-9-]+(?:\\.[A-Za-z0-9-]+)+")||name.isBlank()||pw.length()<10||pw.getBytes(java.nio.charset.StandardCharsets.UTF_8).length>72)throw new IllegalArgumentException("Enter a name, valid email and a password of 10–72 UTF-8 bytes.");
  if(!pw.equals(form.get("rpassword")))throw new IllegalArgumentException("Password and confirmation must match.");
  if(!db.jdbc.queryForList("select id from app_user where email=?",email).isEmpty())throw new IllegalArgumentException("That email is already registered.");
  byte[]image=null;
  if(signature!=null&&!signature.isEmpty()){
   if(signature.getSize()>524288)throw new IllegalArgumentException("Signature image must be at most 512 KiB.");
   try(var input=ImageIO.createImageInputStream(signature.getInputStream())){
    var readers=ImageIO.getImageReaders(input);if(!readers.hasNext())throw new IllegalArgumentException("Use a PNG or JPEG signature image.");
    var reader=readers.next();try{reader.setInput(input);String format=reader.getFormatName();if(!Set.of("png","jpeg","jpg").contains(format.toLowerCase())||reader.getWidth(0)>2000||reader.getHeight(0)>2000)throw new IllegalArgumentException("Signature must be PNG/JPEG, maximum 2000 × 2000 pixels.");BufferedImage im=reader.read(0);ByteArrayOutputStream out=new ByteArrayOutputStream();ImageIO.write(im,"png",out);image=out.toByteArray();}finally{reader.dispose();}
   }
  }
  String dob=clean(form.get("dob"),16);if(!dob.isBlank()){try{if(java.time.LocalDate.parse(dob).isAfter(java.time.LocalDate.now()))throw new Exception();}catch(Exception e){throw new IllegalArgumentException("Date of birth must be a valid past date.");}}
  String id=Db.id();db.jdbc.update("insert into app_user(id,email,display_name,password_hash,role,approval,dob,gender,phone,city,country,attributes,signature_data,created_at) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)",id,email,name,passwords.encode(pw),"USER","PENDING",dob,clean(form.get("gender"),40),clean(form.get("phone"),32),clean(form.get("city"),100),clean(form.get("country"),100),IdentityService.TEMPLATE,image,Db.now());db.notice(id,"Registration received. KGC approval and application-key issuance are pending.");return id;
 }
 private String clean(String s,int max){String v=s==null?"":s.trim();if(v.length()>max)throw new IllegalArgumentException("A profile field exceeds its allowed length.");return v;}
 @Transactional public void decide(Map<String,Object>actor,String id,boolean approve)throws Exception{
  requireRole(actor,"KGC");var user=db.one("select * from app_user where id=? for update",id);if(!Db.str(user,"role").equals("USER")||!Db.str(user,"approval").equals("PENDING"))throw new IllegalArgumentException("Only pending user accounts can be decided.");
  String key=approve?"APP-"+Db.id():null;String wrapped=approve?crypto.wrap(crypto.random(32),"identity:"+id):null;
  db.jdbc.update("update app_user set approval=?, key_id=?, wrapped_identity_key=?,issued_at=? where id=?",approve?"APPROVED":"REJECTED",key,wrapped,approve?Db.now():null,id);
  db.notice(id,approve?"KGC approved your account and issued application identity key "+masked(key)+". This is not a pairing-based private key.":"KGC rejected your registration. Contact the project administrator.");
 }
 public static String masked(String key){return key==null||key.isBlank()?"Not issued":key.substring(0,Math.min(8,key.length()))+"••••"+key.substring(Math.max(0,key.length()-4));}
 public static void requireRole(Map<String,Object>u,String role){if(!Db.str(u,"role").equals(role))throw new org.springframework.security.access.AccessDeniedException("This operation requires the "+role+" role.");}
 public static void approved(Map<String,Object>u){requireRole(u,"USER");if(!Db.str(u,"approval").equals("APPROVED"))throw new org.springframework.security.access.AccessDeniedException("KGC approval is required. Current status: "+Db.str(u,"approval"));}
}
