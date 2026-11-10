INSERT INTO part_category (
    category_code, category_name, description, is_active, created_at, created_by
)
SELECT
    requested.category_code,
    requested.category_name,
    requested.category_name,
    TRUE,
    CURRENT_TIMESTAMP,
    NULL
FROM (
    SELECT 'CONSUMABLE_PARTS' AS category_code, 'Consumable Parts' AS category_name
    UNION ALL SELECT 'DRIVE_FEED_SECTION', 'Drive & Feed Section'
    UNION ALL SELECT 'DRIVE_SECTION', 'Drive Section'
    UNION ALL SELECT 'DRUM_UNIT_ACCESSORIES', 'Drum Unit & Accessories'
    UNION ALL SELECT 'FUSING_HEATER_UNIT', 'Fusing / Heater Unit'
    UNION ALL SELECT 'IMAGE_TRANSFER_BELT', 'Image Transfer Belt'
    UNION ALL SELECT 'MASTER_SECTION', 'Master Section'
    UNION ALL SELECT 'OTHER', 'Other'
    UNION ALL SELECT 'PCBS_ELECTRONICS', 'PCBs / Electronics'
    UNION ALL SELECT 'PAPER_FEED_SECTION', 'Paper Feed Section'
    UNION ALL SELECT 'PHOTO_DRUM_DEVELOPER', 'Photo Conductive / Drum / Developer'
    UNION ALL SELECT 'PLASTIC_COVERS_TRAYS', 'Plastic Covers & Trays'
    UNION ALL SELECT 'RUBBER_ITEMS', 'Rubber Items'
    UNION ALL SELECT 'SCANNING_LASER_UNIT', 'Scanning / Laser Unit'
    UNION ALL SELECT 'SENSORS', 'Sensors'
    UNION ALL SELECT 'TRAY_BYPASS', 'Tray & Bypass'
    UNION ALL SELECT 'UNCATEGORIZED_SOURCE', 'Uncategorized (Source Section Unclear)'
    UNION ALL SELECT 'WIRE_HARNESSES', 'Wire Harnesses'
) requested
WHERE NOT EXISTS (
    SELECT 1
    FROM part_category existing
    WHERE LOWER(existing.category_code) = LOWER(requested.category_code)
);
