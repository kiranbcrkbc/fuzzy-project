# Complete zero-cost demonstration deployment

The Java 17 Spring Boot application is deployed on Render Free Docker hosting with Render Free PostgreSQL 18. The authenticated Hobby workspace showed **No card on file**, $0 current/projected charges, and the created resources both show Free. No card, paid subscription, automatic paid upgrade, custom domain or charge-capable resource was enabled. Provider-issued HTTPS address: https://fuzzy-project-demo.onrender.com/login.

## Persistence and architecture

The optional `postgres,hosted` profile uses the private PostgreSQL connection. Ciphertext persists in `cloud_object.ciphertext` (BYTEA); trusted manifests, wrapped keys, accounts, access decisions and audit records persist in the other six tables. A stable Base64-encoded 32-byte `FUZZY_MASTER_KEY` host secret wraps file/application keys. No durable state depends on the ephemeral web container. The architecture change was explained before implementation. MySQL and disk storage remain supported and tested locally; the working local demonstration was preserved.

Cloud responses read current stored SQL bytes, and corruption modifies actual ciphertext. Trusted baselines are not rebuilt. Role separation is logical in one process/database; this is an educational cloud simulator, not independent authorities. See deployment/RENDER_STORAGE.md and LIMITATIONS.md.

## Actual plan and expiry

Created 28 October 2026 in Oregon. Database dashboard explicitly says **expires 27 November 2026**. Free database: 1 GB, 0.1 CPU, 256 MB RAM, no managed backups, external connections disabled, storage autoscaling disabled. At expiry it becomes inaccessible; official documentation gives a 14-day recovery/upgrade grace period before deletion. No paid upgrade is authorized. Export protected SQL data and the matching master key before expiry, or use the preserved local backup. This is not permanent free database hosting.

Free web service: 512 MB RAM / 0.1 CPU; sleeps after 15 idle minutes, cold start roughly a minute (initial observed Spring boot about 71 seconds). Filesystem is ephemeral. Sessions/OTP/fuzzy gates reset on restart. Workspace shares 750 free instance hours/month, 5 GB outbound bandwidth and 500 build minutes. Initial account usage was 22.52/750 hours, 25 MB/5 GB and 0/500 build minutes; other existing services share these quotas. With no payment method, exhausted hours/bandwidth suspend service; exhausted build minutes disable new builds rather than generating supplementary charges. High service-initiated traffic can also cause suspension. No uptime or permanent free availability is promised.

Official limits checked: [Render Free](https://render.com/docs/free) and [Blueprint specification](https://render.com/docs/blueprint-spec). Railway authentication returned provider errors, including signups disabled; its actual plan was not verified or provisioned. Northflank's official billing documentation requires a payment method regardless of plan, so it was excluded. Existing authenticated Render provided the complete temporary zero-cost route.

## Configuration and security

`render.yaml` declares both Free resources, private DB wiring, generated master/seed secrets, /healthz and disabled automatic app deployments. Docker builds and tests source. `SPRING_PROFILES_ACTIVE=postgres,hosted`, `FUZZY_OBJECT_STORE=sql`, `FUZZY_DATA_ROOT=/tmp/fuzzy`, DB_HOST/PORT/NAME/USER/PASSWORD and the two FUZZY secrets are configured in Render. Never commit actual values. `application-postgres.properties` requires database TLS; provider HTTPS terminates at Render. Forwarded headers and HTTPS-only HttpOnly SameSite=Strict session cookies are configured.

All roles require password and one-use OTP. OTP is explicitly simulated session-local email, not independent two-factor authentication. Published local passwords are rejected in hosted mode; the live login hides them. Hosted reset is disabled. No database console is exposed. Only authenticated CLOUD with CSRF may corrupt bounded synthetic demo objects; do not store personal or production data. Upload limit: 10 MiB/file and 12 MiB/request, with provider edge restrictions also applying.

Private credentials are in ignored `data/trusted/HOSTED_DEMO_CREDENTIALS.md`, or the authenticated Render Environment page's FUZZY_SEED_PASSWORD. Preserve that value across deployments; seeding refuses silently changing existing passwords. Preserve FUZZY_MASTER_KEY permanently with the matching SQL backup. The ignored `data/trusted/render-host.env` is a private exported recovery configuration, never a public deliverable.

## Build, schema and rollback

Local packaged SQL build passed 24 JUnit methods, 91 HTTP assertions and 17 restart checks using a new scratch directory. Real public/container/PostgreSQL evidence is listed in LIVE_TEST_RESULTS.md. Health alone is not a functional test.

Schema initialization uses idempotent CREATE TABLE IF NOT EXISTS against a new dedicated database. No destructive legacy SQL was imported. Future schema changes require reviewed migrations; never overwrite an unrelated database. SQL ciphertext/reference records share transaction boundaries. For backups pause writes, export all seven tables and separately protect the matching master secret. Full audit responses consume additional database space. No managed backup exists on this free tier.

Rollback through Render to the previous compatible successful deployment; free services retain only two prior deploys. Preserve database and secrets. Never reset or recreate resources as a rollback. Local fallback: `.\start-demo.ps1 -Background`, http://127.0.0.1:8080/login; dedicated MySQL data and local master key were retained.

The prototype does not reproduce pairing-based fuzzy identity cryptography, biometric recognition, threshold secret sharing, erasure coding or the research soundness theorem. No production security claim is made.
