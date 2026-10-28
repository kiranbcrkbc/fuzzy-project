# Temporary zero-cost Render deployment

The optional `postgres,hosted` profile preserves the Java application and all existing MySQL/disk functionality. It stores encrypted object bytes in the `cloud_object` BYTEA column rather than Render's ephemeral filesystem. Trusted manifests, wrapped file/application keys, users, grants and audit history remain in relational tables. SQL storage is read afresh for responses/downloads; tampering changes actual ciphertext, never the baseline.

The 32-byte wrapping master key is a stable, generated Render environment secret, `FUZZY_MASTER_KEY`, decoded from Base64. It is never generated into the ephemeral container in hosted SQL mode. Without it the application refuses startup. Preserve this secret and the database together; deleting/recreating the service can lose the secret. Render administrators and the single application process remain trusted. This is logical role separation, not physically separate cryptographic authorities.

`render.yaml` declares only a Free Docker web service and Free PostgreSQL 18 database. Both are in Oregon and use private database connectivity; external database access is disabled. Generated secret values and database credentials are wired by Render rather than committed. Automatic application deployments are disabled. Never change either plan or add payment details.

Actual database created on 28 October 2026; dashboard expiry: **27 November 2026**. Free PostgreSQL has 1 GB shared by ciphertext, metadata, response bytes and history; full audit responses add storage overhead. It has no managed backups. Export protected data and matching master key before expiry or use the preserved local backup. No automatic paid upgrade is authorized.

The free web service sleeps after 15 idle minutes, starts again in roughly a minute, has 512 MB RAM / 0.1 CPU and shares 750 monthly free instance hours with other services in the workspace. Actual workspace included bandwidth: 5 GB; build pipeline: 500 minutes/month. With no payment method, exhausted bandwidth/hours suspend services; exhausted build minutes block builds. These limits are shared, not reserved for this project. Sessions and OTP/fuzzy gates reset on service restart. Domain data and keys must survive; verify using the real restart workflow.

Official references: [Render Free](https://render.com/docs/free), [Blueprint configuration](https://render.com/docs/blueprint-spec). This is temporary demonstration hosting, not permanently free database hosting or production storage.

Local verification: Maven packaged build passed 24 tests. SQL-mode packaged application passed 91 HTTP assertions and 17 restart assertions with the same file-backed SQL database/master secret and a different scratch directory. PostgreSQL/container/public verification is recorded separately in LIVE_TEST_RESULTS.md; local H2 compatibility does not establish PostgreSQL success.
