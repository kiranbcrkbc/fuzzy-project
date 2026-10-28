package edu.fuzzy;

/** Fail-closed configuration for the externally hosted synthetic demonstration. */
public final class HostedPolicy {
 private HostedPolicy() {}
 public static String seedPassword(boolean hosted, String supplied) {
  if (hosted && (supplied == null || supplied.length() < 20 || supplied.equals("Demo@12345") || supplied.contains("REPLACE_") || supplied.contains("CHANGE_")))
   throw new IllegalStateException("Hosted demo requires a private FUZZY_SEED_PASSWORD of at least 20 characters.");
  return supplied == null || supplied.isBlank() ? "Demo@12345" : supplied;
 }
}
