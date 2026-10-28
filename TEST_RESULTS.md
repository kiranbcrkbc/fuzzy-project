# Executed test results

## Deployment continuation — 28 October 2026

The earlier evidence below remains historical. New readiness results are in LIVE_TEST_RESULTS.md: existing package91 HTTP assertions; updated source19 JUnit methods; updated packaged MySQL91 assertions and17 restart assertions. Added tests concern hosted credential guards and minimal health access. These counts are separate categories. No public hosting test is claimed. New raw records are under ignored evidence/deployment/. See that document for the retained EXPIRED challenge timing and the unverified hosting authentication/₹0 eligibility gate.

Date: **28 October 2026**, India Standard Time. Session began approximately **19:42 IST**. These are results of this new application, not copied source-document test claims. No “100% working” or complete-research-replication claim is made.

## Environment and artifact

- Windows 11; Microsoft OpenJDK **17.0.20.1**; Maven **3.9.9**; Spring Boot **3.5.16**; embedded Tomcat **10.1.55**.
- Primary database: actual local **MySQL 8.0.46**, new `fuzzy_demo` schema, restricted `fuzzy_app` user. Unrelated databases were not modified.
- Fallback: actual **file-backed H2**, isolated at `data/h2-demo/trusted/fuzzy.mv.db` with its own cloud/key directories. Unit/MVC tests use an independent in-memory H2 database.
- Final stable runnable artifact: `app/dist/fuzzy-auditing-1.0.0.jar`. Successful fresh-output build: `app/builds/20261028-202543/`. Maven reports are in its `surefire-reports/`; `evidence/latest-build-path.txt` records the location.
- Source inventory confirmed 139 original regular files, 15 DOCX, 10 Java, 35 JSP, 12-page paper, 11-slide old deck and 16-slide final deck. Originals preserved.

## Commands actually executed

| Command / action | Observed result |
|---|---|
| `java -version`, `javac -version`, `mvn -version`, service/listening-port checks | Java17/Maven available; MySQL80 running on 3306; 8080 initially free |
| Dedicated MySQL connection and schema/account creation | MySQL8.0.46 connection successful; only new fuzzy_demo account/schema grants configured |
| `mvn -B -q -DskipTests package` during initial vertical slice | Runnable application compiled and packaged |
| `mvn -B test` | Initial 14 tests passed, zero failures/errors |
| `.\build.ps1` using a fresh build output | Final **15 JUnit test methods passed**, zero failures/errors/skips; runnable Boot JAR packaged. Eight core tests and seven integration tests. |
| `.\start-demo.ps1 -Background` | Actual packaged JAR started on 127.0.0.1:8080 with MySQL; logs show datasource and Tomcat startup |
| `python scripts\http_acceptance.py` | **91 real HTTP assertions passed** against packaged MySQL application. Evidence: `evidence/http-acceptance.json` |
| `.\stop-demo.ps1` then `.\start-demo.ps1 -Background` | Actual process stopped/restarted without data reset |
| `python scripts\restart_check.py` | **17 restart assertions passed**: account/approval/key, decryption, old corruption rejection, PASS/FAIL history, requests/notices and nondestructive new upload |
| `.\start-demo.ps1 -Profile demo -Port 8081 -Background` | Packaged app started against isolated persistent H2, separately labelled |
| `$env:FUZZY_TEST_EVIDENCE_DIR="$PWD\evidence\h2"; python scripts\http_acceptance.py http://127.0.0.1:8081` | **91 real HTTP assertions passed** on file-backed H2; evidence under `evidence/h2/` |
| Stop/restart H2 on 8081; same evidence variable plus `python scripts\restart_check.py http://127.0.0.1:8081` | **17 restart assertions passed** for file-backed H2 |
| `app\mvnw.cmd -version` | Wrapper successfully resolved/executed Maven3.9.9 |
| Maven `wrapper:wrapper '-Dmaven=3.9.9'` | Wrapper scripts/config generated; quoted property fixed an initial PowerShell argument-parsing failure |
| `.\reset-demo.ps1` without -DemoOnly | Refused before changing data, as intended |
| Isolated JAR reset with `--fuzzy.reset-guard=WRONG` | Failed with explicit guard error; no primary demo data touched |
| Isolated JAR reset with demo profile, nonweb mode, `--fuzzy.data-root=./data/reset-safety --fuzzy.reset-demo=true --fuzzy.reset-guard=RESET_FUZZY_DEMO` | Successful guarded reset/reseed in separate H2 data root; context shut down normally. Primary MySQL/H2 review data was not reset. |

The 91-check HTTP script explicitly checks responses and downloads, not simply endpoint reachability. It creates synthetic accounts/files, including rejected/revoked shares and preserved failure histories. Its first execution incorrectly expected the uploaded signature PNG's exact encoded bytes after server normalization; that test expectation was corrected to validate the normalized PNG preview. File downloads, in contrast, are checked for exact original bytes.

## Automated coverage

`CoreLogicTest` covers exact/noisy/wrong identity, 5/6 boundary, duplicates and malformed/oversized samples; incorrect/expired/reused/locked OTP; AES-GCM binary roundtrip, AAD/wrapped-key binding and master-key restart; current-disk unchanged/corrupt/missing/truncated data; wrong audit/nonce/file/version/length/expiry; missing/extra/duplicate/invalid/mutated/null response blocks; secure sampled distinctness/full coverage.

`WorkflowIntegrationTest` covers registration/profile/pending/approval/rejection, duplicate email, unauthorized KGC decisions, sharing pending/approve/reject/revoke and wrong-owner denials, file roundtrip/distinct same-name objects, full audit PASS, replay protection, actual byte flip and stable baseline, FAIL and corrupt download rejection, immutable new-version recovery/history, missing ERROR/truncated FAIL/expired challenge, bad uploads/checksum guard, anonymous/CSRF/invalid login/OTP/session/logout and posted threshold/success flags.

Real MySQL and H2 HTTP tests additionally exercise the actual servlet server, multipart signature/file forms, all profile fields, recipient notices, persisted SQL transitions, ciphertext response serialization/deserialization, current-byte download authorization, oversized HTTP upload 413, stateful cookies/CSRF and local assets. This closes the gap between isolated test services and the packaged application, while remaining distinct from browser verification.

## Browser workflows actually exercised

Browsers: Codex in-app browser, followed by connected Chrome. All visits targeted the real local application at http://127.0.0.1:8080.

| Browser action | Observed result |
|---|---|
| Landing/login and active database label | Real rendered login, exact title, Local cloud simulator and MySQL label |
| Alice password, session-specific demo inbox and OTP | Reached full USER dashboard only after code verification |
| Exact/noisy/wrong identity presets | 8 ACCEPTED, 6 ACCEPTED, 4 REJECTED |
| Text upload/download | `research-notes.txt`, 156 original bytes, 184-byte encrypted envelope; download SHA-256 matched |
| Binary upload/download | `binary-sample.bin`, 204800 original bytes, 204828-byte envelope, four blocks; exact-byte match |
| Bob unauthorized download, catalogue request | Download denied; PENDING request shown |
| Alice owner approval; Bob fuzzy check/download | APPROVED; Bob's downloaded bytes matched the original; recipient object/version notice shown |
| Alice FULL audit → TPA challenge → Cloud response → TPA verify | VERIFIED_PASS, four of four blocks MATCH; nonce/version/coverage/expiry/timings shown |
| Cloud demo corruption | Actual ciphertext byte flipped in block0; event recorded |
| New FULL audit through all roles | VERIFIED_FAIL; block0 MISMATCH; other blocks MATCH; same expected hash retained |
| Alice download after corruption | Useful AES-GCM authentication error, no plaintext fallback |
| Recovery upload attempt in Chrome | File chooser setting blocked by extension file-URL permission; no fabricated upload success |

Browser text download SHA-256: `ab0fc2fa0b32ee3ec72d4b65ff0ad157ac8b0a73639a06822208e4df16e0330f` (full hashes can be recomputed from supplied fixture and downloaded file). Binary download SHA-256, including Bob's approved download: `8c6627e25bfbdef2bba5abc03123ea8e9b60d892f7f180a8b9b5079fb3233c54`.

Actual browser audit evidence:

- PASS: audit `f08de1a1-4509-4fd9-b416-65eaeee4d8fc`, file `b45717c5-ea67-4780-80a7-4288c64aab01` v1. Challenge **168.322 ms**, response **66.788 ms**, verify **97.768 ms**.
- FAIL after block0 flip: audit `4841d96a-cc3d-4736-9d40-f1c37e5e06d3`. Challenge **329.650 ms**, response **63.219 ms**, verify **85.285 ms**.
- Screenshots: `evidence/browser-audit-pass.png`, `evidence/browser-audit-fail.png`. These depict the running application, not reference screenshots.

## Failures found and fixes

1. References initially absent: retrieved both user-provided Drive files, verified archive extraction and slide counts. Connector transport failed, so public-file download was used.
2. Early browser visit raced server startup: connection refused; browser opened successfully after packaged server startup.
3. In-app JavaScript confirm stalled automation: replaced blocking native confirm with the explicitly labelled demo corruption control and explanatory warning. Re-tested in Chrome; actual tamper/FAIL succeeded. Role/demo/CSRF guards retained.
4. Error pages lost authenticated navigation: added role/database context to exception views. Re-tested corrupted-download page in Chrome; navigation and role remained visible.
5. Fuzzy form reset the displayed sample after POST: retained submitted sample; actual decision remained server-side.
6. Source reconciliation found legacy password confirmation: added/validated rpassword while preserving BCrypt storage and no duplicate plaintext column. Subsequent HTTP registration succeeded.
7. Chrome issued missing-resource requests logged as generic server errors: mapped missing resources to 404 and added a local favicon. Final server logs are inspected separately; no claim that a final browser console was inspected.
8. `mvn clean` failed twice on a generated OneDrive directory with `Everyone Deny DeleteSubdirectoriesAndFiles`. No ACL/security bypass attempted. Fresh timestamped output solved build/package and preserves repeatability.
9. Reset path checking needed canonical handling of relative H2 file URLs: normalized its database path and successfully tested guarded reset in an isolated data root.

## Incomplete / unverified

- Complete browser registration, pending restrictions, KGC approval/signature preview, sharing rejection/revocation, recovery and final restart walkthrough **were not all completed through UI**. They have real HTTP/JUnit evidence. Computer Use stopped because it could not confidently determine the current browser URL; no further UI control was attempted afterward.
- Final browser console/network-error inspection remains unverified. Server logs and actual HTTP resource/status checks are separate evidence.
- Actual SMTP delivery, Unix shell execution, double-click .bat execution, production deployment/container build and load/penetration tests are unverified.
- Pairings, paper algorithms, threshold sharing, fuzzy signatures, erasure coding, biometric extraction and security proof remain incomplete as detailed in LIMITATIONS.md / traceability.
- An ELAN WBF fingerprint sensor was detected, but no sample acquired or biometric accuracy claimed.
- Physical internet disconnection was not performed. JAR startup/local assets require no external service; Maven offline-package evidence and final resource checks are recorded in the final validation addendum below.

No ordinary startup or test removed previous primary audit evidence. Primary MySQL remains the intended review profile.

## Final validation addendum

- `mvn -o -B -DskipTests package '-Dfuzzy.build.directory=builds/offline-validation'` succeeded on 28 October at 20:27:51 IST using cached dependencies only. Tests were deliberately skipped for this additional offline packaging check; the separate final tested build passed all15 tests. No physical network isolation was claimed.
- The stable JAR SHA-256 is `973b24e205d747317ca425910f57cd0cc4a4361060f1964aa6948bbf445e83d8`.
- Final package launched through the documented background script. MySQL and H2 restart checks each passed again against that package. H2 was then stopped; primary MySQL remained listening on **127.0.0.1:8080**, managed PID **13464** at checkpoint.
- `evidence/final-validation.json` records18 additional successful assertions: Alice sign-in/OTP/fuzzy gate, actual original re-upload as binary v2, PNG byte roundtrip, authenticated DB health, CSS/JS/favicon200, unknown-resource404 and retained browser-recorded FAIL history.
- Alice's healthy recovery v2 object is `55757173-7e16-4458-a74d-c91a9f3fc191`; PNG object is `41920cac-6631-4c04-89ae-6d2ffb84713e`. Old corrupted v1/history retained.
- Final server stdout/stderr inspected: normal datasource/Tomcat startup; a SecureRandom initialization timing warning, no application ERROR in the final server log. Final browser console/network inspection remains unverified as stated above.

## Subsequent Chapter 8 browser capture run — 28 October 2026

All13 requested report workflows were executed through supported in-app browser/Playwright controls and captured in20 native-resolution PNGs. Registration/pending restriction and KGC approval, previously lacking browser evidence, now have actual browser captures. File chooser and browser download completed successfully in this run; no browser restriction was bypassed. A new Alice-owned 215677-byte synthetic report file was shared with Bob and downloaded with exact-byte equality and SHA-256 5857bc505a1f3b9403f1acc15163290bc24faed0d7b6edab0bbdf110fe562dea. Four-block FULL PASS and a NEW full FAIL after a real block0 flip were recorded, with unchanged expected digests; corrupted download rejected. Both results remain visible to Alice. Final browser warn/error query returned an empty list. No app-code change/rebuild/reset was needed.

See report-assets/chapter-8/SCREENSHOT_EVIDENCE.md and TEST_CASE_RESULTS.csv for18 executed report-case results and capture identifiers. All20 images visually inspected; conversion from browser JPEG toPNG verified identical decoded pixel bytes and native dimensions. FUZZY_PROJECT_Chapter_8_Screenshots.zip contains20 PNGs and support records; CRC/per-entry integrity checked. This capture task did not add browser recovery/restart testing or reproduce the research cryptography.
