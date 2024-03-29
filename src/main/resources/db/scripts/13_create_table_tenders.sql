CREATE TABLE tenders (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    contractor_id INTEGER REFERENCES users(id),
    organization_id INTEGER REFERENCES organizations(id),
    cpv_code VARCHAR NOT NULL,
    tender_type VARCHAR NOT NULL,
    details VARCHAR(250) NOT NULL,
    min_price BIGINT NOT NULL,
    max_price BIGINT NOT NULL,
    currency_id INTEGER REFERENCES currencies(id),
    publication_date TIMESTAMP NOT NULL,
    deadline TIMESTAMP NOT NULL,
    deadline_for_signed_contract TIMESTAMP,
    status VARCHAR NOT NULL,
    contract_url VARCHAR NOT NULL,
    award_decision_url VARCHAR NOT NULL,
    reject_decision_url VARCHAR NOT NULL
);