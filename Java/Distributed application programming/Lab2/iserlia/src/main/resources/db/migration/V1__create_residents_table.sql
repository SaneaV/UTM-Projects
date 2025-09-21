CREATE TABLE residents (
                           id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           first_name VARCHAR(50),
                           last_name VARCHAR(50),
                           birth_date DATE,
                           phone_number VARCHAR(20),
                           passport_number VARCHAR(20)
);
