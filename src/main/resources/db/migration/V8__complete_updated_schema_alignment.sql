-- Columns present in the updated DBML and entity model but absent from the
-- legacy physical schema.
ALTER TABLE installation_status_history
    ADD COLUMN note VARCHAR(255) NULL;

ALTER TABLE machine_warranty
    ADD COLUMN note TEXT NULL AFTER status;
