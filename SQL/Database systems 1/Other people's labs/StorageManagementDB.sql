-- 2.1 
-- Установить соединение с master базой данных
-- Переход на базу данных master, так как операции по удалению и изменению базы данных можно проводить только из неё
USE master;
GO

-- Проверка и удаление базы данных StorageManagementDB, если она существует
-- Если база данных StorageManagementDB существует, выполняется её удаление
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'StorageManagementDB')
BEGIN
    -- Перевод базы данных StorageManagementDB в режим однопользовательского доступа
    -- Это необходимо для принудительного завершения всех открытых транзакций и соединений с базой данных
    ALTER DATABASE StorageManagementDB SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    
    -- Удаление базы данных StorageManagementDB
    DROP DATABASE StorageManagementDB;
END
GO

-- Создание новой базы данных StorageManagementDB
-- Создаётся новая база данных с именем StorageManagementDB
CREATE DATABASE StorageManagementDB;
GO

-- Использовать новую базу данных StorageManagementDB
-- После создания новой базы данных, переключение на неё для дальнейших операций
USE StorageManagementDB;
GO

-- 2.2
-- Создание таблицы менеджеров
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'Managers' 
               AND Type = 'U')
BEGIN
    CREATE TABLE Managers
    (
        Manager_ID INT IDENTITY(1, 1) NOT NULL,
        Name NVARCHAR(100) NOT NULL,
        Contact_Info NVARCHAR(255) NOT NULL DEFAULT 'Not specified'
    );
END;
GO

-- 2.2
-- Создание таблицы складов
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'Warehouses' 
               AND Type = 'U')
BEGIN
    CREATE TABLE Warehouses
    (
        Warehouse_ID INT IDENTITY(1, 1) NOT NULL,
        Name NVARCHAR(100) NOT NULL,
        Address NVARCHAR(255) NOT NULL DEFAULT 'Not specified',
        Manager_ID INT NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы секций
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'Sections' 
               AND Type = 'U')
BEGIN
    CREATE TABLE Sections
    (
        Section_ID INT IDENTITY(1, 1) NOT NULL,
        Section_Name NVARCHAR(100) NOT NULL DEFAULT 'General section',
        Warehouse_ID INT NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы клиентов
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'Clients' 
               AND Type = 'U')
BEGIN
    CREATE TABLE Clients
    (
        Client_ID INT IDENTITY(1, 1) NOT NULL,
        Name NVARCHAR(100) NOT NULL,
        Contact_Info NVARCHAR(255) NOT NULL DEFAULT 'Not specified'
    );
END;
GO

-- 2.2
-- Создание таблицы договоров
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'Contracts' 
               AND Type = 'U')
BEGIN
    CREATE TABLE Contracts
    (
        Contract_ID INT IDENTITY(1, 1) NOT NULL,
        Section_ID INT NOT NULL,
        Client_ID INT NOT NULL,
        Start_Date DATE NOT NULL DEFAULT GETDATE(),
        End_Date DATE NOT NULL
    );
END;
GO

-- 2.3
-- Установка первичных ключей для таблицы Managers
ALTER TABLE Managers ADD CONSTRAINT PK_Managers PRIMARY KEY (Manager_ID); 
GO

-- Установка первичных ключей для таблицы Warehouses
ALTER TABLE Warehouses ADD CONSTRAINT PK_Warehouses PRIMARY KEY (Warehouse_ID); 
GO

-- Установка первичных ключей для таблицы Sections
ALTER TABLE Sections ADD CONSTRAINT PK_Sections PRIMARY KEY (Section_ID); 
GO

-- Установка первичных ключей для таблицы Clients
ALTER TABLE Clients ADD CONSTRAINT PK_Clients PRIMARY KEY (Client_ID); 
GO

-- Установка первичных ключей для таблицы Contracts
ALTER TABLE Contracts ADD CONSTRAINT PK_Contracts PRIMARY KEY (Contract_ID); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы Warehouses
-- Установка уникальности для Manager_ID
ALTER TABLE Warehouses ADD CONSTRAINT UQ_Warehouses_Manager_ID UNIQUE (Manager_ID); 
GO

-- Установка уникальности для Name
ALTER TABLE Warehouses ADD CONSTRAINT UQ_Warehouses_Name UNIQUE (Name); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы Contracts
-- Добавление ограничения проверки для End_Date
ALTER TABLE Contracts ADD CONSTRAINT CHK_Contracts_End_Date CHECK (End_Date > Start_Date); 
GO

-- 2.4
-- Установка внешних ключей для таблицы Warehouses
-- Добавление внешнего ключа для связи с таблицей Managers
ALTER TABLE Warehouses 
ADD CONSTRAINT FK_Warehouses_Manager_ID FOREIGN KEY (Manager_ID) 
REFERENCES Managers (Manager_ID) 
ON DELETE CASCADE ON UPDATE CASCADE; 
GO

-- Установка внешних ключей для таблицы Sections
-- Добавление внешнего ключа для связи с таблицей Warehouses
ALTER TABLE Sections 
ADD CONSTRAINT FK_Sections_Warehouse_ID FOREIGN KEY (Warehouse_ID) 
REFERENCES Warehouses (Warehouse_ID) 
ON DELETE CASCADE ON UPDATE CASCADE; 
GO

-- Установка внешних ключей для таблицы Contracts
-- Добавление внешнего ключа для связи с таблицей Sections
ALTER TABLE Contracts 
ADD CONSTRAINT FK_Contracts_Section_ID FOREIGN KEY (Section_ID) 
REFERENCES Sections (Section_ID) 
ON DELETE CASCADE ON UPDATE CASCADE; 
GO

-- Добавление внешнего ключа для связи с таблицей Clients
ALTER TABLE Contracts 
ADD CONSTRAINT FK_Contracts_Client_ID FOREIGN KEY (Client_ID) 
REFERENCES Clients (Client_ID) 
ON DELETE CASCADE ON UPDATE CASCADE; 
GO

-- 2.5
-- Заполнение таблицы Managers
INSERT INTO Managers (Name, Contact_Info)
VALUES
('Ion Popescu', 'ion.popescu@example.md'),
('Maria Ionescu', 'maria.ionescu@example.md'),
('Vasile Rusu', 'vasile.rusu@example.md'),
('Elena Ciobanu', 'elena.ciobanu@example.md'),
('Mihai Sandu', 'mihai.sandu@example.md'),
('Ana Gheorghe', 'ana.gheorghe@example.md'),
('Andrei Luca', 'andrei.luca@example.md'),
('Irina Balan', 'irina.balan@example.md'),
('Gheorghe Munteanu', 'gheorghe.munteanu@example.md'),
('Cristina Pavel', 'cristina.pavel@example.md'),
('Dumitru Marin', 'dumitru.marin@example.md'),
('Tatiana Stoica', 'tatiana.stoica@example.md'),
('Alexandru Popa', 'alexandru.popa@example.md'),
('Natalia Ursu', 'natalia.ursu@example.md'),
('Victor Toma', 'victor.toma@example.md');
GO

-- 2.5
-- Заполнение таблицы Warehouses
INSERT INTO Warehouses (Name, Address, Manager_ID)
VALUES
('Warehouse Chisinau', 'Strada Stefan cel Mare, 1, Chisinau', 1),
('Warehouse Balti', 'Strada Independentei, 45, Balti', 2),
('Warehouse Cahul', 'Strada Republicii, 12, Cahul', 3),
('Warehouse Orhei', 'Strada Mihai Eminescu, 23, Orhei', 4),
('Warehouse Ungheni', 'Strada Decebal, 34, Ungheni', 5),
('Warehouse Soroca', 'Strada Stefan cel Mare, 56, Soroca', 6),
('Warehouse Comrat', 'Strada Lenin, 78, Comrat', 7),
('Warehouse Hincesti', 'Strada Alexandru cel Bun, 90, Hincesti', 8),
('Warehouse Edinet', 'Strada Mihai Viteazul, 101, Edinet', 9),
('Warehouse Drochia', 'Strada Stefan cel Mare, 112, Drochia', 10),
('Warehouse Falesti', 'Strada Mihai Eminescu, 123, Falesti', 11),
('Warehouse Ialoveni', 'Strada Stefan cel Mare, 134, Ialoveni', 12),
('Warehouse Anenii Noi', 'Strada Mihai Viteazul, 145, Anenii Noi', 13),
('Warehouse Causeni', 'Strada Stefan cel Mare, 156, Causeni', 14),
('Warehouse Nisporeni', 'Strada Mihai Eminescu, 167, Nisporeni', 15);
GO

-- 2.5
-- Заполнение таблицы Sections
INSERT INTO Sections (Section_Name, Warehouse_ID)
VALUES
('Section 1', 1),
('Section 2', 1),
('Section 3', 2),
('Section 4', 2),
('Section 5', 3),
('Section 6', 3),
('Section 7', 4),
('Section 8', 4),
('Section 9', 5),
('Section 10', 5),
('Section 11', 6),
('Section 12', 6),
('Section 13', 7),
('Section 14', 7),
('Section 15', 8);
GO

-- 2.5
-- Заполнение таблицы Clients
INSERT INTO Clients (Name, Contact_Info)
VALUES
('Alexandru Ionescu', 'alexandru.ionescu@example.md'),
('Veronica Popa', 'veronica.popa@example.md'),
('Mihail Rusu', 'mihail.rusu@example.md'),
('Elena Balan', 'elena.balan@example.md'),
('Ion Gheorghe', 'ion.gheorghe@example.md'),
('Ana Luca', 'ana.luca@example.md'),
('Andrei Munteanu', 'andrei.munteanu@example.md'),
('Irina Pavel', 'irina.pavel@example.md'),
('Gheorghe Marin', 'gheorghe.marin@example.md'),
('Cristina Stoica', 'cristina.stoica@example.md'),
('Dumitru Popescu', 'dumitru.popescu@example.md'),
('Tatiana Ursu', 'tatiana.ursu@example.md'),
('Alexandru Sandu', 'alexandru.sandu@example.md'),
('Natalia Ciobanu', 'natalia.ciobanu@example.md'),
('Victor Toma', 'victor.toma@example.md');
GO

-- 2.5
-- Заполнение таблицы Contracts
INSERT INTO Contracts (Section_ID, Client_ID, Start_Date, End_Date)
VALUES
(1, 1, '2024-01-01', '2025-01-01'),
(2, 2, '2024-02-01', '2025-02-01'),
(3, 3, '2024-03-01', '2025-03-01'),
(4, 4, '2024-04-01', '2025-04-01'),
(5, 5, '2024-05-01', '2025-05-01'),
(6, 6, '2024-06-01', '2025-06-01'),
(7, 7, '2024-07-01', '2025-07-01'),
(8, 8, '2024-08-01', '2025-08-01'),
(9, 9, '2024-09-01', '2025-09-01'),
(10, 10, '2024-10-01', '2025-10-01'),
(11, 11, '2024-11-01', '2025-11-01'),
(12, 12, '2024-12-01', '2025-12-01'),
(13, 13, '2024-01-15', '2025-01-15'),
(14, 14, '2024-02-15', '2025-02-15'),
(15, 15, '2024-03-15', '2025-03-15');
GO

-- 3.1.1
-- Написать запрос, который выполняет операцию объединения алгебры отношений.
-- Объединение секций с договорами, начинающимися до 2024 года и секций с договорами, заканчивающимися после 2025 года
SELECT Section_ID, Start_Date, End_Date
FROM Contracts
WHERE Start_Date < '2024-06-01'  -- Договора, начинающиеся до июня 2024 года
UNION
SELECT Section_ID, Start_Date, End_Date
FROM Contracts
WHERE End_Date > '2025-06-01';  -- Договора, заканчивающиеся после июня 2025 года

-- 3.1.1 (альтернатива)
-- Написать запрос, который выполняет операцию объединения алгебры отношений.
-- Объединение секций с договорами, начинающимися до 2024 года и секций с договорами, заканчивающимися после 2025 года
SELECT Section_ID, Start_Date, End_Date
FROM Contracts
WHERE Start_Date < '2024-01-01'  -- Договора, начинающиеся до 2024 года
OR End_Date > '2025-01-01';  -- Договора, заканчивающиеся после 2025 года

-- 3.1.2
-- Написать запрос, который выполняет операцию пересечения алгебры отношений.
-- Пересечение секций с договорами, начинающимися до 2024 года и секций с договорами, заканчивающимися после 2025 года
SELECT Section_ID, Start_Date, End_Date
FROM Contracts
WHERE Start_Date < '2024-06-01'  -- Договора, начинающиеся до июня 2024 года
INTERSECT
SELECT Section_ID, Start_Date, End_Date
FROM Contracts
WHERE End_Date > '2025-01-01';  -- Договора, заканчивающиеся после 2025 года

-- 3.1.2 (альтернатива)
-- Написать запрос, который выполняет операцию пересечения алгебры отношений.
-- Пересечение секций с договорами, начинающимися до 2024 года и секций с договорами, заканчивающимися после 2025 года
SELECT Section_ID, Start_Date, End_Date
FROM Contracts
WHERE Start_Date < '2024-06-01'  -- Договора, начинающиеся до 2024 года
AND End_Date > '2025-01-01';  -- Договора, заканчивающиеся после 2025 года

-- 3.1.3
-- Написать запрос, который выполняет операцию разности алгебры отношений.
-- Разность секций с договорами, начинающимися до 2024 года и секций с договорами, заканчивающимися после 2025 года
SELECT Section_ID, Start_Date, End_Date
FROM Contracts
WHERE Start_Date < '2024-06-01'  -- Договора, начинающиеся до июня 2024 года
EXCEPT
SELECT Section_ID, Start_Date, End_Date
FROM Contracts
WHERE End_Date > '2025-01-01';  -- Договора, заканчивающиеся после 2025 года

-- 3.1.3 (альтернатива)
-- Написать запрос, который выполняет операцию разности алгебры отношений.
-- Разность секций с договорами, начинающимися до 2024 года и секций с договорами, заканчивающимися после 2025 года
SELECT c1.Section_ID, c1.Start_Date, c1.End_Date
FROM Contracts c1
WHERE c1.Start_Date < '2024-06-01'  -- Договора, начинающиеся до июня 2024 года
AND c1.Section_ID NOT IN (
    SELECT c2.Section_ID
    FROM Contracts c2
    WHERE c2.End_Date > '2025-01-01'  -- Договора, заканчивающиеся после 2025 года
);

-- 3.1.4
-- Написать запрос, который выполняет операцию активного дополнения алгебры отношений.
-- Найти все комбинации секций и клиентов, которые не присутствуют в таблице Contracts.
SELECT s.Section_ID AS SectionID, c.Client_ID AS ClientID
FROM Sections s, Clients c
WHERE NOT EXISTS (
    SELECT 1
    FROM Contracts con
    WHERE con.Section_ID = s.Section_ID AND con.Client_ID = c.Client_ID
);

-- 3.1.5
-- Написать запрос, который выполняет операцию декартова произведения алгебры отношений.
-- Выполнить декартово произведение таблиц Sections и Clients
SELECT s.Section_ID, s.Section_Name, c.Client_ID, c.Name
FROM Sections s, Clients c
ORDER BY s.Section_ID;

-- 3.1.6
-- Написать запрос, который выполняет операцию селекции и проекции алгебры отношений.
-- Найти все секции, которые принадлежат складам с определённым именем, и проекцировать только Section_ID и Section_Name
SELECT s.Section_ID, s.Section_Name
FROM Sections s
JOIN Warehouses w ON s.Warehouse_ID = w.Warehouse_ID
WHERE w.Name = 'Warehouse Chisinau';

-- 3.1.7
-- Написать запрос, который выполняет операцию тета-соединения алгебры отношений.
-- Соединить таблицы Sections и Contracts по Section_ID, где End_Date больше 2025-01-01
SELECT s.Section_ID, s.Section_Name, c.Start_Date, c.End_Date
FROM Sections s
JOIN Contracts c ON s.Section_ID = c.Section_ID
WHERE c.End_Date > '2025-01-01';

-- 3.1.8
-- Написать запрос, который выполняет операцию естественного соединения алгебры отношений.
-- Естественное соединение таблиц Sections и Contracts по полю Section_ID
SELECT s.Section_ID, s.Section_Name, c.Start_Date, c.End_Date
FROM Sections s
INNER JOIN Contracts c ON s.Section_ID = c.Section_ID;

-- 3.1.9
-- Написать запрос, который выполняет операцию полусоединения алгебры отношений.
-- Найти все секции, которые не имеют договоров с определённым клиентом (например, с Client_ID = 5)
SELECT s.Section_ID, s.Section_Name
FROM Sections s
RIGHT JOIN Contracts c
    ON s.Section_ID = c.Section_ID
WHERE c.Client_ID = 5  -- Определённый клиент
    AND s.Section_ID IS NOT NULL;  -- Секции, которые не имеют договоров с данным клиентом

-- 3.1.10
-- Написать запрос, который выполняет операцию левого внешнего соединения алгебры отношений.
-- Найти все секции и информацию о договорах, если таковые есть
SELECT s.Section_ID, s.Section_Name, c.Contract_ID, c.Start_Date, c.End_Date
FROM Sections s
LEFT JOIN Contracts c 
    ON s.Section_ID = c.Section_ID;

-- 3.1.11
-- Написать запрос, который выполняет операцию правого внешнего соединения алгебры отношений.
-- Найти все договоры и секции, к которым они относятся (если таковые есть)
SELECT c.Contract_ID, c.Start_Date, c.End_Date, s.Section_ID, s.Section_Name
FROM Contracts c
RIGHT JOIN Sections s 
    ON c.Section_ID = s.Section_ID;

-- 3.1.12
-- Написать запрос, который выполняет операцию полного внешнего соединения алгебры отношений.
-- Найти все договоры и все секции, которые имеют или не имеют договоров
SELECT c.Contract_ID, c.Start_Date, c.End_Date, s.Section_ID, s.Section_Name
FROM Contracts c
FULL OUTER JOIN Sections s 
    ON c.Section_ID = s.Section_ID;

-- 3.1.13
-- Написать запрос, который выполняет операцию деления алгебры отношений.
-- Найти все секции, которые имеют хотя бы 2 договора.
SELECT s.Section_ID, s.Section_Name
FROM Sections s
WHERE NOT EXISTS (
    SELECT 1
    FROM Contracts c
    WHERE NOT EXISTS (
        SELECT 1
        FROM Contracts c2
        WHERE c2.Section_ID = s.Section_ID AND c2.Contract_ID = c.Contract_ID
    )
);

-- 3.2.1
-- Написать запрос, который выводит минимальную, максимальную и среднюю дату начала договоров.
-- Запрос должен вернуть минимальную дату начала (MIN), максимальную дату начала (MAX) и среднюю продолжительность договоров в днях (AVG).
SELECT 
    MIN(Start_Date) AS MinStartDate,
    MAX(Start_Date) AS MaxStartDate,
    AVG(DATEDIFF(day, Start_Date, End_Date)) AS AvgContractDuration
FROM Contracts;

-- 3.2.2
-- Написать запрос, который использует агрегатную функцию SUM.
-- Запрос должен вернуть общую продолжительность всех договоров в днях (SUM).
SELECT 
    SUM(DATEDIFF(day, Start_Date, End_Date)) AS TotalContractDuration
FROM Contracts;

-- 3.2.3
-- Написать запрос, который использует агрегатную функцию COUNT.
-- Запрос должен вернуть количество клиентов (COUNT).
SELECT 
    COUNT(*) AS TotalClients
FROM Clients;

-- 3.3.1
-- Написать запрос с группировкой для одной таблицы.
-- Запрос должен вернуть количество секций (COUNT) в каждом складе (Warehouse_ID).
SELECT Warehouse_ID, COUNT(*) AS TotalSections
FROM Sections
GROUP BY Warehouse_ID;

-- 3.3.2
-- Написать запрос с группировкой для нескольких таблиц.
-- Запрос должен вернуть количество договоров (COUNT) для каждого склада (Warehouse_ID).
SELECT w.Warehouse_ID, w.Name AS WarehouseName, COUNT(c.Contract_ID) AS TotalContracts
FROM Warehouses w
JOIN Sections s ON w.Warehouse_ID = s.Warehouse_ID
JOIN Contracts c ON s.Section_ID = c.Section_ID
GROUP BY w.Warehouse_ID, w.Name;

-- Добавление дополнительных данных в таблицу Contracts
INSERT INTO Contracts (Section_ID, Client_ID, Start_Date, End_Date)
VALUES
(1, 1, '2024-01-01', '2025-01-01'),
(1, 2, '2024-02-01', '2025-02-01'),
(1, 3, '2024-03-01', '2025-03-01'),
(2, 4, '2024-04-01', '2025-04-01'),
(2, 5, '2024-05-01', '2025-05-01'),
(3, 6, '2024-06-01', '2025-06-01'),
(3, 7, '2024-07-01', '2025-07-01'),
(3, 8, '2024-08-01', '2025-08-01'),
(3, 9, '2024-09-01', '2025-09-01'),
(4, 10, '2024-10-01', '2025-10-01'),
(4, 11, '2024-11-01', '2025-11-01'),
(4, 12, '2024-12-01', '2025-12-01'),
(5, 13, '2024-01-15', '2025-01-15'),
(5, 14, '2024-02-15', '2025-02-15'),
(5, 15, '2024-03-15', '2025-03-15'),
(5, 1, '2024-04-15', '2025-04-15'),
(5, 2, '2024-05-15', '2025-05-15'),
(5, 3, '2024-06-15', '2025-06-15');
GO

-- 3.4.1
-- Написать запрос с подзапросом, использующим операторы сравнения.
-- Запрос должен вернуть все секции, которые имеют количество договоров больше среднего количества договоров по всем секциям.
SELECT Section_ID, COUNT(Contract_ID) AS TotalContracts
FROM Contracts
GROUP BY Section_ID
HAVING COUNT(Contract_ID) > (SELECT AVG(TotalContracts) FROM (SELECT COUNT(Contract_ID) AS TotalContracts FROM Contracts GROUP BY Section_ID) AS AvgContracts);

-- 3.4.2
-- Написать запрос с подзапросом, использующим оператор IN.
-- Запрос должен вернуть все секции, которые принадлежат складам в Кишиневе.
SELECT Section_ID, Section_Name
FROM Sections
WHERE Warehouse_ID IN (
    SELECT Warehouse_ID
    FROM Warehouses
    WHERE Address LIKE '%Chisinau%'
);

-- 3.4.3
-- Написать запрос с подзапросом, использующим оператор ALL.
-- Запрос должен вернуть все секции, которые имеют количество договоров больше, чем у всех секций, принадлежащих складам в Кишиневе.
SELECT Section_ID, COUNT(Contract_ID) AS TotalContracts
FROM Contracts
GROUP BY Section_ID
HAVING COUNT(Contract_ID) > ALL (
    SELECT COUNT(c.Contract_ID)
    FROM Contracts c
    JOIN Sections s ON c.Section_ID = s.Section_ID
    JOIN Warehouses w ON s.Warehouse_ID = w.Warehouse_ID
    WHERE w.Address LIKE '%Chisinau%'
    GROUP BY s.Section_ID
);

-- 3.4.4
-- Написать запрос с подзапросом, использующим оператор ANY.
-- Запрос должен вернуть все секции, которые имеют количество договоров больше, чем у любой секции, принадлежащей складам в Кишиневе.
SELECT Section_ID, COUNT(Contract_ID) AS TotalContracts
FROM Contracts
GROUP BY Section_ID
HAVING COUNT(Contract_ID) > ANY (
    SELECT COUNT(c.Contract_ID)
    FROM Contracts c
    JOIN Sections s ON c.Section_ID = s.Section_ID
    JOIN Warehouses w ON s.Warehouse_ID = w.Warehouse_ID
    WHERE w.Address LIKE '%Chisinau%'
    GROUP BY s.Section_ID
);

-- 3.4.5
-- Написать запрос с подзапросом, использующим оператор EXISTS.
-- Запрос должен вернуть все секции, которые принадлежат складам в Кишиневе.
SELECT Section_ID, Section_Name
FROM Sections s
WHERE EXISTS (
    SELECT 1
    FROM Warehouses w
    WHERE s.Warehouse_ID = w.Warehouse_ID
    AND w.Address LIKE '%Chisinau%'
);

-- 3.4.6
-- Написать запрос с коррелированным подзапросом в WHERE.
-- Запрос должен вернуть все секции, которые имеют договоры с клиентами, чьи имена содержат слово "Ion".
SELECT Section_ID, Section_Name
FROM Sections s
WHERE EXISTS (
    SELECT 1
    FROM Contracts c
    JOIN Clients cl ON c.Client_ID = cl.Client_ID
    WHERE c.Section_ID = s.Section_ID
    AND cl.Name LIKE '%Ion%'
);

-- 3.4.7
-- Написать запрос с подзапросом в HAVING.
-- Запрос должен вернуть все склады, у которых общее количество договоров превышает среднее количество договоров по всем складам.
SELECT w.Warehouse_ID, w.Name AS WarehouseName, COUNT(c.Contract_ID) AS TotalContracts
FROM Warehouses w
JOIN Sections s ON w.Warehouse_ID = s.Warehouse_ID
JOIN Contracts c ON s.Section_ID = c.Section_ID
GROUP BY w.Warehouse_ID, w.Name
HAVING COUNT(c.Contract_ID) > (
    SELECT AVG(TotalContracts)
    FROM (
        SELECT COUNT(c.Contract_ID) AS TotalContracts
        FROM Warehouses w
        JOIN Sections s ON w.Warehouse_ID = s.Warehouse_ID
        JOIN Contracts c ON s.Section_ID = c.Section_ID
        GROUP BY w.Warehouse_ID
    ) AS AvgContracts
);

-- 3.4.8
-- Написать запрос с подзапросом в BETWEEN.
-- Запрос должен вернуть все секции, которые имеют договоры, начавшиеся в определенный временной промежуток.
SELECT Section_ID, Section_Name
FROM Sections
WHERE Section_ID IN (
    SELECT Section_ID
    FROM Contracts
    WHERE Start_Date BETWEEN '2024-01-01' AND '2024-12-31'
);

-- 3.4.9
-- Написать запрос с подзапросом в FROM.
-- Запрос должен вернуть количество договоров для каждой секции.
SELECT Section_ID, COUNT(Contract_ID) AS TotalContracts
FROM (
    SELECT Section_ID, Contract_ID
    FROM Contracts
) AS SubQuery
GROUP BY Section_ID;

-- 3.4.10
-- Написать запрос с подзапросом в SELECT.
-- Запрос должен вернуть информацию о секциях вместе с количеством договоров, которые они имеют.
SELECT 
    s.Section_ID, 
    s.Section_Name, 
    (SELECT COUNT(*) 
     FROM Contracts c 
     WHERE c.Section_ID = s.Section_ID) AS TotalContracts
FROM 
    Sections s;

-- 4.1.1
-- Написать запрос, который удаляет первые несколько записей с условием.
-- Запрос должен удалить первые 5 записей, где имя клиента содержит слово "Ion".
DELETE TOP (5)
FROM Clients
WHERE Name LIKE '%Ion%';

-- 4.1.2
-- Написать запрос для условного удаления с подзапросом для одной таблицы.
-- Удалить все секции, которые не связаны ни с одной записью в таблице договоров.
DELETE FROM Sections
WHERE Section_ID NOT IN (
    SELECT Section_ID
    FROM Contracts
);

-- 4.1.3
-- Написать запрос для обновления записей в таблице с использованием TOP.
-- Обновить первые 5 записей в таблице Contracts, установив значение End_Date на '2025-12-31'.
UPDATE TOP (5) Contracts
SET End_Date = '2025-12-31';

-- 4.1.4
-- Написать запрос, который обновляет записи на основе подзапроса с несколькими таблицами.
-- Обновить дату окончания у договоров, которые принадлежат складам в Кишиневе.
UPDATE Contracts
SET End_Date = DATEADD(year, 1, End_Date)
WHERE Section_ID IN (
    SELECT s.Section_ID
    FROM Sections s
    JOIN Warehouses w ON s.Warehouse_ID = w.Warehouse_ID
    WHERE w.Address LIKE '%Chisinau%'
);

-- 4.2.1
-- Написать запрос для создания новой записи INSERT INTO с SELECT.
-- Создать новую запись в таблице Contracts, копируя данные из таблицы Sections.
-- Используем существующий Client_ID из таблицы Clients.
INSERT INTO Contracts (Section_ID, Client_ID, Start_Date, End_Date)
SELECT Section_ID, (SELECT TOP 1 Client_ID FROM Clients), GETDATE(), DATEADD(year, 1, GETDATE())
FROM Sections
WHERE Section_ID = 3;

-- 4.2.2
-- Написать запрос для вставки результата запроса SELECT в новую таблицу.
-- Создать новую таблицу recent_contracts и вставить в нее данные из таблицы Contracts для договоров, начавшихся после 2024 года.
SELECT Contract_ID, Section_ID, Client_ID, Start_Date, End_Date
INTO recent_contracts
FROM Contracts
WHERE Start_Date > '2024-01-01';