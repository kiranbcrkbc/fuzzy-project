# Public repository safety review

Reviewed 28 October 2026 before changing the exact repository `kiranbcrkbc/fuzzy-project` from private to public. Public visibility is explicitly authorized by the owner. No history rewrite, force-push or credential rotation was required by the findings below.

## Scope and method

- Fetched origin and tags. The remote has one branch, main, and no tags. Before this review there were two commits, ending in 987005b.
- Enumerated every reachable commit/tree/blob, including local reflog reachability: 94 distinct historical blobs and 88 current tracked files. Checked both historical paths and contents; `.gitignore` was not treated as a history scrubber.
- Ran Gitleaks 8.30.1 from its official GitHub release, verified the downloaded archive against the official release checksum, and scanned all Git history with `--all --full-history` and redacted output.
- Exported the current tracked tree and scanned it independently with Gitleaks. Inspected credential/configuration-related source and documentation and the known synthetic fixture files.
- Checked exclusions for runtime MySQL configuration, master keys, H2 files, supplied legacy archive, screenshots and report documents. None occurs in reachable history.

## Findings and disposition

The raw history and current-tree scans each flagged one item: AGENTS.md line157. It is an encryption requirement naming the public Java transformation AES/GCM/NoPadding, not an API token, key or password. The historical finding is precisely recorded in `.gitleaksignore` by commit/file/rule/line fingerprint. No broad path or secret-rule exclusion was added. The reviewed history scan then reported no leaks. The current-tree raw result has the same manually reviewed algorithm-name false positive and no other finding.

The supplemental complete-blob pattern/path scan found no private-key or provider-token patterns and no forbidden runtime/reference/build/report paths. No real credentials, database contents, session/OTP state, uploaded personal content or sensitive legacy configuration was identified in the current tracked tree or reachable history. Scanner results are evidence of these checks, not a guarantee against every conceivable secret format.

Local-only demo passwords and isolated test-fixture passwords are intentional synthetic examples, not third-party credentials. Hosted mode rejects the published local password, missing/short secrets and example placeholders, hides local credentials and refuses silent credential changes. An externally hosted instance must use a fresh private secret and isolated synthetic data. No hosted secret is committed.

The binary and image fixtures under demo-samples are intentionally generated synthetic content. Actual runtime ciphertext, keys, database records, reports, screenshots, supplied ZIP/DOCX/PDF and legacy source remain local and ignored. The legacy reference scan found credential/service indicators in19 files in the earlier review; the entire references tree is excluded rather than publishing those old configurations.

## Reproduction and private evidence

`scripts/audit_public_history.py` enumerates the history and checks forbidden paths/credential patterns without printing matching values. `scripts/check_publish_safety.py` checks staged files. Gitleaks history command:

```text
gitleaks git . --log-opts="--all --full-history" --redact --report-format json --report-path <private-evidence-path>
```

Raw/reviewed scanner reports and historical blob manifests are retained locally in ignored `evidence/deployment/public-audit/`. Do not add those reports if they ever contain an actual finding. Future secrets must be rotated and historical exposure remediated before publication; deleting only the current file is insufficient.
