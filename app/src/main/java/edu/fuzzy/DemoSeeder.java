package edu.fuzzy;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
@Component
public class DemoSeeder implements ApplicationRunner {
 private final Db db;private final PasswordEncoder encoder;private final CryptoService crypto;private final boolean demo;
 public DemoSeeder(Db db,PasswordEncoder encoder,CryptoService crypto,@Value("${fuzzy.demo}")boolean demo){this.db=db;this.encoder=encoder;this.crypto=crypto;this.demo=demo;}
 @Value("${fuzzy.hosted:false}") private boolean hosted;
 @Value("${fuzzy.seed-password:}") private String suppliedPassword;
 public void run(ApplicationArguments args)throws Exception{if(!demo)return;String password=HostedPolicy.seedPassword(hosted,suppliedPassword);for(String[]a:new String[][]{{"alice","Alice","USER"},{"bob","Bob","USER"},{"kgc","KGC Administrator","KGC"},{"tpa","Third-Party Auditor","TPA"},{"cloud","Cloud Operator","CLOUD"}}){String email=a[0]+"@demo.local";if(db.jdbc.queryForObject("select count(*) from app_user where email=?",Integer.class,email)>0){if(hosted&&!encoder.matches(password,Db.str(db.user(email),"password_hash")))throw new IllegalStateException("Hosted seed account differs from configured credentials; preserve data and use a reviewed credential rotation.");continue;}String id=Db.id(),key="APP-"+Db.id();db.jdbc.update("insert into app_user(id,email,display_name,password_hash,role,approval,attributes,key_id,wrapped_identity_key,issued_at,created_at) values(?,?,?,?,?,?,?,?,?,?,?)",id,email,a[1],encoder.encode(password),a[2],"APPROVED",IdentityService.TEMPLATE,a[2].equals("USER")?key:null,a[2].equals("USER")?crypto.wrap(crypto.random(32),"identity:"+id):null,Db.now(),Db.now());db.notice(id,"Welcome to the educational demonstration. Local inbox messages simulate email.");}}
}
