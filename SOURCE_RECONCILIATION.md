# Original-source reconciliation

The user-provided Drive ZIP and final Office deck were downloaded on 28 October 2026 and preserved under `references/`. The connected Drive metadata identified ZIP size 15,538,804 bytes and final PPTX size 10,054,243 bytes. Connector raw transfer failed with transport closure; direct public-file download completed. The Drive warning HTML was not mistaken for the ZIP: the downloaded archive was validated by successful extraction.

The extracted original root contains **139 regular files**, including **15 DOCX**, **10 Java files**, **35 JSP**, **one 12-page paper**, **one SQL dump**, and **one 11-slide deck**. The separately supplied final deck has **16 slides**. File names, sizes and SHA-256 hashes are in `references/extracted-text/SOURCE_INVENTORY.json`. No original was edited or executed. Bundled dependency binaries and generated build files were inventoried, not independently security-audited.

## All 15 DOCX documents

Paths in this table are beneath `references/Fuzzy identity/`. Extracted text is preserved in `references/extracted-text/`. Embedded architecture, flow and screenshot images were inspected as contact sheets under its `images/` subdirectory.

| Document | Requirements / classification | Reconciliation |
|---|---|---|
| SRS/Fuzzy Identity-Based Data Integrity Auditing for.docx | Fuzzy identity error-tolerance, KGC/TPA/cloud, Java/MySQL; stronger research claims | Four roles and educational tolerance implemented. Pairings/soundness are explicitly deferred. Final PPT supersedes Windows 7/NetBeans 7.2.1 hardware/software details. |
| REVIEW DOCUMENTS/COMPLETE DOCUMENTS/ABSTRACT.docx | Attributes, overlap tolerance, selective-ID/CDH/DL claims | Synthetic overlap is implemented; selective-ID security is a paper claim, not an application test result. |
| …/BIBILOGRAPHY.docx | 34 research references | Background, including NIST, PDP/POR, RSA, BLS, secret sharing and biometric research. Citing a scheme does not require adding every cited algorithm as a product feature. |
| …/CONCLUSION.docx | Paper conclusions; real-world implementation/evaluation as future work | Honest limitations and presentation notes; real-world independent infrastructure evaluation remains future work. |
| …/IMPLEMENTATION.docx | User register/upload, encrypted data/hash, sharing approve/reject and mail; KGC issue keys; TPA audit queue; cloud proof | Implemented connected workflows. Raw-key email replaced by recipient notices and application-mediated decryption per AGENTS. Metadata identifies the object/version; external delivery is simulated. |
| …/INPUT DESIGN AND OUTPUT DESIGN.docx | Validation, clear messages, input/error guidance, record viewing, output status/warnings | Server-side validation, error pages, state badges, persisted histories and measured timing tables. Generic design objectives do not add an unrelated data-entry subsystem. |
| …/INTRODUCTION.docx | Cloud characteristics/service models, scalability/availability discussion | About/Method explains local simulation. No claim of actual elasticity, global availability or replicated infrastructure. |
| …/LITERATURE SURVEY.docx | NIST roadmap, provable possession, homomorphic identification, RSA, compact POR | Background research. Compact proof/public verification/POR are explicitly unimplemented. |
| …/SCREEN SHOTS.docx | Registration/profile/signature, KGC key request, login/OTP, encrypted upload/list, TPA/cloud forwarding/proof | Inspected 15 embedded images. Modern screens implement the workflow with synthetic data. Historical addresses/credentials were not reused. |
| …/SOFTWARE ENVIRONMENT.docx | Generic historical Java/JVM/networking/servlet/SQL explanations, isolated MS Access statement | Explanatory background. Java 17/Spring Boot replaces old applets/J2ME/ODBC/FTP; MySQL follows final PPT and SQL. No MS Access/geography example feature. |
| …/SYSTEM ANALYSIS.docx | Existing PKI costs, fuzzy identity proposal, formal model and soundness advantages | Explained in notes. No formal proof claim for this implementation. |
| …/SYSTEM DESIGN.docx | Four roles/flows, user proof verification, cloud performance; UML/DFD descriptions | Real queues, backend verification/user evidence, measured performance table/chart. Generic UML descriptions are design background. Architecture appears in About and this documentation. |
| …/SYSTEM SPECIFICATION.docx | Windows XP/7, Java/J2EE, MySQL, 512 MB/40 GB historical hardware | Conflicts resolved to final PPT Windows/4 GB guidance; no forced legacy IDE or disk allocation. |
| …/SYSTEM STUDY.docx | Economic/technical/social feasibility | Small local single-module build, no paid dependency, documentation/rehearsal for usability. Not an external business-feasibility research claim. |
| …/SYSTEM TESTING.docx | Unit/integration/functional/acceptance testing and generic “passed” statements | New executed tests only in TEST_RESULTS.md. Source pass statements were not reused as evidence. |

## Presentation and paper

- Final `references/fuzzy-ppt.pptx`: all 16 slides extracted/read; slides 6/9/10/11 checked. Slide 10's embedded four-role diagram and slide 11's user flow were visually inspected. Slides 1–16 are mapped individually in PRESENTATION_NOTES.md. No hyperlinks to a separate live application were supplied.
- ZIP `REVIEW DOCUMENTS/PPT.pptx`: all 11 slides read. Slides 2–7 repeat abstract/existing/proposed/soundness text; slide 8 contains architecture; slides 9–10 historical hardware/software; slide 11 bibliography. Final deck takes precedence for presentation and environment.
- `BASE PAPER/Fuzzy Identity-Based Data Integrity Auditing for.pdf`: 12 pages extracted. System/security model §3, six algorithms and erasure-code/pairing construction §4, proof §5 and empirical MIRACL/fingerprint discussion inspected. Mathematical equations were reviewed as source requirements; no claim of implementing them. Hardware/library feasibility assessment is in LIMITATIONS.md. The paper's C++ experiment is distinct from the ZIP's Java/JSP application.
- `DATABASE/Fuzzy.sql`: five empty legacy tables (`user`, `fileupload`, `audit_request`, `cloud_request`, `audit_proof`) reviewed. No DROP statements imported. New relational schema replaces plaintext content/passwords, ambiguous hashes and disconnected request records.

## All 35 legacy JSP routes

All paths below originate in `SOURCE CODE/Fuzzy_IDbased_DataIntegrity/web/`. A modern equivalent preserves the product purpose, not the vulnerable implementation or historical URL spelling. Traceability statuses for application features are in REQUIREMENTS_TRACEABILITY.md.

| Legacy route | Purpose | New route / implementation |
|---|---|---|
| index.jsp | Entry and four-role architecture | /login, /dashboard, /about |
| User.jsp | User login | /login → /otp |
| user_login.jsp | Password/KGC gate and OTP issue | WebController.loginPost, OtpService, approval guards |
| User_reg.jsp | All profile/password/signature fields | /register; AccountService.register; confirmation validated, not persisted |
| user_signup.jsp | Duplicate registration implementation | AccountService.register with hashes/parameterized SQL |
| User_home.jsp | User home | /dashboard (USER) |
| otp.jsp | OTP form | /otp |
| otp1.jsp | OTP verification | OtpService.verify, protected session issuance |
| File_Upload.jsp | Upload form | /user/files and /user/upload |
| MyFiles.jsp | Own file list | /user/files; /files/{id} |
| audit_request.jsp | Owner files eligible for audit | /files/{id} audit form |
| Auditing_request.jsp | Submit audit | /user/files/{id}/audit |
| user_check.jsp | Owner proof list | /audits filtered by requester |
| proof_Check.jsp | Proof list | /audits (role/owner filtered) |
| proof.jsp | Proof page scaffold | /audits/{id} evidence |
| proof_verify.jsp | Browser hash comparison | WorkflowService.verify / AuditProtocol.verify on backend |
| test.jsp | Duplicate browser hash validation | Same backend verifier; no posted expected-hash trust |
| KGC.jsp | KGC login | /login, stored KGC role |
| KGC_home.jsp | KGC home | /dashboard (KGC) |
| keyreq.jsp | Pending key requests/signature | /dashboard → /kgc/users/{id} |
| kgc_users.jsp | User list/status/signature | KGC user list and inspection |
| kgc_idreq.jsp | Full user profile list | /kgc/users/{id} preserves DOB/gender/phone/city/country |
| kgc_request.jsp | KGC request scaffold | KGC inspection decision POST |
| sendkey.jsp | Issuance/notification | AccountService.decide, secure random wrapped application key |
| pic.jsp | Signature binary rendering | /kgc/users/{id}/signature, KGC-protected normalized PNG |
| TPA.jsp | TPA login | /login, stored TPA role |
| TPA_home.jsp | TPA home | /dashboard (TPA) |
| TPA_audit_request.jsp | Pending user audits | /dashboard and /audits queue |
| send_cloud.jsp | Forwarding to cloud | /tpa/audits/{id}/challenge; CHALLENGED state |
| cloud_req.jsp | Forwarded audit list | Persistent CHALLENGED queue and history |
| Cloud.jsp | Cloud login | /login, stored CLOUD role |
| cloud_home.jsp | Cloud home | /dashboard (CLOUD) |
| cloud_audit.jsp | Pending cloud proof requests | CHALLENGED queue; /cloud/audits/{id}/respond |
| audit_proof.jsp | Generates proof by copying old hash | AuditProtocol.respond reads current ciphertext blocks; no reference hashes used |
| All_files.jsp | Cloud file metadata | /cloud/files (no plaintext/keys/trusted hashes) |

## All 10 legacy Java files and remaining assets

| Java file | Replacement / treatment |
|---|---|
| signup.java | AccountService: all fields, duplicate/size/format checks, password hash, normalized signature |
| Upload.java | WorkflowService.upload, CryptoService and trusted block manifest; binary bytes, UUID path, no SQL plaintext/FTP |
| Encryption.java | Explicit AES/GCM/NoPadding with fresh per-file key/nonce/AAD |
| Decryption.java | Current-byte authenticated decryption; no stale fallback |
| KGC.java | Protected stored-role account + OTP, AccountService KGC decisions |
| TPA.java | Protected stored-role account + OTP and TPA transitions |
| clloud.java | Protected stored-role account + OTP and cloud response/tamper operations |
| SQLconnection.java | Spring datasource/environment config and parameterized JdbcTemplate; no legacy connection reuse |
| FTPcon.java | Configured local object store, clearly labelled simulator; no contact with historical FTP account |
| Mail.java | Session/recipient simulated inbox plus configurable SMTP OTP adapter; no legacy external account used |

Legacy `about.html`, `blog.html`, `contact.html`, `support.html`, sliders, fonts, stock images, CSS/JS theme and copyright notice are template/background assets, not distinct project workflows. New local UI assets avoid CDNs. Legacy `js/validation.js` hash checks are replaced by server validation. `Biometric Signature/` examples are preserved reference images, not test recognition evidence. NetBeans/Ant manifests, web.xml, libraries and bundled API documentation are reference dependencies. No obsolete JAR is on the new application's classpath.
