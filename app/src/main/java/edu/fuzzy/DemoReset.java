package edu.fuzzy;
import org.springframework.stereotype.Component;
import org.springframework.boot.*;
import org.springframework.core.annotation.Order;
import org.springframework.beans.factory.annotation.Value;
import java.nio.file.*;
@Component @Order(-100)
public class DemoReset implements ApplicationRunner {
 @Value("${fuzzy.hosted:false}") private boolean hosted;
 private final Db db;private final CryptoService crypto;private final boolean enabled,demo;private final String guard;
 public DemoReset(Db db,CryptoService crypto,@Value("${fuzzy.reset-demo:false}")boolean enabled,@Value("${fuzzy.demo}")boolean demo,@Value("${fuzzy.reset-guard:}")String guard){this.db=db;this.crypto=crypto;this.enabled=enabled;this.demo=demo;this.guard=guard;}
 public void run(ApplicationArguments args)throws Exception{
  if(!enabled)return;if(hosted)throw new IllegalStateException("Reset is disabled for hosted deployments.");if(!demo||!guard.equals("RESET_FUZZY_DEMO"))throw new IllegalStateException("Reset requires demo mode and the explicit demo-only guard.");
  try(var connection=db.jdbc.getDataSource().getConnection()){
   String url=connection.getMetaData().getURL();if(url.startsWith("jdbc:mysql:")&&!"fuzzy_demo".equals(connection.getCatalog()))throw new IllegalStateException("Reset is restricted to the dedicated fuzzy_demo database.");
   if(url.startsWith("jdbc:h2:")){if(!url.startsWith("jdbc:h2:file:")||!Path.of(url.substring("jdbc:h2:file:".length()).split(";")[0]).toAbsolutePath().normalize().equals(crypto.root.resolve("trusted/fuzzy")))throw new IllegalStateException("Reset is restricted to this data root's trusted/fuzzy database.");}
  }
  for(String table:new String[]{"tamper_event","notification","audit_record","share_request","stored_file","app_user"})db.jdbc.update("delete from "+table);
  try(var paths=Files.list(crypto.root.resolve("cloud"))){for(Path path:paths.toList())if(path.getFileName().toString().matches("[0-9a-fA-F-]{36}\\.bin")&&path.normalize().startsWith(crypto.root.resolve("cloud")))Files.delete(path);}
  System.out.println("Dedicated demo records and UUID ciphertext objects reset. Master key and local credentials retained. Demo accounts will be reseeded.");
 }
}
