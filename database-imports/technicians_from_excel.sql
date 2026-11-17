-- Technician import prepared from Technician_Base_Team_Leaders_Updated(1).xlsx.
-- Run V22 before this script.
--
-- IMPORTANT VALIDATION ITEMS FROM THE SOURCE FILE:
-- 1. Tech code 8050 is assigned to two different people. Correct one value below.
-- 2. The eight division corrections supplied after the workbook review are applied below.
-- 3. Missing emails receive tech.<tech_code>@servvista.local for internal account storage.
--    Replace those values here if real unique email addresses are available.
-- 4. The script finds the first active ADMIN account and records it as created_by.
--
-- Password for every imported technician: 00000000
-- The database stores the BCrypt hash below, never the plain-text password.

DROP TEMPORARY TABLE IF EXISTS tmp_technician_import;
CREATE TEMPORARY TABLE tmp_technician_import (
    source_row INT NOT NULL,
    tech_code VARCHAR(20) NOT NULL,
    technician_name VARCHAR(150) NOT NULL,
    mobile_number VARCHAR(20) NULL,
    email VARCHAR(150) NULL,
    area VARCHAR(20) NOT NULL,
    cities VARCHAR(1000) NULL,
    is_active TINYINT(1) NOT NULL,
    technician_role VARCHAR(30) NOT NULL,
    company_gestetner TINYINT(1) NOT NULL,
    company_fintech TINYINT(1) NOT NULL,
    division_outright TINYINT(1) NOT NULL,
    division_rental TINYINT(1) NOT NULL
);

INSERT INTO tmp_technician_import (
    source_row, tech_code, technician_name, mobile_number, email, area, cities,
    is_active, technician_role, company_gestetner, company_fintech,
    division_outright, division_rental
) VALUES
(4, '8061', 'DILSHIKA THARINDA', '778404382', '', 'COLOMBO', 'COLOMBO - 01', 1, 'TECHNICIAN', 1, 0, 1, 0),
(5, '3152', 'DINESH SANJEEWA', '774187959', '', 'COLOMBO', 'COLOMBO - 4, COLOMBO - 7,COLOMBO - 2,COLOMBO - 3', 1, 'TECHNICIAN', 1, 0, 1, 0),
(6, '8062', 'LASANTHA SANDARUWAN', '761921951', '', 'COLOMBO', 'COLOMBO - 10', 1, 'TECHNICIAN', 1, 0, 1, 0),
(7, '8050', 'LOCHANA MADUWANTHA', '773608387', '', 'COLOMBO', 'MALABE , BATTARAMULLA , ATHURUGIRIYA , RAJAGIRIYA', 1, 'TECHNICIAN', 1, 0, 1, 0),
(8, '3127', 'FAZLAN AMEEN', '772929506', '', 'COLOMBO', 'COLOMBO - 9,COLOMBO - 11, COLOMBO - 12,COLOMBO - 13,COLOMBO - 14,COLOMBO - 15', 1, 'TECHNICIAN', 1, 0, 1, 0),
(9, '3157', 'DAKSHINA PRABATH', '772385536', '', 'COLOMBO', 'COLOMBO - 01', 1, 'TECHNICIAN', 1, 0, 1, 0),
(10, '3092', 'SUGATH SILVA', '772929515', '', 'COLOMBO', 'COLOMBO - 4,COLOMBO - 7', 1, 'TECHNICIAN', 1, 0, 1, 1),
(11, '8045', 'ROSHEN ABEYSINGHE', '767140259', 'technical@gestetnersl.com', 'COLOMBO', 'COLOMBO - 2,COLOMBO - 3', 1, 'TECHNICIAN', 1, 0, 1, 0),
(12, '9006', 'I.M.R.PRASANNA', '773635023', '', 'COLOMBO', 'COLOMBO', 1, 'TECHNICIAN', 1, 0, 1, 0),
(13, '8059', 'THARINDA CHATHURYAYA', '762492956', '', 'COLOMBO', 'COLOMBO 1 TO 15', 1, 'TECHNICIAN', 0, 1, 1, 0),
(14, '5033', 'H.K.DILUKSHAN', '772104180', 'dilukshan@gestetnersl.com', 'COLOMBO', 'COLOMBO 4,5,6,7,8,9,10, IDH , MULLERIYAWA', 1, 'TECHNICIAN', 0, 1, 1, 0),
(15, '2055', 'S.A.UPUL', '742118051', 'upul@finteksel.com', 'COLOMBO', 'COLOMBO 1,2,3,11,12,13,14,15,', 1, 'TECHNICIAN', 0, 1, 1, 0),
(19, '8034', 'SENAL KANISHKA', '773635019', '', 'SUBURB', 'COLOMBO - 5,6,8', 1, 'TECHNICIAN', 1, 0, 1, 0),
(20, '4050', 'THILINA LAKSHAN', '773635014', '', 'SUBURB', 'KADAWATHA , KALANIYA , BIYAGAMA , WELLAMPITIYA', 1, 'TECHNICIAN', 1, 0, 1, 0),
(21, '8049', 'ISURU GISHANTHA', '740643801', '', 'SUBURB', 'MAHARAGAMA , HOMAGAMA , PANNIPITIYA , KOSGAMA , HANWELLA', 1, 'TECHNICIAN', 1, 0, 1, 0),
(22, '8029', 'RASIKA LAKMAL', '777420857', '', 'SUBURB', 'PILIYANDALA , HORANA , BANDARAGAMA , BULATHSINGHALA , INGIRIYA', 1, 'TECHNICIAN', 1, 0, 1, 0),
(23, '8058', 'SUPUN DILHARA', '766330276', '', 'SUBURB', 'WATTALA , MABOLA , JAELA , RAGAMA , GANEMULLA', 1, 'TECHNICIAN', 1, 0, 1, 0),
(24, '8033', 'KAVINDU KALHARA', '763972768', '', 'SUBURB', 'RAJAGIRIYA , NAWALA , KOHUWALA , DEHIWALA , MOUNT LAVINIA', 1, 'TECHNICIAN', 1, 0, 1, 0),
(25, '8028', 'DAMITH SANJAYA', '763279610', '', 'SUBURB', 'PANADURA ,RATMALANA , MORATUWA ,', 1, 'TECHNICIAN', 1, 0, 1, 0),
(26, '8036', 'ASIRI SASANKA', '773994388', '', 'SUBURB', 'GAMPAHA, DOMPE , MINUWANGODA , VEYANGODA', 1, 'TECHNICIAN', 1, 0, 1, 1),
(27, '8065', 'Kavindu Sahan', '762862948', '', 'SUBURB', 'GAMPAHA, DOMPE , MINUWANGODA , VEYANGODA', 1, 'TECHNICIAN', 1, 0, 1, 0),
(28, '4042', 'GAMINI SAMANTHA', '772961669', '', 'SUBURB', 'MALABE , BATTARAMULLA , ATHURUGIRIYA , RAJAGIRIYA', 1, 'TEAM_LEADER', 1, 0, 1, 0),
(29, '3158', 'M.CHAMILA JAYASASANKA', '777787404', '', 'SUBURB', 'KADAWATHA , KALANIYA , BIYAGAMA , WELLAMPITIYA', 1, 'TEAM_LEADER', 1, 0, 1, 0),
(30, '8047', 'SELVARAJ VIVEK', '773849539', 'selvaraj@gestetnersl.com', 'SUBURB', 'WATTALA , MABOLA , JAELA , RAGAMA , GANEMULLA', 1, 'TECHNICIAN', 1, 0, 1, 0),
(31, '2030', 'THUSHARA JAYANTHA', '770689462', '', 'SUBURB', 'BATTARAMULLA , MAHARAGAMA , KOTTAWA , HOMAGAMA , RAJAGIRIYA', 1, 'TECHNICIAN', 0, 1, 1, 0),
(32, '8050', 'AMAROSA', '740197880', '', 'SUBURB', 'WATTALA , MABOLA , JAELA , RAGAMA , GANEMULLA , GAMPAHA', 1, 'TECHNICIAN', 0, 1, 1, 0),
(36, '3121', 'KALANA BANDARA', '777788965', '', 'OUTSTATION', 'KANDY', 1, 'TEAM_LEADER', 1, 0, 1, 1),
(37, '1024', 'E.M.LAKSHMAN', '773530677', '', 'OUTSTATION', 'KANDY, PERADENIYA', 1, 'TECHNICIAN', 1, 0, 1, 1),
(38, '3138', 'W.G.RUMESH', '772104586', '', 'OUTSTATION', 'MATHALE,KATUGASTHOTA', 1, 'TECHNICIAN', 1, 0, 1, 1),
(39, '3189', 'NILESH NETHMIKA', '764795039', '', 'OUTSTATION', 'KEGALLE, WARAKAPOLA', 1, 'TECHNICIAN', 1, 0, 1, 1),
(40, '3195', 'THARAKA DESHAN', '768274659', '', 'OUTSTATION', 'KANDY SERVICE TECH', 1, 'TECHNICIAN', 1, 0, 1, 1),
(41, '3136', 'MALITH SAMEERA', '777420863', 'sameera@gestetnersl.com', 'OUTSTATION', 'KURUNEGALA', 1, 'TEAM_LEADER', 1, 1, 1, 1),
(42, '3201', 'NUWAN ISHARA', '767397241', '', 'OUTSTATION', 'KURUNEGALA SERVICE TECH', 1, 'TECHNICIAN', 1, 1, 1, 1),
(43, '3123', 'SOBAN N.', '777420850', '', 'OUTSTATION', 'TRINCOMALEE', 1, 'TECHNICIAN', 1, 1, 1, 1),
(44, '3129', 'J.S.RUSHANTH', '771274231', 'rushanth@gestetnersl.com', 'OUTSTATION', 'BATICOLOA,OLUVIL', 1, 'TECHNICIAN', 1, 1, 1, 1),
(45, '3135', 'A.KIRIYUTHAN', '772659141', '', 'OUTSTATION', 'BATICOLOA', 1, 'TECHNICIAN', 1, 1, 1, 1),
(46, '3107', 'GAYAN ASIRI', '767080984', '', 'OUTSTATION', 'ANURADHAPURA', 1, 'TECHNICIAN', 1, 0, 1, 1),
(47, '3141', 'S.MAYUNTHAN', '768274664', '', 'OUTSTATION', 'VAVUNIYA ,MANNAR', 1, 'TECHNICIAN', 1, 1, 1, 1),
(48, '3192', 'S.MATHOOJAN', '772765426', '', 'OUTSTATION', 'VAVUNIYA ,MANNAR SERVICE TCH', 1, 'TECHNICIAN', 1, 1, 1, 1),
(49, '3151', 'W.D.I.CHANUKA WEERASINGHE', '768274674', '', 'OUTSTATION', 'KULIYAPITIYA, WENNAPPUWA,MARAWILA', 1, 'TECHNICIAN', 1, 1, 1, 1),
(50, '8039', 'RUMESH', '760202286', '', 'OUTSTATION', 'COLOMBO', 0, 'TECHNICIAN', 1, 0, 1, 1),
(51, '3187', 'B. VENUTHUS', '773361389', '', 'OUTSTATION', 'JAFFNA, KILINOCHCHI SERVICE TECH', 1, 'TECHNICIAN', 1, 1, 1, 1),
(52, '3176', 'LAKSHITHA RAJAPAKSHA', '770356042', '', 'OUTSTATION', 'POLONNARUWA,DEHIATHTHAKANDIYA', 1, 'TECHNICIAN', 1, 1, 1, 1),
(53, '3184', 'DARSHANA RUKSHAN', '762966537', '', 'OUTSTATION', 'POLONNARUWA,DEHIATHTHAKANDIYA SERVICE TECH', 1, 'TECHNICIAN', 1, 1, 1, 1),
(54, '3180', 'HARSHA MALSHAN', '769240894', '', 'OUTSTATION', 'DAMBULLA,GALGAMUWA,MAWATHAGAMA', 1, 'TECHNICIAN', 1, 1, 1, 1),
(55, '3167', 'NIRUSHAN', '763851347', '', 'OUTSTATION', 'AMPARA', 1, 'TECHNICIAN', 1, 1, 1, 1),
(56, '3102', 'SAMANTHA PUSHPAKUMARA', '777420860', '', 'OUTSTATION', 'BADULLA', 1, 'TEAM_LEADER', 1, 1, 1, 1),
(57, '3161', 'AMAL DINESH GODKUMBURA', '777673802', '', 'OUTSTATION', 'HATTON, NAWALAPITIYA', 1, 'TECHNICIAN', 1, 1, 1, 1),
(58, '3162', 'MAHINDA PRASANNA WEJKOON', '768274670', '', 'OUTSTATION', 'NUWARAELIYA, WELIMADA,BANDARAWELA', 1, 'TECHNICIAN', 1, 1, 1, 1),
(59, '3166', 'PRABHATH NAYANAJITH', '763425378', '', 'OUTSTATION', 'MONRAGALA', 1, 'TECHNICIAN', 1, 1, 1, 1),
(60, '3199', 'LAKSHAN VIMUKTHI', '772156846', '', 'OUTSTATION', 'MAHIYANGANAYA', 1, 'TECHNICIAN', 1, 1, 1, 1),
(61, '3181', 'DILSHAN WIJERATHNA', '777420861', '', 'OUTSTATION', 'MATHARA SERVICE TECH', 1, 'TECHNICIAN', 1, 1, 1, 1),
(62, '3137', 'KAVINDA LIYANAGE', '773669120', '', 'OUTSTATION', 'GALLE', 1, 'TECHNICIAN', 1, 1, 1, 1),
(63, '3197', 'DILAN DARSHANA', '779656087', '', 'OUTSTATION', 'GALLE SERVICE TECH', 1, 'TECHNICIAN', 1, 1, 1, 1),
(64, '3159', 'P.K.GAGANA SAHAN GAWESHA', '768274675', '', 'OUTSTATION', 'HAMBANTHOTA, AMBILIPITIYA', 1, 'TECHNICIAN', 1, 1, 1, 1),
(65, '3196', 'DILSHAN MENUKA', '769924185', '', 'OUTSTATION', 'AMBALANGODA, HIKKADUWA', 1, 'TECHNICIAN', 1, 1, 1, 1),
(66, '3185', 'THARINDU THATHSARA', '768847357', '', 'OUTSTATION', 'HAMBANTHOTA, AMBILIPITIYA SERVICE TECH', 1, 'TECHNICIAN', 1, 1, 1, 1),
(67, '3174', 'M PRIYANATH JAYANTHA', '773635012', '', 'OUTSTATION', 'RATHNAPURA,BALANGODA', 1, 'TECHNICIAN', 1, 0, 1, 0),
(68, '3191', 'PRADEEP SAMPATH', '742586360', '', 'OUTSTATION', 'RATHNAPURA,BALANGODA SERVICE TECH', 1, 'TECHNICIAN', 1, 0, 1, 1),
(69, '3168', 'VISHARA AVISHKA', '768713047', '', 'OUTSTATION', 'KALUTHARA,MATHUGAMA', 1, 'TECHNICIAN', 1, 1, 1, 1),
(70, '3177', 'RASITHA PERERA', '768518361', '', 'OUTSTATION', 'AVISSAWELLA', 1, 'TECHNICIAN', 1, 1, 1, 1),
(71, '3163', 'VIJITHA BANDARA', '773618104', '', 'OUTSTATION', 'MATHALE,KANDY', 1, 'TECHNICIAN', 0, 1, 0, 1),
(72, '3170', 'ISURU SHALINDA', '777420855', '', 'OUTSTATION', 'ANURADHAPURA', 1, 'TECHNICIAN', 0, 1, 0, 1),
(73, '3164', 'DAMITH ERANDA', '773828032', '', 'OUTSTATION', 'MATHARA, AMBALANTHOTA', 1, 'TECHNICIAN', 0, 1, 0, 1);

-- Review these before importing. Both result sets must be empty.
SELECT tech_code, GROUP_CONCAT(CONCAT(technician_name, ' (Excel row ', source_row, ')')
    ORDER BY source_row SEPARATOR ', ') AS conflicting_people
FROM tmp_technician_import
GROUP BY tech_code
HAVING COUNT(*) > 1;

SELECT source_row, tech_code, technician_name
FROM tmp_technician_import
WHERE division_outright = 0 AND division_rental = 0
ORDER BY source_row;

-- Example corrections (remove the leading -- and supply the correct values):
-- UPDATE tmp_technician_import SET tech_code = 'CORRECT_CODE' WHERE source_row = 32;

DROP PROCEDURE IF EXISTS import_technicians_from_staging;
DELIMITER $$

CREATE PROCEDURE import_technicians_from_staging()
BEGIN
    DECLARE admin_user_id BIGINT;

    IF EXISTS (
        SELECT 1
        FROM tmp_technician_import
        GROUP BY tech_code
        HAVING COUNT(*) > 1
    ) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Import stopped: duplicate tech codes exist in staging';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM tmp_technician_import
        WHERE division_outright = 0 AND division_rental = 0
    ) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Import stopped: one or more technicians have no division';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM tmp_technician_import
        WHERE company_gestetner = 0 AND company_fintech = 0
    ) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Import stopped: one or more technicians have no company';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM tmp_technician_import s
        JOIN technician t ON t.tech_code = s.tech_code
    ) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Import stopped: a staged tech code already exists';
    END IF;

    IF EXISTS (
        SELECT 1
        FROM tmp_technician_import s
        JOIN users u ON LOWER(u.email) = LOWER(
            COALESCE(NULLIF(TRIM(s.email), ''),
                CONCAT('tech.', LOWER(s.tech_code), '@servvista.local'))
        )
    ) THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Import stopped: a staged email already exists';
    END IF;

    SELECT user_id INTO admin_user_id
    FROM users
    WHERE role = 'ADMIN' AND is_active = 1
    ORDER BY user_id
    LIMIT 1;

    IF admin_user_id IS NULL THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Import stopped: no active ADMIN account exists';
    END IF;

    START TRANSACTION;

    INSERT INTO users (
        user_name, mobile_number, email, password_hash, is_active,
        division, area, created_at, created_by, role
    )
    SELECT
        TRIM(technician_name),
        CASE
            WHEN TRIM(mobile_number) REGEXP '^[0-9]{9}$'
                THEN CONCAT('0', TRIM(mobile_number))
            ELSE NULLIF(TRIM(mobile_number), '')
        END,
        COALESCE(NULLIF(LOWER(TRIM(email)), ''),
            CONCAT('tech.', LOWER(tech_code), '@servvista.local')),
        '$2a$12$3mgVb3FWCRFru2DfzuHJRuL1VPXXgnXPZr1LxVnn2Pkl1SMFEEQxS',
        is_active,
        CASE WHEN division_outright = 1 THEN 'OUTRIGHT' ELSE 'RENTAL' END,
        area,
        CURRENT_TIMESTAMP,
        admin_user_id,
        'TECHNICIAN'
    FROM tmp_technician_import
    ORDER BY source_row;

    INSERT INTO technician (
        tech_code, user_id, technician_name, mobile_number, email,
        division, area, cities, technician_role
    )
    SELECT
        s.tech_code,
        u.user_id,
        s.technician_name,
        u.mobile_number,
        u.email,
        u.division,
        s.area,
        NULLIF(TRIM(s.cities), ''),
        s.technician_role
    FROM tmp_technician_import s
    JOIN users u ON LOWER(u.email) = LOWER(
        COALESCE(NULLIF(TRIM(s.email), ''),
            CONCAT('tech.', LOWER(s.tech_code), '@servvista.local'))
    )
    ORDER BY s.source_row;

    INSERT INTO user_company (user_id, company)
    SELECT u.user_id, 'GESTETNER'
    FROM tmp_technician_import s
    JOIN users u ON LOWER(u.email) = LOWER(
        COALESCE(NULLIF(TRIM(s.email), ''),
            CONCAT('tech.', LOWER(s.tech_code), '@servvista.local'))
    )
    WHERE s.company_gestetner = 1
    UNION ALL
    SELECT u.user_id, 'FINTECH'
    FROM tmp_technician_import s
    JOIN users u ON LOWER(u.email) = LOWER(
        COALESCE(NULLIF(TRIM(s.email), ''),
            CONCAT('tech.', LOWER(s.tech_code), '@servvista.local'))
    )
    WHERE s.company_fintech = 1;

    INSERT INTO technician_division (technician_id, division)
    SELECT t.technician_id, 'OUTRIGHT'
    FROM tmp_technician_import s
    JOIN technician t ON t.tech_code = s.tech_code
    WHERE s.division_outright = 1
    UNION ALL
    SELECT t.technician_id, 'RENTAL'
    FROM tmp_technician_import s
    JOIN technician t ON t.tech_code = s.tech_code
    WHERE s.division_rental = 1;

    COMMIT;
END$$

DELIMITER ;

CALL import_technicians_from_staging();

-- These cleanup statements run after a successful import.
DROP PROCEDURE import_technicians_from_staging;
DROP TEMPORARY TABLE tmp_technician_import;

-- Final verification.
SELECT
    t.technician_id,
    t.tech_code,
    t.technician_name,
    t.mobile_number,
    t.email,
    t.area,
    t.cities,
    GROUP_CONCAT(DISTINCT td.division ORDER BY td.division) AS divisions,
    GROUP_CONCAT(DISTINCT uc.company ORDER BY uc.company) AS companies,
    u.is_active
FROM technician t
JOIN users u ON u.user_id = t.user_id
LEFT JOIN technician_division td ON td.technician_id = t.technician_id
LEFT JOIN user_company uc ON uc.user_id = u.user_id
GROUP BY
    t.technician_id, t.tech_code, t.technician_name, t.mobile_number,
    t.email, t.area, t.cities, u.is_active
ORDER BY t.tech_code;

