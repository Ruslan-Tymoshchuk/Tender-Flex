CREATE TABLE countries (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    country_name VARCHAR UNIQUE NOT NULL
);