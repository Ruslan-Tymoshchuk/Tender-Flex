CREATE TABLE tokens (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    jwt_token VARCHAR UNIQUE NOT NULL,
    token_type VARCHAR NOT NULL,
    revoked BOOLEAN NOT NULL,
    expired BOOLEAN NOT NULL
);