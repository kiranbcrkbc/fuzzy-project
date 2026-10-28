package edu.fuzzy;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
@Service
public class OtpService {
 public static class State {public final String email,code;public final long expires;public int attempts;public boolean used;State(String email,String code,long expires){this.email=email;this.code=code;this.expires=expires;}}
 public State issue(String email,long now){return new State(email,String.format("%06d",new SecureRandom().nextInt(1000000)),now+180000);}
 public void verify(State state,String entered,long now){
  if(state==null||state.used||now>state.expires||state.attempts>=5)throw new IllegalArgumentException("OTP expired, already used or locked. Please sign in again.");
  state.attempts++;
  if(entered==null||!MessageDigest.isEqual(state.code.getBytes(StandardCharsets.UTF_8),entered.getBytes(StandardCharsets.UTF_8)))throw new IllegalArgumentException("Incorrect OTP. Five attempts maximum.");
  state.used=true;
 }
}
