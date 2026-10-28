# Deployment verification evidence

Final verification at approximately23:42 IST: Render reports `Deploy succeeded | Live` for **1a237b7af042f3cca00c082af77120356958188f** (deploy dep-dbh3kiff3r2c73eqhvjg). After this fresh container deployment, `scripts/restart_check.py https://fuzzy-project-demo.onrender.com` passed **17 assertions**, including exact healthy-object decryption, stable wrapping keys, approval/key IDs, retained full PASS/FAIL, corrupt-download rejection, access decisions/notices and a new upload. Log: evidence/deployment/live-restart.log. This verifies real provider persistence across redeploy rather than only a local restart.

Corrected public login opened in Chrome and displays PostgreSQL / persistent SQL storage with valid punctuation. Genuine screenshot: evidence/deployment/live-login.jpg. Browser warning/error log query returned[]. HTTPS login200; /healthz UP; session cookie Secure, HttpOnly and SameSite=Strict; published local password absent. Full-history Gitleaks scan of five commits found no leaks after the exact reviewed false-positive exclusion. Local MySQL /healthz remained UP. Temporary SQL validation server stopped; local8080 backup remains running.

## Public Render deployment — 28 October 2026

Actual public HTTPS URL: https://fuzzy-project-demo.onrender.com/login. Initial deployed commit `6595dee1f8667fad9723da6babe6cbeacba80362`; final display correction `1a237b7af042f3cca00c082af77120356958188f`. Active server profiles: postgres,hosted; PostgreSQL 18 connection observed in provider startup logs. The initial UI incorrectly displayed H2; the corrected database/layout labels were rebuilt with all 24 JUnit methods passing and redeployed. The initial HTTP state file's H2 label is an observation of that UI defect, not the actual database.

Executed packaged builds: `.\app\mvnw.cmd -B -f app\pom.xml package -Dfuzzy.build.directory=<fresh absolute output directory>`. Build logs: evidence/deployment/render-build.log and render-fixed-build.log. 24 tests, zero failures/errors/skips. Render Docker's `mvn -B verify` also passed all 24 tests before packaging.

`scripts/http_acceptance.py http://127.0.0.1:8083`: 91 assertions passed using SQL object mode with H2 persistence. Restarted JAR with the same SQL database/master secret and a different scratch directory; `scripts/restart_check.py http://127.0.0.1:8083`: 17 assertions passed. Main MySQL backup on8080 remained available.

`scripts/http_acceptance.py https://fuzzy-project-demo.onrender.com`: **91 assertions passed**, with the private hosted seed loaded from ignored configuration, never printed. Actual registration with all profile fields/signature, pending restriction, KGC approval/key notification, OTP incorrect/reuse restrictions, fuzzy five/six/duplicate boundaries, binary exact-byte roundtrip, duplicate filenames, oversized/empty/path validation, ownership/role restrictions, sharing approval/rejection/revocation, backend full PASS, real SQL ciphertext corruption and new full FAIL, corrupt-download rejection, new-version recovery, retained failed history and navigation/assets were exercised. Log: evidence/deployment/live-http.log; JSON: evidence/deployment/live-render/http-acceptance.json.

Live binary fixture: 153600 bytes, SHA-256 `41dae6b28c30ab57faaf5c4707cb4bcda0dcff3057a394447eaad9a67d06ad82`. Full PASS audit `ca6e05ce-5638-4635-8cc6-86940dd18725`; new full FAIL audit `e18ae2cd-b55d-4e6b-807e-d1a0f14a6bec`; corrupted object `378387ef-cccb-4172-940f-7c2f03a8ba84`; healthy recovered object `ea55527e-6582-42c0-a9d8-85d39d7ed50a`. All records remain synthetic; no local personal data was migrated.

First public run stopped at the path-traversal assertion because Render's edge rejected it with403 rather than the application's400. Targeted rerun confirmed403 denial; the assertion now accepts either rejection status. The complete rerun passed91 checks; security protections were not weakened.

Actual Chrome browser checks: opened public login, private-password login to OTP, session-local demo inbox, OTP completion, protected Alice dashboard, exact eight-overlap acceptance, noisy six-overlap acceptance, wrong four-overlap rejection clearing the gate. Browser file selection was blocked by the extension's missing file-URL permission. Public uploads/downloads/sharing/auditing were therefore verified by real HTTPS automation, not claimed as browser-click verification. A complete browser-only walkthrough remains unverified. The native picker fallback did not resolve it. No phantom screenshots or mail delivery claims are made.

Render actual account: Hobby, No card on file, $0 current/projected; both resources Free. Database dashboard expiry27 November2026; web sleeps15 idle minutes. See DEPLOYMENT.md. New resources only; unrelated services left unchanged. Credentials are in the ignored private handoff file or authenticated Render Environment page.

Research limitations remain: synthetic matching, ordinary application keys, local simulated OTP email, logical roles, O(block-count) manifest/response costs; no biometric processing, bilinear pairings, threshold key derivation, erasure coding or research proof theorem. Load/security/expiry recovery tests are not claimed.


Historical pre-deployment evidence follows; current public evidence is above.

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
