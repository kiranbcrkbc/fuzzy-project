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

## Final local package and isolated hosted profile

The final hosted-policy guard also rejects example placeholders. A fresh package passed all19 tests and replaced the stable local executable after retaining its predecessor at ignored `data/trusted/package-backups/fuzzy-auditing-before-hosted-readiness.jar`. Final JAR SHA-256: `086d6605fdcc8a52883bc852db800662ee0be7d1fa5eb08e63223f081260971c`. The primary MySQL server is again running on8080. Both17 persistence assertions after that upgrade and91 fresh HTTP assertions passed. No accounts, keys, objects or historical audits were reset.

An isolated `demo,hosted` package on8083 seeded a private random validation password from an ignored local file. **91 HTTP assertions and17 post-restart assertions passed on file-backed H2**, preserving data, master key, decryption and history. This test overrode secure-cookie/address settings only for loopback HTTP; it does NOT verify public HTTPS or provider volumes. The deployed hosted profile still defaults to secure cookies and0.0.0.0. The private test password is neither printed nor published.

Additional browser verification against `localhost:8082` observed password→OTP→protected dashboard, six-overlap ACCEPTED and four-overlap REJECTED clearing prior verification. Browser warning/error query returned an empty list. This was local browser evidence, not a hosted workflow.

GitHub source push was verified against remote main; the repository is private. The staged publication check excluded references, runtime data, environment secrets, master keys, database files, screenshots and report/build artifacts. Token/private-key pattern scan found no matches. Known synthetic local/test passwords remain visibly marked fixtures; hosted mode rejects published local defaults.

## Recent expired challenge

Audit `c2823e30-f4df-439d-a55e-b3808bc4cc3b` remains unchanged. Its challenge was issued at **22:13:36.671 IST** and expired at **22:23:36.671 IST**, a 600000 ms window. State is EXPIRED; detail is “Challenge expired before response.” This matches expected timeout handling. The original record does not retain the exact rejected-click time, so none is invented. Current-byte generation checks server time first. Unit/integration tests separately reject expiry, wrong binding and replay. No expiry or nonce protection was disabled. The read-only SQL evidence is saved privately in `evidence/deployment/expired-challenge.txt`.

## Live checks pending

Hosting authentication and actual ₹0 eligibility remain unverified. No resources, domain or hosted dataset have been provisioned. Public HTTPS/browser workflows, remote container build, host logs, HTTPS cookies/proxy, durable volumes and hosted redeployment persistence are **UNVERIFIED**. A local PASS is not a live PASS.

Docker is unavailable locally; no container build execution is claimed. The application remains an educational prototype with research cryptography and biometric processing incomplete. Existing report evidence is historical and was not silently rewritten.
