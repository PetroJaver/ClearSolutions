CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR CHECK ( email LIKE '%@%' ),
    first_name VARCHAR,
    last_name VARCHAR,
    birth_date DATE,
    address VARCHAR,
    phone_number VARCHAR CHECK (phone_number ~ '^\+?\d{1,3}?[-\s]?\(?\d{3}\)?[-\s]?\d{3}[-\s]?\d{4}$')
);