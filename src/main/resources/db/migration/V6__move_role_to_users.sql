ALTER TABLE users
    ADD COLUMN role VARCHAR(40) NULL AFTER updated_by;

UPDATE users u
JOIN (
    SELECT ur.user_id,
           CASE r.role_code
               WHEN 'MAIN_TECHNICIAN' THEN 'TECHNICIAN'
               WHEN 'SERVICE_TECHNICIAN' THEN 'TECHNICIAN'
               WHEN 'WORKSHOP_ENGINEER' THEN 'WORKSHOP_TECHNICIAN'
               ELSE r.role_code
           END AS migrated_role
    FROM user_role ur
    JOIN role r ON r.role_id = ur.role_id
    WHERE ur.is_primary = 1
) primary_role ON primary_role.user_id = u.user_id
SET u.role = primary_role.migrated_role;

UPDATE users u
JOIN (
    SELECT ur.user_id,
           CASE MIN(ur.role_id)
               WHEN 1 THEN 'TECHNICIAN'
               WHEN 2 THEN 'TECHNICIAN'
               WHEN 3 THEN 'TEAM_LEADER'
               WHEN 4 THEN 'WORKSHOP_TECHNICIAN'
               WHEN 5 THEN 'AREA_MANAGER'
               WHEN 6 THEN 'MANAGER'
               WHEN 7 THEN 'DATA_ENTRY_OPERATOR'
               WHEN 8 THEN 'SALESMAN'
               WHEN 9 THEN 'FINANCE'
               WHEN 10 THEN 'ADMIN'
           END AS migrated_role
    FROM user_role ur
    GROUP BY ur.user_id
) any_role ON any_role.user_id = u.user_id
SET u.role = any_role.migrated_role
WHERE u.role IS NULL;

ALTER TABLE role_permission
    DROP FOREIGN KEY fk_role_permission_role_id,
    ADD COLUMN role VARCHAR(40) NULL AFTER role_id;

UPDATE role_permission rp
JOIN role r ON r.role_id = rp.role_id
SET rp.role = CASE r.role_code
    WHEN 'MAIN_TECHNICIAN' THEN 'TECHNICIAN'
    WHEN 'SERVICE_TECHNICIAN' THEN 'TECHNICIAN'
    WHEN 'WORKSHOP_ENGINEER' THEN 'WORKSHOP_TECHNICIAN'
    ELSE r.role_code
END;

ALTER TABLE role_permission
    DROP PRIMARY KEY,
    DROP COLUMN role_id,
    MODIFY COLUMN role VARCHAR(40) NOT NULL,
    ADD PRIMARY KEY (role, permission_id);

ALTER TABLE users
    MODIFY COLUMN role VARCHAR(40) NOT NULL,
    DROP COLUMN must_change_password;

DROP TABLE user_role;
DROP TABLE role;
