# Implementation decisions

- Start: 2026-10-28 19:42 IST; 180-minute target. Continue autonomously per additional user instruction, without delegation.
- Java 17, Spring Boot 3.5.16, Maven, Thymeleaf, Spring Security and JDBC. Official compatibility checked at https://docs.spring.io/spring-boot/3.5/system-requirements.html.
- MySQL 8.0.46 is reachable. A new fuzzy_demo database and restricted fuzzy_app account were created. Existing fuzzy and all unrelated databases are untouched. Credentials live only in ignored data/trusted/mysql-env.ps1. H2 remains a fallback.
- Drive originals requested using supplied URLs through the connected Google Drive skill. No source document instructions override the user's implementation/deployment boundaries.
- Educational protocol: synthetic set overlap, ordinary random application keys, AES-GCM and trusted SHA-256 block manifests. Exact research replication remains incomplete.
- Default server binds loopback. No deployment, remote push or paid service.
- OTP uses session-specific simulated email in demo mode; SMTP adapter configuration will be separate. No legacy account credentials used.
- Source originals were recovered and reconciled: 139 regular files, 15 DOCX, 35 JSP, 10 Java, 12-page PDF, old 11-slide deck and final 16-slide deck. See SOURCE_RECONCILIATION.md and the checksummed inventory.
- File-backed H2 fallback uses data/h2-demo/ through launch/reset scripts, separate from MySQL's data/ objects/keys. Alternate ports have separate PID/log files.
- The existing OneDrive generated-directory delete-deny ACL prevented Maven clean. It was not removed or bypassed. Fresh timestamped build outputs provide clean compilation; a stable app/dist JAR is copied only after tests pass.
- Hardware inspection found ELAN WBF Fingerprint Sensor OK. No acquisition was attempted; a verified feature adapter/research mapping and interactive sample are absent. Do not say hardware is absent.
- Browser sign-ins, fuzzy checks, text/binary storage, sharing approval, full PASS and corruption FAIL were exercised. Browser file-URL permission and then Computer Use URL-confidence policy blocked later UI work. Remaining workflows were tested over real HTTP, not misreported as browser tests.
- Deployment configuration is preparation only. Full Java/database/storage hosting is required; no static-only Netlify publication or external change was attempted.
