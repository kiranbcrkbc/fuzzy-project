package edu.fuzzy;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.web.context.*;
@Configuration
public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean UserDetailsService users(Db db){return email->{try{var u=db.user(email.toLowerCase().trim());return User.withUsername(Db.str(u,"email")).password(Db.str(u,"password_hash")).roles(Db.str(u,"role")).build();}catch(Exception e){throw new UsernameNotFoundException("Invalid credentials");}};}
 @Bean AuthenticationManager authenticationManager(UserDetailsService users,PasswordEncoder encoder){DaoAuthenticationProvider p=new DaoAuthenticationProvider(users);p.setPasswordEncoder(encoder);return new ProviderManager(p);}
 @Bean SecurityContextRepository securityContextRepository(){return new HttpSessionSecurityContextRepository();}
 @Bean @org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication SecurityFilterChain chain(HttpSecurity http,SecurityContextRepository repository) throws Exception {
  http.authorizeHttpRequests(a->a.requestMatchers("/login","/register","/otp","/demo-inbox","/style.css","/app.js","/error","/healthz").permitAll().requestMatchers("/kgc/**").hasRole("KGC").requestMatchers("/tpa/**").hasRole("TPA").requestMatchers("/cloud/**").hasRole("CLOUD").requestMatchers("/user/**").hasRole("USER").anyRequest().authenticated())
   .securityContext(s->s.securityContextRepository(repository))
   .exceptionHandling(e->e.authenticationEntryPoint((req,res,ex)->res.sendRedirect("/login")))
   .logout(l->l.logoutUrl("/logout").logoutSuccessUrl("/login?logout").invalidateHttpSession(true).clearAuthentication(true))
   .headers(h->h.contentSecurityPolicy(c->c.policyDirectives("default-src 'self'; img-src 'self' data: blob:; script-src 'self'; style-src 'self'; frame-ancestors 'none'; base-uri 'self'; form-action 'self'")));
  return http.build();
 }
}
