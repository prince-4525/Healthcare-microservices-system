CREATE TABLE patient
(
    id              BINARY(16)   NOT NULL,
    name            VARCHAR(255) NOT NULL,
    email           VARCHAR(255) NOT NULL,
    address         VARCHAR(255) NOT NULL,
    date_of_birth   date         NOT NULL,
    registered_date date         NOT NULL,
    CONSTRAINT pk_patient PRIMARY KEY (id)
);

ALTER TABLE patient
    ADD CONSTRAINT uc_patient_email UNIQUE (email);