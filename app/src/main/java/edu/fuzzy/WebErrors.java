package edu.fuzzy;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import jakarta.servlet.http.HttpServletResponse;
@ControllerAdvice
public class WebErrors {
 private final Db db;private final org.springframework.core.env.Environment env;
 public WebErrors(Db db,org.springframework.core.env.Environment env){this.db=db;this.env=env;}
 @ExceptionHandler(Exception.class)public String handle(Exception e,Model m,HttpServletResponse response,java.security.Principal principal){
  m.addAttribute("demo",env.getProperty("fuzzy.demo",Boolean.class,false));m.addAttribute("database",Ui.database(String.join(",",env.getActiveProfiles())));if(principal!=null)m.addAttribute("me",db.user(principal.getName()));
  if(e instanceof org.springframework.web.servlet.resource.NoResourceFoundException){response.setStatus(404);m.addAttribute("errorMessage","That page or asset does not exist. Use the navigation to continue.");}
  else if(e instanceof AccessDeniedException){response.setStatus(403);m.addAttribute("errorMessage",e.getMessage());}
  else if(e instanceof IllegalArgumentException){response.setStatus(400);m.addAttribute("errorMessage",e.getMessage());}
  else if(e instanceof org.springframework.web.bind.ServletRequestBindingException || e instanceof org.springframework.web.method.annotation.MethodArgumentTypeMismatchException){response.setStatus(400);m.addAttribute("errorMessage","Required input is missing or has an invalid format.");}
  else if(e instanceof MaxUploadSizeExceededException){response.setStatus(413);m.addAttribute("errorMessage","Upload exceeds the 10 MiB file limit.");}
  else if(e instanceof org.springframework.dao.DataIntegrityViolationException){response.setStatus(409);m.addAttribute("errorMessage","A conflicting record already exists. Refresh the page and inspect its status.");}
  else{response.setStatus(500);m.addAttribute("errorMessage","The operation could not complete. No success was recorded. Please return to the dashboard and inspect the current state.");org.slf4j.LoggerFactory.getLogger(WebErrors.class).error("Operation failed: {}",e.getClass().getSimpleName());}
  return "error";
 }
}
