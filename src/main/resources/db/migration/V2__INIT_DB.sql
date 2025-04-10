CREATE TABLE IF NOT EXISTS swift_codes
(
    code               VARCHAR(16) PRIMARY KEY,
    bank_name          VARCHAR(255) NOT NULL,
    address            VARCHAR(255),
    country_iso2       VARCHAR(2) NOT NULL,
    country_name       VARCHAR(255) NOT NULL,
    is_headquarter     SMALLINT NOT NULL
);

CREATE TABLE IF NOT EXISTS headquarters_branches
(
    branch_code        VARCHAR(255) NOT NULL,
    headquarter_code   VARCHAR(255) NOT NULL,
    FOREIGN KEY(branch_code) REFERENCES swift_codes(code),
    FOREIGN KEY(headquarter_code) REFERENCES swift_codes(code)
);
