package edu.fuzzy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class FuzzyApplication { public static void main(String[] args) { var context=SpringApplication.run(FuzzyApplication.class,args);if(context.getEnvironment().getProperty("fuzzy.reset-demo",Boolean.class,false))context.close(); } }
