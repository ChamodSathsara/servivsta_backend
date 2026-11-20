INSERT INTO city (area, city_name, is_active)
SELECT 'UNKNOWN', 'UNKNOWN', 1
WHERE NOT EXISTS (
    SELECT 1 FROM city WHERE area = 'UNKNOWN' AND UPPER(city_name) = 'UNKNOWN'
);
