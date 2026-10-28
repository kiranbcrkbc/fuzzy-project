package edu.fuzzy;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Map;
@RestController
public class ReadinessController {
 private final Db db; private final CryptoService crypto;
 public ReadinessController(Db db,CryptoService crypto){this.db=db;this.crypto=crypto;}
 @GetMapping("/healthz") public ResponseEntity<Map<String,String>> health(){
  try {db.jdbc.queryForObject("select 1",Integer.class);
   if(!crypto.masterReady())throw new IllegalStateException();
   return ResponseEntity.ok(Map.of("status","UP"));
  }catch(Exception e){return ResponseEntity.status(503).body(Map.of("status","DOWN"));}
 }
}
