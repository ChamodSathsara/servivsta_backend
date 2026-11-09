-- Reference data required by the requested Gestetner model catalogue.
INSERT INTO manufacturer (manufacturer_name, is_active, created_at, created_by)
SELECT 'test', TRUE, CURRENT_TIMESTAMP, NULL
WHERE NOT EXISTS (
    SELECT 1 FROM manufacturer WHERE LOWER(manufacturer_name) = LOWER('test')
);

INSERT INTO machine_type (machine_type_name, machine_type_description, is_active)
SELECT 'PRODUCTION PRINTER', 'Production printer machine', TRUE
WHERE NOT EXISTS (
    SELECT 1 FROM machine_type WHERE LOWER(machine_type_name) = LOWER('PRODUCTION PRINTER')
);

INSERT INTO machine_model (
    company, manufacturer_id, machine_type_id, model_number, model_name,
    description, is_active, created_at, created_by
)
SELECT
    'GESTETNER', manufacturer.manufacturer_id, machine_type.machine_type_id,
    requested.model_number, requested.model_name,
    'Gestetner production printer model', TRUE, CURRENT_TIMESTAMP, NULL
FROM (
    SELECT '2014' AS model_number, '2014' AS model_name
    UNION ALL SELECT '6123', '6123'
    UNION ALL SELECT '2430', '2430'
    UNION ALL SELECT '3440', '3440'
    UNION ALL SELECT '3442', '3442'
    UNION ALL SELECT '3443', '3443'
    UNION ALL SELECT '3344', '3344'
    UNION ALL SELECT 'BLUE BPS550', 'Blue BPS550'
    UNION ALL SELECT 'CORONA', 'Corona'
    UNION ALL SELECT 'DD4450', 'DD4450'
    UNION ALL SELECT 'DX4545', 'DX4545'
    UNION ALL SELECT 'DX4542', 'DX4542'
    UNION ALL SELECT '5430', '5430'
    UNION ALL SELECT 'DD5450', 'DD5450'
    UNION ALL SELECT 'IMC2000', 'IMC2000'
    UNION ALL SELECT 'IMC2500', 'IMC2500'
    UNION ALL SELECT 'IMC3000', 'IMC3000'
    UNION ALL SELECT 'IMC3500', 'IMC3500'
    UNION ALL SELECT 'IMC4500', 'IMC4500'
    UNION ALL SELECT 'IMC6000', 'IMC6000'
    UNION ALL SELECT 'IMC2010', 'IMC2010'
    UNION ALL SELECT 'M2510', 'M2510'
    UNION ALL SELECT 'M2310', 'M2310'
    UNION ALL SELECT 'M2701', 'M2701'
    UNION ALL SELECT 'IM2702', 'IM2702'
    UNION ALL SELECT 'MC2000', 'MC2000'
    UNION ALL SELECT 'MP C2003', 'MP C2003'
    UNION ALL SELECT 'MP C2503', 'MP C2503'
    UNION ALL SELECT 'MP C3003', 'MP C3003'
    UNION ALL SELECT 'MP C2011', 'MP C2011'
    UNION ALL SELECT 'MP C2004', 'MP C2004'
    UNION ALL SELECT 'MP C2504', 'MP C2504'
    UNION ALL SELECT 'MP C2094', 'MP C2094'
    UNION ALL SELECT 'MP C2594', 'MP C2594'
    UNION ALL SELECT 'MP305', 'MP305'
) requested
JOIN (
    SELECT MIN(manufacturer_id) AS manufacturer_id
    FROM manufacturer
    WHERE LOWER(manufacturer_name) = LOWER('test')
) manufacturer ON manufacturer.manufacturer_id IS NOT NULL
JOIN (
    SELECT MIN(machine_type_id) AS machine_type_id
    FROM machine_type
    WHERE LOWER(machine_type_name) = LOWER('PRODUCTION PRINTER')
) machine_type ON machine_type.machine_type_id IS NOT NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM machine_model existing
    WHERE existing.company = 'GESTETNER'
      AND LOWER(existing.model_number) = LOWER(requested.model_number)
);
