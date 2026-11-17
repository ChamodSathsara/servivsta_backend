ALTER TABLE technician
    ADD COLUMN cities VARCHAR(1000) NULL AFTER area;

CREATE TABLE technician_division (
    technician_id BIGINT NOT NULL,
    division VARCHAR(20) NOT NULL,
    PRIMARY KEY (technician_id, division),
    CONSTRAINT fk_technician_division_technician
        FOREIGN KEY (technician_id) REFERENCES technician (technician_id)
        ON DELETE CASCADE
);

-- Preserve every existing technician's current division as their first assignment.
INSERT INTO technician_division (technician_id, division)
SELECT technician_id, division
FROM technician;
