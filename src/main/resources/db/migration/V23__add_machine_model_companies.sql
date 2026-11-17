CREATE TABLE machine_model_company (
    model_id BIGINT NOT NULL,
    company VARCHAR(20) NOT NULL,
    PRIMARY KEY (model_id, company),
    CONSTRAINT fk_machine_model_company_model
        FOREIGN KEY (model_id) REFERENCES machine_model(model_id) ON DELETE CASCADE
);

INSERT INTO machine_model_company (model_id, company)
SELECT model_id, company
FROM machine_model;
