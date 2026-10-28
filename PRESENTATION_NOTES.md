# Final presentation notes — 16 slides

Reference: unchanged `references/fuzzy-ppt.pptx`, verified 16 slides. The older ZIP deck has 11 slides and is supplementary. Presenter name is **ARPITHA CB**, exactly as supplied; no other name was substituted.

| Slide | Suggested explanation / demonstration |
|---|---|
| 1 — Title | Read the exact title. Identify the build as an educational implementation of the workflow. |
| 2 — Contents | Outline identity, key administration, encrypted storage, TPA/cloud audit and observed results. |
| 3 — Abstract | “We demonstrate tolerance of synthetic identity variation and auditing of actual encrypted stored bytes.” Distinguish these from the paper's stronger primitive. |
| 4 — Introduction | The demonstration's cloud is a local disk simulator, not a deployed cloud service. Discuss outsourced storage motivation without claiming external hosting. |
| 5 — Existing system | RDIC/PDP/POR are research background. Our visible TPA role requests actual bytes and verifies trusted hashes; it does not provide all properties of those research systems. |
| 6 — Proposed system | **Critical wording:** “The research proposal uses fuzzy identity-based cryptography. Our Java prototype implements a server-side set-overlap tolerance gate, ordinary identity-associated application keys, AES-GCM and a trusted block-manifest audit. It does not reproduce pairing-based Setup/Extract, signatures or compact proofs.” Show noisy 6-pass and wrong 4-fail. Do not call application keys identity-based cryptographic private keys. |
| 7 — Project explanation | Show authorized upload/download, encryption, owner-approved sharing, unchanged PASS and deliberate corruption FAIL. Avoid general guarantees of production security. |
| 8 — Hardware | Windows/4 GB assumption guided a small single-process app. The listed 500 GB is machine capacity, not allocation. A WBF sensor is detected, but the demo does not acquire biometrics. |
| 9 — Software | Java 17, Spring Boot, HTML/CSS/JS and tested MySQL; Maven packages the embedded server. No old NetBeans project conversion was needed. H2 is an explicitly labelled fallback. |
| 10 — Architecture | Point to all four roles. User enrollment to KGC; ordinary application key record/notice back; encrypted file storage; user audit request to TPA; TPA challenge to Cloud; current-byte response; TPA result visible to the user. Roles share one machine. |
| 11 — Flow | Demonstrate login plus OTP, file upload/list, file-access request, owner decision, download, audit request and proof evidence. Backend verification is authoritative; the browser never supplies expected hashes or a success flag. |
| 12 — Languages | Java, SQL, HTML, JavaScript and CSS. Generic source tutorials do not add PHP/Python/Swift/appraisal features. Python scripts are test/reference tooling, not the application backend. |
| 13 — Advantages | Cite observed outcomes: exact binary round trips, enforced denial paths, six-overlap tolerance and real corruption detection. Qualify sampled coverage and local trust. |
| 14 — Future enhancements | AI-based security, mobile app, multi-cloud and faster authentication are future work, not hidden implemented features. |
| 15 — Conclusion | **Critical wording:** “The paper establishes its own formal cryptographic results. Our implemented results are the tested educational workflow, encrypted storage and block-manifest integrity checks. We do not claim its soundness theorem, proof of retrievability, compact proofs or biometric accuracy.” Use TEST_RESULTS.md evidence, not generic pass statements from source documents. |
| 16 — Thank you | Invite questions and open About / Method or audit history. |

For secret-key/email wording in IMPLEMENTATION.docx, explain the secure replacement: access is mediated by the application, keys remain server-side, and recipient notices include object/version metadata without raw AES keys. Signature preview is not sensor recognition. Recovery creates a fresh immutable object/version; verification never repairs old data.
