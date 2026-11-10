CREATE TEMPORARY TABLE tmp_seed_number (
    number_value INT NOT NULL PRIMARY KEY
);

CREATE TEMPORARY TABLE tmp_seed_model_number (
    number_value INT NOT NULL PRIMARY KEY
);

INSERT INTO tmp_seed_number (number_value)
SELECT ones.digit + (tens.digit * 10) + (hundreds.digit * 100) + 1
FROM
    (SELECT 0 AS digit UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
     UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) ones
CROSS JOIN
    (SELECT 0 AS digit UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4
     UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) tens
CROSS JOIN
    (SELECT 0 AS digit UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4) hundreds
WHERE ones.digit + (tens.digit * 10) + (hundreds.digit * 100) + 1 <= 444;

-- MySQL cannot reference the same temporary table twice in one statement.
-- Keep a separate, small number source for the model side of the mapping join.
INSERT INTO tmp_seed_model_number (number_value)
SELECT number_value
FROM tmp_seed_number
WHERE number_value BETWEEN 18 AND 52;

INSERT IGNORE INTO part_machine_model (part_id, model_id)
SELECT part_number.number_value, model_number.number_value
FROM tmp_seed_number part_number
JOIN part part_record ON part_record.part_id = part_number.number_value
CROSS JOIN tmp_seed_model_number model_number
JOIN machine_model model_record ON model_record.model_id = model_number.number_value
WHERE
    -- Main model groups.
    (part_number.number_value BETWEEN 1 AND 70 AND model_number.number_value = 18)
    OR (part_number.number_value BETWEEN 71 AND 98 AND model_number.number_value BETWEEN 19 AND 24)
    OR (part_number.number_value BETWEEN 99 AND 110 AND model_number.number_value = 25)
    OR (part_number.number_value BETWEEN 111 AND 144 AND model_number.number_value = 26)
    OR (part_number.number_value BETWEEN 145 AND 190 AND model_number.number_value BETWEEN 27 AND 30)
    OR (part_number.number_value BETWEEN 191 AND 207 AND model_number.number_value = 31)
    OR (part_number.number_value BETWEEN 208 AND 245 AND model_number.number_value BETWEEN 32 AND 37)
    OR (part_number.number_value BETWEEN 246 AND 272 AND model_number.number_value = 38)
    OR (part_number.number_value BETWEEN 273 AND 327 AND model_number.number_value BETWEEN 39 AND 40)
    OR (part_number.number_value BETWEEN 328 AND 356 AND model_number.number_value BETWEEN 41 AND 42)
    OR (part_number.number_value = 357 AND model_number.number_value = 43)
    OR (part_number.number_value BETWEEN 358 AND 393
        AND part_number.number_value NOT IN (361, 362, 363, 374, 375, 376, 377, 378, 380)
        AND model_number.number_value BETWEEN 44 AND 51)
    OR (part_number.number_value IN (361, 362, 363, 374, 375, 376, 377, 378, 380)
        AND model_number.number_value BETWEEN 44 AND 47)
    OR (part_number.number_value BETWEEN 394 AND 409 AND model_number.number_value BETWEEN 48 AND 51)
    OR (part_number.number_value BETWEEN 410 AND 444 AND model_number.number_value = 52)

    -- Cross-compatible model exceptions supplied with the import mapping.
    OR (part_number.number_value IN (4, 6, 8, 15, 22, 23, 24, 25, 26, 27, 29, 31, 34, 37, 56, 66, 67)
        AND model_number.number_value BETWEEN 41 AND 42)
    OR (part_number.number_value = 7 AND model_number.number_value IN (41, 42, 52))
    OR (part_number.number_value IN (11, 55) AND model_number.number_value = 52)
    OR (part_number.number_value IN (185, 186) AND model_number.number_value = 31)
    OR (part_number.number_value IN (132, 139, 140) AND model_number.number_value BETWEEN 44 AND 51)
    OR (part_number.number_value = 138
        AND (model_number.number_value BETWEEN 32 AND 38
             OR model_number.number_value BETWEEN 41 AND 51))
    OR (part_number.number_value = 141 AND model_number.number_value BETWEEN 32 AND 37)
    OR (part_number.number_value = 142 AND model_number.number_value BETWEEN 48 AND 51)
    OR (part_number.number_value IN (208, 209, 211, 214, 217, 222, 223, 224, 225, 226,
                                     233, 234, 235, 236, 238, 240, 241, 242, 244)
        AND model_number.number_value = 43)
    OR (part_number.number_value IN (210, 212)
        AND model_number.number_value BETWEEN 41 AND 43)
    OR (part_number.number_value IN (213, 241)
        AND model_number.number_value = 38)
    OR (part_number.number_value IN (217, 233, 234, 235, 236)
        AND model_number.number_value BETWEEN 44 AND 51)
    OR (part_number.number_value = 218 AND model_number.number_value BETWEEN 44 AND 51)
    OR (part_number.number_value = 221
        AND (model_number.number_value IN (38, 43)
             OR model_number.number_value BETWEEN 48 AND 51))
    OR (part_number.number_value = 245 AND model_number.number_value BETWEEN 41 AND 42)
    OR (part_number.number_value = 250 AND model_number.number_value = 43);

DROP TEMPORARY TABLE tmp_seed_number;
DROP TEMPORARY TABLE tmp_seed_model_number;
