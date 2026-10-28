# Fuzzy Identity-Based Data Integrity Auditing for Reliable Cloud Storage Systems

**28 October deployment continuation:** GitHub publishing and complete deployment are authorized, with an overriding **₹0 total budget**, no payment method/paid upgrades/custom domain. See [PROJECT_LINKS.md](PROJECT_LINKS.md), [DEPLOYMENT.md](DEPLOYMENT.md) and [LIVE_TEST_RESULTS.md](LIVE_TEST_RESULTS.md) for current truth. The private source repository exists; hosting authentication/account eligibility and a live URL remain pending. Earlier local checkpoint statements are historical. The local MySQL application remains the backup.

Locally runnable educational final-year project: Java 17, Spring Boot 3.5.16, Spring Security, Thymeleaf, JDBC, MySQL and a persistent H2 fallback. Four roles perform real backend operations. This is **not full replication of the paper's fuzzy identity cryptography**. Read LIMITATIONS.md before presenting its security properties.

## Start on this computer

Open PowerShell in `C:\Users\a\OneDrive\Desktop\FUZZY_PROJECT`:

```powershell
.\start-demo.ps1 -Background
```

Open **http://127.0.0.1:8080/login**. The default on this computer is **MySQL**, using the dedicated `fuzzy_demo` database and a restricted `fuzzy_app` account. Local database credentials are already saved in ignored `data\trusted\mysql-env.ps1`; do not share that file. Other existing databases were not modified.

You can also double-click `start-demo.bat` for a console run. The console must remain open; Ctrl+C stops it. No internet is required to run the packaged JAR or local UI assets.

| Account | Email | Password |
|---|---|---|
| Alice / owner | alice@demo.local | Demo@12345 |
| Bob / requester | bob@demo.local | Demo@12345 |
| KGC administrator | kgc@demo.local | Demo@12345 |
| Third-party auditor | tpa@demo.local | Demo@12345 |
| Cloud operator | cloud@demo.local | Demo@12345 |

All accounts require OTP. On the OTP page, open **your session's demo inbox**, copy the displayed six-digit code, and return to verify it. This is **simulated email**, restricted to the initiating browser session. It is not external mail delivery. Use synthetic accounts and files only.

Only one account can be active per browser session. Sign out between roles. Separate Chrome profiles/incognito windows can isolate sessions; ordinary tabs in the same browser share cookies.

## Stop and restart

```powershell
.\stop-demo.ps1
.\start-demo.ps1 -Background
```

Background logs: `data\logs\server.log`, `data\logs\server-error.log`. PID: `data\server.pid`. The stop script verifies that the PID belongs to this project's JAR. If the app was launched in a console, use Ctrl+C there instead. Restart invalidates browser sessions; sign in and complete OTP/fuzzy verification again. Persistent accounts, grants, files and audit history remain.

## Build and tests

Java 17+ and Maven 3.9.9 are installed on this machine. Stop the server before rebuilding:

```powershell
.\stop-demo.ps1
.\build.ps1
.\start-demo.ps1 -Background
python scripts\http_acceptance.py
```

`build.ps1` compiles and tests in a **fresh** `app\builds\<timestamp>` directory, then copies the runnable package to **`app\dist\fuzzy-auditing-1.0.0.jar`**. The fresh output avoids an existing OneDrive delete-deny ACL that prevented `mvn clean`; the ACL was not changed. Test reports are in that build directory's `surefire-reports/`; `evidence\latest-build-path.txt` identifies the last successful build. Maven wrapper scripts/config are provided under `app/` if Maven is absent. Initial dependency/wrapper downloads require internet; subsequent JAR startup does not.

Ordinary Maven build, where filesystem deletion works:

```powershell
cd app
.\mvnw.cmd package
```

That Maven default creates `app\target\fuzzy-auditing-1.0.0.jar`; root startup scripts use the stable `app\dist` copy produced by `build.ps1`.

The HTTP acceptance script creates uniquely named synthetic users/files and retains PASS/FAIL evidence. It is a backend HTTP test, not a substitute claim of browser automation. After restarting, run `python scripts\restart_check.py` to verify the last HTTP test's persisted state.

## H2 fallback and Unix

```powershell
.\start-demo.ps1 -Profile demo -Background
```

This uses **file-backed H2** at `data\h2-demo\trusted\fuzzy.mv.db`. H2 and MySQL maintain separate records, even though their demo accounts use the same credentials. The scripts isolate H2 objects/keys under data/h2-demo/ to protect the MySQL demonstration. Keep their object/key store backups together with their database. Do not switch profiles during a live demonstration and assume their file inventories are the same.

Unix after building: `sh ./start-demo.sh`. It defaults to persistent H2. For MySQL, set `FUZZY_PROFILE=mysql`, `DB_URL`, `DB_USER`, `DB_PASSWORD` first. Local startup needs no Docker or IDE.

Direct packaged run from the project root:

```powershell
. .\data\trusted\mysql-env.ps1
$env:FUZZY_DATA_ROOT="$PWD\data"
$env:FUZZY_DEMO='true'
java -jar app\dist\fuzzy-auditing-1.0.0.jar --spring.profiles.active=mysql
```

## Configuration and persistence

- `FUZZY_DATA_ROOT`: trusted root, defaults to `./data` when running the JAR directly. Scripts resolve it absolutely so paths with spaces/OneDrive work.
- `data/cloud/<UUID>.bin`: ciphertext envelopes only; nonce followed by authenticated ciphertext/tag. No user-controlled storage paths.
- `data/trusted/master.key`: local 256-bit wrapping key. Back up securely; losing it makes existing wrapped file keys unusable. Do not replace it during recovery.
- MySQL trusted tables hold wrapped per-file keys, immutable manifests, users, grants, notices and audit evidence. Cloud/TPA web routes never expose key material.
- `data/trusted/mysql-env.ps1`: ignored application database configuration. Root/legacy external credentials are not used by the running app.
- `PORT` or `-Port 8081`: select another port. `SERVER_ADDRESS` defaults to loopback. Do not expose this educational build publicly without deployment review.
- Upload cap: **10 MiB**, nonempty ordinary binary files. Signature preview: PNG/JPEG, maximum 512 KiB and 2000 × 2000 pixels; decoded and normalized to PNG.
- OTP: 3 minutes, one use, 5 attempts. Fuzzy check: 10 minutes, bound to current user/session. A failed fuzzy attempt clears prior acceptance.
- SMTP adapter: set `FUZZY_SMTP=true`, `SPRING_MAIL_HOST`, `SPRING_MAIL_PORT`, `SPRING_MAIL_USERNAME`, `SPRING_MAIL_PASSWORD`, and appropriate JavaMail SMTP authentication/STARTTLS properties. Real SMTP is untested. Persisted notices remain local application messages; no raw AES key is ever sent.

## Demo reset and backups

Normal startup never clears data. Prefer retaining the current evidence and using new uploads. To deliberately clear the **dedicated** demo records and UUID ciphertext objects:

```powershell
.\stop-demo.ps1
# Back up data/ and the fuzzy_demo SQL database first.
.\reset-demo.ps1 -DemoOnly -Profile mysql
.\start-demo.ps1 -Background
```

The reset requires the explicit `-DemoOnly` guard and refuses to run while this application is detected. MySQL reset is limited to `fuzzy_demo`; H2 reset is limited to the selected data root. It retains master-key/config files and reseeds demo accounts. It intentionally removes demo history; do not run it during review. Never import the legacy destructive SQL dump into another database.

For MySQL backups, use a logical `mysqldump --no-tablespaces --single-transaction` of `fuzzy_demo` with the dedicated account through a protected option file; do not place passwords in shared commands. Back up the matching `data/cloud` objects and `data/trusted/master.key` while uploads are stopped. Restoring one without the others is insufficient.

## Troubleshooting

- Database connection failure: confirm MySQL80 is running and local ignored config exists. Use the explicitly labelled H2 profile if necessary; it is a separate demo database.
- Port in use: stop the previous server or select `-Port 8081`. Do not stop unrelated processes.
- Download blocked after corruption: this is expected authentication failure. Re-upload original sample bytes from the file detail page as a **new version**. Failed history is preserved; verification does not repair storage.
- Access denied: inspect KGC approval, the current role, ownership, sharing decision and recent fuzzy-check status.
- OTP expiry/reuse/lockout: start a fresh sign-in. A code from another browser session will not work.
- Maven clean failure in this workspace: use `build.ps1` fresh-output build rather than removing or changing filesystem security controls.

Use REVIEW_READY.md for the current checkpoint, DEMO_SCRIPT.md for the walkthrough, TEST_RESULTS.md for executed evidence, and REQUIREMENTS_TRACEABILITY.md / SOURCE_RECONCILIATION.md for scope coverage. Deployment has not started.
