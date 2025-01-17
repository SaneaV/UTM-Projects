-- 2.1 
-- Установить соединение с master базой данных
-- Переход на базу данных master, так как операции по удалению и изменению базы данных можно проводить только из неё
USE master;
GO

-- Проверка и удаление базы данных MoldovanEmploymentDB, если она существует
-- Если база данных MoldovanEmploymentDB существует, выполняется её удаление
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'MoldovanEmploymentDB')
BEGIN
    -- Перевод базы данных MoldovanEmploymentDB в режим однопользовательского доступа
    -- Это необходимо для принудительного завершения всех открытых транзакций и соединений с базой данных
    ALTER DATABASE MoldovanEmploymentDB SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    
    -- Удаление базы данных MoldovanEmploymentDB
    DROP DATABASE MoldovanEmploymentDB;
END
GO

-- Создание новой базы данных MoldovanEmploymentDB
-- Создаётся новая база данных с именем MoldovanEmploymentDB
CREATE DATABASE MoldovanEmploymentDB;
GO

-- Использовать новую базу данных MoldovanEmploymentDB
-- После создания новой базы данных, переключение на неё для дальнейших операций
USE MoldovanEmploymentDB;
GO

-- 2.2
-- Создание таблицы граждан
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'Citizens' 
               AND Type = 'U')
BEGIN
    CREATE TABLE Citizens
    (
        CitizenID INT IDENTITY(1, 1) NOT NULL PRIMARY KEY,
        FullName NVARCHAR(100) NOT NULL,
        BirthDate DATE NOT NULL,
        Gender CHAR(1) NOT NULL,
        HomeCountry NVARCHAR(50) NOT NULL
    );
END;
GO

-- Создание таблицы стран
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'Countries' 
               AND Type = 'U')
BEGIN
    CREATE TABLE Countries
    (
        CountryID INT IDENTITY(1, 1) NOT NULL PRIMARY KEY,
        CountryName NVARCHAR(100) NOT NULL,
        Region NVARCHAR(100) NOT NULL
    );
END;
GO

-- Создание таблицы работ
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'Jobs' 
               AND Type = 'U')
BEGIN
    CREATE TABLE Jobs
    (
        JobID INT IDENTITY(1, 1) NOT NULL PRIMARY KEY,
        JobTitle NVARCHAR(100) NOT NULL,
        Industry NVARCHAR(100) NOT NULL
    );
END;
GO

-- Создание таблицы контрактов работы
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'EmploymentContracts' 
               AND Type = 'U')
BEGIN
    CREATE TABLE EmploymentContracts
    (
        ContractID INT IDENTITY(1, 1) NOT NULL PRIMARY KEY,
        CitizenID INT NOT NULL,
        CountryID INT NOT NULL,
        JobID INT NOT NULL,
        StartDate DATE NOT NULL,
        EndDate DATE,
        Salary DECIMAL(10, 2) NOT NULL,
        FOREIGN KEY (CitizenID) REFERENCES Citizens(CitizenID),
        FOREIGN KEY (CountryID) REFERENCES Countries(CountryID),
        FOREIGN KEY (JobID) REFERENCES Jobs(JobID)
    );
END;
GO

-- Создание таблицы навыков
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'Skills' 
               AND Type = 'U')
BEGIN
    CREATE TABLE Skills
    (
        SkillID INT IDENTITY(1, 1) NOT NULL PRIMARY KEY,
        SkillName NVARCHAR(100) NOT NULL
    );
END;
GO

-- Создание таблицы навыков граждан
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'CitizenSkills' 
               AND Type = 'U')
BEGIN
    CREATE TABLE CitizenSkills
    (
        CitizenID INT NOT NULL,
        SkillID INT NOT NULL,
        PRIMARY KEY (CitizenID, SkillID),
        FOREIGN KEY (CitizenID) REFERENCES Citizens(CitizenID),
        FOREIGN KEY (SkillID) REFERENCES Skills(SkillID)
    );
END;
GO

-- Создание таблицы политик стран
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'CountryPolicies' 
               AND Type = 'U')
BEGIN
    CREATE TABLE CountryPolicies
    (
        PolicyID INT IDENTITY(1, 1) NOT NULL PRIMARY KEY,
        CountryID INT NOT NULL,
        PolicyDescription TEXT NOT NULL,
        FOREIGN KEY (CountryID) REFERENCES Countries(CountryID)
    );
END;
GO

-- 2.3
-- Установка ограничений целостности для таблицы Citizens
-- Установка уникальности для FullName и BirthDate (если необходимо)
ALTER TABLE Citizens ADD CONSTRAINT UQ_Citizens_FullName_BirthDate UNIQUE (FullName, BirthDate); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы Countries
-- Установка уникальности для CountryName (если необходимо)
ALTER TABLE Countries ADD CONSTRAINT UQ_Countries_CountryName UNIQUE (CountryName); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы Jobs
-- Установка уникальности для JobTitle (если необходимо)
ALTER TABLE Jobs ADD CONSTRAINT UQ_Jobs_JobTitle UNIQUE (JobTitle); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы EmploymentContracts
-- Добавление ограничения проверки для StartDate и EndDate
ALTER TABLE EmploymentContracts ADD CONSTRAINT CHK_EmploymentContracts_Dates CHECK (StartDate <= EndDate OR EndDate IS NULL); 
GO

-- Добавление ограничения проверки для Salary
ALTER TABLE EmploymentContracts ADD CONSTRAINT CHK_EmploymentContracts_Salary CHECK (Salary > 0); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы Skills
-- Установка уникальности для SkillName (если необходимо)
ALTER TABLE Skills ADD CONSTRAINT UQ_Skills_SkillName UNIQUE (SkillName); 
GO

-- 2.3
-- Установка значений по умолчанию
-- Установка значения по умолчанию для Salary в таблице EmploymentContracts
ALTER TABLE EmploymentContracts ADD CONSTRAINT DF_EmploymentContracts_Salary DEFAULT 1000.00 FOR Salary; 
GO

-- Установка значения по умолчанию для StartDate в таблице EmploymentContracts
ALTER TABLE EmploymentContracts ADD CONSTRAINT DF_EmploymentContracts_StartDate DEFAULT GETDATE() FOR StartDate; 
GO

-- 2.5
-- Заполнение таблицы Citizens
INSERT INTO Citizens (FullName, BirthDate, Gender, HomeCountry)
VALUES
('Ion Popescu', '1980-01-15', 'M', 'Moldova'),
('Maria Ionescu', '1985-03-22', 'F', 'Moldova'),
('Vasile Rusu', '1990-07-30', 'M', 'Moldova'),
('Elena Ciobanu', '1978-11-05', 'F', 'Moldova'),
('Andrei Munteanu', '1982-04-18', 'M', 'Moldova'),
('Ana Sandu', '1992-09-25', 'F', 'Moldova'),
('Gheorghe Mihai', '1987-06-12', 'M', 'Moldova'),
('Irina Pavel', '1995-12-03', 'F', 'Moldova'),
('Sergiu Luca', '1983-08-19', 'M', 'Moldova'),
('Tatiana Dumitru', '1991-10-10', 'F', 'Moldova');
GO

-- Заполнение таблицы Countries
INSERT INTO Countries (CountryName, Region)
VALUES
('Romania', 'Europe'),
('Italy', 'Europe'),
('Germany', 'Europe'),
('France', 'Europe'),
('Spain', 'Europe'),
('United Kingdom', 'Europe'),
('United States', 'North America'),
('Canada', 'North America'),
('Russia', 'Europe'),
('Ukraine', 'Europe');
GO

-- Заполнение таблицы Jobs
INSERT INTO Jobs (JobTitle, Industry)
VALUES
('Software Developer', 'IT'),
('Nurse', 'Healthcare'),
('Construction Worker', 'Construction'),
('Teacher', 'Education'),
('Chef', 'Hospitality'),
('Driver', 'Transport'),
('Electrician', 'Construction'),
('Sales Manager', 'Retail'),
('Accountant', 'Finance'),
('Engineer', 'Engineering');
GO

-- Заполнение таблицы EmploymentContracts
INSERT INTO EmploymentContracts (CitizenID, CountryID, JobID, StartDate, EndDate, Salary)
VALUES
(1, 1, 1, '2023-01-01', '2024-01-01', 3000.00),
(2, 2, 2, '2022-05-15', '2023-05-15', 2500.00),
(3, 3, 3, '2021-09-01', '2022-09-01', 2000.00),
(4, 4, 4, '2020-02-01', '2021-02-01', 2200.00),
(5, 5, 5, '2019-07-01', '2020-07-01', 2800.00),
(6, 6, 6, '2018-11-01', '2019-11-01', 2400.00),
(7, 7, 7, '2017-03-01', '2018-03-01', 2600.00),
(8, 8, 8, '2016-06-01', '2017-06-01', 2700.00),
(9, 9, 9, '2015-10-01', '2016-10-01', 2900.00),
(10, 10, 10, '2014-12-01', '2015-12-01', 3100.00);
GO

-- Заполнение таблицы Skills
INSERT INTO Skills (SkillName)
VALUES
('Java Programming'),
('Patient Care'),
('Masonry'),
('Lesson Planning'),
('Culinary Arts'),
('Driving'),
('Electrical Wiring'),
('Sales Techniques'),
('Financial Analysis'),
('Mechanical Engineering');
GO

-- Заполнение таблицы CitizenSkills
INSERT INTO CitizenSkills (CitizenID, SkillID)
VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(7, 7),
(8, 8),
(9, 9),
(10, 10);
GO

-- Заполнение таблицы CountryPolicies
INSERT INTO CountryPolicies (CountryID, PolicyDescription)
VALUES
(1, 'Visa-free entry for Moldovan citizens'),
(2, 'Work permit required for non-EU citizens'),
(3, 'Blue Card scheme for skilled workers'),
(4, 'Temporary residence permit for job seekers'),
(5, 'Seasonal work visa for agricultural workers'),
(6, 'Points-based immigration system'),
(7, 'H-1B visa for specialty occupations'),
(8, 'Express Entry system for skilled immigrants'),
(9, 'Work visa for CIS citizens'),
(10, 'Temporary work permit for foreign workers');
GO

-- 3.1.1
-- Написать запрос, который выполняет операцию объединения алгебры отношений.
-- Объединение контрактов с зарплатой больше 2500 и контрактов, начавшихся в 2023 году
SELECT ContractID, CitizenID, CountryID, JobID, StartDate, EndDate, Salary
FROM EmploymentContracts
WHERE Salary > 2500  -- Контракты с зарплатой больше 2500
UNION
SELECT ContractID, CitizenID, CountryID, JobID, StartDate, EndDate, Salary
FROM EmploymentContracts
WHERE StartDate BETWEEN '2023-01-01' AND '2023-12-31';  -- Контракты, начавшиеся в 2023 году

-- 3.1.1 (альтернатива)
-- Написать запрос, который выполняет операцию объединения алгебры отношений.
-- Объединение контрактов с зарплатой больше 2500 и контрактов, начавшихся в 2023 году
SELECT ContractID, CitizenID, CountryID, JobID, StartDate, EndDate, Salary
FROM EmploymentContracts
WHERE Salary > 2500  -- Контракты с зарплатой больше 2500
OR StartDate BETWEEN '2023-01-01' AND '2023-12-31';  -- Контракты, начавшиеся в 2023 году

-- 3.1.2
-- Написать запрос, который выполняет операцию пересечения алгебры отношений.
-- Пересечение контрактов с зарплатой больше 2500 и контрактов, начавшихся в 2023 году
SELECT ContractID, CitizenID, CountryID, JobID, StartDate, EndDate, Salary
FROM EmploymentContracts
WHERE Salary > 2500  -- Контракты с зарплатой больше 2500
INTERSECT
SELECT ContractID, CitizenID, CountryID, JobID, StartDate, EndDate, Salary
FROM EmploymentContracts
WHERE StartDate BETWEEN '2023-01-01' AND '2023-12-31';  -- Контракты, начавшиеся в 2023 году

-- 3.1.2 (альтернатива)
-- Написать запрос, который выполняет операцию пересечения алгебры отношений.
-- Пересечение контрактов с зарплатой больше 2500 и контрактов, начавшихся в 2023 году
SELECT ContractID, CitizenID, CountryID, JobID, StartDate, EndDate, Salary
FROM EmploymentContracts
WHERE Salary > 2500  -- Контракты с зарплатой больше 2500
AND StartDate BETWEEN '2023-01-01' AND '2023-12-31';  -- Контракты, начавшиеся в 2023 году

-- 3.1.3
-- Написать запрос, который выполняет операцию разности алгебры отношений.
-- Разность контрактов с зарплатой больше 2500 и контрактов, начавшихся в 2022 году
SELECT ContractID, CitizenID, CountryID, JobID, StartDate, EndDate, Salary
FROM EmploymentContracts
WHERE Salary > 2500  -- Контракты с зарплатой больше 2500
EXCEPT
SELECT ContractID, CitizenID, CountryID, JobID, StartDate, EndDate, Salary
FROM EmploymentContracts
WHERE StartDate BETWEEN '2022-01-01' AND '2022-12-31';  -- Контракты, начавшиеся в 2022 году

-- 3.1.3 (альтернатива)
-- Написать запрос, который выполняет операцию разности алгебры отношений.
-- Разность контрактов с зарплатой больше 2500 и контрактов, начавшихся в 2022 году
SELECT ec.ContractID, ec.CitizenID, ec.CountryID, ec.JobID, ec.StartDate, ec.EndDate, ec.Salary
FROM EmploymentContracts ec
WHERE ec.Salary > 2500  -- Контракты с зарплатой больше 2500
AND ec.ContractID NOT IN (
    SELECT ec2.ContractID
    FROM EmploymentContracts ec2
    WHERE ec2.StartDate BETWEEN '2022-01-01' AND '2022-12-31'  -- Контракты, начавшиеся в 2022 году
);

-- 3.1.4
-- Написать запрос, который выполняет операцию активного дополнения алгебры отношений.
-- Найти все комбинации граждан и навыков, которые не присутствуют в таблице CitizenSkills.
SELECT c.CitizenID, s.SkillID
FROM Citizens c, Skills s
WHERE NOT EXISTS (
    SELECT 1
    FROM CitizenSkills cs
    WHERE cs.CitizenID = c.CitizenID AND cs.SkillID = s.SkillID
);

-- 3.1.5
-- Написать запрос, который выполняет операцию декартова произведения алгебры отношений.
-- Выполнить декартово произведение таблиц Citizens и Jobs
SELECT c.CitizenID, c.FullName, j.JobID, j.JobTitle
FROM Citizens c, Jobs j
ORDER BY c.FullName;

-- 3.1.6
-- Написать запрос, который выполняет операцию селекции и проекции алгебры отношений.
-- Найти все контракты, заключённые гражданами с определённым именем, и проекцировать только ContractID и StartDate
SELECT ec.ContractID, ec.StartDate
FROM EmploymentContracts ec
JOIN Citizens c ON ec.CitizenID = c.CitizenID
WHERE c.FullName = 'Ion Popescu';

-- 3.1.7
-- Написать запрос, который выполняет операцию тета-соединения алгебры отношений.
-- Соединить таблицы EmploymentContracts и CitizenSkills по CitizenID, где SkillID больше 5
SELECT ec.ContractID, ec.StartDate, cs.SkillID
FROM EmploymentContracts ec
JOIN CitizenSkills cs ON ec.CitizenID = cs.CitizenID
WHERE cs.SkillID > 5;

-- 3.1.8
-- Написать запрос, который выполняет операцию естественного соединения алгебры отношений.
-- Естественное соединение таблиц EmploymentContracts и Citizens по полю CitizenID
SELECT ec.ContractID, ec.StartDate, c.FullName
FROM EmploymentContracts ec
INNER JOIN Citizens c ON ec.CitizenID = c.CitizenID;

-- 3.1.9
-- Написать запрос, который выполняет операцию полусоединения алгебры отношений.
-- Найти все контракты, которые не содержат определённый навык (например, с SkillID = 3)
SELECT ec.ContractID, ec.StartDate
FROM EmploymentContracts ec
RIGHT JOIN CitizenSkills cs
    ON ec.CitizenID = cs.CitizenID
WHERE cs.SkillID = 3  -- Определённый навык
    AND ec.ContractID IS NOT NULL;  -- Контракты, которые не содержат данный навык

-- 3.1.10
-- Написать запрос, который выполняет операцию левого внешнего соединения алгебры отношений.
-- Найти все контракты и информацию о гражданах, которые их заключили (если таковые есть)
SELECT ec.ContractID, ec.StartDate, c.FullName
FROM EmploymentContracts ec
LEFT JOIN Citizens c 
    ON ec.CitizenID = c.CitizenID;

-- 3.1.11
-- Написать запрос, который выполняет операцию правого внешнего соединения алгебры отношений.
-- Найти всех граждан и контракты, которые они заключили (если таковые есть)
SELECT c.CitizenID, c.FullName, ec.ContractID, ec.StartDate
FROM Citizens c
RIGHT JOIN EmploymentContracts ec 
    ON c.CitizenID = ec.CitizenID;

-- 3.1.12
-- Написать запрос, который выполняет операцию полного внешнего соединения алгебры отношений.
-- Найти все контракты и всех граждан, которые их заключили или не заключили
SELECT ec.ContractID, ec.StartDate, c.CitizenID, c.FullName
FROM EmploymentContracts ec
FULL OUTER JOIN Citizens c 
    ON ec.CitizenID = c.CitizenID;

-- 3.1.13
-- Написать запрос, который выполняет операцию деления алгебры отношений.
-- Найти всех граждан, которые имеют хотя бы 1 навык.
SELECT c.CitizenID, c.FullName
FROM Citizens c
WHERE (
    SELECT COUNT(DISTINCT cs.SkillID)
    FROM CitizenSkills cs
    WHERE cs.CitizenID = c.CitizenID
) >= 1;

-- 3.2.1
-- Написать запрос, который выводит минимальную, максимальную и среднюю зарплату контрактов.
-- Запрос должен вернуть минимальную зарплату (MIN), максимальную зарплату (MAX) и среднюю зарплату (AVG) контрактов.
SELECT 
    MIN(Salary) AS MinSalary,
    MAX(Salary) AS MaxSalary,
    AVG(Salary) AS AvgSalary
FROM EmploymentContracts;

-- 3.2.2
-- Написать запрос, который использует агрегатную функцию SUM.
-- Запрос должен вернуть общую сумму зарплат всех контрактов (SUM).
SELECT 
    SUM(Salary) AS TotalSalary
FROM EmploymentContracts;

-- 3.2.3
-- Написать запрос, который использует агрегатную функцию COUNT.
-- Запрос должен вернуть количество контрактов (COUNT).
SELECT 
    COUNT(*) AS TotalContracts
FROM EmploymentContracts;

-- 3.3.1
-- Написать запрос с группировкой для одной таблицы.
-- Запрос должен вернуть количество контрактов (COUNT) для каждого гражданина (CitizenID).
SELECT CitizenID, COUNT(*) AS TotalContracts
FROM EmploymentContracts
GROUP BY CitizenID;

-- 3.3.2
-- Написать запрос с группировкой для нескольких таблиц.
-- Запрос должен вернуть количество контрактов (COUNT) для каждой страны (CountryName).
SELECT c.CountryName, COUNT(ec.ContractID) AS TotalContracts
FROM Countries c
JOIN EmploymentContracts ec ON c.CountryID = ec.CountryID
GROUP BY c.CountryName;

-- 3.4.1
-- Написать запрос с подзапросом, использующим операторы сравнения.
-- Запрос должен вернуть все контракты, которые имеют зарплату больше средней зарплаты всех контрактов.
SELECT ContractID, Salary
FROM EmploymentContracts
WHERE Salary > (SELECT AVG(Salary) FROM EmploymentContracts);

-- 3.4.2
-- Написать запрос с подзапросом, использующим оператор IN.
-- Запрос должен вернуть все контракты, заключённые гражданами из Молдовы.
SELECT ContractID, StartDate
FROM EmploymentContracts
WHERE CitizenID IN (
    SELECT CitizenID
    FROM Citizens
    WHERE HomeCountry = 'Moldova'
);

-- 3.4.3
-- Написать запрос с подзапросом, использующим оператор ALL.
-- Запрос должен вернуть все контракты, которые имеют зарплату больше, чем у всех контрактов, заключённых гражданами из Румынии.
SELECT ContractID, Salary
FROM EmploymentContracts
WHERE Salary > ALL (
    SELECT ec.Salary
    FROM EmploymentContracts ec
    JOIN Citizens c ON ec.CitizenID = c.CitizenID
    WHERE c.HomeCountry = 'Romania'
);

-- 3.4.4
-- Написать запрос с подзапросом, использующим оператор ANY.
-- Запрос должен вернуть все контракты, которые имеют зарплату больше, чем у любого контракта, заключённого гражданами из Италии.
SELECT ContractID, Salary
FROM EmploymentContracts
WHERE Salary > ANY (
    SELECT ec.Salary
    FROM EmploymentContracts ec
    JOIN Citizens c ON ec.CitizenID = c.CitizenID
    WHERE c.HomeCountry = 'Italy'
);

-- 3.4.5
-- Написать запрос с подзапросом, использующим оператор EXISTS.
-- Запрос должен вернуть все контракты, заключённые гражданами из Молдовы.
SELECT ContractID, StartDate
FROM EmploymentContracts ec
WHERE EXISTS (
    SELECT 1
    FROM Citizens c
    WHERE ec.CitizenID = c.CitizenID
    AND c.HomeCountry = 'Moldova'
);

-- 3.4.6
-- Написать запрос с коррелированным подзапросом в WHERE.
-- Запрос должен вернуть все контракты, заключённые гражданами с навыком "Java Programming".
SELECT ContractID, StartDate
FROM EmploymentContracts ec
WHERE EXISTS (
    SELECT 1
    FROM CitizenSkills cs
    JOIN Skills s ON cs.SkillID = s.SkillID
    WHERE cs.CitizenID = ec.CitizenID
    AND s.SkillName = 'Java Programming'
);

-- 3.4.6
-- Написать запрос с подзапросом в HAVING.
-- Запрос должен вернуть все страны, у которых общее количество контрактов превышает среднее количество контрактов по всем странам.
SELECT c.CountryName, COUNT(ec.ContractID) AS TotalContracts
FROM Countries c
JOIN EmploymentContracts ec ON c.CountryID = ec.CountryID
GROUP BY c.CountryName
HAVING COUNT(ec.ContractID) > (
    SELECT AVG(ContractCount)
    FROM (
        SELECT COUNT(ec.ContractID) AS ContractCount
        FROM Countries c
        JOIN EmploymentContracts ec ON c.CountryID = ec.CountryID
        GROUP BY c.CountryName
    ) AS AvgContracts
);

-- 3.4.6
-- Написать запрос с подзапросом в BETWEEN.
-- Запрос должен вернуть все контракты, начавшиеся в определённый временной промежуток.
SELECT ContractID, StartDate
FROM EmploymentContracts
WHERE StartDate BETWEEN '2023-01-01' AND '2023-12-31';

-- 3.4.7
-- Написать запрос с подзапросом в FROM.
-- Запрос должен вернуть количество контрактов для каждого гражданина.
SELECT CitizenID, COUNT(ContractID) AS TotalContracts
FROM (
    SELECT CitizenID, ContractID
    FROM EmploymentContracts
) AS SubQuery
GROUP BY CitizenID;

-- 3.4.8
-- Написать запрос с подзапросом в SELECT.
-- Запрос должен вернуть информацию о гражданах вместе с количеством контрактов, которые они заключили.
SELECT 
    c.CitizenID, 
    c.FullName, 
    (SELECT COUNT(*) 
     FROM EmploymentContracts ec 
     WHERE ec.CitizenID = c.CitizenID) AS TotalContracts
FROM 
    Citizens c;

-- 4.1.1
-- Написать запрос, который удаляет первые несколько записей с условием.
-- Запрос должен удалить первые 5 записей, где название навыка содержит слово "Programming".
-- Удаление записей из таблицы CitizenSkills, связанных с первыми 5 записями в таблице Skills, где название навыка содержит слово "Programming"
DELETE FROM CitizenSkills
WHERE SkillID IN (
    SELECT TOP (5) SkillID
    FROM Skills
    WHERE SkillName LIKE '%Programming%'
);

-- Удаление первых 5 записей из таблицы Skills, где название навыка содержит слово "Programming"
DELETE TOP (5)
FROM Skills
WHERE SkillName LIKE '%Programming%';

-- 4.1.2
-- Написать запрос для условного удаления с подзапросом для одной таблицы.
-- Удалить все навыки, которые не связаны ни с одной записью в таблице CitizenSkills.
DELETE FROM Skills
WHERE SkillID NOT IN (
    SELECT SkillID
    FROM CitizenSkills
);

-- 4.1.3
-- Написать запрос для обновления записей в таблице с использованием TOP.
-- Обновить первые 5 записей в таблице Skills, установив значение SkillName на 'Updated Skill'.
WITH TopSkills AS (
    SELECT SkillID, ROW_NUMBER() OVER (ORDER BY SkillID) AS RowNum
    FROM Skills
    WHERE SkillName != 'Updated Skill'
)
UPDATE Skills
SET SkillName = 'Updated Skill ' + CAST(RowNum AS NVARCHAR(10))
FROM TopSkills
WHERE Skills.SkillID = TopSkills.SkillID;

-- 4.1.4
-- Написать запрос, который обновляет записи на основе подзапроса с несколькими таблицами.
-- Обновить зарплату контрактов, заключённых в текущем месяце.
UPDATE EmploymentContracts
SET Salary = Salary + 500.00
WHERE ContractID IN (
    SELECT DISTINCT ec.ContractID
    FROM EmploymentContracts ec
    WHERE MONTH(ec.StartDate) = MONTH(GETDATE()) 
      AND YEAR(ec.StartDate) = YEAR(GETDATE())
);

-- 4.2.1
-- Написать запрос для создания новой записи INSERT INTO с SELECT.
-- Создать новую запись в таблице EmploymentContracts, копируя данные из таблицы CitizenSkills.
INSERT INTO EmploymentContracts (CitizenID, CountryID, JobID, StartDate, EndDate, Salary)
SELECT cs.CitizenID, 1, 1, GETDATE(), NULL, 3000.00
FROM CitizenSkills cs
WHERE cs.SkillID = 2;

-- 4.2.2
-- Написать запрос для вставки результата запроса SELECT в новую таблицу.
-- Создать новую таблицу recent_contracts и вставить в неё данные из таблицы EmploymentContracts для контрактов, начавшихся после 1 января 2023 года.
SELECT ContractID, CitizenID, CountryID, JobID, StartDate, EndDate, Salary
INTO recent_contracts
FROM EmploymentContracts
WHERE StartDate > '2023-01-01';