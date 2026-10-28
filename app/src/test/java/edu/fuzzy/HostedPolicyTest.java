package edu.fuzzy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class HostedPolicyTest {
 @Test void hostedCannotUsePublishedOrMissingPassword(){
  for(String value:new String[]{"","Demo@12345","short","REPLACE_WITH_PRIVATE_RANDOM_20_PLUS_CHARACTER_SECRET"})assertThrows(IllegalStateException.class,()->HostedPolicy.seedPassword(true,value));
  assertThrows(IllegalStateException.class,()->HostedPolicy.seedPassword(true,null));
  assertEquals("a-long-synthetic-test-secret",HostedPolicy.seedPassword(true,"a-long-synthetic-test-secret"));
 }
 @Test void localDefaultsRemainCompatible(){assertEquals("Demo@12345",HostedPolicy.seedPassword(false,null));}
}
