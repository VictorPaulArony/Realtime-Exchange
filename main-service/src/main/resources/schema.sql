DROP TABLE IF EXISTS conversions;

CREATE TABLE conversions (
    id SERIAL PRIMARY KEY,
    from_currency VARCHAR(3) NOT NULL,
    to_currency VARCHAR(3) NOT NULL,
    amount DECIMAL(19,4) NOT NULL,
    rate DECIMAL(19,6) NOT NULL,
    result DECIMAL(19,4) NOT NULL,
    timestamp TIMESTAMP NOT NULL
);

GRANT ALL PRIVILEGES ON TABLE conversions TO myuser;
GRANT USAGE, SELECT ON SEQUENCE conversions_id_seq TO myuser;
