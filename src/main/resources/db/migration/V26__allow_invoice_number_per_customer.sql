SET @invoice_unique_index = (
    SELECT INDEX_NAME
    FROM INFORMATION_SCHEMA.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = 'machine_invoice'
      AND COLUMN_NAME = 'invoice_number'
      AND NON_UNIQUE = 0
      AND INDEX_NAME <> 'PRIMARY'
    LIMIT 1
);

SET @drop_invoice_index_sql = IF(
    @invoice_unique_index IS NULL,
    'SELECT 1',
    CONCAT('ALTER TABLE machine_invoice DROP INDEX `', @invoice_unique_index, '`')
);

PREPARE drop_invoice_index_stmt FROM @drop_invoice_index_sql;
EXECUTE drop_invoice_index_stmt;
DEALLOCATE PREPARE drop_invoice_index_stmt;

ALTER TABLE machine_invoice
    ADD CONSTRAINT uk_machine_invoice_number_customer
    UNIQUE (invoice_number, customer_id);
