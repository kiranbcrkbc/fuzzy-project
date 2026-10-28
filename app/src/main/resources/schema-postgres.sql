CREATE TABLE IF NOT EXISTS app_user (
 id VARCHAR(36) PRIMARY KEY, email VARCHAR(190) NOT NULL UNIQUE, display_name VARCHAR(100) NOT NULL,
 password_hash VARCHAR(100) NOT NULL, role VARCHAR(16) NOT NULL, approval VARCHAR(16) NOT NULL,
 dob VARCHAR(16), gender VARCHAR(40), phone VARCHAR(32), city VARCHAR(100), country VARCHAR(100),
 attributes VARCHAR(500) NOT NULL, signature_data BYTEA, key_id VARCHAR(80), wrapped_identity_key TEXT, issued_at BIGINT, created_at BIGINT NOT NULL
);
CREATE TABLE IF NOT EXISTS stored_file (
 id VARCHAR(36) PRIMARY KEY, owner_id VARCHAR(36) NOT NULL, display_name VARCHAR(240) NOT NULL,
 file_version INT NOT NULL, family_id VARCHAR(36) NOT NULL, original_length BIGINT NOT NULL, envelope_length BIGINT NOT NULL,
 wrapped_key TEXT NOT NULL, manifest_json TEXT NOT NULL, manifest_checksum VARCHAR(64) NOT NULL,
 created_at BIGINT NOT NULL, upload_ms DOUBLE PRECISION NOT NULL, demo_owned BOOLEAN NOT NULL,
 FOREIGN KEY(owner_id) REFERENCES app_user(id)
);
CREATE TABLE IF NOT EXISTS share_request (
 id VARCHAR(36) PRIMARY KEY, file_id VARCHAR(36) NOT NULL, requester_id VARCHAR(36) NOT NULL,
 request_state VARCHAR(16) NOT NULL, created_at BIGINT NOT NULL, decided_at BIGINT,
 UNIQUE(file_id,requester_id), FOREIGN KEY(file_id) REFERENCES stored_file(id), FOREIGN KEY(requester_id) REFERENCES app_user(id)
);
CREATE TABLE IF NOT EXISTS audit_record (
 id VARCHAR(36) PRIMARY KEY, file_id VARCHAR(36) NOT NULL, requester_id VARCHAR(36) NOT NULL,
 audit_mode VARCHAR(16) NOT NULL, audit_state VARCHAR(24) NOT NULL, created_at BIGINT NOT NULL,
 challenge_json TEXT, response_json TEXT, result_json TEXT, detail TEXT,
 challenge_ms DOUBLE PRECISION, response_ms DOUBLE PRECISION, verify_ms DOUBLE PRECISION, verified_at BIGINT,
 FOREIGN KEY(file_id) REFERENCES stored_file(id), FOREIGN KEY(requester_id) REFERENCES app_user(id)
);
CREATE TABLE IF NOT EXISTS notification (
 id VARCHAR(36) PRIMARY KEY, recipient_id VARCHAR(36) NOT NULL, message TEXT NOT NULL, created_at BIGINT NOT NULL,
 FOREIGN KEY(recipient_id) REFERENCES app_user(id)
);
CREATE TABLE IF NOT EXISTS tamper_event (
 id VARCHAR(36) PRIMARY KEY, file_id VARCHAR(36) NOT NULL, actor_id VARCHAR(36) NOT NULL, block_index INT NOT NULL, created_at BIGINT NOT NULL
);
CREATE TABLE IF NOT EXISTS cloud_object (
 id VARCHAR(36) PRIMARY KEY, ciphertext BYTEA NOT NULL
);
