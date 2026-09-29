ALTER TABLE breakdown
    ADD COLUMN expected_completion_at TIMESTAMP NULL AFTER created_at;
