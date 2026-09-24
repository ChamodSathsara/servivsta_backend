-- Migrate legacy lookup-table relationships to the enum-based schema.
-- This migration preserves existing rows by translating lookup codes before
-- removing the obsolete lookup tables.

SET FOREIGN_KEY_CHECKS = 0;

-- -------------------------------------------------------------------------
-- Users and staff profiles
-- -------------------------------------------------------------------------
ALTER TABLE users
    ADD COLUMN division VARCHAR(20) NULL AFTER is_active,
    ADD COLUMN area VARCHAR(20) NULL AFTER division;

UPDATE users u
LEFT JOIN division d ON d.division_id = u.division_id
LEFT JOIN area a ON a.area_id = u.primary_area_id
SET u.division = d.division_code,
    u.area = COALESCE(a.area_code, 'COLOMBO');

ALTER TABLE users
    DROP FOREIGN KEY fk_users_division_id,
    DROP FOREIGN KEY fk_users_primary_area_id,
    DROP COLUMN tech_code,
    DROP COLUMN division_id,
    DROP COLUMN primary_area_id,
    MODIFY COLUMN area VARCHAR(20) NOT NULL;

CREATE TABLE technician (
    technician_id BIGINT NOT NULL AUTO_INCREMENT,
    tech_code VARCHAR(20) NOT NULL,
    user_id BIGINT NOT NULL,
    technician_name VARCHAR(150) NOT NULL,
    mobile_number VARCHAR(20) NULL,
    email VARCHAR(150) NOT NULL,
    division VARCHAR(20) NOT NULL,
    area VARCHAR(20) NOT NULL,
    technician_role VARCHAR(30) NOT NULL,
    PRIMARY KEY (technician_id),
    CONSTRAINT uk_technician_tech_code UNIQUE (tech_code),
    CONSTRAINT uk_technician_user UNIQUE (user_id),
    CONSTRAINT uk_technician_email UNIQUE (email),
    CONSTRAINT fk_technician_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE coordinator (
    coordinator_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    coordinator_name VARCHAR(150) NOT NULL,
    coordinator_role VARCHAR(30) NOT NULL,
    mobile_number VARCHAR(20) NULL,
    email VARCHAR(150) NOT NULL,
    division VARCHAR(20) NULL,
    area VARCHAR(20) NULL,
    PRIMARY KEY (coordinator_id),
    CONSTRAINT uk_coordinator_user UNIQUE (user_id),
    CONSTRAINT uk_coordinator_email UNIQUE (email),
    CONSTRAINT fk_coordinator_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE finance (
    finance_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    finance_name VARCHAR(150) NOT NULL,
    mobile_number VARCHAR(20) NULL,
    email VARCHAR(150) NOT NULL,
    PRIMARY KEY (finance_id),
    CONSTRAINT uk_finance_user UNIQUE (user_id),
    CONSTRAINT uk_finance_email UNIQUE (email),
    CONSTRAINT fk_finance_user FOREIGN KEY (user_id) REFERENCES users (user_id)
);

DROP TABLE user_service_area;

-- -------------------------------------------------------------------------
-- Organization and geography enum migrations
-- -------------------------------------------------------------------------
ALTER TABLE user_company ADD COLUMN company VARCHAR(20) NULL;
UPDATE user_company uc JOIN company c ON c.company_id = uc.company_id
SET uc.company = c.company_code;
ALTER TABLE user_company
    DROP FOREIGN KEY fk_user_company_company_id,
    DROP PRIMARY KEY,
    DROP COLUMN company_id,
    ADD COLUMN user_company_id BIGINT NOT NULL AUTO_INCREMENT FIRST,
    ADD PRIMARY KEY (user_company_id),
    MODIFY COLUMN company VARCHAR(20) NOT NULL,
    ADD CONSTRAINT uk_user_company UNIQUE (user_id, company);

ALTER TABLE city ADD COLUMN area VARCHAR(20) NULL AFTER city_id;
UPDATE city cty JOIN area a ON a.area_id = cty.area_id
SET cty.area = a.area_code;
ALTER TABLE city
    DROP FOREIGN KEY fk_city_area_id,
    DROP COLUMN area_id,
    MODIFY COLUMN area VARCHAR(20) NOT NULL;

ALTER TABLE company_customer ADD COLUMN company VARCHAR(20) NULL;
UPDATE company_customer cc JOIN company c ON c.company_id = cc.company_id
SET cc.company = c.company_code;
ALTER TABLE company_customer
    DROP FOREIGN KEY fk_company_customer_company_id,
    DROP PRIMARY KEY,
    DROP COLUMN company_id,
    ADD COLUMN company_customer_id BIGINT NOT NULL AUTO_INCREMENT FIRST,
    ADD PRIMARY KEY (company_customer_id),
    MODIFY COLUMN company VARCHAR(20) NOT NULL,
    ADD CONSTRAINT uk_company_customer UNIQUE (customer_id, company);

ALTER TABLE customer_site ADD COLUMN area VARCHAR(20) NULL AFTER address_line_3;
UPDATE customer_site cs JOIN area a ON a.area_id = cs.area_id
SET cs.area = a.area_code;
ALTER TABLE customer_site
    DROP FOREIGN KEY fk_customer_site_area_id,
    DROP COLUMN area_id,
    MODIFY COLUMN area VARCHAR(20) NOT NULL;

ALTER TABLE salesman ADD COLUMN company VARCHAR(20) NULL AFTER email;
UPDATE salesman s JOIN company c ON c.company_id = s.company_id
SET s.company = c.company_code;
ALTER TABLE salesman
    DROP FOREIGN KEY fk_salesman_company_id,
    DROP COLUMN company_id;

ALTER TABLE machine_model ADD COLUMN company VARCHAR(20) NULL AFTER model_id;
UPDATE machine_model mm JOIN company c ON c.company_id = mm.company_id
SET mm.company = c.company_code;
ALTER TABLE machine_model
    DROP FOREIGN KEY fk_machine_model_company_id,
    DROP COLUMN company_id,
    MODIFY COLUMN company VARCHAR(20) NOT NULL,
    ADD CONSTRAINT uk_machine_model_company_model UNIQUE (company, model_number);

-- -------------------------------------------------------------------------
-- Customer classification enums
-- -------------------------------------------------------------------------
ALTER TABLE customer
    ADD COLUMN customer_grade VARCHAR(20) NULL AFTER is_active,
    ADD COLUMN customer_type VARCHAR(30) NULL AFTER customer_grade,
    ADD COLUMN customer_segment VARCHAR(50) NULL AFTER customer_type;

UPDATE customer c
LEFT JOIN customer_grade cg ON cg.customer_grade_id = c.customer_grade_id
LEFT JOIN customer_type ct ON ct.customer_type_id = c.customer_type_id
LEFT JOIN sub_customer_type sct ON sct.sub_customer_type_id = c.sub_customer_type_id
SET c.customer_grade = CASE cg.grade_code
        WHEN 'STRONG' THEN 'STRONG'
        WHEN 'GOOD' THEN 'GOOD'
        WHEN 'WEAK' THEN 'WEAK'
        ELSE 'UNKNOWN'
    END,
    c.customer_type = CASE ct.customer_type_name
        WHEN 'DEALER' THEN 'DEALER'
        WHEN 'CREDIT_CUSTOMER' THEN 'CREDIT_CUSTOMER'
        WHEN 'INTERNAL_CUSTOMER' THEN 'INTERNAL_CUSTOMER'
        ELSE 'INTERNAL_CUSTOMER'
    END,
    c.customer_segment = COALESCE(sct.sub_customer_type_name, 'OTHERS');

ALTER TABLE customer
    DROP FOREIGN KEY fk_customer_customer_grade_id,
    DROP FOREIGN KEY fk_customer_customer_type_id,
    DROP FOREIGN KEY fk_customer_sub_customer_type_id,
    DROP COLUMN customer_grade_id,
    DROP COLUMN customer_type_id,
    DROP COLUMN sub_customer_type_id,
    MODIFY COLUMN customer_grade VARCHAR(20) NOT NULL,
    MODIFY COLUMN customer_type VARCHAR(30) NOT NULL,
    MODIFY COLUMN customer_segment VARCHAR(50) NOT NULL;

-- -------------------------------------------------------------------------
-- Machine, installation, warranty and agreement enums
-- -------------------------------------------------------------------------
ALTER TABLE machine
    ADD COLUMN company VARCHAR(20) NULL AFTER serial_number,
    ADD COLUMN division VARCHAR(20) NULL AFTER company,
    ADD COLUMN current_status VARCHAR(30) NULL AFTER model_id,
    ADD COLUMN salesman_id BIGINT NULL AFTER rep_id,
    ADD COLUMN note TEXT NULL AFTER original_install_date;

UPDATE machine m
JOIN company c ON c.company_id = m.company_id
JOIN division d ON d.division_id = m.division_id
JOIN machine_status ms ON ms.machine_status_id = m.current_status_id
SET m.company = c.company_code,
    m.division = d.division_code,
    m.current_status = CASE
        WHEN ms.status_code IN ('AVAILABLE', 'INSTALLATION_PENDING', 'ACTIVE_FS', 'ACTIVE_MA',
                                'NO_SERVICE', 'OUT_OF_SERVICE', 'RETURNED', 'CREDIT_NOTE', 'REMOVED')
            THEN ms.status_code
        ELSE 'OUT_OF_SERVICE'
    END;

ALTER TABLE machine
    DROP FOREIGN KEY fk_machine_company_id,
    DROP FOREIGN KEY fk_machine_division_id,
    DROP FOREIGN KEY fk_machine_current_status_id,
    DROP FOREIGN KEY fk_machine_current_main_technician_id,
    DROP FOREIGN KEY fk_machine_current_service_technician_id,
    DROP COLUMN company_id,
    DROP COLUMN division_id,
    DROP COLUMN current_status_id,
    DROP COLUMN current_customer_id,
    DROP COLUMN current_site_id,
    MODIFY COLUMN company VARCHAR(20) NOT NULL,
    MODIFY COLUMN division VARCHAR(20) NOT NULL,
    MODIFY COLUMN current_status VARCHAR(30) NOT NULL,
    ADD CONSTRAINT fk_machine_current_main_technician FOREIGN KEY (current_main_technician_id) REFERENCES technician (technician_id),
    ADD CONSTRAINT fk_machine_current_service_technician FOREIGN KEY (current_service_technician_id) REFERENCES technician (technician_id),
    ADD CONSTRAINT fk_machine_salesman FOREIGN KEY (salesman_id) REFERENCES salesman (salesman_id);

ALTER TABLE machine_status_history
    ADD COLUMN previous_status VARCHAR(30) NULL AFTER machine_id,
    ADD COLUMN new_status VARCHAR(30) NULL AFTER previous_status;
UPDATE machine_status_history msh
LEFT JOIN machine_status previous_ms ON previous_ms.machine_status_id = msh.previous_status_id
JOIN machine_status new_ms ON new_ms.machine_status_id = msh.new_status_id
SET msh.previous_status = CASE
        WHEN previous_ms.status_code IN ('AVAILABLE', 'INSTALLATION_PENDING', 'ACTIVE_FS', 'ACTIVE_MA',
                                         'NO_SERVICE', 'OUT_OF_SERVICE', 'RETURNED', 'CREDIT_NOTE', 'REMOVED')
            THEN previous_ms.status_code
        WHEN previous_ms.status_code IS NULL THEN NULL
        ELSE 'OUT_OF_SERVICE'
    END,
    msh.new_status = CASE
        WHEN new_ms.status_code IN ('AVAILABLE', 'INSTALLATION_PENDING', 'ACTIVE_FS', 'ACTIVE_MA',
                                    'NO_SERVICE', 'OUT_OF_SERVICE', 'RETURNED', 'CREDIT_NOTE', 'REMOVED')
            THEN new_ms.status_code
        ELSE 'OUT_OF_SERVICE'
    END;
ALTER TABLE machine_status_history
    DROP FOREIGN KEY fk_machine_status_history_previous_status_id,
    DROP FOREIGN KEY fk_machine_status_history_new_status_id,
    DROP COLUMN previous_status_id,
    DROP COLUMN new_status_id,
    MODIFY COLUMN new_status VARCHAR(30) NOT NULL;

ALTER TABLE machine_warranty ADD COLUMN warranty_type VARCHAR(20) NULL AFTER machine_id;
UPDATE machine_warranty mw JOIN warranty_type wt ON wt.warranty_type_id = mw.warranty_type_id
SET mw.warranty_type = wt.warranty_type_code;
ALTER TABLE machine_warranty
    DROP FOREIGN KEY fk_machine_warranty_warranty_type_id,
    DROP COLUMN warranty_type_id,
    MODIFY COLUMN warranty_type VARCHAR(20) NOT NULL;

ALTER TABLE installation_job
    ADD COLUMN company VARCHAR(20) NULL AFTER job_number,
    ADD COLUMN division VARCHAR(20) NULL AFTER company;
UPDATE installation_job ij
JOIN company c ON c.company_id = ij.company_id
JOIN division d ON d.division_id = ij.division_id
SET ij.company = c.company_code, ij.division = d.division_code;
ALTER TABLE installation_job
    DROP FOREIGN KEY fk_installation_job_company_id,
    DROP FOREIGN KEY fk_installation_job_division_id,
    DROP FOREIGN KEY fk_installation_job_assigned_technician_id,
    DROP COLUMN company_id,
    DROP COLUMN division_id,
    MODIFY COLUMN company VARCHAR(20) NOT NULL,
    MODIFY COLUMN division VARCHAR(20) NOT NULL,
    ADD CONSTRAINT fk_installation_job_technician FOREIGN KEY (assigned_technician_id) REFERENCES technician (technician_id);

ALTER TABLE installation_submission ADD COLUMN agreement_type_requested VARCHAR(10) NULL AFTER initial_meter_reading;
UPDATE installation_submission ins
JOIN agreement_type atp ON atp.agreement_type_id = ins.agreement_type_requested_id
SET ins.agreement_type_requested = atp.agreement_type_code;
ALTER TABLE installation_submission
    DROP FOREIGN KEY fk_installation_submission_agreement_type_requested_id,
    DROP COLUMN agreement_type_requested_id;

ALTER TABLE machine_agreement ADD COLUMN agreement_type VARCHAR(10) NULL AFTER machine_id;
UPDATE machine_agreement ma JOIN agreement_type atp ON atp.agreement_type_id = ma.agreement_type_id
SET ma.agreement_type = atp.agreement_type_code;
ALTER TABLE machine_agreement
    DROP FOREIGN KEY fk_machine_agreement_agreement_type_id,
    DROP COLUMN agreement_type_id,
    MODIFY COLUMN agreement_type VARCHAR(10) NOT NULL;

-- Technician references now point to the dedicated technician profile.
ALTER TABLE machine_technician_assignment
    DROP FOREIGN KEY fk_machine_technician_assignment_technician_id,
    ADD CONSTRAINT fk_machine_technician_assignment_technician FOREIGN KEY (technician_id) REFERENCES technician (technician_id);
ALTER TABLE service_schedule
    DROP FOREIGN KEY fk_service_schedule_assigned_technician_id,
    ADD CONSTRAINT fk_service_schedule_technician FOREIGN KEY (assigned_technician_id) REFERENCES technician (technician_id);
ALTER TABLE breakdown_technician_assignment
    DROP FOREIGN KEY fk_breakdown_technician_assignment_technician_id,
    ADD CONSTRAINT fk_breakdown_technician_assignment_technician FOREIGN KEY (technician_id) REFERENCES technician (technician_id);
ALTER TABLE estimate
    DROP FOREIGN KEY fk_estimate_technician_id,
    ADD CONSTRAINT fk_estimate_technician FOREIGN KEY (technician_id) REFERENCES technician (technician_id);

-- Role permissions now use the surrogate key defined by the updated schema.
ALTER TABLE role_permission
    DROP PRIMARY KEY,
    ADD COLUMN role_permission_id BIGINT NOT NULL AUTO_INCREMENT FIRST,
    ADD PRIMARY KEY (role_permission_id),
    ADD CONSTRAINT uk_role_permission UNIQUE (role, permission_id);

-- Remove lookup tables replaced by enum-valued columns.
DROP TABLE agreement_type;
DROP TABLE warranty_type;
DROP TABLE machine_status;
DROP TABLE sub_customer_type;
DROP TABLE customer_type;
DROP TABLE customer_grade;
DROP TABLE area;
DROP TABLE division;
DROP TABLE company;

SET FOREIGN_KEY_CHECKS = 1;
