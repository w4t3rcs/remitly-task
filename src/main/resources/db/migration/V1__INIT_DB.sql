CREATE TABLE headquarters_branches
(
    branch_id        VARCHAR(255) NOT NULL,
    headquarter_code VARCHAR(255) NOT NULL
);

CREATE TABLE swift_codes
(
    code              VARCHAR(16) PRIMARY KEY,
    bank_name         VARCHAR(255) NOT NULL,
    address           VARCHAR(255) NOT NULL,
    country_iso2      VARCHAR(2) NOT NULL,
    country_name      VARCHAR(255) NOT NULL,
    is_headquarter    INTEGER(1) NOT NULL
);

ALTER TABLE headquarters_branches
    ADD CONSTRAINT uc_headquarters_branches_branch UNIQUE (branch_id);

ALTER TABLE headquarters_branches
    ADD CONSTRAINT fk_heabra_on_branch FOREIGN KEY (branch_id) REFERENCES swift_codes (code);

ALTER TABLE headquarters_branches
    ADD CONSTRAINT fk_heabra_on_headquarter_code FOREIGN KEY (headquarter_code) REFERENCES swift_codes (code);