package edu.fuzzy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.*;
@Component
public class Db {
 public final JdbcTemplate jdbc;
 public Db(JdbcTemplate jdbc){this.jdbc=jdbc;}
 public Map<String,Object> one(String sql,Object...args){List<Map<String,Object>>rows=jdbc.queryForList(sql,args);if(rows.isEmpty())throw new IllegalArgumentException("Record not found.");return rows.get(0);}
 public Map<String,Object> user(String email){return one("select * from app_user where email=?",email);}
 public static String str(Map<String,Object>m,String k){Object o=m.get(k);return o==null?"":o.toString();}
 public static long num(Map<String,Object>m,String k){return ((Number)m.get(k)).longValue();}
 public static String id(){return UUID.randomUUID().toString();}
 public static long now(){return System.currentTimeMillis();}
 public void notice(String recipient,String message){jdbc.update("insert into notification(id,recipient_id,message,created_at) values(?,?,?,?)",id(),recipient,message,now());}
}
