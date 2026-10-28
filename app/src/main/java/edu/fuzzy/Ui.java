package edu.fuzzy;
import org.springframework.stereotype.Component;
import java.time.*;
import java.time.format.DateTimeFormatter;
@Component("ui") public class Ui {
 public String when(Object o){if(o==null)return "—";return Instant.ofEpochMilli(((Number)o).longValue()).atZone(ZoneId.of("Asia/Kolkata")).format(DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm:ss 'IST'"));}
 public String ms(Object o){return o==null?"—":String.format(java.util.Locale.ROOT,"%.3f",((Number)o).doubleValue());}
 public String bytes(Object o){return String.format(java.util.Locale.ROOT,"%,d bytes",((Number)o).longValue());}
 public String key(Object o){return AccountService.masked(o==null?null:o.toString());}
}
