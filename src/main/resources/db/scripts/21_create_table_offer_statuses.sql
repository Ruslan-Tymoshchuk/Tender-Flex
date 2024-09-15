CREATE TABLE offer_statuses (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    offer_id INTEGER REFERENCES tenders(id),
    status VARCHAR,
    last_updated TIMESTAMP
);