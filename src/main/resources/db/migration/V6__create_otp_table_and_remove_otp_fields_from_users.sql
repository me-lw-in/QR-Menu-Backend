CREATE TABLE otp_verifications (
phone_number VARCHAR(15) PRIMARY KEY,
otp VARCHAR(10) NOT NULL,
generated_at TIMESTAMP NOT NULL
);


ALTER TABLE users DROP COLUMN otp;
ALTER TABLE users DROP COLUMN otp_generated_at;