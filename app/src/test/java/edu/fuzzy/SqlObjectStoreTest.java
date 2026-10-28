package edu.fuzzy;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.access.AccessDeniedException;
import java.nio.file.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(properties={"spring.datasource.url=jdbc:h2:mem:sqlobjects;MODE=MySQL;DB_CLOSE_DELAY=-1","fuzzy.data-root=target/sql-test-runtime","fuzzy.object-store=sql","fuzzy.master-key-base64=AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=","fuzzy.seed-password=isolated-sql-fixture-password"})
@ActiveProfiles({"demo","hosted"})
class SqlObjectStoreTest {
 @Autowired Db db;@Autowired WorkflowService flow;@Autowired ObjectStore objects;@Autowired CryptoService crypto;
 @TempDir Path temp;
 Map<String,Object> user(String name){return db.user(name+"@demo.local");}
 String upload(byte[]bytes)throws Exception{return flow.upload(user("alice"),new MockMultipartFile("file","sql-object.bin","application/octet-stream",bytes),"");}
 String audit(String id)throws Exception{String a=flow.requestAudit(user("alice"),id,"FULL");flow.challenge(user("tpa"),a);flow.respond(user("cloud"),a);flow.verify(user("tpa"),a);return a;}
 @Test void actualSqlBytesPassThenFailAndHistoryStays()throws Exception{
  byte[]bytes=new byte[180000];new Random(17).nextBytes(bytes);String id=upload(bytes);String baseline=Db.str(flow.file(id),"manifest_json");
  assertArrayEquals(bytes,flow.download(user("alice"),id));assertFalse(Files.exists(crypto.object(id)));assertFalse(Files.exists(crypto.root.resolve("trusted/master.key")));
  String pass=audit(id);assertEquals("VERIFIED_PASS",flow.audit(user("alice"),pass).get("audit_state"));
  flow.tamper(user("cloud"),id,0);String fail=audit(id);assertEquals("VERIFIED_FAIL",flow.audit(user("alice"),fail).get("audit_state"));
  assertEquals(baseline,flow.file(id).get("manifest_json"));assertEquals("VERIFIED_PASS",flow.audit(user("alice"),pass).get("audit_state"));
  assertThrows(IllegalArgumentException.class,()->flow.download(user("alice"),id));assertThrows(IllegalArgumentException.class,()->flow.verify(user("tpa"),fail));
 }
 @Test void externalMasterRestoresFromDifferentScratchDirectory()throws Exception{
  byte[]bytes={0,1,13,10,-1,0,42};String id=upload(bytes);var f=flow.file(id);
  CryptoService restarted=new CryptoService(temp.resolve("new-container-scratch").toString(),Base64.getEncoder().encodeToString(new byte[32]));
  byte[]key=restarted.unwrap(Db.str(f,"wrapped_key"),"file:"+id);
  assertArrayEquals(bytes,restarted.decrypt(objects.read(id),key,id+":1"));assertFalse(Files.exists(restarted.root.resolve("trusted/master.key")));
 }
 @Test void sqlSharingStillRequiresOwnerApproval()throws Exception{
  byte[]bytes={9,0,-1};String id=upload(bytes);assertThrows(AccessDeniedException.class,()->flow.download(user("bob"),id));flow.requestAccess(user("bob"),id);
  String request=Db.str(db.one("select * from share_request where file_id=?",id),"id");assertThrows(AccessDeniedException.class,()->flow.decideShare(user("bob"),request,"APPROVED"));
  flow.decideShare(user("alice"),request,"APPROVED");assertArrayEquals(bytes,flow.download(user("bob"),id));assertThrows(AccessDeniedException.class,()->flow.download(user("cloud"),id));
 }
 @Test void missingAndTruncatedSqlObjectsNeverPass()throws Exception{
  String missing=upload(new byte[140000]);String a=flow.requestAudit(user("alice"),missing,"FULL");flow.challenge(user("tpa"),a);objects.delete(missing);flow.respond(user("cloud"),a);assertEquals("ERROR",flow.audit(user("alice"),a).get("audit_state"));
  String shortId=upload(new byte[140000]);db.jdbc.update("update cloud_object set ciphertext=? where id=?",new byte[3],shortId);String b=audit(shortId);assertEquals("VERIFIED_FAIL",flow.audit(user("alice"),b).get("audit_state"));assertThrows(IllegalArgumentException.class,()->flow.download(user("alice"),shortId));
 }
 @Test void sqlHostRefusesMissingMasterAndDuplicateObjects()throws Exception{
  CryptoService fileKey=new CryptoService(temp.resolve("file-key").toString());assertThrows(IllegalStateException.class,()->new ObjectStore(db,fileKey,"sql",true));
  String id=UUID.randomUUID().toString();objects.put(id,new byte[]{1});assertThrows(org.springframework.dao.DataIntegrityViolationException.class,()->objects.put(id,new byte[]{2}));assertArrayEquals(new byte[]{1},objects.read(id));
  assertThrows(IllegalStateException.class,()->new CryptoService(temp.resolve("bad-key").toString(),Base64.getEncoder().encodeToString(new byte[31])));
 }
}
