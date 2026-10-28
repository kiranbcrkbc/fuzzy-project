# Local review checkpoint

**Deployment continuation, 28 October 2026:** The local package has been updated and reverified while preserving data. Current JAR SHA-256 is `086d6605fdcc8a52883bc852db800662ee0be7d1fa5eb08e63223f081260971c`; its previous version is retained privately for rollback. Latest source has19 passing tests; MySQL and an isolated hosted-profile H2 run each passed91 HTTP and17 restart assertions. GitHub source is pushed privately. Hosting is authorized only at₹0 and remains blocked on authentication/account-plan verification, not general approval. See PROJECT_LINKS.md / LIVE_TEST_RESULTS.md. Earlier dates/counts below are historical evidence.

**Local application available. Deployment has not started.**

Open **http://127.0.0.1:8080/login**. The final packaged Java application is running on loopback against the dedicated **MySQL 8.0.46 / fuzzy_demo** database. The separately tested H2 fallback on 8081 has been stopped to avoid confusion.

## Demo credentials

All five use **`Demo@12345`**:

| Role | Email |
|---|---|
| Alice / file owner | alice@demo.local |
| Bob / requester | bob@demo.local |
| KGC | kgc@demo.local |
| TPA | tpa@demo.local |
| Cloud | cloud@demo.local |

Complete OTP using the link to **your session's simulated email inbox**. Sign out between roles; ordinary browser tabs share cookies. Use synthetic data only.

## Recommended review

1. Sign in as Alice, complete OTP, open About / Method and confirm MySQL / Local cloud simulator labels.
2. Identity check: Exact → ACCEPTED; Wrong → REJECTED; Noisy → ACCEPTED. Finish with a successful check before file operations.
3. Files: a fresh, healthy **binary-sample.bin v2** is ready (object `55757173-7e16-4458-a74d-c91a9f3fc191`), plus `synthetic-diagram.png`. Download v2 and compare with `demo-samples/binary-sample.bin`. Version1 was intentionally corrupted and should fail authenticated download.
4. Review audit history: browser-observed PASS `f08de1a1-4509-4fd9-b416-65eaeee4d8fc`; browser-observed FAIL `4841d96a-cc3d-4736-9d40-f1c37e5e06d3`. Block0 differs while trusted expected hash remains the same.
5. For a fresh live demonstration, upload a new sample or audit the healthy v2. Alice requests FULL → TPA creates challenge → Cloud reads/responds → TPA verifies. Then corrupt the **same new object once** as Cloud and repeat FULL to show FAIL.
6. Bob can request access to the healthy v2; Alice approves from Access requests; Bob's own successful identity check is still required to download. Existing Bob approval for corrupted v1 does not grant v2 access.
7. Register a unique synthetic user, preview the supplied synthetic signature and inspect PENDING restrictions. KGC reviews/approves and issues an ordinary application key; the recipient sees a notice. See DEMO_SCRIPT.md for the full 7–10 minute sequence.

Do not reset data during review. Prior PASS/FAIL and access-decision history is deliberately retained.

## Restart and build

From `C:\Users\a\OneDrive\Desktop\FUZZY_PROJECT`:

```powershell
.\stop-demo.ps1
.\start-demo.ps1 -Background
```

For a console run/double click, `start-demo.bat` is provided. To rebuild, stop first and run `.\build.ps1`. Runnable JAR: **`app\dist\fuzzy-auditing-1.0.0.jar`**. Java17 is required; no internet or IDE is required to start the existing package.

Logs: `data\logs\server.log`, `data\logs\server-error.log`. Protected local DB config: `data\trusted\mysql-env.ps1` (do not share). Runtime data is ignored by git. `README.md` contains H2, Unix, configuration, backup and guarded-reset instructions.

## What passed

- Final fresh-output Maven build/package: **15 tests, zero failures/errors/skips**.
- **91 real HTTP assertions on MySQL**, plus **91 on persistent H2**.
- **17 restart assertions per profile**, including persisted approvals/keys, exact decrypted bytes, sharing decisions/notices and both audit outcomes.
- Browser-tested login/OTP, exact/noisy/wrong matching, text/binary storage, denial/owner-approved sharing, full PASS, actual stored-byte corruption, full FAIL and corrupt-download rejection.
- Final package recovery/PNG byte round trips, database health and local CSS/JS/favicon checks.
- Maven offline packaging from cached dependencies; isolated reset guard/reseed tests; documented PowerShell startup/stop commands.

See `TEST_RESULTS.md`, `evidence/*.json`, and successful build's `surefire-reports/` for actual evidence. Physical internet disconnection, .bat double-click/Unix execution and production/container hosting were not separately verified.

## Incomplete, simulated or blocked

- **Exact research replication is incomplete:** paper Setup/Extract/MetadataGen/Challenge/Response/Verify mathematics, pairings, threshold sharing, fuzzy signatures, erasure coding, compact/public proofs and soundness theorem are not implemented. The educational workflow must not be presented as those cryptographic results.
- **Identity and mail simulation:** attributes are synthetic; signature is a preview; OTP/notices use a recipient/session-local simulated inbox. SMTP adapter is untested. No real email was sent.
- **Sensor:** ELAN WBF sensor exists, but no verified biometric acquisition/feature pipeline or interactive sample was integrated. No recognition accuracy is claimed.
- **Browser verification is partial:** registration/KGC/recovery and final console/network walkthrough were not all completed in-browser after file-upload permission and Computer Use URL-confidence tooling stopped UI control. Their backend flows passed real HTTP/integration tests. No further UI actions were attempted after the stop.
- Deployment templates are prepared, unexecuted and untested in production. Deployment requires explicit approval after this review.

All **75 traceability entries** and individual original-document/module mappings are in `REQUIREMENTS_TRACEABILITY.md` / `SOURCE_RECONCILIATION.md`. Consequential decisions are in `DECISIONS.md`; research limitations, presentation wording and viva preparation have separate Markdown files.

## Browser proof of work

![Actual full audit after deliberate block0 corruption](C:/Users/a/OneDrive/Desktop/FUZZY_PROJECT/evidence/browser-audit-fail.png)

**The local build is ready for your review. Deployment has not started.**
