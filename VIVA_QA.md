# Viva questions and answers

1. **What problem does the project address?** Authorized storage access and detection of changed outsourced bytes, with a workflow inspired by fuzzy identity auditing.
2. **What is fuzzy identity here?** A synthetic set of integer attributes. The server accepts at least six distinct overlaps with the account's fixed eight-attribute template.
3. **Why does the noisy sample pass?** Six enrolled attributes still match. Five matches fail; repeated copies of an attribute count only once.
4. **Is the signature image a biometric check?** No. It is validated enrollment imagery and a preview. No fingerprint/handwriting recognition is claimed.
5. **What does KGC do?** It reviews registrations and issues random identity-associated application keys with status/time/notices. These are not the paper's mathematical identity-based private keys.
6. **What does TPA do?** It creates a unique expiring challenge and verifies returned ciphertext blocks server-side against trusted upload-time metadata.
7. **What does Cloud do?** It stores local ciphertext objects and rereads the current object for each response. It cannot obtain AES keys or plaintext through its web role.
8. **Encryption versus integrity?** Encryption protects content confidentiality; integrity checks detect modification. AES-GCM also rejects altered ciphertext during decryption. The audit allows checking encrypted storage without decrypting it.
9. **Why SHA-256?** It computes block digests for comparison with trusted reference metadata. A hash alone is not encryption or a digital signature.
10. **Where are keys stored?** Each file has a fresh AES key wrapped under a local master key. Wrapped keys are in trusted SQL records, while ciphertext objects are in `data/cloud`. One machine remains a shared trust boundary.
11. **How are shares approved?** Bob requests an object, its owner decides, and server authorization checks the persisted decision before decryption. Raw AES keys are never emailed. Rejected/revoked requests deny download.
12. **What prevents replay?** Audit ID, nonce, file/version, expiry and exact requested blocks are checked; the terminal state is consumed once under a database row lock. This cannot stop a dishonest server that retained correct old bytes.
13. **Does a sampled PASS guarantee the entire file?** No. It covers only sampled blocks. Full mode checks every block and is used for deterministic live corruption detection.
14. **Can verification recover lost data?** No. Missing/truncated/corrupt objects fail or report ERROR. Recovery requires re-uploading the original as a new version. The build does not implement erasure coding or proof of retrievability.
15. **How does this differ from the paper?** The paper uses pairing-based fuzzy identity signatures, threshold sharing and homomorphic authenticators with a formal soundness model. This prototype uses synthetic overlap, ordinary keys and an O(number of blocks) trusted manifest. It transfers selected encrypted blocks rather than a compact public proof.
16. **Was MySQL actually tested?** Yes, the dedicated local MySQL 8.0.46 database is the primary run; H2 is a separately labelled fallback. Consult TEST_RESULTS.md for the exact executed results.
17. **What remains future work?** The final PPT lists AI security, mobile support, multi-cloud and faster authentication. Exact scheme validation, verified sensor integration and real-world independent infrastructure also remain unfinished.
