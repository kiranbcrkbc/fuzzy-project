package edu.fuzzy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.containsString;
@SpringBootTest(properties={"spring.datasource.url=jdbc:h2:mem:hosted;MODE=MySQL;DB_CLOSE_DELAY=-1","fuzzy.data-root=target/hosted-test-runtime","fuzzy.seed-password=isolated-hosted-test-password"})
@ActiveProfiles({"demo","hosted"}) @AutoConfigureMockMvc
class HostedProfileTest {
 @Autowired MockMvc mvc; @Autowired Db db; @Autowired PasswordEncoder encoder;
 @Test void hostedLoginHidesDefaultAndRejectsPublishedPassword()throws Exception{
  mvc.perform(get("/login")).andExpect(status().isOk()).andExpect(content().string(not(containsString("Demo@12345"))));
  mvc.perform(post("/login").with(csrf()).param("email","kgc@demo.local").param("password","Demo@12345")).andExpect(redirectedUrl("/login"));
  mvc.perform(post("/login").with(csrf()).param("email","kgc@demo.local").param("password","isolated-hosted-test-password")).andExpect(redirectedUrl("/otp"));
  for(String name:new String[]{"alice","bob","kgc","tpa","cloud"})assertFalse(encoder.matches("Demo@12345",Db.str(db.user(name+"@demo.local"),"password_hash")));
 }
 @Test void minimalHealthProbeWorksWithoutAuthentication()throws Exception{
  mvc.perform(get("/healthz")).andExpect(status().isOk()).andExpect(content().json("{\"status\":\"UP\"}"));
  mvc.perform(get("/health")).andExpect(redirectedUrl("/login"));
 }
}
