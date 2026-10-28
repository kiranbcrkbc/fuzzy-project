package edu.fuzzy;
import org.springframework.stereotype.Component;
import java.time.*;
import java.time.format.DateTimeFormatter;
@Component("ui") public class Ui {
 @org.springframework.beans.factory.annotation.Value("${fuzzy.object-store:disk}")private String objectStore;
 public static String database(String profiles){return profiles.contains("postgres")?"PostgreSQL · persistent hosted demo":profiles.contains("mysql")?"MySQL · fuzzy_demo":"H2 · persistent demo";}
 public String storage(){return "sql".equals(objectStore)?"Cloud simulator · persistent SQL ciphertext":"Local cloud simulator";}
 public String footer(){return "Educational prototype · Synthetic identity · "+("sql".equals(objectStore)?"Persistent SQL storage":"Local storage");}
 public String when(Object o){if(o==null)return "—";return Instant.ofEpochMilli(((Number)o).longValue()).atZone(ZoneId.of("Asia/Kolkata")).format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm:ss 'IST'"));}
 public String ms(Object o){return o==null?"—":String.format(java.util.Locale.ROOT,"%.3f",((Number)o).doubleValue());}
 public String bytes(Object o){return String.format(java.util.Locale.ROOT,"%,d bytes",((Number)o).longValue());}
 public String key(Object o){return AccountService.masked(o==null?null:o.toString());}
}
