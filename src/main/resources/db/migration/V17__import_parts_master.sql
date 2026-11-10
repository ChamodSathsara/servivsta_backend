INSERT INTO part (part_code, part_name, description, unit_price, is_active, created_by, created_at)
SELECT requested.part_code, requested.part_code, requested.description, NULL, TRUE, NULL, CURRENT_TIMESTAMP
FROM (
    SELECT 'AB010601' AS part_code, 'GEAR DRIVE DEV.ROLLER' AS description
    UNION ALL SELECT 'AB017869' AS part_code, 'GEAR DEV.DRIVE IDLER' AS description
    UNION ALL SELECT 'AX060602' AS part_code, 'BRUSHLESS MOTOR:' AS description
    UNION ALL SELECT 'AX202690' AS part_code, 'REGISTRATION CLUTCH' AS description
    UNION ALL SELECT 'GX640028' AS part_code, 'FAN MOTOR SMALL' AS description
    UNION ALL SELECT 'GX645734' AS part_code, 'FAN MOTOR:LARGE' AS description
    UNION ALL SELECT 'AA066637' AS part_code, 'STRIPPER SPRING(1600/2014)' AS description
    UNION ALL SELECT 'AB011621' AS part_code, 'GEAR:HOT ROLLER' AS description
    UNION ALL SELECT 'AE011133' AS part_code, 'HOT ROLLER(MP2014)' AS description
    UNION ALL SELECT 'AE020235' AS part_code, 'PRESSURE ROLLER:MM25' AS description
    UNION ALL SELECT 'AE044073' AS part_code, 'Stripper Pawls 2014' AS description
    UNION ALL SELECT 'AW100167' AS part_code, 'Thermistor :2014' AS description
    UNION ALL SELECT 'D0B24612' AS part_code, 'TRANSFER FRAME' AS description
    UNION ALL SELECT 'D2454113' AS part_code, 'HEATER:120V:700W:' AS description
    UNION ALL SELECT 'D2454174' AS part_code, 'BUSHING PRESS ROLLER 2014' AS description
    UNION ALL SELECT 'D2454299' AS part_code, 'HOT ROLLER BEARING' AS description
    UNION ALL SELECT 'D2455050' AS part_code, 'PCB:MPU:ASS''Y' AS description
    UNION ALL SELECT 'D2455400' AS part_code, 'POWER PACK:CBT' AS description
    UNION ALL SELECT 'D2461402' AS part_code, 'OPERATION PANEL' AS description
    UNION ALL SELECT 'D2475308' AS part_code, 'POWER SUPPLY UNIT' AS description
    UNION ALL SELECT 'A1843420' AS part_code, 'BOTTLE CHUCK' AS description
    UNION ALL SELECT 'AB011402' AS part_code, 'GEAR - 14Z' AS description
    UNION ALL SELECT 'AB011403' AS part_code, 'GEAR - 13Z' AS description
    UNION ALL SELECT 'AD027018' AS part_code, 'CHARGE ROLLER' AS description
    UNION ALL SELECT 'AW230107' AS part_code, 'TD SENSOR' AS description
    UNION ALL SELECT 'B0392288' AS part_code, 'CAM - CLEANING BLADE' AS description
    UNION ALL SELECT 'B0399510' AS part_code, 'OPC Drum' AS description
    UNION ALL SELECT 'D1583052' AS part_code, 'DEV.ROLLER ASSEMB' AS description
    UNION ALL SELECT 'D2452281' AS part_code, 'Cleaning Blade(MP2014)' AS description
    UNION ALL SELECT 'D2453011' AS part_code, 'DEVELOPMENT UNIT:PCU:ASS''Y' AS description
    UNION ALL SELECT 'D2453033' AS part_code, 'TONER SUPPLY UNIT MP2014' AS description
    UNION ALL SELECT 'D2453060' AS part_code, 'DEVELOPMENT ROLLER GEAR' AS description
    UNION ALL SELECT 'D2453064' AS part_code, 'GEAR:IDLER' AS description
    UNION ALL SELECT 'D2453245' AS part_code, 'GEAR:TRANSPORT SCREW' AS description
    UNION ALL SELECT 'D2453342' AS part_code, 'COVER:TONER BOTTLE' AS description
    UNION ALL SELECT 'D2453348' AS part_code, 'CAM:HANDLE' AS description
    UNION ALL SELECT 'D2459640' AS part_code, 'Developer (MP2014)' AS description
    UNION ALL SELECT 'D8840160' AS part_code, 'PCDU(DRUM UNIT)' AS description
    UNION ALL SELECT 'G0203334' AS part_code, 'SLIDER ASS`Y' AS description
    UNION ALL SELECT 'AW020203' AS part_code, 'REGISTRATION SENSOR' AS description
    UNION ALL SELECT 'AX209406' AS part_code, 'PAPER TRAY CLUTCH' AS description
    UNION ALL SELECT 'AX209407' AS part_code, 'BYPASS CLUTCH' AS description
    UNION ALL SELECT 'D2451240' AS part_code, 'COVER DOOR' AS description
    UNION ALL SELECT 'D2451241' AS part_code, 'COVER:FRONT' AS description
    UNION ALL SELECT 'D2451248' AS part_code, 'COVER:SCANNER:REAR UP' AS description
    UNION ALL SELECT 'D2451249' AS part_code, 'INNER COVER FRAME' AS description
    UNION ALL SELECT 'D2452774' AS part_code, 'BYPASS UPPER COVER' AS description
    UNION ALL SELECT 'D2452859' AS part_code, 'BYPASS LOWER COVER' AS description
    UNION ALL SELECT 'D2453856' AS part_code, 'TRANSFER BEARING F' AS description
    UNION ALL SELECT 'D2453857' AS part_code, 'TRANSFER BEARING R' AS description
    UNION ALL SELECT 'D2453870' AS part_code, 'STOPPER FRAME' AS description
    UNION ALL SELECT 'D2464619' AS part_code, 'COVER:DUPLEX' AS description
    UNION ALL SELECT 'D2464642' AS part_code, 'RIGHT DOOR LEVER' AS description
    UNION ALL SELECT 'D3B60083' AS part_code, 'ARDF:ASS''Y (ONLY 2014AD)' AS description
    UNION ALL SELECT 'D3B61500' AS part_code, 'PAPER FEED UNIT ARDF' AS description
    UNION ALL SELECT 'D6063112' AS part_code, 'FRICTION PAD' AS description
    UNION ALL SELECT 'D2451631' AS part_code, 'CIS UNIT' AS description
    UNION ALL SELECT 'D2451900' AS part_code, 'LASER UNIT' AS description
    UNION ALL SELECT 'D2471627' AS part_code, 'SCANNER:DF:ASS''Y' AS description
    UNION ALL SELECT 'AF031089' AS part_code, 'FEED ROLLER TRAY' AS description
    UNION ALL SELECT 'D2452511' AS part_code, 'PAPER TRAY:ASS''Y' AS description
    UNION ALL SELECT 'D2452527' AS part_code, 'FEELER:PAPER SENSOR' AS description
    UNION ALL SELECT 'D2452563' AS part_code, 'COVER:PAPER TRAY:NO.1' AS description
    UNION ALL SELECT 'D2452576' AS part_code, 'FRICTION PAD TRAY' AS description
    UNION ALL SELECT 'D2452712' AS part_code, 'BYPASS HOLDER' AS description
    UNION ALL SELECT 'D2452713' AS part_code, 'FEED ROLLER  BYPASS' AS description
    UNION ALL SELECT 'D2452716' AS part_code, 'PICKUP ROLLER BYPASS' AS description
    UNION ALL SELECT 'D2452722' AS part_code, 'PAPER STOPPER' AS description
    UNION ALL SELECT 'D2452736' AS part_code, 'FRICTION:PAD BYPASS' AS description
    UNION ALL SELECT 'D2452881' AS part_code, 'MANUAL FEED TABLE:ASS''Y' AS description
    UNION ALL SELECT 'C2525800' AS part_code, 'MAIN MOTOR' AS description
    UNION ALL SELECT 'C2615290' AS part_code, 'BRUSHLES MOTOR: ASSY' AS description
    UNION ALL SELECT 'C2615876' AS part_code, 'STEPLER MOTOR' AS description
    UNION ALL SELECT 'C2382410' AS part_code, 'CLAMP REAR' AS description
    UNION ALL SELECT 'C2522340' AS part_code, 'SCREEN:B4:ASS''Y' AS description
    UNION ALL SELECT 'C2612401' AS part_code, 'CLAMP ASS''Y' AS description
    UNION ALL SELECT 'C2672362' AS part_code, 'FLANGE DRUM B4 RIGHT' AS description
    UNION ALL SELECT 'C2672428' AS part_code, 'INK PUMP UNIT' AS description
    UNION ALL SELECT 'C2612151' AS part_code, 'THERMAL HEAD' AS description
    UNION ALL SELECT 'C2615102' AS part_code, 'PCB MPU ( C2615101 )' AS description
    UNION ALL SELECT 'C2741739' AS part_code, 'CIS' AS description
    UNION ALL SELECT 'C2523656' AS part_code, 'PRESSURE PLATE' AS description
    UNION ALL SELECT 'C2612271' AS part_code, 'CUTTER UNIT B4' AS description
    UNION ALL SELECT 'C2612750' AS part_code, 'PAPER FEED TRAY' AS description
    UNION ALL SELECT 'C2615160' AS part_code, 'OPERATION PANNEL6123' AS description
    UNION ALL SELECT 'C2741452' AS part_code, 'OPERATIONAL PANNEL2430' AS description
    UNION ALL SELECT 'C2522590' AS part_code, 'METAL SCREEN' AS description
    UNION ALL SELECT 'C2522820' AS part_code, 'SEPARATION PAD ASSUBLY' AS description
    UNION ALL SELECT 'C2613050' AS part_code, 'PRESSURE ROLLER : B4' AS description
    UNION ALL SELECT 'C2613879' AS part_code, 'TRANSPORT BELT' AS description
    UNION ALL SELECT 'C2672819' AS part_code, 'SEPARATION PAD(3443)' AS description
    UNION ALL SELECT 'C2742802' AS part_code, 'PAPER FEED ROLLER(2430)' AS description
    UNION ALL SELECT 'C2822802' AS part_code, 'PAPER FEED ROLLER(3440)' AS description
    UNION ALL SELECT 'B0391762' AS part_code, 'PCB :OPTICS SENSOR' AS description
    UNION ALL SELECT 'C2038137' AS part_code, 'MASTER SENSOR' AS description
    UNION ALL SELECT 'C2525230' AS part_code, 'HARNESSMASTER EJECT' AS description
    UNION ALL SELECT 'C2615200' AS part_code, 'HARNESS:DC DRIVE' AS description
    UNION ALL SELECT 'C2615201' AS part_code, 'HARNESS MOTOR' AS description
    UNION ALL SELECT '170026BP' AS part_code, 'DRUM UNIT A3' AS description
    UNION ALL SELECT '170051BP' AS part_code, 'METAL SCREEN' AS description
    UNION ALL SELECT '20102286BP' AS part_code, 'CLOTH SCREEN' AS description
    UNION ALL SELECT '20306234BP' AS part_code, 'PRESS ROLLER A3' AS description
    UNION ALL SELECT '30217002BP' AS part_code, 'SENSORS' AS description
    UNION ALL SELECT '20306113BP' AS part_code, '1ST FEED SOLENIOD' AS description
    UNION ALL SELECT '20306126BP' AS part_code, '1ST FEED TOOTH PLATE' AS description
    UNION ALL SELECT '30210004BP' AS part_code, 'THERMAL HEAD' AS description
    UNION ALL SELECT '20301171BP' AS part_code, 'PAPER FEED ROLLER' AS description
    UNION ALL SELECT '20306076BP' AS part_code, 'FRICTION PAD' AS description
    UNION ALL SELECT '30601312BP' AS part_code, 'EXIT ROLLER' AS description
    UNION ALL SELECT '30603201BP' AS part_code, 'EXIT BELT' AS description
    UNION ALL SELECT 'D2896410' AS part_code, 'COLLECTION BOTTLE' AS description
    UNION ALL SELECT 'AW100174' AS part_code, 'THERMISTOR EU:D197' AS description
    UNION ALL SELECT 'D2024234' AS part_code, 'LEVPRES RELE:FRONT' AS description
    UNION ALL SELECT 'D2024236' AS part_code, 'LEV:PRESSURE:FRONT' AS description
    UNION ALL SELECT 'D2024244' AS part_code, 'LEVPRESS RELE:REAR' AS description
    UNION ALL SELECT 'D2024262' AS part_code, 'FUSING COVER:UPPER' AS description
    UNION ALL SELECT 'D2024313' AS part_code, 'PRESS ROLLER:DIA32' AS description
    UNION ALL SELECT 'D8694037' AS part_code, 'FUSING SLEEVE' AS description
    UNION ALL SELECT 'D8694054' AS part_code, 'FUSING UNIT' AS description
    UNION ALL SELECT 'AD027034' AS part_code, 'ROLLER:CHARGE' AS description
    UNION ALL SELECT 'AD041162' AS part_code, 'BLADE CLEANING' AS description
    UNION ALL SELECT 'AD042078' AS part_code, 'BRUSH ROLLER' AS description
    UNION ALL SELECT 'D1979510' AS part_code, 'OPC DRUM' AS description
    UNION ALL SELECT 'D1979641' AS part_code, 'DEVELOPER BLACK' AS description
    UNION ALL SELECT 'D1993051' AS part_code, 'TONER SUPPLY SUB' AS description
    UNION ALL SELECT 'D2020127' AS part_code, 'DRUM UNIT' AS description
    UNION ALL SELECT 'D2022302' AS part_code, 'UPPER:ASS''Y' AS description
    UNION ALL SELECT 'D2023372' AS part_code, 'BASE:TONER' AS description
    UNION ALL SELECT 'D8692221' AS part_code, 'PCU:SERVICE PARTS' AS description
    UNION ALL SELECT 'D8693021' AS part_code, 'DEVELOPMENT UNIT' AS description
    UNION ALL SELECT 'AC015063' AS part_code, 'SHIELD GLASS' AS description
    UNION ALL SELECT 'AC030169' AS part_code, 'MIRROR:NO.1' AS description
    UNION ALL SELECT 'AC030210' AS part_code, 'MIRROR:NO.2' AS description
    UNION ALL SELECT 'D1471805' AS part_code, 'GLASS:DOCUMENT FE' AS description
    UNION ALL SELECT 'D1491749' AS part_code, 'MIRROR:NO.3' AS description
    UNION ALL SELECT 'D1991860' AS part_code, 'LASER UNIT' AS description
    UNION ALL SELECT 'D2021703' AS part_code, 'DC STEPPER MOTOR' AS description
    UNION ALL SELECT 'AF030094' AS part_code, 'PICKUP ROLLER' AS description
    UNION ALL SELECT 'AF031094' AS part_code, 'PAPER FEED ROLLER' AS description
    UNION ALL SELECT 'AF032094' AS part_code, 'SEPERATE ROLLER' AS description
    UNION ALL SELECT 'D1494695' AS part_code, 'LINK BYPASS' AS description
    UNION ALL SELECT 'D2022669' AS part_code, 'BYPASS COVER' AS description
    UNION ALL SELECT 'D2022670' AS part_code, 'BYPASS TABLE' AS description
    UNION ALL SELECT 'D2024630' AS part_code, 'DUPLEX COVER' AS description
    UNION ALL SELECT 'C2382369' AS part_code, 'INK ROLLER DRIVE GEAR' AS description
    UNION ALL SELECT 'C2382552' AS part_code, 'DRUM GRIP' AS description
    UNION ALL SELECT 'C2492472' AS part_code, 'INK SENSOR' AS description
    UNION ALL SELECT 'C2595804' AS part_code, 'DC MOTOR(INSIDE DRUM)' AS description
    UNION ALL SELECT 'C2595806' AS part_code, 'INK PUMP MORTOR(DC MOTOR)' AS description
    UNION ALL SELECT 'C2641160' AS part_code, 'GEAR:DRUM DRIVE(27911)' AS description
    UNION ALL SELECT 'C2642368' AS part_code, 'GEAR DRIVE DRUM(inside)' AS description
    UNION ALL SELECT 'C2645140' AS part_code, 'PCB:INK DETECTION' AS description
    UNION ALL SELECT 'C2722381' AS part_code, 'A3 CLOTH SCREEN' AS description
    UNION ALL SELECT 'C2722581' AS part_code, 'METAL SCREEN A3' AS description
    UNION ALL SELECT 'C2722586' AS part_code, 'INK SEAL A3' AS description
    UNION ALL SELECT 'C2792310' AS part_code, 'CLAMPER ASSY A3' AS description
    UNION ALL SELECT 'C2792372' AS part_code, 'FLANGE DRUM(A3)' AS description
    UNION ALL SELECT 'C6182631' AS part_code, 'INK PUMP ASSY (DISCONTINUE)' AS description
    UNION ALL SELECT 'C2492151' AS part_code, 'THERMAL HEAD A3' AS description
    UNION ALL SELECT 'C2645183' AS part_code, 'PCB:OPERATION(RIGHT)' AS description
    UNION ALL SELECT 'C2725120' AS part_code, 'MPU BOARD(4545)' AS description
    UNION ALL SELECT 'C2725181' AS part_code, 'PCB:MPU(4545)' AS description
    UNION ALL SELECT 'C2725310' AS part_code, 'POWER SUPPLY(4545)' AS description
    UNION ALL SELECT 'C2795108' AS part_code, 'MPU BOARD(4450)' AS description
    UNION ALL SELECT 'C2384365' AS part_code, 'EXIT PAWL' AS description
    UNION ALL SELECT 'C2492293' AS part_code, 'MASTER FLANGE(52mm)' AS description
    UNION ALL SELECT 'C2492294' AS part_code, 'MASTER FLANGE C(57mm)' AS description
    UNION ALL SELECT 'C2643489' AS part_code, 'CASE:MASTER EJECTION' AS description
    UNION ALL SELECT 'C2722071' AS part_code, 'CUTTER UNIT' AS description
    UNION ALL SELECT 'C2723805' AS part_code, 'HALE UNIT(P. DELIVERY)' AS description
    UNION ALL SELECT 'C2791321' AS part_code, 'COVER DOOR FRONT' AS description
    UNION ALL SELECT 'C2382835' AS part_code, 'PAPER FEED ROLLER' AS description
    UNION ALL SELECT 'C2382845' AS part_code, 'SEPARATION PAD ASSUBLY' AS description
    UNION ALL SELECT 'C2492904' AS part_code, 'REGISTRATION ROLLER' AS description
    UNION ALL SELECT 'C2493875' AS part_code, 'TRANSPORT BELT' AS description
    UNION ALL SELECT 'C2643050' AS part_code, 'PRESSURE ROLLER : A3' AS description
    UNION ALL SELECT 'C2643466' AS part_code, 'LOWER UPPER MAS EJECT' AS description
    UNION ALL SELECT 'C2723455' AS part_code, 'ROLLER UPPER MAS EJECT' AS description
    UNION ALL SELECT 'A1001714' AS part_code, 'REFLECTOR' AS description
    UNION ALL SELECT 'A2501784' AS part_code, '1st MIRROR DX4542' AS description
    UNION ALL SELECT 'A2505736' AS part_code, 'LAMP STABILAZER' AS description
    UNION ALL SELECT 'C2575841' AS part_code, 'SCANNER MORTOR' AS description
    UNION ALL SELECT 'C2621673' AS part_code, 'LENS HOLDER' AS description
    UNION ALL SELECT 'C2655135' AS part_code, 'HARENSS FLEX' AS description
    UNION ALL SELECT 'C2575807' AS part_code, 'REGISTRATION SENSOR' AS description
    UNION ALL SELECT 'C2645825' AS part_code, 'OPTIC SENSOR(2nd D.M)' AS description
    UNION ALL SELECT 'C2645236' AS part_code, 'HARN: PAPER FEED TRAY(4545)' AS description
    UNION ALL SELECT 'C2645237' AS part_code, 'HARNESS PAPER TRAY(4545)' AS description
    UNION ALL SELECT 'C2795200' AS part_code, 'HARNESS DC DRIVE(4450)' AS description
    UNION ALL SELECT 'C2795285' AS part_code, 'HANESS:MAS EJECT(4450)' AS description
    UNION ALL SELECT 'C2842240' AS part_code, 'METAL SCREEN' AS description
    UNION ALL SELECT 'C2842335' AS part_code, 'INK PUMP UNIT' AS description
    UNION ALL SELECT 'C2862245' AS part_code, 'CLOTH SCREEN' AS description
    UNION ALL SELECT 'C2841924' AS part_code, 'SPONGE ROLLER B' AS description
    UNION ALL SELECT 'C2841927' AS part_code, 'SPONGE ROLLER A' AS description
    UNION ALL SELECT 'C2841936' AS part_code, 'CUTTER UNIT' AS description
    UNION ALL SELECT 'C2841985' AS part_code, 'SPONGE ROLLER ANGLE' AS description
    UNION ALL SELECT 'C2842007' AS part_code, 'SPONGE ROLLERCLUTCH' AS description
    UNION ALL SELECT 'C2841475' AS part_code, 'OPERATION PANEL' AS description
    UNION ALL SELECT 'C2842653' AS part_code, 'SEPARATOR:ASSY' AS description
    UNION ALL SELECT 'C2842730' AS part_code, 'PAPER FEED:RING:UNIT' AS description
    UNION ALL SELECT 'C2842733' AS part_code, 'DP PULLY ASSY' AS description
    UNION ALL SELECT 'C2842750' AS part_code, 'SEPARATOR BASE:UNIT' AS description
    UNION ALL SELECT 'C2842752' AS part_code, 'AUXILIARY:SEPARATOR' AS description
    UNION ALL SELECT 'C2843068' AS part_code, 'PRESSURE ROLLER' AS description
    UNION ALL SELECT 'C2844123' AS part_code, 'EXIT:BELT' AS description
    UNION ALL SELECT 'C2844250' AS part_code, 'PAPER TRAY' AS description
    UNION ALL SELECT 'AF030049' AS part_code, 'PICKUP ROLLER BYPASS' AS description
    UNION ALL SELECT 'AF031046' AS part_code, 'FEED ROLLER BYPASS' AS description
    UNION ALL SELECT 'AF031049' AS part_code, 'FEED ROLLER TRAY' AS description
    UNION ALL SELECT 'AF032046' AS part_code, 'SEP.ROLLER BYPASS' AS description
    UNION ALL SELECT 'AF032049' AS part_code, 'SEP.ROLLER TRAY' AS description
    UNION ALL SELECT 'AE020282' AS part_code, 'PRESSURE ROLLER' AS description
    UNION ALL SELECT 'D0BM4052' AS part_code, 'FUSING SLEEVE IMC 2000-3500' AS description
    UNION ALL SELECT 'D0BQ4052' AS part_code, 'FUSING SLEEVE IMC4500-6000' AS description
    UNION ALL SELECT 'D0BQ6005' AS part_code, 'ITB UNIT' AS description
    UNION ALL SELECT 'D0BQ6141' AS part_code, 'CLEANING UNIT(ITB)' AS description
    UNION ALL SELECT 'D1776098' AS part_code, 'ITB BELT ONLY' AS description
    UNION ALL SELECT 'AZ240325' AS part_code, 'POWER SUPPLY IMC2000-3500' AS description
    UNION ALL SELECT 'D0BQ1500' AS part_code, 'OPERATION PANEL' AS description
    UNION ALL SELECT 'D0BK2053' AS part_code, 'CHARGE UNIT:30:ASSY' AS description
    UNION ALL SELECT 'D0BK2245' AS part_code, 'DRUM UNIT FOR IMC2000' AS description
    UNION ALL SELECT 'D0BK3000' AS part_code, 'DEV. BLACK FOR IMC2000' AS description
    UNION ALL SELECT 'D0BK3001' AS part_code, 'DEV. CYAN FOR IMC2000' AS description
    UNION ALL SELECT 'D0BK3002' AS part_code, 'DEV. MAGENTA FOR IMC2000' AS description
    UNION ALL SELECT 'D0BK3003' AS part_code, 'DEV. YELLOW FOR IMC2000' AS description
    UNION ALL SELECT 'D0BM2215' AS part_code, 'DRUM UNIT BLACK IMC3000-6000' AS description
    UNION ALL SELECT 'D0BM2216' AS part_code, 'DRUM UNIT COLOR IMC3000-6000' AS description
    UNION ALL SELECT 'D0BQ3000' AS part_code, 'DEV. BLACK FOR IMC3000-6000' AS description
    UNION ALL SELECT 'D0BQ3001' AS part_code, 'DEV. CYAN FOR IMC3000-6000' AS description
    UNION ALL SELECT 'D0BQ3002' AS part_code, 'DEV. MAGENTA IMC3000-6000' AS description
    UNION ALL SELECT 'D0BQ3003' AS part_code, 'DEV. YELLOW IMC3000-6000' AS description
    UNION ALL SELECT 'D0BQ3242' AS part_code, 'TONER HOPPER BLACK' AS description
    UNION ALL SELECT 'D0BQ3272' AS part_code, 'TONER HOPPER CYAN' AS description
    UNION ALL SELECT 'D0BQ3282' AS part_code, 'TONER HOPPER MAGENTA' AS description
    UNION ALL SELECT 'D0BQ3312' AS part_code, 'TONER HOPPER YELLOW' AS description
    UNION ALL SELECT 'D0BK2800' AS part_code, 'PAPER TRAY NO1' AS description
    UNION ALL SELECT 'D0BK2805' AS part_code, 'PAPER TRAY NO2' AS description
    UNION ALL SELECT 'D0BQ1242' AS part_code, 'FRONT DOOR' AS description
    UNION ALL SELECT 'D0BQ4563' AS part_code, 'BYPASS TRAY' AS description
    UNION ALL SELECT 'D0BQ6400' AS part_code, 'WASTE TONER BOX' AS description
    UNION ALL SELECT 'D2414627' AS part_code, 'RIGHT DOOR' AS description
    UNION ALL SELECT 'D2414995' AS part_code, 'GUIDE PLATE DUPLEX' AS description
    UNION ALL SELECT 'D0BM1855' AS part_code, 'LASSER UNIT' AS description
    UNION ALL SELECT 'D0BQ1640' AS part_code, 'CARRIAGE SCANNER' AS description
    UNION ALL SELECT 'AF031185' AS part_code, 'FEED ROLLER TRAY' AS description
    UNION ALL SELECT 'AF032102' AS part_code, 'SEPERATE ROLLER TRAY' AS description
    UNION ALL SELECT 'D0DZ4052' AS part_code, 'FUSING SLEEVE' AS description
    UNION ALL SELECT 'D0E36005' AS part_code, 'ITB UNIT' AS description
    UNION ALL SELECT 'D0E36097' AS part_code, 'ITB BELT' AS description
    UNION ALL SELECT 'D0E36141' AS part_code, 'ITB CLEANER BLADE' AS description
    UNION ALL SELECT 'AZ240400' AS part_code, 'POWER SUPPLY UNIT' AS description
    UNION ALL SELECT 'D0E08703' AS part_code, 'PRINTER CONTROLL BOARD' AS description
    UNION ALL SELECT 'D0E09591' AS part_code, 'NV RAM' AS description
    UNION ALL SELECT 'D0E15114' AS part_code, 'MAINBOARD' AS description
    UNION ALL SELECT 'D0E35126' AS part_code, 'IOB BOARD' AS description
    UNION ALL SELECT 'D0E35156' AS part_code, 'HIGH VOLTAGE BOARD' AS description
    UNION ALL SELECT 'AW230113' AS part_code, 'TD SENSOR' AS description
    UNION ALL SELECT 'D0DZ2205' AS part_code, 'DRUM UNIT' AS description
    UNION ALL SELECT 'D0DZ3000' AS part_code, 'DEV UNIT BLACK' AS description
    UNION ALL SELECT 'D0DZ3001' AS part_code, 'DEV UNIT CYAN' AS description
    UNION ALL SELECT 'D0DZ3002' AS part_code, 'DEV UNIT MAGENTA' AS description
    UNION ALL SELECT 'D0DZ3003' AS part_code, 'DEV UNIT YELLOW' AS description
    UNION ALL SELECT 'D0E33340' AS part_code, 'TONER SUPPLY UNIT BLACK' AS description
    UNION ALL SELECT 'D0E33350' AS part_code, 'TONER SUPPLY UNIT CYAN' AS description
    UNION ALL SELECT 'D0E33360' AS part_code, 'TONER SUPPLY UNIT MAGENTA' AS description
    UNION ALL SELECT 'D0E33370' AS part_code, 'TONER SUPPLY UNIT YELLOW' AS description
    UNION ALL SELECT 'D0DZ2800' AS part_code, '1ST TRAY' AS description
    UNION ALL SELECT 'D0DZ2805' AS part_code, '2ND TRAY' AS description
    UNION ALL SELECT 'D0DZ4555' AS part_code, 'RIGHT DOOR' AS description
    UNION ALL SELECT 'D0E34563' AS part_code, 'BYPASS TRAY' AS description
    UNION ALL SELECT 'D0E11855' AS part_code, 'LASER UNIT' AS description
    UNION ALL SELECT 'D0FL0807' AS part_code, 'GUIDE PLATE BYPASS' AS description
    UNION ALL SELECT 'D0FL0809' AS part_code, 'FEALER PAPER END BYPASS' AS description
    UNION ALL SELECT 'D0FL0812' AS part_code, 'BYPASS TRAY CLUTCH' AS description
    UNION ALL SELECT 'D0FL0813' AS part_code, 'PAPER FEED ROLLER BYPASS' AS description
    UNION ALL SELECT 'DOFL0704' AS part_code, 'CIS UNIT' AS description
    UNION ALL SELECT 'DOFL0806' AS part_code, 'FRICTION PAD BYPASS' AS description
    UNION ALL SELECT 'D0FL0603' AS part_code, 'SENSOR PAPER EXIT' AS description
    UNION ALL SELECT 'D0FL1701' AS part_code, 'FRAME FUSING' AS description
    UNION ALL SELECT 'D0FL1708' AS part_code, 'BUSHING HOT ROLLER' AS description
    UNION ALL SELECT 'D0FL1709' AS part_code, 'HOT ROLLER' AS description
    UNION ALL SELECT 'D0FL1715' AS part_code, 'PRESSURE ROLLER' AS description
    UNION ALL SELECT 'D0FL1721' AS part_code, 'Thermistor' AS description
    UNION ALL SELECT 'D0FL1802' AS part_code, 'FEELER PAPER EXIT' AS description
    UNION ALL SELECT 'D0FL1903' AS part_code, 'COVER PAPER TRAY' AS description
    UNION ALL SELECT 'D3LX0601' AS part_code, 'PAPER TRAY' AS description
    UNION ALL SELECT 'D3LX0609' AS part_code, 'SEPARATION PAD' AS description
    UNION ALL SELECT 'D3LX0611' AS part_code, 'PAPER FEED ROLLER' AS description
    UNION ALL SELECT 'D0FL2801' AS part_code, 'CHERGER UNIT ASSY' AS description
    UNION ALL SELECT 'D0FL2802' AS part_code, 'FUSING UNIT' AS description
    UNION ALL SELECT 'D0FL2803' AS part_code, 'DEVELOPER UNIT' AS description
    UNION ALL SELECT 'D0FL2804' AS part_code, 'DRUM UNIT' AS description
    UNION ALL SELECT 'D0FL1301' AS part_code, 'CASE DEVELPMENT' AS description
    UNION ALL SELECT 'D0FL1307' AS part_code, 'GEAR TRANSEPORT DEV  LEFT' AS description
    UNION ALL SELECT 'D0FL1308' AS part_code, 'GEAR TRANSEPORT DEV  RIGHT' AS description
    UNION ALL SELECT 'D0FL1309' AS part_code, 'GEAR DEVELOPMENT IDLER' AS description
    UNION ALL SELECT 'D0FL1310' AS part_code, 'GEAR MAGNET ROLLER' AS description
    UNION ALL SELECT 'D0FL1311' AS part_code, 'MAGNET ROLLER' AS description
    UNION ALL SELECT 'D0FL1319' AS part_code, 'TD SENSOR' AS description
    UNION ALL SELECT 'D0FL1402' AS part_code, 'DISCHARGE LAMP' AS description
    UNION ALL SELECT 'D0FL1412' AS part_code, 'CHARGER GRIDE' AS description
    UNION ALL SELECT 'D0FL1601' AS part_code, 'FRAME CLEANING' AS description
    UNION ALL SELECT 'D0FL1602' AS part_code, 'SEAL CLEANING FRONT' AS description
    UNION ALL SELECT 'D0FL1603' AS part_code, 'SEAL CLEANING REAR' AS description
    UNION ALL SELECT 'D0FL1604' AS part_code, 'SEAL CLEANING SIDE' AS description
    UNION ALL SELECT 'D0FL1618' AS part_code, 'CLEANING BLADE' AS description
    UNION ALL SELECT 'D0FL1619' AS part_code, 'OPC DRUM' AS description
    UNION ALL SELECT 'D0FL2701' AS part_code, 'DEVELOPER' AS description
    UNION ALL SELECT 'DOFL1321' AS part_code, 'SEAL DEVELOPMENT' AS description
    UNION ALL SELECT 'DOFL1325' AS part_code, 'COVER DEVELOPMENT' AS description
    UNION ALL SELECT 'D0FL0101' AS part_code, 'COVER FTONT' AS description
    UNION ALL SELECT 'D0FL0102' AS part_code, 'COVER TONER SUPPLY' AS description
    UNION ALL SELECT 'D0FL0203' AS part_code, 'OPERATION PANNEL 2310' AS description
    UNION ALL SELECT 'D0FL0205' AS part_code, 'OPERATION PANEL 2510' AS description
    UNION ALL SELECT 'D0FL0208' AS part_code, 'OPERATION PANNEL 2810' AS description
    UNION ALL SELECT 'D0FL0308' AS part_code, 'COVER LEFT UNDER' AS description
    UNION ALL SELECT 'D0FL0801' AS part_code, 'COVER DUPLEX(RIGHT DOOR)' AS description
    UNION ALL SELECT 'D0FL1001' AS part_code, 'MAIN MOTOR' AS description
    UNION ALL SELECT 'D0FL1020' AS part_code, 'DUPLEX CLUTCH' AS description
    UNION ALL SELECT 'D0FL1101' AS part_code, 'TRAY BYPAS' AS description
    UNION ALL SELECT 'D0FL1102' AS part_code, 'SIDE FENCE FRONT' AS description
    UNION ALL SELECT 'D0FL1103' AS part_code, 'SIDE FENCE REAR' AS description
    UNION ALL SELECT 'D0FL1105' AS part_code, 'COVER MANUAL FEED' AS description
    UNION ALL SELECT 'D0LX0403' AS part_code, 'FEALER PAPER END SENSOR' AS description
    UNION ALL SELECT 'DOFL0108' AS part_code, 'COVER FRONT LEFT' AS description
    UNION ALL SELECT 'DOFL0808' AS part_code, 'PAPER GUIDE BYPASS' AS description
    UNION ALL SELECT 'AX060530' AS part_code, 'MAIN MOTOR' AS description
    UNION ALL SELECT 'AE011149' AS part_code, 'HOT ROLLER' AS description
    UNION ALL SELECT 'AE020263' AS part_code, 'PRESSURE ROLLER' AS description
    UNION ALL SELECT 'AE044076' AS part_code, 'STRIPPER PAWL' AS description
    UNION ALL SELECT 'AW100197' AS part_code, 'THERMISTOR' AS description
    UNION ALL SELECT 'D0B24006' AS part_code, 'FUSING UNIT ASSY' AS description
    UNION ALL SELECT 'D0B25101' AS part_code, 'M2701 MAIN BOARD' AS description
    UNION ALL SELECT 'D0B25300' AS part_code, 'M2701 POWER SUPPLY' AS description
    UNION ALL SELECT 'D0B45300' AS part_code, 'IM2702 POWER SUPPLY' AS description
    UNION ALL SELECT 'D0B45600' AS part_code, 'IM2702 MAIN BOARD' AS description
    UNION ALL SELECT 'A2673440' AS part_code, 'DEVELOPMENT JOINT' AS description
    UNION ALL SELECT 'D0B22370' AS part_code, 'DRUM STAY' AS description
    UNION ALL SELECT 'D0B23001' AS part_code, 'LOWER DEVELOPMENT UNIT' AS description
    UNION ALL SELECT 'D9070151' AS part_code, 'PCDU(DRUM UNIT)' AS description
    UNION ALL SELECT 'D0B21245' AS part_code, 'RIGHT SIDE REAR COVER (4000)' AS description
    UNION ALL SELECT 'D0B22549' AS part_code, 'GRIP PAPER TRAY' AS description
    UNION ALL SELECT 'D0B22774' AS part_code, 'BYPASS UPPER (4500)' AS description
    UNION ALL SELECT 'D0B22859' AS part_code, 'BYPASS LOWER (8500)' AS description
    UNION ALL SELECT 'D5412241' AS part_code, 'ADF REVERSE ROLLER IM2702' AS description
    UNION ALL SELECT 'D6842102' AS part_code, 'PAPER FEED UNIT ADF IM2702' AS description
    UNION ALL SELECT 'D0B21900' AS part_code, 'LASER UNIT M2701/M2700' AS description
    UNION ALL SELECT 'D0B41910' AS part_code, 'LASER UNIT IM2702' AS description
    UNION ALL SELECT 'D2451629' AS part_code, 'CIS UNIT M2701/M2700' AS description
    UNION ALL SELECT 'D0B22501' AS part_code, 'PAPER TRAY:ASS''Y' AS description
    UNION ALL SELECT 'D0B22542' AS part_code, 'PAPER SENSOR' AS description
    UNION ALL SELECT 'D0B22736' AS part_code, 'BYPASS FRICTION PAD' AS description
    UNION ALL SELECT 'D0B22880' AS part_code, 'BYPASS TRAY' AS description
    UNION ALL SELECT 'D0B24620' AS part_code, 'RIGHT DOOR' AS description
    UNION ALL SELECT 'D0B24642' AS part_code, 'RIGHT DOOR LEVER' AS description
    UNION ALL SELECT 'D0D81402' AS part_code, 'OPERATION PANEL' AS description
    UNION ALL SELECT 'B8303504' AS part_code, 'TIMING BELT(FEEDING)' AS description
    UNION ALL SELECT 'D1752729' AS part_code, 'PAPER FEED UNIT(T1)' AS description
    UNION ALL SELECT 'D1752730' AS part_code, 'PAPER FEED UNIT(T2)' AS description
    UNION ALL SELECT 'AE020247' AS part_code, 'PRESSURE ROLLER' AS description
    UNION ALL SELECT 'AE020288' AS part_code, 'PRESSURE ROLLER:DIA3' AS description
    UNION ALL SELECT 'D1764042' AS part_code, 'FUSING SLEEVE' AS description
    UNION ALL SELECT 'D0392936' AS part_code, 'Cover Vertical Transport' AS description
    UNION ALL SELECT 'D1496370' AS part_code, 'TONER OUTLET UNIT (ITB)' AS description
    UNION ALL SELECT 'D1776006' AS part_code, 'ITB UNIT' AS description
    UNION ALL SELECT 'AZ230274' AS part_code, 'POWER SUPPLY UNIT' AS description
    UNION ALL SELECT 'AZ320192' AS part_code, 'POWERPACK(HV)' AS description
    UNION ALL SELECT 'D1425765' AS part_code, 'HDD:250GB' AS description
    UNION ALL SELECT 'D1509591' AS part_code, 'NV RAM' AS description
    UNION ALL SELECT 'D1765675' AS part_code, 'PCB: PRINT CONTROLLER' AS description
    UNION ALL SELECT 'D1775650' AS part_code, 'IMAGE PROCESSING UNIT' AS description
    UNION ALL SELECT 'M1295245' AS part_code, 'RAM: DDR3 1.5GB' AS description
    UNION ALL SELECT 'D1773035' AS part_code, 'DEVELOPER: BLACK' AS description
    UNION ALL SELECT 'D1773036' AS part_code, 'DEVELOPER: CYAN' AS description
    UNION ALL SELECT 'D1773037' AS part_code, 'DEVELOPER: MAGENTA' AS description
    UNION ALL SELECT 'D1773038' AS part_code, 'DEVELOPER: YELLOW' AS description
    UNION ALL SELECT 'D1882235' AS part_code, 'DRUM UNIT' AS description
    UNION ALL SELECT 'D1882254CET' AS part_code, 'DRUM KIT (CHINESE)' AS description
    UNION ALL SELECT 'D1752608' AS part_code, 'MANUAL FEED TABLE' AS description
    UNION ALL SELECT 'D1752801' AS part_code, 'PAPER TRAY 1(A4)' AS description
    UNION ALL SELECT 'D1752894' AS part_code, 'PAPER TRAY 2(A3)' AS description
    UNION ALL SELECT 'D1761411' AS part_code, 'OPRATION PANEL' AS description
    UNION ALL SELECT 'D1771240' AS part_code, 'FRONT DOOR COVER' AS description
    UNION ALL SELECT 'D1771302' AS part_code, 'CLEANIG SHIELD GLASS' AS description
    UNION ALL SELECT 'D2426400' AS part_code, 'COLLECTION BOTTLE' AS description
    UNION ALL SELECT 'D1471855' AS part_code, 'LASER UNIT' AS description
    UNION ALL SELECT 'D1491771' AS part_code, 'LENS HOLDER' AS description
    UNION ALL SELECT 'D1496211' AS part_code, 'TRANSFER ROLLER' AS description
    UNION ALL SELECT 'D1496226' AS part_code, 'LEVER TRANSFER:FRONT' AS description
    UNION ALL SELECT 'D1496227' AS part_code, 'LEVER SET:FRONT' AS description
    UNION ALL SELECT 'D1496231' AS part_code, 'LEVER TRANSFER:REAR' AS description
    UNION ALL SELECT 'D1496232' AS part_code, 'LEVER SET:REAR' AS description
    UNION ALL SELECT 'AE020266' AS part_code, 'PRESSURE ROLLER' AS description
    UNION ALL SELECT 'AW100154' AS part_code, 'THERMISTOR:PRESS ROLL(RE)' AS description
    UNION ALL SELECT 'AW100156' AS part_code, 'THERMISTOR:PRESS ROLL(FRNT)' AS description
    UNION ALL SELECT 'AW110111' AS part_code, 'THERMOSTAT:MIDDLE' AS description
    UNION ALL SELECT 'AW110112' AS part_code, 'THERMOSTAT:REAR' AS description
    UNION ALL SELECT 'D0BQ4218' AS part_code, 'LINK:GATE PAWL:FUSING EXIT' AS description
    UNION ALL SELECT 'D1494261' AS part_code, 'FUSING COVER UPPER' AS description
    UNION ALL SELECT 'D2394012' AS part_code, 'FUSING UNIT' AS description
    UNION ALL SELECT 'D2394042' AS part_code, 'SLEEVE UNIT(WITH LAMP)' AS description
    UNION ALL SELECT 'D2414212' AS part_code, 'GATE PAWL:FUSING EXIT' AS description
    UNION ALL SELECT 'D2442250' AS part_code, 'PCU(DRUM ONLY)' AS description
    UNION ALL SELECT 'D2443020' AS part_code, 'DEVELOPER: BLACK' AS description
    UNION ALL SELECT 'D2443021' AS part_code, 'DEVELOPER: CYAN' AS description
    UNION ALL SELECT 'D2443022' AS part_code, 'DEVELOPER: MAGENTA' AS description
    UNION ALL SELECT 'D2443023' AS part_code, 'DEVELOPER: YELLOW' AS description
    UNION ALL SELECT 'D2412627' AS part_code, 'MANUAL FEED TABLE' AS description
    UNION ALL SELECT 'AB011620' AS part_code, 'GEAR HOT ROLLER' AS description
    UNION ALL SELECT 'AE011134' AS part_code, 'HOT ROLLER' AS description
    UNION ALL SELECT 'AE020232' AS part_code, 'PRESSURE ROLLER' AS description
    UNION ALL SELECT 'AW100164' AS part_code, 'THERMISTOR' AS description
    UNION ALL SELECT 'D2054174' AS part_code, 'PRESSURE ROLLER BUSH' AS description
    UNION ALL SELECT 'D2594059' AS part_code, 'FUSING UNIT' AS description
    UNION ALL SELECT 'AZ230291' AS part_code, 'POWER SUPPLY UNIT' AS description
    UNION ALL SELECT 'AZ320207' AS part_code, 'HV BOARD' AS description
    UNION ALL SELECT 'D2055111' AS part_code, 'MAIN BOARD' AS description
    UNION ALL SELECT 'D2055611' AS part_code, 'PRINTER CONTROL BOARD' AS description
    UNION ALL SELECT 'D2051201' AS part_code, 'FRONT COVER' AS description
    UNION ALL SELECT 'D2051250' AS part_code, 'REAR COVER' AS description
    UNION ALL SELECT 'D2054408' AS part_code, 'PAPER FEED SENSOR' AS description
    UNION ALL SELECT 'D2591431' AS part_code, 'OPERATION PANEL' AS description
    UNION ALL SELECT 'D2051784' AS part_code, 'CIS UNIT' AS description
    UNION ALL SELECT 'D2051860' AS part_code, 'LASER UNIT' AS description
    UNION ALL SELECT 'AX201020' AS part_code, 'PAPER FEED C;UTCH' AS description
    UNION ALL SELECT 'AX640235' AS part_code, 'REAR FAN MOTOR' AS description
    UNION ALL SELECT 'B0392740' AS part_code, 'FEED ROLLER TRAY' AS description
    UNION ALL SELECT 'D2051121' AS part_code, 'BRUSHLESS MOTOR' AS description
    UNION ALL SELECT 'D2052500' AS part_code, 'PAPER TRAY' AS description
    UNION ALL SELECT 'D2052525' AS part_code, 'TRAY FONT COVER' AS description
    UNION ALL SELECT 'D2052535' AS part_code, 'TRAY HANDLE' AS description
    UNION ALL SELECT 'D2052552' AS part_code, 'FRICTION PAD TRAY' AS description
    UNION ALL SELECT 'D2052705' AS part_code, 'BYPASS TRAY' AS description
    UNION ALL SELECT 'D2052716' AS part_code, 'BYPASS FEEDROLLER' AS description
    UNION ALL SELECT 'D2052726' AS part_code, 'BYPASS FRICTION PAD' AS description
    UNION ALL SELECT 'D2052731' AS part_code, 'BYPASS PICKUP R COVER' AS description
    UNION ALL SELECT 'D2054640' AS part_code, 'RIGHT DOOR' AS description
    UNION ALL SELECT 'D2054675' AS part_code, 'RIGHT DOOR LEVER' AS description
    UNION ALL SELECT 'D6062113' AS part_code, 'BYPASS PICKUP ROLLER' AS description
    UNION ALL SELECT 'D2052249' AS part_code, 'DRUM UNIT' AS description
    UNION ALL SELECT 'D2053212' AS part_code, 'PIPE TONER SUPPLY' AS description
    UNION ALL SELECT 'D2053310' AS part_code, 'TONER SUPPLY UNIT' AS description
    UNION ALL SELECT 'D2056250' AS part_code, 'TRANFER ASSY' AS description
) requested
WHERE NOT EXISTS (
    SELECT 1 FROM part existing WHERE LOWER(existing.part_code) = LOWER(requested.part_code)
);
