# Limitations and incomplete research requirements

The runnable application is an **educational workflow prototype**, not a complete implementation of the supplied research protocol. Matching screens does not establish research equivalence or a soundness theorem.

| Area | Implemented | Remaining limitation |
|---|---|---|
| Identity tolerance | Distinct synthetic integer set overlap, fixed server threshold 6 of 8; recent session gate | No real biometric matching, fuzzy extractor or noisy-biometric key recovery |
| Signature enrollment | Validated image upload, normalized PNG preview | No handwriting/fingerprint recognition; image byte count/hash is never used to authenticate |
| KGC | Approval/rejection, random wrapped application identity keys, masked ID/status/notices | Not paper Setup/Extract or identity-based encryption/signature; the issued application key is an identity-associated record, not a file-encryption key |
| File encryption | Fresh AES-256-GCM key/12-byte nonce, 128-bit tag, immutable object ID/version AAD, wrapped keys | Single-process service holds master key and decrypts for authorized users; not end-to-end encryption from the server operator |
| Metadata | Trusted upload-time SHA-256 block manifest/checksum | O(number of blocks) storage. Checksum stored alongside metadata diagnoses accidental corruption, not adversarial forgery |
| Audit | Secure random sampled indices or all blocks; bound challenge; actual current disk bytes; backend verification and terminal transition locks | No pairing-based authenticators, homomorphic aggregation, compact/public proof, or proof of retrievability |
| Sampling | Up to three distinct blocks, visible coverage | PASS certifies selected bytes at response time only; changes elsewhere may be missed |
| Full audit | Every encrypted block transferred and checked internally | O(file size) I/O and communication; not the paper's bandwidth benefit |
| Replay controls | Nonce/file/version/audit/expiry/index/length checks and one terminal consumption | A malicious server retaining correct historical bytes can still answer; nonce does not prove storage freshness |
| Roles | Four protected web roles with owner/recipient checks | Logical separation in one Java process/database/machine, not independent infrastructure |
| OTP and notices | Expiring one-use session code, recipient inbox | Local email is SIMULATED. SMTP OTP adapter exists but no external mail service was tested. Simulated OTP is accessible to anyone who already has the initiating password session; it is a demonstration, not an independent second factor |
| Persistence | MySQL and file-backed H2 profiles; local disk envelopes/master key | One machine and disk can fail together. No replication, managed KMS, key rotation, multi-instance sessions or automatic repair |
| Performance | Actual monotonic upload/challenge/response/verify timings | Local measurements only, excluding human queue/network time and final transaction commit; no paper benchmark reproduction or load-test claim |
| Recovery | Upload original as new immutable version, new baseline; preserve old audit history | Requires original bytes. Verification alone cannot reconstruct or repair data; access grants remain tied to individual object versions |
| Public deployment | Configuration/plan prepared | Not deployed, not penetration-tested, not production-certified. Domain, external accounts, HTTPS and secrets review still required |

## Exact mathematics — not implemented

Paper Setup, Extract, MetadataGen, Challenge, Response and Verify mathematics; Shamir threshold sharing/Lagrange interpolation; bilinear pairings; fuzzy identity-based signatures; erasure coding; aggregate authenticators; public verifiability; CDH/discrete-log selective-ID soundness/extractor proof remain **DEFERRED** against the user's full research scope. The application method names describing workflow must not be presented as these mathematical algorithms.

The supplied Java/JSP code does not implement that research scheme. The paper describes C++/MIRACL, a pairing construction and optical fingerprint acquisition. The ZIP includes historical Java dependencies, not a verified end-to-end implementation/test vectors of the six algorithms. [MIRACL's official repository](https://github.com/miracl/MIRACL) supplies cryptographic building blocks, which alone do not validate a reconstruction of this particular protocol. JPBC's official site was inaccessible during inspection; no JPBC verification or integration is claimed.

Hardware inspection on 28 October 2026 found **ELAN WBF Fingerprint Sensor, status OK**. No biometric sample was acquired. Presence of a Windows Hello/WBF device does not establish compatible feature-vector extraction for the paper. No vendor adapter, raw-capture permission or independently validated feature-to-attribute pipeline was supplied. [Microsoft's WinBioCaptureSample documentation](https://learn.microsoft.com/en-us/windows/win32/api/winbio/nf-winbio-winbiocapturesample) describes privilege and secure-sensor restrictions; this session did not attempt to bypass them. Biometric acquisition/recognition is **BLOCKED by the absent verified integration and interactive sample**, not by an assertion that the computer has no sensor.

## Source classification and future work

All 139 original regular files are preserved. All 15 DOCX text sources, both decks, research PDF, schema and Java/JSP routes are reconciled in SOURCE_RECONCILIATION.md. Generic Java/networking/UML/cloud tutorials are explanatory background, not obligations to implement applets, MS Access, ODBC, FTP or every cited research scheme. The PPT's explicit future work remains AI security, mobile support, multi-cloud and faster authentication. CONCLUSION.docx also names real-world evaluation as future work.

## Verification limits

Browser tooling exercised login/OTP, exact/noisy/wrong identity, text/binary upload/download, sharing denial/request/approval, audit PASS, real corruption, audit FAIL and corrupt-download denial. Later Chrome file upload required an extension permission; Computer Use subsequently stopped because it could not confidently establish the browser URL. No further UI automation was attempted in that turn. Registration/signature/KGC approval and recovery have real HTTP/integration evidence, but **their complete browser walkthrough and a final browser console/network inspection remain unverified**. See TEST_RESULTS.md for current package, restart and database evidence. No universal correctness or “100% working” claim is made.

IMPLEMENTATION.docx uses the phrase encrypted keywords. No separate searchable-encryption/keyword-index workflow is specified by the legacy schema/routes or final deck. The prototype encrypts file content; catalogue filenames remain visible metadata. Encrypted keyword indexing is explicitly DEFERRED, not claimed implemented.
