# Deployment verification evidence

28 October 2026, IST. **No live HTTPS deployment exists yet.** All checks below are local unless a later section names an actual public URL and deployed commit.

## Executed checks

- Original packaged app on MySQL/8080: all 91 HTTP assertions passed again. Evidence: ignored `evidence/deployment/baseline-mysql/`.
- Fresh unchanged-source package: all 15 existing tests passed before hosting changes.
- Updated source package: **19 tests passed, zero failures/errors/skips**. These are the 15 existing methods, two hosted-policy and two hosted-profile methods. Build output: `evidence/deployment/final-build.log`; directory recorded in `final-build-path.txt`.
- Updated executable JAR on MySQL/8082: all **91 HTTP assertions passed**. Primary 8080 stayed available. Tests exercise registration/approval/OTP, fuzzy boundaries, encrypted binary roundtrip, sharing approval/denial/rejection/revocation, full PASS, actual byte corruption/new FAIL, corrupt download and replayed-transition denial. Evidence: `evidence/deployment/updated-mysql/`.
- Hosted-profile checks reject the published local password, hide it from login, verify private seed hashes and retain OTP authentication. Minimal anonymous readiness works; detailed health stays protected.

Counts describe separate test categories and are not added into a total of unique tests.

The updated MySQL package was then stopped and restarted on8082 without resetting data. `scripts/restart_check.py http://127.0.0.1:8082` passed17 assertions: approval/key state, exact decrypted bytes, retained PASS/FAIL, rejected corrupted download, grants/rejections/revocation/notices and a nondestructive new upload.

## Recent expired challenge

Audit `c2823e30-f4df-439d-a55e-b3808bc4cc3b` remains unchanged. Its challenge was issued at **22:13:36.671 IST** and expired at **22:23:36.671 IST**, a 600000 ms window. State is EXPIRED; detail is “Challenge expired before response.” This matches expected timeout handling. The original record does not retain the exact rejected-click time, so none is invented. Current-byte generation checks server time first. Unit/integration tests separately reject expiry, wrong binding and replay. No expiry or nonce protection was disabled. The read-only SQL evidence is saved privately in `evidence/deployment/expired-challenge.txt`.

## Live checks pending

Hosting authentication and actual ₹0 eligibility remain unverified. No resources, domain or hosted dataset have been provisioned. Public HTTPS/browser workflows, remote container build, host logs, HTTPS cookies/proxy, durable volumes and hosted redeployment persistence are **UNVERIFIED**. A local PASS is not a live PASS.

Docker is unavailable locally; no container build execution is claimed. The application remains an educational prototype with research cryptography and biometric processing incomplete. Existing report evidence is historical and was not silently rewritten.
