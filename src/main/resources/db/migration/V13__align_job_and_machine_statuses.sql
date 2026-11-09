-- Preserve existing records while reducing the application status sets.
UPDATE breakdown
SET status = 'ASSIGNED'
WHERE status IN ('PROCESSING', 'APPROVED', 'TRAVELLING');

UPDATE machine
SET current_status = 'RETURNED'
WHERE current_status = 'AVAILABLE';

UPDATE machine
SET current_status = 'REMOVED'
WHERE current_status = 'CREDIT_NOTE';

UPDATE machine_status_history
SET previous_status = 'RETURNED'
WHERE previous_status = 'AVAILABLE';

UPDATE machine_status_history
SET new_status = 'RETURNED'
WHERE new_status = 'AVAILABLE';

UPDATE machine_status_history
SET previous_status = 'REMOVED'
WHERE previous_status = 'CREDIT_NOTE';

UPDATE machine_status_history
SET new_status = 'REMOVED'
WHERE new_status = 'CREDIT_NOTE';
