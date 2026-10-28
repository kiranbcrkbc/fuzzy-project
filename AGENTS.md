# FUZZY_PROJECT — Codex build instructions

## 1. Mission and working agreement

Build a working, locally runnable final-year demonstration of **Fuzzy Identity-Based Data Integrity Auditing for Reliable Cloud Storage Systems**. The student has a demonstration tomorrow. Use a **180-minute implementation budget**, measured from the start of the implementation session. This document is a source-grounded implementation brief, not a completed application or a guarantee of completion within that time.

Act as the implementing engineer: inspect the machine, create the application, run it, fix failures, test the important flows, and write the demonstration instructions. Do not stop after planning or scaffolding. Make routine reversible implementation decisions independently. Ask only when a real blocker cannot be resolved from the files or environment. Never claim an unrun test passed.

Work inside the folder containing this file. Preserve supplied references. Create the new application in `app/`. Do not spend the session making the old NetBeans installation work. Keep progress concise and write `BUILD_STATUS.md` after each milestone so a resumed Codex session can continue.

The requested file format is Markdown (`.md`). A Word macro template (`.dotm`) is not needed.

## 2. Authoritative sources and what was reviewed

The supplied ZIP is `Fuzzy_Project.zip`; its extracted root is `Fuzzy identity/`. It contains 139 regular files, including a 12-page research PDF, 15 DOCX documents, an 11-slide PPT, 10 Java files, 35 JSP files, SQL, configuration, images and third-party libraries. The separately supplied **`fuzzy ppt.pptx` has 16 slides** and is the final presentation reference. The complete document text, slide text, architecture/flow images, legacy application logic and schema were reviewed to prepare this brief. Bundled third-party binaries and generated build scripts are reference dependencies, not independently audited implementations.

The starter package puts those sources under `references/`, with extracted text under `references/extracted-text/`. If only this Markdown file was downloaded, copy the original ZIP and the final PPT into `references/` before building. This brief is self-contained enough to start while those are being copied. Do not expect access to the previous ChatGPT session, its attachments, Google Drive account, or its temporary filesystem.

Read these relevant originals first, with a 10-minute cap:

1. `references/fuzzy-ppt.pptx`, especially slides 6, 9, 10 and 11.
2. `references/Fuzzy identity/REVIEW DOCUMENTS/COMPLETE DOCUMENTS/IMPLEMENTATION.docx`.
3. `references/Fuzzy identity/SRS/Fuzzy Identity-Based Data Integrity Auditing for.docx`.
4. `references/Fuzzy identity/BASE PAPER/Fuzzy Identity-Based Data Integrity Auditing for.pdf`, especially sections 3, 4 and 6.
5. `references/Fuzzy identity/DATABASE/Fuzzy.sql` and the application files identified below.

### Latest user priority: account for everything

The user explicitly said: "Do not miss anything mentioned in the ZIP; I want all." Treat full coverage as the requested scope. The 180-minute budget does not authorize claiming an incomplete or simplified implementation is complete. Create `REQUIREMENTS_TRACEABILITY.md` before building, with one row per source requirement, its source/file/slide, implementation location, verification evidence and status: `IMPLEMENTED`, `SIMULATED`, `DEFERRED`, `BLOCKED` or `FUTURE_WORK`. Reconcile all 15 DOCX documents, both decks, the paper, SQL and legacy application routes against it. Generic language tutorials and third-party dependency functionality are background material, not additional product features. Explicit future enhancements remain labelled future work.

Required traceability entries include: registration and all registration fields from the legacy form; signature-image enrollment/preview; KGC approval/rejection, user list, private/application key issuance and key status; login/logout and OTP; fuzzy identity tolerance; file encryption, upload, own-file list and binary download; file-key/access requests, owner approval/rejection and notifications/verification metadata; all TPA/cloud request queues and forwarding states; current-byte proof generation, verification, result history and user visibility; performance analysis; validation and authorization; startup, persistence and test evidence. Preserve DOB, gender, phone, city and country as optional profile fields rather than silently omitting the source form. Signature preview must never be misrepresented as biometric recognition.

**Exact research implementation:** the paper's Setup/Extract/MetadataGen/Challenge/Response/Verify mathematics, fuzzy identity-based signature, threshold sharing, bilinear pairings, erasure coding and biometric sensor acquisition must each receive explicit coverage/status rows. The practical prototype below is the first runnable milestone, not a substitute for those requirements. Evaluate whether existing verified libraries/hardware enable the exact scheme; never invent the cryptography, fake hardware capture or call ordinary keys identity-based cryptography. If full research replication is not achieved, disclose that as incomplete against the user's full scope. Do not claim complete coverage based merely on matching screens.

### Final PPT, slide by slide

| Slide | Content | Implementation consequence |
| --- | --- | --- |
| 1 | Project title; Presented By: ARPITHA CB | Keep the exact project title. Use the presenter name only in presentation/demo documentation; do not replace it with a guessed name. |
| 2 | Contents | Overview, security, architecture and flow must be explainable. |
| 3 | Abstract: fuzzy identity and integrity auditing | Demonstrate both tolerance of identity variation and verification of stored bytes. |
| 4 | Cloud computing introduction | Explain a local cloud-storage simulator truthfully. |
| 5 | Existing system; RDIC and TPA | Make the TPA a visible role with a real audit workflow. |
| 6 | Proposed system; fuzzy authentication and identity-based cryptography | Provide a real threshold comparison; explicitly disclose the limits of ordinary application keys versus the paper's cryptography. |
| 7 | Secure file storage, privacy and modification detection | Encrypt actual files, enforce access rights, and detect deliberate changes. |
| 8 | Windows, i3+, 4 GB+ RAM and 500 GB disk listed | Keep setup lightweight. The listed disk capacity is a machine specification, not storage the application must allocate. |
| 9 | HTML/CSS, Java, MySQL, NetBeans/Eclipse | Preserve Java backend and MySQL support. IDE choice does not require an old project format. |
| 10 | User, KGC, TPA and Cloud Server architecture | Implement all four roles. User sends fuzzy identity to KGC; KGC issues a key. User requests audit; TPA challenges cloud; cloud responds; user sees result. |
| 11 | Login check; upload; view; file-key request; requested files; download; audit request; verify proof | Implement sharing/access requests and downloads as well as auditing. |
| 12 | Java, SQL, HTML, JavaScript, CSS | Use these technologies. The generic language collage does not add Python/PHP/Swift requirements. |
| 13 | Security, authentication, reliability | Show working success and failure paths. |
| 14 | AI security, mobile, multi-cloud, faster authentication | These are future enhancements. Do not build them in the three-hour window. |
| 15 | Conclusion and research claims | Separate results actually achieved by this demo from claims proved by the paper's authors. |
| 16 | Thank you | No additional implementation requirement. |

The final PPT contains no external hyperlink relationships. Its architecture and flow diagrams are embedded images and have been inspected. No separate live site or repository link was supplied.

### Other source findings

- `IMPLEMENTATION.docx`: User registers and uploads encrypted files, manages other users' file requests; KGC approves users and issues identity-associated keys; TPA processes audit requests and challenges cloud; Cloud Server produces responses.
- `SYSTEM DESIGN.docx`: four role flows, user audit-proof verification and cloud performance analysis. Some diagrams/text repeat.
- SRS, abstract and analysis documents: describe fuzzy identity as descriptive attributes and tolerance when identities overlap sufficiently.
- `SOFTWARE ENVIRONMENT.docx`: contains generic historical Java/networking material, including an unrelated MS Access/cache sentence. Use MySQL from the final PPT and actual SQL; do not implement MS Access, applets, J2ME, ODBC examples or geography chart features.
- Old hardware/OS requirements conflict across documents (XP/7, Windows 10, varying RAM). Use the final PPT's Windows/4 GB assumption.
- `SYSTEM TESTING.docx` contains generic claims that tests passed. They are not evidence about the new build. Run and report your own tests.
- Screenshot document shows registration, KGC approval/key issue, OTP, user upload/audit, TPA forwarding and cloud proof screens. Recreate the workflow with clearer UI, not the old styling.

## 3. Research scope and honest implementation boundary

The base paper is by Yannan Li, Yong Yu, Geyong Min, Willy Susilo, Jianbing Ni and Kim-Kwang Raymond Choo. The project documents cite the IEEE TDSC 2019 publication. Its supplied PDF describes:

- Four entities: KGC, user, cloud server and TPA.
- Six algorithms: `Setup`, `Extract`, `MetadataGen`, `Challenge`, `Response`, `Verify`.
- Three phases: Enroll, Store and Audit.
- Identity sets with acceptance when `|omega intersection omega_prime| >= d`.
- Threshold secret sharing/Lagrange interpolation, bilinear groups, fuzzy identity-based signatures, file tags and block authenticators.
- Homomorphic responses and soundness assumptions involving CDH and discrete logarithms.
- A C++/MIRACL prototype with a fingerprint sensor, distinct from the Java/JSP application included in the ZIP.

**Three-hour target:** a clearly labelled educational prototype using synthetic identity attribute sets, ordinary application key issuance, AES-GCM encryption, and genuine ciphertext-block challenge/response checks against trusted SHA-256 reference metadata. This preserves the four-role workflow and demonstrates the ideas. It does not reproduce pairing-based fuzzy identity cryptography, a fuzzy extractor, biometric sensor recognition, erasure coding, compact homomorphic proofs or the paper's soundness theorem.

Show a short explanation in the app's About/Method screen and in `LIMITATIONS.md`. Do not claim production security, biometric accuracy, proof of retrievability, constant-size proofs, or cryptographic key recovery from noisy biometrics. A server-side fuzzy matching gate is an application-level tolerance demonstration. Ordinary keys associated with an identity are not identity-based encryption/signatures in the cryptographic sense.

The original PPT's strongest claims exceed this practical demo. Create `PRESENTATION_NOTES.md` explaining the distinction and giving suggested spoken wording, especially for slides 6 and 15. Leave the supplied PPT unchanged unless the user asks for edits.

## 4. Legacy code: use as reference, not as a secure baseline

Project root: `Fuzzy identity/SOURCE CODE/Fuzzy_IDbased_DataIntegrity/`.

| Source | Observed behaviour / issue | Required replacement |
| --- | --- | --- |
| `src/java/FUZZY/signup.java` | Stores uploaded signature and image byte length; plaintext passwords | Password hashes, validation, actual synthetic identity attributes |
| `web/sendkey.jsp` | Constructs a key identifier from image byte length plus random digits | Secure random application key; identity-linked record; explain this is not the paper's Extract algorithm |
| `src/java/FUZZY/Upload.java` | Reads input as text, removes line breaks, uses `String.hashCode()`, fixed `D:/`, FTP dependency; SQL retains plaintext content | Binary-safe files, AES-GCM, SHA-256, configurable local paths, no retained plaintext |
| `Encryption.java`, `Decryption.java` | Bare `AES` transformation and internal Base64 APIs | Explicit `AES/GCM/NoPadding`, standard `java.util.Base64` where needed |
| `web/Auditing_request.jsp`, `send_cloud.jsp` | User request, then TPA forwards to cloud | Persisted state machine with authorization |
| `web/audit_proof.jsp` | Copies saved database hash into proof; does not reread cloud bytes | Read current stored ciphertext blocks on every challenge |
| `web/proof_verify.jsp`, `test.jsp`, `js/validation.js` | Compares displayed hash strings in browser form | Server-side verification with immutable reference metadata |
| `user_login.jsp`, `otp1.jsp` | KGC gate and mail OTP, SQL concatenation; weak session flow | Proper login state, parameterized persistence, optional local demo OTP inbox |
| `KGC.java`, `TPA.java`, `clloud.java` | Fixed role credentials; insufficient route protection | Separate role accounts and protected routes |
| `SQLconnection.java`, `FTPcon.java`, `Mail.java` | Embedded historical connection credentials/external services | New local configuration; never reuse or contact legacy accounts |
| `nbproject/project.properties` | Java 6 / old Java EE / Tomcat / NetBeans Ant assumptions | New build reproducible with the available modern JDK |

Legacy MySQL schema contains five tables: `user`, `fileupload`, `audit_request`, `cloud_request`, `audit_proof`. All dumped tables are empty. Do not expect existing demo users or use the destructive DROP statements on an unrelated database. Create a new application schema with equivalent workflow concepts and better relations. A `filekey` identifier is not an encryption key.

## 5. Stack and setup decision

Use **Java + Spring Boot + server-rendered HTML/Thymeleaf + local CSS/JavaScript**, Maven, and relational persistence. This reduces frontend build overhead and remains aligned with the PPT. Keep it a single application with clear role/service boundaries. Do not build microservices, a React frontend, Docker-only startup or a paid cloud deployment.

1. Detect Windows versus Linux/WSL, working directory, `java`, `javac`, Maven/wrapper, MySQL and occupied ports. Do not assume a Desktop path, `D:` drive or an installed IDE.
2. Use an installed compatible JDK, preferring Java 17 or 21. Choose a concrete stable Spring Boot release compatible with the detected JDK; verify official compatibility and resolve dependencies immediately. Avoid snapshot releases and avoid upgrading the machine unnecessarily.
3. **MySQL is the intended database.** If reachable locally, configure a new `fuzzy_demo` database with credentials from environment/local ignored config. Do not alter unrelated databases.
4. Provide a **file-backed H2 `demo` profile** for a dependable local run if MySQL is unavailable. Explicitly label the active database on About/diagnostics. Do not claim MySQL was tested when only H2 ran. Attempt MySQL setup for at most 10 minutes; keep implementing with H2 if blocked. A tested MySQL run remains the preferred final demonstration.
5. Provide scripts for Windows PowerShell/batch and a Unix shell command. Use quoted relative paths or resolve the script directory so spaces and OneDrive Desktop paths work.
6. Download dependencies during the build; package a runnable JAR. Verify it starts without an internet connection after dependencies are installed. Bundle UI assets locally, without fonts/CDNs required at demo time.
7. A local disk object store represents cloud storage. Label it **Local cloud simulator**. Do not require DriveHQ FTP, SMTP, an AWS account, API keys or payment.

Official implementation references (check exact version documentation when building):

- Spring Boot requirements: https://docs.spring.io/spring-boot/system-requirements.html
- Spring Boot getting started: https://spring.io/guides/gs/spring-boot/
- Java Cipher API: https://docs.oracle.com/en/java/javase/18/docs/api/java.base/javax/crypto/Cipher.html

## 6. Minimum complete user journeys

### Accounts and roles

Roles: `USER`, `KGC`, `TPA`, `CLOUD`. Include seeded demo users Alice and Bob plus three role administrators. Seed idempotently only in demo mode. List actual demo credentials in `README.md`; never use real email addresses or legacy source credentials. A role dropdown must never grant a role by itself.

- User registration creates `PENDING` account and synthetic attribute template.
- KGC can inspect pending users, approve/reject and issue a random application identity key/key ID, with issuance time. Store sensitive key material server-side and show a masked key ID, not a secret to every role.
- Pending/rejected users cannot upload, download, share, or audit. They may see their approval status after a limited login.
- Approved users log in and demonstrate a fuzzy identity check against a saved template. Require a valid recent fuzzy check for upload, download and audit request. Bind it to the authenticated session/user; limit its lifetime and clear it on logout.
- Use framework-supported sessions, password hashing and CSRF protection. Enforce role and ownership checks server-side on every action.

OTP exists in the legacy screenshots/code and is REQUIRED by the user's instruction to include all project features. Implement a one-use expiring OTP delivered to a **local demo inbox**, clearly marked simulated email and restricted to the user's session. Add an SMTP adapter configurable through new credentials if available, but do not block the local demonstration on external delivery and do not claim simulated mail was actually sent. Require OTP completion before granting the full authenticated session. Test incorrect, expired and reused OTPs. Include identity-key issuance notices and approved file-access/verification-object notices in the recipient-specific local inbox; never expose raw AES keys in notifications.

### Fuzzy identity demonstration

Use explicit synthetic integer attributes, not invented biometric recognition:

- Enrolled set: `[11, 22, 33, 44, 55, 66, 77, 88]`.
- Server-side threshold: `d = 6` distinct matching attributes.
- Exact match: eight overlaps, accept.
- Noisy sample: `[11, 22, 33, 44, 55, 66, 91, 92]`, six overlaps, accept.
- Wrong sample: `[11, 22, 33, 44, 91, 92, 93, 94]`, four overlaps, reject.
- Boundary test: five matches rejects; six accepts.
- Reject empty/malformed/oversized inputs. Deduplicate sets so repeated attributes cannot inflate the score. Never accept a client-provided threshold or client-provided success flag.

Show overlap, threshold, and decision. The template belongs to the enrolled account; do not permit the user to swap a template during verification. Label presets as synthetic. An image-upload preview may be included to explain the source assets, but image filesize/hash is not a biometric match. Real fingerprint extraction and secure noisy-biometric key derivation are outside this demo.

### Encrypted storage

Approved, recently fuzzy-verified users can upload named files, see their own file inventory and download original bytes after authorization.

- Treat input as bytes. Support TXT, PDF, PNG and other ordinary binary files, with a modest documented size cap, e.g. 10 MiB. Reject empty uploads cleanly.
- Use a fresh random AES key per file, `AES/GCM/NoPadding`, a unique 12-byte random nonce and a 128-bit authentication tag. Use secure randomness. Persist the nonce/envelope format so restart/decrypt works.
- Bind immutable file ID/version as authenticated additional data where practical.
- Store ciphertext under a UUID/object ID, never a user-controlled filesystem path. Escape display names, normalize paths and protect against traversal/collisions.
- Keep keys in a trusted key store/service, separate from cloud ciphertext. A local prototype may use protected server-side files or wrapped database keys, but document the single-machine trust limitation and exclude secrets/data from git.
- No plaintext file copies in SQL, logs, object storage, fixtures or debugging output after upload. Use synthetic demo samples only.
- Upload creates trusted baseline metadata once. Auditing must not regenerate the baseline from current cloud bytes.
- Downloads must read and decrypt the current object; authentication-tag failure, missing objects and corrupt data produce a useful error, never a stale plaintext fallback.

### File access and key requests (required by PPT slide 11)

- Bob can browse a catalogue of shareable file metadata without seeing file content, keys or identity templates.
- Bob requests access to an Alice-owned file. Prevent duplicate pending requests.
- Alice views requests and approves/rejects them; nobody else may approve them.
- Pending/rejected requests deny download. An approved Bob can download after his own fuzzy check. Sharing grants application-mediated decryption access; never email a raw AES key in this demo.
- Provide request history with `PENDING`, `APPROVED`, `REJECTED` states. If inexpensive, support revocation and test it; clearly mark it optional until working.

### Four-role data integrity auditing

Implement meaningful checks over actual current stored ciphertext. Keep the trusted baseline outside cloud-role write access. Use a simple block-manifest protocol, with no claim of reproducing the research proof:

1. At upload, split the stored ciphertext envelope into fixed blocks, e.g. 64 KiB. Record a trusted manifest containing file ID/version, envelope byte length, block size, block count, each block's expected length and SHA-256 digest. Record a manifest checksum in trusted metadata for accidental-change diagnostics. A checksum stored alongside mutable data is not a signature or adversarial trust anchor.
2. User requests an audit of an owned file after a fuzzy check. Store requester, file/version, mode and timestamp. Deny arbitrary other-user file IDs.
3. TPA creates a challenge: unique audit ID, random nonce, file/version, distinct block indices, issue/expiry timestamps and mode. For sampled mode, select indices with secure randomness. For full mode, select all blocks. Only TPA can create this transition.
4. Cloud role sees pending challenges and clicks Generate Response. The service reads the actual object from disk now, seeks to requested blocks and returns those **ciphertext block bytes**, lengths and challenge metadata. Never simply return saved hash strings. Do not include trusted expected hashes, decryption keys or plaintext in cloud response generation.
5. TPA verifies on the backend: correct audit ID, nonce, file/version, expiry, exact requested index set, block lengths, current object length where observable, and `SHA-256(returned block bytes)` versus the trusted upload-time manifest. Reject missing, duplicate, extra or wrong blocks and stale/replayed responses. Persist the verified terminal state atomically, so a response cannot be consumed twice.
6. User sees the result and detail: challenge, counts, failed blocks, digest comparisons, mode and real measured duration. Expected hashes are read-only display fields, never accepted as request inputs.

States: `REQUESTED -> CHALLENGED -> RESPONDED -> VERIFIED_PASS / VERIFIED_FAIL`; also `EXPIRED` or a recorded operational `ERROR`. Enforce transitions, role boundaries and ownership. A failed attempt must not silently become PASS. Give infrastructure errors their own explanation.

**Limits:** This protocol transmits selected ciphertext blocks to a trusted verifier and stores O(number of blocks) trusted hashes. Full mode reads/transfers the complete encrypted object internally. It is not the paper's compact/publicly verifiable proof, and adding a nonce does not stop a malicious server that retains old correct bytes. In a single process, role separation is logical rather than independent infrastructure. A sampled PASS only covers selected blocks; it cannot certify the entire file. Show coverage and call it a sampled check.

### Tamper demonstration and recovery

In local demo mode, CLOUD may deliberately flip a byte in a selected ciphertext block of a demo-owned file. Restrict the operation to the configured demo object store, authenticated CLOUD role, POST+CSRF and demo mode. Record tamper event and block index. Never touch arbitrary paths or the manifest. Mark the control **Demo: simulate corruption**.

- Untouched object + full audit: PASS.
- Corrupted object + full audit: FAIL deterministically.
- Missing/truncated object: fail/error clearly, never PASS.
- A sampled audit can miss a changed block: explain coverage instead of forcing a fake failure. Use full audit in the live demonstration.
- Corrupt ciphertext download must fail authenticated decryption.
- Recovery re-uploads the original sample as a new version and creates new baseline metadata. Do not pretend verification repaired data. Do not erase failed audit history.
- Provide a separate explicit demo reset script, limited to dedicated demo data, with a confirmation or `--demo-only` guard. Never reset automatically on ordinary startup.

## 7. Suggested implementation structure

Keep code small and readable. A single Maven module is enough:

- Controllers: auth, user/files/shares, KGC, TPA, cloud, diagnostics.
- Services: identity match, key issuance, encryption, object store, audit challenge/response/verification, sharing, demo seeding.
- Persistence: users, identity/key records, stored files, trusted block manifests, access requests, audit records and challenge/response data.
- Templates: login/register, four dashboards, file inventory/upload/detail, access requests, fuzzy verification, audit detail/history and About.
- Local CSS/JS: navy/teal academic security dashboard, readable tables, labelled states, useful empty/error states and a clear active role. Keep graphics minimal.
- Runtime directories: `data/cloud/` (ciphertext), `data/trusted/` (keys/trusted files if needed), database files outside source directories. Allow a configured data root.

Use one source of truth for audit state and persist real response data needed for verification. Avoid implementing both REST and MVC forms for everything. The browser should not execute cryptographic verification. Use transactions/constraints for approval and audit transitions, unique emails and duplicate requests.

## 8. Three-hour delivery schedule and stop rules

| Elapsed time | Required milestone | Time-control action |
| --- | --- | --- |
| 0–15 min | Environment checked, source differences recorded, dependencies resolved, app boots | If MySQL is blocked after 10 min, use the labelled persistent H2 profile while keeping MySQL config. |
| 15–40 min | Login, role guards, seed users, KGC approval and fuzzy checks | Get a running vertical slice before styling. |
| 40–75 min | Binary-safe encryption/upload/download and persistent metadata | Verify round-trip bytes now, including restart persistence. |
| 75–110 min | User request, TPA challenge, cloud response and server verification | Verify actual disk reads and both PASS/FAIL paths. |
| 110–135 min | Sharing approvals/denials, tamper control and history | Full-mode corruption failure is mandatory. |
| 135–160 min | Integration tests and UI walkthrough; fix failures | Stop adding features. |
| 160–180 min | Runnable JAR/start scripts, offline restart, demo script and limitations | Freeze scope. Report remaining issues honestly. |

Under time pressure, preserve an executable core first. Defer external deployment and decorative animation before required functional features. Real SMTP, biometric hardware and the exact pairing-based protocol need explicit status reporting, not silent removal. Multi-cloud, AI and mobile are future enhancements in the final PPT. Include a simple performance table and local chart based on real measured upload, challenge, response and verification timings, as the cloud role flow mentions performance analysis. Do not cut Java, OTP, fuzzy threshold checks, all four roles, encryption, sharing, notifications, real timing evidence or corruption detection. If the core is incomplete, report precisely which acceptance criteria remain instead of hiding it behind a polished UI.

Do not delegate unless separately authorized by the user. Keep database/schema/security dependencies coherent. If dependencies cannot be fetched, inspect installed caches and use an existing compatible setup; report the exact blocker if no executable route exists. Do not substitute a static mock and call it complete.

## 9. Acceptance tests and evidence

Use meaningful unit tests for the identity and integrity logic, integration tests for protected workflows and a browser walkthrough if browser tooling is available. A running server alone is insufficient.

Required checks:

1. Fresh registration is pending; KGC approval unlocks it. Non-KGC approval is denied.
2. Duplicate registration is rejected. Invalid credentials, logout and unauthenticated protected routes behave correctly.
3. Fuzzy exact/noisy/wrong samples and five/six overlap boundary behave as specified. Duplicate attributes and tampered thresholds cannot bypass it.
4. Unauthorized roles and wrong owners are blocked server-side for file/audit/key actions, including direct URL requests.
5. Text and binary upload/download round trips preserve bytes. Same display filenames do not overwrite each other. Oversized/path-traversal inputs fail safely.
6. Stored objects are ciphertext; plaintext and keys are not leaked to the cloud/TPA UI or logs.
7. Bob cannot download before approval, can after Alice approves, and remains denied if Alice rejects another request. A third user cannot approve on Alice's behalf.
8. A complete untampered audit passes. Repeated TPA clicks cannot create conflicting completed state.
9. An actual ciphertext byte flip makes a subsequent full audit fail while baseline hashes stay unchanged. Missing and truncated objects do not pass.
10. Replay/wrong nonce, expired challenge, wrong file/version, missing/extra/duplicate block and mutated response cases reject.
11. After shutdown/restart, accounts, approval, keys, encrypted files, access grants and audit history remain usable. New uploads do not reset existing data.
12. Clean build/package succeeds. Runnable JAR starts with the documented command and local UI assets work offline. Test the actual packaged JAR, not only an IDE launch.
13. If MySQL is available, execute a smoke workflow using that profile. Record H2 and MySQL outcomes separately. Never describe an untested profile as verified.

Record commands, observed results, date/time, active profile and unresolved issues in `TEST_RESULTS.md`. Never copy generic source-document pass claims. Screenshots, if available, must depict the running application. Keep test fixtures synthetic.

## 10. Required final deliverables from the build session

- `app/` source with `pom.xml` and Maven wrapper where feasible.
- Runnable packaged JAR, with exact actual path documented.
- `start-demo.bat` / `start-demo.ps1`, a Unix equivalent or documented command, and guarded demo reset instructions.
- `README.md`: prerequisites, actual build/run commands, port/URL, demo credentials, demo/MySQL profiles, configuration, data directories and troubleshooting.
- `.env.example` or equivalent with placeholders, plus ignored real configuration and runtime data. Never commit secrets or downloaded legacy credentials.
- `BUILD_STATUS.md`: completed milestones, remaining work and the exact command for continuing.
- `TEST_RESULTS.md`: real executed evidence.
- `DEMO_SCRIPT.md`: a reproducible 7–10 minute walkthrough.
- `VIVA_QA.md`: about 15 concise questions/answers covering fuzzy identities, KGC/TPA/cloud, encryption vs integrity, SHA-256, threshold distance, sampling, nonce/replay, tampering and limitations.
- `LIMITATIONS.md`: educational implementation boundary, single-machine trust, synthetic features, ordinary key issuance, block-manifest cost, sampled-audit coverage and any untested configuration.
- `PRESENTATION_NOTES.md`: match the final 16-slide PPT and explain prototype deviations without editing the supplied file.

At the end give the student exact commands to run, local URL, demo logins, test results and unresolved issues. Do not merely say 'done' or claim compliance with the full research protocol.

## 11. Rehearsal sequence

1. Start the packaged app, show the project title and four roles, and identify the active database and local cloud simulator.
2. Register a synthetic user or show an existing pending account. Log in as KGC, approve it and show its key ID.
3. Log in as Alice. Show noisy identity acceptance and wrong identity rejection. Complete a successful verification to proceed.
4. Upload the provided synthetic sample. Show metadata and ciphertext object information, with no plaintext cloud preview.
5. Log in as Bob, request Alice's file, and show a denied download while pending. Alice approves; Bob verifies his identity and downloads the exact original bytes.
6. Alice requests a full audit. TPA creates the challenge, CLOUD generates the response, and TPA verifies. Show Alice's PASS and the audit evidence.
7. CLOUD runs the demo corruption operation. Alice requests a new full audit; repeat the role transitions and show FAIL with a mismatching block.
8. Show that corrupt ciphertext cannot be downloaded successfully. Re-upload as a new version to demonstrate recovery. Keep the failed audit visible.
9. Explain that the paper uses a stronger pairing-based fuzzy identity protocol, while this build demonstrates its workflow through synthetic feature matching and trusted ciphertext-block auditing. List genuine future work from slide 14.

Suggested spoken explanation: 'Our demonstration separates identity verification, key administration, storage and auditing into four roles. It tolerates a defined number of changed synthetic identity attributes, encrypts files and checks current stored ciphertext against trusted reference metadata. This is an educational implementation of the workflow. The research paper's full fuzzy identity cryptography and compact proof system remain outside this prototype.'

## 12. Prompt to paste into Codex

Use GPT-6 Astra as chosen by the user. The folder must be open in Codex before pasting:

```text
Read AGENTS.md in this FUZZY_PROJECT folder and implement the project now. Use the included final 16-slide fuzzy-ppt.pptx and the ZIP references as specified in AGENTS.md. I have a final demonstration tomorrow and a 180-minute implementation budget starting now.

Build and verify the complete local educational prototype: Java backend, MySQL support with the labelled persistent H2 demo fallback if necessary, HTML/CSS/JavaScript, all four roles, KGC approval/key issuance, OTP and recipient-specific demo notifications, signature/profile enrollment, real synthetic fuzzy attribute threshold checks, binary-safe AES-GCM storage/download, file access requests and approvals, real ciphertext-block challenge/response verification, audit history, and a deterministic full-audit tamper demonstration. Be precise about the difference from the paper's pairing-based cryptographic protocol.

The user requires everything in the ZIP to be accounted for. Create REQUIREMENTS_TRACEABILITY.md and explicitly track any simplified, unimplemented or blocked item, including the exact research cryptography and biometric sensor. Include measured performance analysis. Never silently omit requirements or call the prototype full research replication. Inspect the environment and start coding immediately. Make routine reversible decisions without stopping for confirmation. Work inside app/, preserve references, keep a running vertical slice, and update BUILD_STATUS.md at milestones. Use the time gates in AGENTS.md and stop adding features for the final testing/packaging period. Do not contact legacy FTP/email accounts, require paid services, fabricate proof results, or claim unrun tests passed.

Do not stop at a plan or UI scaffold. Run the application, test the important positive and negative paths, fix failures, package a runnable JAR, verify restart persistence, and write the README, demo script, viva answers, presentation notes, limitations and actual test results. Finish with exact launch commands, the local URL, demo credentials, what passed, and any remaining blockers. If a task cannot be finished within the budget, state exactly what is incomplete while preserving the runnable core.
```
