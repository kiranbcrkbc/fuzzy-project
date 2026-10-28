package edu.fuzzy;
import java.util.*;
import org.springframework.stereotype.Service;
@Service
public class IdentityService {
 public static final String TEMPLATE="11,22,33,44,55,66,77,88";
 public static final int THRESHOLD=6;
 public Set<Integer> parse(String text) {
  if(text==null || text.isBlank() || text.length()>500) throw new IllegalArgumentException("Enter 1–32 synthetic integer attributes.");
  String[] parts=text.trim().split("[\\s,]+",-1);
  if(parts.length>32) throw new IllegalArgumentException("At most 32 attributes are allowed.");
  Set<Integer> result=new HashSet<>();
  for(String p:parts) { if(!p.matches("[0-9]{1,6}")) throw new IllegalArgumentException("Attributes must be integers from 0 to 999999, separated by commas."); result.add(Integer.valueOf(p)); }
  return result;
 }
 public Match match(String template,String sample) { Set<Integer> s=parse(sample); s.retainAll(parse(template)); return new Match(s.size(),THRESHOLD,s.size()>=THRESHOLD); }
 public record Match(int overlap,int threshold,boolean accepted) {}
}
