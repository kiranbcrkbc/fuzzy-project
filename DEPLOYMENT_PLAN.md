# Historical deployment preparation

**Superseded approval/budget status:** The user now authorizes publishing and complete deployment, but only at ₹0 with no card or paid upgrades. Consult DEPLOYMENT.md and PROJECT_LINKS.md. The plan below records the earlier preparation and must not be interpreted as current permission to use a paid service.

No remote repository push, public deployment, account provisioning, DNS change or paid resource was performed. The local review checkpoint comes first. The prepared files are templates and have not been deployed or production-tested.

## Platform fit checked 28 October 2026

- **Netlify alone is not the selected backend host.** The current project requires a long-running JVM, relational database, durable ciphertext and persistent key storage. Its static interface cannot be published by itself and called the complete project. Review [Netlify Functions documentation](https://docs.netlify.com/build/functions/overview/) before any future integration; a separate backend would still be required for this architecture.
- A compatible future option is **Render Docker web service + persistent disk + MySQL**, or an equivalent Linux VM/container host. [Render documents Docker image/build support](https://render.com/docs/docker). [Render persistent disks](https://render.com/docs/disks) are available on paid services; the default filesystem is ephemeral. A disk-backed service cannot scale to multiple instances and has deployment downtime. Render provides managed Postgres, so preserving this project's MySQL means a separately hosted MySQL service or managed MySQL provider. Do not silently substitute databases.
- The supplied Dockerfile is a portable preparation for a compatible backend host. Local demonstration remains a direct Java JAR; Docker is not needed locally. [Docker's Java guide](https://docs.docker.com/guides/java/) describes this deployment model.

Final host/provider, region, budget and credentials must be approved/provided at deployment time. No cost estimate is guaranteed, and no purchase is authorized now. A paid durable service, database, storage, backups and optional domain/SMTP may incur separate costs; check current pricing immediately before committing.

## Complete architecture to deploy after approval

| Concern | Preparation / required action |
|---|---|
| Java backend | Build/test JAR; container runs Java 17 with MySQL profile and a stable artifact. Set SERVER_ADDRESS=0.0.0.0 inside the hosted container and PORT from the provider. |
| Database | Dedicated MySQL instance/schema `fuzzy_demo` and least-privilege application user. Prefer provider-managed MySQL with TLS, private network access and backups. Never publish port 3306. |
| Schema/migrations | Current v1 schema is in `app/src/main/resources/schema.sql`, using idempotent CREATE TABLE IF NOT EXISTS. For deployment, apply the reviewed v1 DDL once with a migration account, set `SPRING_SQL_INIT_MODE=never`, and version all subsequent changes. A Flyway/Liquibase migration system is not yet installed/tested. |
| Ciphertext | Mount durable `/var/data/cloud`; local container filesystem alone is insufficient. Upload limit 10 MiB; reverse proxy body cap must allow multipart overhead (12 MiB). |
| Key management | Preserve matching `/var/data/trusted/master.key` with restricted OS ownership and encrypted, access-controlled backup. Existing wrapped SQL keys require the same master key. Managed KMS integration/key rotation remain future hardening, not current features. |
| Secrets | DB credentials, SMTP credentials and persistent master key must be supplied through host secrets/secure volumes. Never bake data/, local config, reference files, credentials or test evidence into images/repositories. |
| Accounts | Disable FUZZY_DEMO; no demo seeding, simulated OTP inbox or corruption controls. Provision administrator accounts offline with hashed passwords and a reviewed procedure before first login; production account-provisioning tooling remains required. Never expose the listed demo password publicly. |
| OTP and notices | Configure real SMTP OTP delivery and test it first. Local persisted notices remain clearly labelled application notifications. Real external notice delivery beyond OTP needs explicit implementation if required. |
| HTTPS/session | Provider-managed HTTPS/reverse proxy; COOKIE_SECURE=true, `server.forward-headers-strategy=framework`, HTTP-only SameSite cookies, CSRF preserved. Do not disable security to force a smoke test. Current sessions are in-process and invalidate on restart; single-instance deployment only. |
| Health checks | `/login` returns 200 as an unauthenticated basic HTTP probe; use an authenticated `/health` probe for DB connectivity. A dedicated minimal public readiness endpoint should be reviewed before provider setup. No secret credentials in URL query strings. |
| Logs | Collect stdout/stderr, rotate/redact logs, monitor authentication failures/500s/storage errors/disk usage. Do not log OTP, file keys, raw biometric data or plaintext uploads. |
| Backup | Coordinated SQL logical backup plus ciphertext/master-key snapshot while writes paused. Test restore and decryption. Disk snapshots alone are not a consistent MySQL backup. |
| Rollback | Retain previous image/JAR digest and reviewed schema version. Stop writes, snapshot DB/objects/master key, deploy new package, smoke test. On failure restore matching data set and previous image. Never pair an old database with incompatible object/key state. |

## Approval-to-live sequence

1. User reviews local app/evidence and explicitly authorizes deployment.
2. Resolve host, budget, domain and credentials. Review all production blockers above.
3. Build image and test the complete system privately with persistent MySQL/files/keys and real OTP delivery.
4. Back up, configure secrets/TLS, provision protected administrators and migrate schema. Keep demo controls disabled.
5. Deploy the whole backend/UI/database/storage system.
6. Run public-URL smoke tests: registration/pending, authorized admin approval, OTP, fuzzy success/failure, binary encrypted round trip, sharing denial/approval, full audit PASS, negative protocol checks and persistence after restart. Test corruption in a separate explicitly authorized test environment; do not corrupt real user objects.
7. Report actual URL and observed production results. Roll back immediately on material failure.

Until those steps are authorized and verified, the project remains local and must not be described as live online.
