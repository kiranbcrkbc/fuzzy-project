# Complete deployment and zero cost constraint

Publishing and deployment were authorized on 28 October 2026. The later budget instruction limits the total to **₹0**: no payment method, subscription, automatic paid upgrade or purchased domain. This supersedes historical approval checkpoints in earlier build documents.

## Current state

GitHub CLI is authenticated and a dedicated private repository has been created. Railway requires user sign-in; its connector is not connected. Actual account plan, remaining credits, expiry, billing restrictions and resource entitlement are unverified. No hosting resources have been provisioned. No live success is claimed.

## Architecture and durable storage

The complete application is a Java 17 Spring Boot service with server-rendered UI, relational metadata, encrypted UUID objects and a stable master key. The Dockerfile builds/tests source rather than copying local uploads or secrets.

MySQL remains supported and locally tested. For a constrained single-service trial, `demo,hosted` can use the explicitly labelled persistent H2 fallback. H2 is not described as MySQL. Either arrangement requires actual hosted restart tests.

Mount a provider **persistent volume** at `/var/data`; never use the container layer as storage:

| Component | Persistence requirement |
|---|---|
| Ciphertext | `/var/data/cloud/*.bin` |
| Master key | `/var/data/trusted/master.key`, stable 32 bytes; losing it loses key-unwrapping ability |
| H2 fallback | `/var/data/trusted/fuzzy.mv.db`, same durable volume |
| MySQL alternative | External dedicated SQL database; consistent backup with objects/key |

Only new synthetic data may be created on the host. No local database, uploads, master key or historical evidence will be migrated publicly.

## Official provider research

Checked 28 October 2026. These are public capabilities, not verified account entitlements.

- [Railway trial](https://docs.railway.com/pricing/free-trial): one-time $5 grant for up to 30 days, then Free with $1 monthly credit. Trial volume retention is limited after credit expiry; limited accounts restrict outbound networking. Actual account expiry must be read before provisioning.
- [Railway limits](https://docs.railway.com/pricing/plans): Trial volume 0.5 GB; Free RAM 0.5 GB and volume 0.5 GB per service. Credits may not sustain an always-on Java/MySQL combination. Never call trial hosting permanently free. Verify actual no-charge behaviour, payment state, remaining credit and exhaustion behaviour first.
- [Render Free](https://render.com/docs/free): files disappear on restart/redeploy/spin-down; free services cannot attach persistent disks. They sleep after 15 idle minutes. The unchanged disk-backed application is unsuitable; an external SQL database alone does not preserve ciphertext/keys.
- [Koyeb instances](https://www.koyeb.com/docs/reference/instances): free instances cannot attach volumes. Account/payment eligibility and an alternative durable store would still need verification.
- [Northflank Sandbox](https://northflank.com/pricing): advertises no card, two services, one database and no sleeping. Actual account eligibility and application-volume inclusion are unverified; a free database alone is insufficient.
- [Aiven MySQL Free](https://aiven.io/docs/products/mysql/concepts/mysql-free-tier): no-card small single-node SQL service, with inactivity restrictions. It does not supply the Java host or object/key filesystem.
- [Netlify Functions](https://docs.netlify.com/build/functions/overview/): not a standalone host for this existing long-running JVM and persistent filesystem. No frontend-only substitute is acceptable.

No paid option has been selected. No claim that every possible free architecture is impossible is made; a complete free arrangement must be authenticated and tested.

## Prepared configuration

Build context is the repository root. `deployment/Dockerfile` uses Maven/Java 17 build and Java 17 runtime. `railway.toml` selects that Dockerfile, `/healthz`, a 180-second startup window and bounded failure restarts. Docker is unavailable locally; container execution remains unverified until host build logs establish it.

Set `SPRING_PROFILES_ACTIVE=mysql,hosted` for MySQL or `demo,hosted` for the persistent H2 fallback. Use one instance. Configure the provider's assigned `PORT`, `FUZZY_DATA_ROOT=/var/data`, private `FUZZY_SEED_PASSWORD` (20+ random characters), and MySQL `DB_URL`, `DB_USER`, `DB_PASSWORD` when applicable. Environment examples contain placeholders only.

The hosted profile binds 0.0.0.0, uses forwarded proxy headers, HTTPS-only HttpOnly SameSite cookies, cached templates and no H2 console. TLS must come from the host. Sessions are in-process and invalidate on restart; domain records/files/keys must survive. Upload limit is 10 MiB and multipart limit 12 MiB, subject to provider proxy limits.

The minimal anonymous `/healthz` returns only UP/DOWN, checking SQL and key readability. Detailed `/health` remains authenticated. Hosted reset is disabled even with the local reset guard. No reset route is exposed. The container drops privilege after preparing dedicated volume directories.

## Restricted demonstration

Hosted startup rejects missing/short seeds and the published local password. The login page hides local credentials. A seed account differing from the configured private password causes failure rather than silently rotating an existing database. Keep the secret stable across deployments and hand it over through an ignored local file or host secret manager, not source/logs/URLs.

OTP can remain the clearly labelled session-local simulated inbox for this educational demo; it is not actual mail or an independent second factor. New accounts remain pending until private KGC approval. Corruption requires CLOUD role, demo mode, POST+CSRF, eligible demo-owned object and bounded index. Host only isolated synthetic data. Pairings, the paper's algorithms and actual biometrics remain incomplete.

## Provisioning and acceptance gate

1. Complete legitimate hosting authentication; inspect actual plan, expiry, credits, restrictions and no-charge status. Stop if a card, upgrade or potentially billable resource is required.
2. Select confirmed free/trial resources, attach durable storage, configure secrets and obtain a provider-issued HTTPS subdomain. Record true expiry/exhaustion behaviour.
3. Deploy the pushed commit; inspect build/runtime logs and health.
4. Browser-test actual HTTPS: OTP, accepted/rejected identity, exact-byte encrypted roundtrip, sharing denial/approval, full PASS, real corruption/new full FAIL and corrupted-download rejection.
5. Controlled restart/redeploy: sign in again and prove accounts, grants, files, usable keys and history persist. Health alone is not completion.

## Schema, backup and rollback

Schema v1 uses idempotent CREATE TABLE IF NOT EXISTS. For a fresh dedicated database, bootstrap the reviewed schema once, then set `SPRING_SQL_INIT_MODE=never`. Future changes need reviewed migrations; never import destructive legacy dumps.

Pause writes and back up SQL, ciphertext and the matching master key as one set. Stop H2 before copying its file. Protect backups outside Git and test restoration. Credit expiry is not a backup policy. Retain the previous tested commit/image and compatible data; roll back application code without resetting storage. Railway Free/Trial image retention is limited, so do not assume indefinite image rollback.

The local backup remains available through `.\start-demo.ps1 -Background` at http://127.0.0.1:8080/login. Preserve its database and master key.
