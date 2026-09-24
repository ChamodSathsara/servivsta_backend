ALTER TABLE machine_agreement
    ADD COLUMN note TEXT NULL AFTER previous_agreement_id;
