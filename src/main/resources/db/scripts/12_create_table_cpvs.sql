CREATE TABLE cpvs (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    code VARCHAR NOT NULL,
    summary VARCHAR
);