CREATE TABLE offer_statuses (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    contractor VARCHAR,
    bidder VARCHAR,
    tender VARCHAR
);