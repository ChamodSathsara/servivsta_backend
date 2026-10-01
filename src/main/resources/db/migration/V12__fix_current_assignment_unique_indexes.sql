-- MySQL has no native partial unique indexes. The previous schema accidentally
-- made is_current globally unique, allowing only one current row in each table.

ALTER TABLE machine_assignment
    DROP INDEX is_current,
    ADD COLUMN current_machine_id BIGINT
        GENERATED ALWAYS AS (
            CASE WHEN is_current = 1 THEN machine_id ELSE NULL END
        ) STORED,
    ADD UNIQUE INDEX uq_machine_assignment_current_machine (current_machine_id),
    ADD INDEX idx_machine_assignment_machine_current (machine_id, is_current);

ALTER TABLE machine_technician_assignment
    DROP INDEX is_current,
    ADD COLUMN current_machine_id BIGINT
        GENERATED ALWAYS AS (
            CASE WHEN is_current = 1 THEN machine_id ELSE NULL END
        ) STORED,
    ADD UNIQUE INDEX uq_machine_technician_current_role (
        current_machine_id,
        assignment_role
    ),
    ADD INDEX idx_machine_technician_machine_role_current (
        machine_id,
        assignment_role,
        is_current
    );
