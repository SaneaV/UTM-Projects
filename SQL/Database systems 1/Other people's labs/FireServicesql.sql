-- 2.1 
-- Установить соединение с master базой данных
-- Переход на базу данных master, так как операции по удалению и изменению базы данных можно проводить только из неё
USE master;
GO

-- Проверка и удаление базы данных FireService, если она существует
-- Если база данных FireService существует, выполняется её удаление
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'FireService')
BEGIN
    -- Перевод базы данных FireService в режим однопользовательского доступа
    -- Это необходимо для принудительного завершения всех открытых транзакций и соединений с базой данных
    ALTER DATABASE FireService SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    
    -- Удаление базы данных FireService
    DROP DATABASE FireService;
END
GO

-- Создание новой базы данных FireService
-- Создаётся новая база данных с именем FireService
CREATE DATABASE FireService;
GO

-- Использовать новую базу данных FireService
-- После создания новой базы данных, переключение на неё для дальнейших операций
USE FireService;
GO

-- 2.2
-- Создание таблицы пожарных станций
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'fire_stations' 
               AND Type = 'U')
BEGIN
    CREATE TABLE fire_stations
    (
        station_id INT IDENTITY(1, 1) NOT NULL,
        station_name NVARCHAR(100) NOT NULL,
        station_address NVARCHAR(255) NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы пожарных машин
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'fire_trucks' 
               AND Type = 'U')
BEGIN
    CREATE TABLE fire_trucks
    (
        truck_id INT IDENTITY(1, 1) NOT NULL,
        truck_name NVARCHAR(7) NOT NULL,
        station_id INT NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы деталей пожарных машин
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'fire_truck_details' 
               AND Type = 'U')
BEGIN
    CREATE TABLE fire_truck_details
    (
        truck_id INT NOT NULL,
        manufacture_year INT NOT NULL,
        truck_type NVARCHAR(50) NOT NULL,
        engine_capacity DECIMAL(5,2) NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы вызовов пожарных служб
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'fire_calls' 
               AND Type = 'U')
BEGIN
    CREATE TABLE fire_calls
    (
        call_id INT IDENTITY(1, 1) NOT NULL,
        call_time DATETIME NOT NULL,
        call_address NVARCHAR(255) NOT NULL,
        call_description NVARCHAR(500)
    );
END;
GO

-- 2.2
-- Создание таблицы ассоциации вызовов и пожарных машин
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'fire_truck_fire_call' 
               AND Type = 'U')
BEGIN
    CREATE TABLE fire_truck_fire_call
    (
        truck_id INT NOT NULL,
        call_id INT NOT NULL
    );
END;
GO

-- 2.3
-- Установка первичных ключей для таблицы fire_stations
ALTER TABLE fire_stations ADD CONSTRAINT PK_fire_stations PRIMARY KEY (station_id); 
GO

-- Установка первичных ключей для таблицы fire_trucks
ALTER TABLE fire_trucks ADD CONSTRAINT PK_fire_trucks PRIMARY KEY (truck_id); 
GO

-- Установка первичных ключей для таблицы fire_truck_details
ALTER TABLE fire_truck_details ADD CONSTRAINT PK_fire_truck_details PRIMARY KEY (truck_id); 
GO

-- Установка первичных ключей для таблицы fire_calls
ALTER TABLE fire_calls ADD CONSTRAINT PK_fire_calls PRIMARY KEY (call_id); 
GO

-- Установка первичных ключей для таблицы fire_truck_fire_call
ALTER TABLE fire_truck_fire_call ADD CONSTRAINT PK_fire_truck_fire_call PRIMARY KEY (truck_id, call_id); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы пожарных станций
-- Установка уникальности для station_name (если оно необходимо, можно оставить)
ALTER TABLE fire_stations ADD CONSTRAINT UQ_fire_stations_station_name UNIQUE (station_name); 
GO

-- Установка уникальности для station_address (если оно необходимо, можно оставить)
ALTER TABLE fire_stations ADD CONSTRAINT UQ_fire_stations_station_address UNIQUE (station_address); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы пожарных машин
-- Установка уникальности для truck_name (если необходимо, оставьте)
ALTER TABLE fire_trucks ADD CONSTRAINT UQ_fire_trucks_truck_name UNIQUE (truck_name); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы деталей пожарных машин
-- Добавление ограничения проверки для manufacture_year
ALTER TABLE fire_truck_details ADD CONSTRAINT CHK_manufacture_year CHECK (manufacture_year >= 1900 AND manufacture_year <= YEAR(GETDATE())); 
GO

-- Добавление ограничения проверки для engine_capacity
ALTER TABLE fire_truck_details ADD CONSTRAINT CHK_engine_capacity CHECK (engine_capacity > 0); 
GO

-- 2.3
-- Установка ограничения целостности для таблицы пожарных машин
-- Добавление ограничения проверки для форматирования номерных знаков
ALTER TABLE fire_trucks ADD CONSTRAINT CK_Truck_Number_Plate_Format
CHECK (truck_name LIKE '[A-Z][A-Z][A-Z] [0-9][0-9][0-9]');
GO

-- 2.3
-- Установка ограничений целостности для таблицы вызовов пожарных служб
-- Добавление ограничения проверки для call_time
ALTER TABLE fire_calls ADD CONSTRAINT CHK_call_time CHECK (call_time <= GETDATE()); 
GO

-- Установка значения по умолчанию для столбца call_time в таблице fire_calls
-- Установка текущей даты и времени в качестве значения по умолчанию для call_time
ALTER TABLE fire_calls 
ADD CONSTRAINT DF_fire_calls_call_time DEFAULT GETDATE() FOR call_time; 
GO

-- Добавление ограничения для call_address
ALTER TABLE fire_calls ADD CONSTRAINT CHK_call_address CHECK (LEN(call_address) > 0); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы ассоциации вызовов и пожарных машин
-- Добавление ограничения проверки для уникальности связки truck_id и call_id
ALTER TABLE fire_truck_fire_call ADD CONSTRAINT UQ_fire_truck_fire_call UNIQUE (truck_id, call_id); 
GO

-- 2.3
-- Установка значений по умолчанию
-- Установка значения по умолчанию для engine_capacity в таблице fire_truck_details
ALTER TABLE fire_truck_details ADD CONSTRAINT DF_fire_truck_details_engine_capacity DEFAULT 4.5 FOR engine_capacity; 
GO

-- 2.4
-- Установка внешних ключей для таблицы fire_trucks
-- Добавление внешнего ключа для связи с таблицей fire_stations
ALTER TABLE fire_trucks 
ADD CONSTRAINT FK_fire_trucks_station_id FOREIGN KEY (station_id) 
REFERENCES fire_stations (station_id) 
ON DELETE CASCADE ON UPDATE CASCADE; 
GO

-- Установка внешнего ключа для таблицы fire_truck_details
-- Добавление внешнего ключа для связи с таблицей fire_trucks
ALTER TABLE fire_truck_details 
ADD CONSTRAINT FK_fire_truck_details_truck_id FOREIGN KEY (truck_id) 
REFERENCES fire_trucks (truck_id) 
ON DELETE CASCADE ON UPDATE CASCADE; 
GO

-- Установка внешних ключей для таблицы fire_truck_fire_call
-- Добавление внешнего ключа для связи с таблицей fire_trucks
ALTER TABLE fire_truck_fire_call 
ADD CONSTRAINT FK_fire_truck_fire_call_truck_id FOREIGN KEY (truck_id) 
REFERENCES fire_trucks (truck_id) 
ON DELETE CASCADE ON UPDATE CASCADE; 
GO

-- Добавление внешнего ключа для связи с таблицей fire_calls
ALTER TABLE fire_truck_fire_call 
ADD CONSTRAINT FK_fire_truck_fire_call_call_id FOREIGN KEY (call_id) 
REFERENCES fire_calls (call_id) 
ON DELETE CASCADE ON UPDATE CASCADE; 
GO

-- 2.5
-- Заполнение таблицы fire_stations
INSERT INTO fire_stations (station_name, station_address)
VALUES
('Comrat Fire Station', 'Strada Gagauzia, 11, Comrat'),
('Orhei Fire Station', 'Strada Calarasi, 5, Orhei'),
('Soroca Fire Station', 'Strada Alexandru Ioan Cuza, 17, Soroca'),
('Ungheni Fire Station', 'Strada Stefan cel Mare, 12, Ungheni'),
('Edinet Fire Station', 'Strada Unirii, 8, Edinet'),
('Drochia Fire Station', 'Strada Mihai Viteazul, 14, Drochia'),
('Falesti Fire Station', 'Strada Maresalului Averescu, 20, Falesti'),
('Ialoveni Fire Station', 'Strada Independentei, 9, Ialoveni'),
('Anenii Noi Fire Station', 'Strada 31 August, 15, Anenii Noi'),
('Causeni Fire Station', 'Strada Ludus, 6, Causeni'),
('Hancesti Fire Station', 'Strada Rosca, 7, Hancesti');
GO

-- 2.5
-- Заполнение таблицы fire_trucks
INSERT INTO fire_trucks (truck_name, station_id)
VALUES
('ABC 001', 1),
('XYZ 002', 1),
('DEF 003', 1),
('GHI 004', 2),
('JKL 005', 2),
('MNO 006', 2),
('PQR 007', 3),
('STU 008', 3),
('VWX 009', 3),
('YZA 010', 4),
('BCD 011', 4),
('EFG 012', 4),
('HIJ 013', 5),
('KLM 014', 5),
('NOP 015', 5),
('QRS 016', 6),
('TUV 017', 6),
('WXY 018', 6),
('ZAB 019', 7),
('CDE 020', 7),
('FGH 021', 7),
('IJK 022', 8),
('LMN 023', 8),
('OPQ 024', 8),
('RST 025', 9),
('UVW 026', 9);
GO

-- 2.5
-- Заполнение таблицы fire_truck_details
INSERT INTO fire_truck_details (truck_id, manufacture_year, truck_type, engine_capacity)
VALUES
(1, 2015, 'Fire Tanker', 6.5),
(2, 2016, 'Fire Tanker', 7.0),
(3, 2017, 'Ladder', 7.5),
(4, 2018, 'Pump-Hose Unit', 8.0),
(5, 2020, 'Water Tanker', 8.0),
(6, 2014, 'Ladder', 7.0),
(7, 2019, 'Fire Tanker', 6.5),
(8, 2020, 'Fire Tanker', 7.0),
(9, 2017, 'Water Tanker', 8.5),
(10, 2021, 'Ladder', 6.5),
(11, 2021, 'Fire Tanker', 7.5),
(12, 2015, 'Fire Rescue Vehicle', 6.0),
(13, 2016, 'High Pressure Pump', 8.0),
(14, 2019, 'Fire Tanker', 7.5),
(15, 2020, 'Ladder', 7.0),
(16, 2021, 'Fire Tanker', 7.5),
(17, 2022, 'Ambulance', 7.5),
(18, 2021, 'Fire Rescue Vehicle', 6.5),
(19, 2015, 'Fire Tanker', 7.0),
(20, 2016, 'Pump-Hose Unit', 7.5),
(21, 2019, 'Fire Tanker', 8.0),
(22, 2020, 'Fire Tanker', 7.5),
(23, 2021, 'High Pressure Pump', 8.5),
(24, 2022, 'Fire Rescue Vehicle', 7.5),
(25, 2022, 'Fire Tanker', 7.0);
GO

-- 2.5
-- Заполнение таблицы fire_calls
INSERT INTO fire_calls (call_time, call_address, call_description)
VALUES
('2024-11-01 08:15:23', 'Chisinau, Strada Vasile Alecsandri, 1', 'Fire in apartment'),
('2024-11-01 09:30:45', 'Balti, Strada Mihai Eminescu, 23', 'Car accident with casualties'),
('2024-11-01 11:45:05', 'Tiraspol, Strada Maresesti, 45', 'Bonfire on the street'),
('2024-11-01 12:30:59', 'Cahul, Strada Victoriei, 3', 'Gas leak'),
('2024-11-01 14:00:22', 'Comrat, Strada Gagauzia, 11', 'Flood in the basement'),
('2024-11-01 15:15:18', 'Orhei, Strada Calarasi, 5', 'Burn victim'),
('2024-11-01 16:45:33', 'Soroca, Strada Alexandru Ioan Cuza, 17', 'Fire in store'),
('2024-11-01 18:00:12', 'Ungheni, Strada Stefan cel Mare, 12', 'Car accident with trapped victims'),
('2024-11-01 19:30:40', 'Edinet, Strada Unirii, 8', 'Forest fire'),
('2024-11-01 20:15:10', 'Drochia, Strada Mihai Viteazul, 14', 'Car accident with explosion'),
('2024-11-01 21:30:56', 'Falesti, Strada Maresalului Averescu, 20', 'Short circuit'),
('2024-11-01 23:00:03', 'Ialoveni, Strada Independentei, 9', 'Fire in cottage'),
('2024-11-02 01:30:27', 'Anenii Noi, Strada 31 August, 15', 'Gas explosion'),
('2024-11-02 03:00:50', 'Causeni, Strada Ludus, 6', 'Car accident with casualties'),
('2024-11-02 04:15:08', 'Hancesti, Strada Rosca, 7', 'Fire in warehouse');
GO

-- 2.5
-- Заполнение таблицы fire_truck_fire_call
INSERT INTO fire_truck_fire_call (truck_id, call_id)
VALUES
(2, 1),
(3, 1),
(4, 2),
(5, 2),
(6, 2),
(7, 3),
(8, 3),
(9, 3),
(10, 4),
(11, 4),
(12, 4),
(13, 5),
(14, 5),
(15, 5),
(16, 6),
(17, 6),
(18, 6),
(19, 7),
(20, 7),
(21, 7),
(22, 8),
(23, 8),
(24, 8),
(25, 9),
(2, 9),
(3, 10),
(4, 10),
(5, 10),
(6, 11),
(7, 11),
(8, 11),
(9, 12),
(10, 12),
(11, 12),
(12, 13),
(13, 13),
(14, 13),
(15, 14),
(16, 14),
(17, 14),
(18, 15),
(19, 15),
(20, 15)
GO

-- Добавление записей в таблицу fire_truck_fire_call для всех вызовов
INSERT INTO fire_truck_fire_call (truck_id, call_id)
SELECT 1, call_id
FROM fire_calls;

-- 3.1.1
-- Написать запрос, который выполняет операцию объединения алгебры отношений.
-- Объединение машин старше 7 лет и машин с объёмом двигателя меньше 7
SELECT truck_id, manufacture_year, engine_capacity
FROM fire_truck_details
WHERE YEAR(GETDATE()) - manufacture_year > 7  -- Машины старше 7 лет
UNION
SELECT truck_id, manufacture_year, engine_capacity
FROM fire_truck_details
WHERE engine_capacity < 7;  -- Машины с объёмом двигателя меньше 7

-- 3.1.1 (альтернатива)
-- Написать запрос, который выполняет операцию объединения алгебры отношений.
-- Объединение машин старше 7 лет и машин с объёмом двигателя меньше 7
SELECT truck_id, manufacture_year, engine_capacity
FROM fire_truck_details
WHERE YEAR(GETDATE()) - manufacture_year > 7  -- Машины старше 7 лет
OR engine_capacity < 7;  -- Машины с объёмом двигателя меньше 7


-- 3.1.2
-- Написать запрос, который выполняет операцию пересечения алгебры отношений.
-- Пересечение машин старше 7 лет и машин с объёмом двигателя меньше 7
SELECT truck_id, manufacture_year, engine_capacity
FROM fire_truck_details
WHERE YEAR(GETDATE()) - manufacture_year > 7  -- Машины старше 7 лет
INTERSECT
SELECT truck_id, manufacture_year, engine_capacity
FROM fire_truck_details
WHERE engine_capacity < 7;  -- Машины с объёмом двигателя меньше 7


-- 3.1.2 (альтернатива)
-- Написать запрос, который выполняет операцию пересечения алгебры отношений.
-- Пересечение машин старше 7 лет и машин с объёмом двигателя меньше 7
SELECT truck_id, manufacture_year, engine_capacity
FROM fire_truck_details
WHERE YEAR(GETDATE()) - manufacture_year > 7  -- Машины старше 7 лет
AND engine_capacity < 7;  -- Машины с объёмом двигателя меньше 7

-- 3.1.3
-- Написать запрос, который выполняет операцию разности алгебры отношений.
-- Разность машин старше 7 лет и машин с объёмом двигателя меньше 7
SELECT truck_id, manufacture_year, engine_capacity
FROM fire_truck_details
WHERE YEAR(GETDATE()) - manufacture_year > 7  -- Машины старше 7 лет
EXCEPT
SELECT truck_id, manufacture_year, engine_capacity
FROM fire_truck_details
WHERE engine_capacity < 7;  -- Машины с объёмом двигателя меньше 7

-- 3.1.3 (альтернатива)
-- Написать запрос, который выполняет операцию разности алгебры отношений.
-- Разность машин старше 7 лет и машин с объёмом двигателя меньше 7
SELECT ftd.truck_id, ftd.manufacture_year, ftd.engine_capacity
FROM fire_truck_details ftd
WHERE YEAR(GETDATE()) - ftd.manufacture_year > 7  -- Машины старше 7 лет
AND ftd.truck_id NOT IN (
    SELECT ftd2.truck_id
    FROM fire_truck_details ftd2
    WHERE ftd2.engine_capacity < 7  -- Машины с объёмом двигателя меньше 7
);

-- 3.1.4
-- Написать запрос, который выполняет операцию активного дополнения алгебры отношений.
-- Найти все комбинации пожарных машин и вызовов, которые не присутствуют в таблице fire_truck_fire_call.
SELECT ft1.truck_id AS TruckID, fc1.call_id AS CallID
FROM fire_trucks ft1, fire_calls fc1
WHERE NOT EXISTS (
    SELECT 1
    FROM fire_truck_fire_call ftfc
    WHERE ftfc.truck_id = ft1.truck_id AND ftfc.call_id = fc1.call_id
);

-- 3.1.5
-- Написать запрос, который выполняет операцию декартова произведения алгебры отношений.
-- Выполнить декартово произведение таблиц fire_trucks и fire_calls
SELECT ft.truck_id, ft.truck_name, fc.call_id, fc.call_time, fc.call_address
FROM fire_trucks ft, fire_calls fc
ORDER BY fc.call_time;

-- 3.1.6
-- Написать запрос, который выполняет операцию селекции и проекции алгебры отношений.
-- Найти все машины, которые принадлежат пожарным станциям с определённым именем, и проекцировать только truck_id и truck_name
SELECT ft.truck_id, ft.truck_name
FROM fire_trucks ft
JOIN fire_stations fs ON ft.station_id = fs.station_id
WHERE fs.station_name = 'Ungheni Fire Station';

-- 3.1.7
-- Написать запрос, который выполняет операцию тета-соединения алгебры отношений.
-- Соединить таблицы fire_trucks и fire_truck_details по truck_id, где engine_capacity больше 7
SELECT ft.truck_id, ft.truck_name, ftd.manufacture_year, ftd.engine_capacity
FROM fire_trucks ft
JOIN fire_truck_details ftd ON ft.truck_id = ftd.truck_id
WHERE ftd.engine_capacity > 7;

-- 3.1.8
-- Написать запрос, который выполняет операцию естественного соединения алгебры отношений.
-- Естественное соединение таблиц fire_trucks и fire_truck_details по полю truck_id
SELECT ft.truck_id, ft.truck_name, ftd.manufacture_year, ftd.engine_capacity
FROM fire_trucks ft
INNER JOIN fire_truck_details ftd ON ft.truck_id = ftd.truck_id;

-- 3.1.9
-- Написать запрос, который выполняет операцию полусоединения алгебры отношений.
-- Найти все пожарные машины, которые не принимали участие в определённом вызове (например, с call_id = 5)
SELECT ft.truck_id, ft.truck_name
FROM fire_trucks ft
RIGHT JOIN fire_truck_fire_call ftfc
    ON ft.truck_id = ftfc.truck_id
WHERE ftfc.call_id = 9  -- Определённый вызов
    AND ft.truck_id IS NOT NULL;  -- Машины, которые не участвовали в данном вызове

-- 3.1.10
-- Написать запрос, который выполняет операцию левого внешнего соединения алгебры отношений.
-- Найти все пожарные машины и информацию о вызовах, в которых они принимали участие (если таковые есть)
SELECT ft.truck_id, ft.truck_name, fcall.call_id, fcall.call_time
FROM fire_trucks ft
LEFT JOIN fire_truck_fire_call ftfc 
    ON ft.truck_id = ftfc.truck_id
LEFT JOIN fire_calls fcall
    ON ftfc.call_id = fcall.call_id;

-- 3.1.11
-- Написать запрос, который выполняет операцию правого внешнего соединения алгебры отношений.
-- Найти все вызовы и пожарные машины, которые участвовали в этих вызовах (если таковые есть)
SELECT fcall.call_id, fcall.call_time, ft.truck_id, ft.truck_name
FROM fire_calls fcall
RIGHT JOIN fire_truck_fire_call ftfc 
    ON fcall.call_id = ftfc.call_id
RIGHT JOIN fire_trucks ft
    ON ftfc.truck_id = ft.truck_id;

-- 3.1.12
-- Написать запрос, который выполняет операцию полного внешнего соединения алгебры отношений.
-- Найти все вызовы и все пожарные машины, которые участвовали или не участвовали в вызовах
SELECT fcall.call_id, fcall.call_time, ft.truck_id, ft.truck_name
FROM fire_calls fcall
FULL OUTER JOIN fire_truck_fire_call ftfc 
    ON fcall.call_id = ftfc.call_id
FULL OUTER JOIN fire_trucks ft
    ON ftfc.truck_id = ft.truck_id;

-- 3.1.13
-- Написать запрос, который выполняет операцию деления алгебры отношений.
-- Найти все машины, которые участвовали в хотя бы 2 вызовах.
SELECT ft.truck_id, ft.truck_name
FROM fire_trucks ft
WHERE NOT EXISTS (
    SELECT 1
    FROM fire_calls fc
    WHERE NOT EXISTS (
        SELECT 1
        FROM fire_truck_fire_call ftfc
        WHERE ftfc.truck_id = ft.truck_id AND ftfc.call_id = fc.call_id
    )
);

-- 3.2.1
-- Написать запрос, который выводит минимальную, максимальную и среднюю емкость двигателя пожарных машин.
-- Запрос должен вернуть минимальную емкость двигателя (MIN), максимальную емкость двигателя (MAX) и среднюю емкость двигателя (AVG) пожарных машин.
SELECT 
    MIN(engine_capacity) AS MinEngineCapacity,
    MAX(engine_capacity) AS MaxEngineCapacity,
    AVG(engine_capacity) AS AvgEngineCapacity
FROM fire_truck_details;

-- 3.2.2
-- Написать запрос, который использует агрегатную функцию SUM.
-- Запрос должен вернуть общую емкость двигателей (SUM) всех пожарных машин.
SELECT SUM(engine_capacity) AS TotalEngineCapacity
FROM fire_truck_details;

-- 3.2.3
-- Написать запрос, который использует агрегатную функцию COUNT.
-- Запрос должен вернуть количество пожарных машин (COUNT).
SELECT COUNT(*) AS TotalFireTrucks
FROM fire_trucks;

-- 3.3.1
-- Написать запрос с группировкой для одной таблицы.
-- Запрос должен вернуть количество пожарных машин (COUNT) на каждой станции (station_id).
SELECT station_id, COUNT(*) AS TotalFireTrucks
FROM fire_trucks
GROUP BY station_id;

-- 3.3.2
-- Написать запрос с группировкой для нескольких таблиц.
-- Запрос должен вернуть количество вызовов (COUNT) для каждой пожарной станции (station_id).
SELECT fs.station_id, fs.station_name,COUNT(fc.call_id) AS TotalFireCalls
FROM fire_stations fs
JOIN fire_trucks ft ON fs.station_id = ft.station_id
JOIN fire_truck_fire_call ftfc ON ft.truck_id = ftfc.truck_id
JOIN fire_calls fc ON ftfc.call_id = fc.call_id
GROUP BY fs.station_id, fs.station_name;

-- 3.4.1
-- Написать запрос с подзапросом, использующим операторы сравнения.
-- Запрос должен вернуть все пожарные машины, которые имеют емкость двигателя больше средней емкости всех пожарных машин.
SELECT truck_id, engine_capacity
FROM fire_truck_details
WHERE engine_capacity > (SELECT AVG(engine_capacity) FROM fire_truck_details);

-- 3.4.2
-- Написать запрос с подзапросом, использующим оператор IN.
-- Запрос должен вернуть все пожарные машины, которые участвовали в вызовах в Кишиневе.
SELECT truck_id, truck_name
FROM fire_trucks
WHERE truck_id IN (
    SELECT ftfc.truck_id
    FROM fire_truck_fire_call ftfc
    JOIN fire_calls fc ON ftfc.call_id = fc.call_id
    WHERE fc.call_address LIKE '%Chisinau%'
);

-- 3.4.3
-- Написать запрос с подзапросом, использующим оператор ALL.
-- Запрос должен вернуть все пожарные машины, которые имеют емкость двигателя больше, чем у всех машин, участвовавших в вызовах в Кишиневе.
SELECT truck_id, engine_capacity
FROM fire_truck_details
WHERE engine_capacity > ALL (
    SELECT ftd.engine_capacity
    FROM fire_truck_details ftd
    JOIN fire_truck_fire_call ftfc ON ftd.truck_id = ftfc.truck_id
    JOIN fire_calls fc ON ftfc.call_id = fc.call_id
    WHERE fc.call_address LIKE '%Chisinau%'
);

-- 3.4.4
-- Написать запрос с подзапросом, использующим оператор ANY.
-- Запрос должен вернуть все пожарные машины, которые имеют емкость двигателя больше, чем у любой машины, участвовавшей в вызовах в Кишиневе.
SELECT truck_id, engine_capacity
FROM fire_truck_details
WHERE engine_capacity > ANY (
    SELECT ftd.engine_capacity
    FROM fire_truck_details ftd
    JOIN fire_truck_fire_call ftfc ON ftd.truck_id = ftfc.truck_id
    JOIN fire_calls fc ON ftfc.call_id = fc.call_id
    WHERE fc.call_address LIKE '%Chisinau%'
);

-- 3.4.5
-- Написать запрос с подзапросом, использующим оператор EXISTS.
-- Запрос должен вернуть все пожарные машины, которые участвовали хотя бы в одном вызове в Кишиневе.
SELECT truck_id, truck_name
FROM fire_trucks ft
WHERE EXISTS (
    SELECT 1
    FROM fire_truck_fire_call ftfc
    JOIN fire_calls fc ON ftfc.call_id = fc.call_id
    WHERE ftfc.truck_id = ft.truck_id
    AND fc.call_address LIKE '%Chisinau%'
);

-- 3.4.6
-- Написать запрос с коррелированным подзапросом в WHERE.
-- Запрос должен вернуть все пожарные машины, которые участвовали в вызовах, где описание вызова содержит слово "fire".
SELECT truck_id, truck_name
FROM fire_trucks ft
WHERE EXISTS (
    SELECT 1
    FROM fire_truck_fire_call ftfc
    JOIN fire_calls fc ON ftfc.call_id = fc.call_id
    WHERE ftfc.truck_id = ft.truck_id
    AND fc.call_description LIKE '%fire%'
);

-- 3.4.6
-- Написать запрос с подзапросом в HAVING.
-- Запрос должен вернуть все пожарные станции, у которых общее количество вызовов превышает среднее количество вызовов по всем станциям.
SELECT fs.station_id, fs.station_name, COUNT(fc.call_id) AS TotalFireCalls
FROM fire_stations fs
JOIN fire_trucks ft ON fs.station_id = ft.station_id
JOIN fire_truck_fire_call ftfc ON ft.truck_id = ftfc.truck_id
JOIN fire_calls fc ON ftfc.call_id = fc.call_id
GROUP BY fs.station_id, fs.station_name
HAVING COUNT(fc.call_id) > (
    SELECT AVG(TotalCalls)
    FROM (
        SELECT COUNT(fc.call_id) AS TotalCalls
        FROM fire_stations fs
        JOIN fire_trucks ft ON fs.station_id = ft.station_id
        JOIN fire_truck_fire_call ftfc ON ft.truck_id = ftfc.truck_id
        JOIN fire_calls fc ON ftfc.call_id = fc.call_id
        GROUP BY fs.station_id
    ) AS AvgCalls
);

-- 3.4.6
-- Написать запрос с подзапросом в BETWEEN.
-- Запрос должен вернуть все пожарные машины, которые участвовали в вызовах, произошедших в определенный временной промежуток.
SELECT truck_id, truck_name
FROM fire_trucks
WHERE truck_id IN (
    SELECT ftfc.truck_id
    FROM fire_truck_fire_call ftfc
    JOIN fire_calls fc ON ftfc.call_id = fc.call_id
    WHERE fc.call_time BETWEEN '2024-11-01 00:00:00' AND '2024-11-02 23:59:59'
);

-- 3.4.7
-- Написать запрос с подзапросом в FROM.
-- Запрос должен вернуть количество вызовов для каждой пожарной машины.
SELECT truck_id, COUNT(call_id) AS TotalCalls
FROM (
    SELECT truck_id, call_id
    FROM fire_truck_fire_call
) AS SubQuery
GROUP BY truck_id;

-- 3.4.8
-- Написать запрос с подзапросом в SELECT.
-- Запрос должен вернуть информацию о пожарных машинах вместе с количеством вызовов, в которых они участвовали.
SELECT ft.truck_id, ft.truck_name, 
    (SELECT COUNT(*) 
     FROM fire_truck_fire_call ftfc 
     WHERE ftfc.truck_id = ft.truck_id) AS TotalCalls
FROM fire_trucks ft;

-- 4.1.1
-- Написать запрос, который удаляет первые несколько записей с условием.
-- Запрос должен удалить первые 5 записей, где описание вызова содержит слово "fire".
DELETE TOP (5)
FROM fire_calls
WHERE call_description LIKE '%fire%';

-- 4.1.2
-- Написать запрос для условного удаления с подзапросом для одной таблицы.
-- Удалить все пожарные машины, которые не связаны ни с одной записью в таблице вызовов.
DELETE FROM fire_trucks
WHERE truck_id NOT IN (
    SELECT truck_id
    FROM fire_truck_fire_call
);

-- 4.1.3
-- Написать запрос для обновления записей в таблице с использованием TOP.
-- Обновить первые 5 записей в таблице fire_trucks, установив значение engine_capacity на 5.0.
UPDATE TOP (5) fire_truck_details
SET engine_capacity = 5.0;

-- 4.1.4
-- Написать запрос, который обновляет записи на основе подзапроса с несколькими таблицами.
-- Обновить объем двигателя у пожарных машин, которые участвовали в вызовах, совершенных в текущем месяце.
UPDATE fire_truck_details
SET engine_capacity = engine_capacity + 1
WHERE truck_id IN (
    SELECT DISTINCT ftd.truck_id
    FROM fire_truck_details ftd
    JOIN fire_truck_fire_call ftfc ON ftd.truck_id = ftfc.truck_id
    JOIN fire_calls fc ON ftfc.call_id = fc.call_id
    WHERE MONTH(fc.call_time) = MONTH(GETDATE()) 
      AND YEAR(fc.call_time) = YEAR(GETDATE())
);

-- 4.2.1
-- Написать запрос для создания новой записи INSERT INTO с SELECT.
-- Создать новую запись в таблице fire_calls, копируя данные из таблицы fire_truck_fire_call.
INSERT INTO fire_calls (call_time, call_address, call_description)
SELECT GETDATE(), '123 Main St', 'Test call description'
FROM fire_truck_fire_call
WHERE truck_id = 3;


-- 4.2.2
-- Написать запрос для вставки результата запроса SELECT в новую таблицу.
-- Создать новую таблицу recent_fire_trucks и вставить в нее данные из таблицы fire_trucks для машин, произведенных после 2020 года.
SELECT truck_id, truck_name, station_id
INTO recent_fire_trucks
FROM fire_trucks
WHERE truck_id IN (
    SELECT truck_id
    FROM fire_truck_details
    WHERE manufacture_year > 2020
);

