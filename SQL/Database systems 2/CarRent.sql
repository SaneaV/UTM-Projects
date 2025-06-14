-- 2.1 
-- Установить соединение с master базой данных
USE master;
GO

-- Проверка и удаление базы данных CarRent, если она существует
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'CarRent')
BEGIN
    ALTER DATABASE CarRent SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE CarRent;
END
GO

-- Создание новой базы данных CarRent, если она не существует
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'CarRent')
BEGIN
    CREATE DATABASE CarRent;
END
GO

-- Использовать новую базу данных CarRent
USE CarRent;
GO

-- 2.2
-- Создание таблицы марок автомобилей
IF NOT EXISTS (SELECT * FROM sys.objects WHERE Name = 'brands' AND Type = 'U')
BEGIN
    CREATE TABLE brands
    (
        Id TINYINT IDENTITY(1, 1) NOT NULL,
        Brand VARCHAR(50) NOT NULL
    );
END;
GO

-- Создание таблицы цветов автомобилей
IF NOT EXISTS (SELECT * FROM sys.objects WHERE Name = 'colors' AND Type = 'U')
BEGIN
    CREATE TABLE colors
    (
        Id TINYINT IDENTITY(1, 1) NOT NULL,
        Color VARCHAR(30) NOT NULL
    );
END;
GO

-- Создание таблицы типов топлива
IF NOT EXISTS (SELECT * FROM sys.objects WHERE Name = 'fuel_types' AND Type = 'U')
BEGIN
    CREATE TABLE fuel_types
    (
        Id TINYINT IDENTITY(1, 1) NOT NULL,
        Fuel VARCHAR(60) NOT NULL
    );
END;
GO

-- Создание таблицы типов трансмиссий
IF NOT EXISTS (SELECT * FROM sys.objects WHERE Name = 'transmission_types' AND Type = 'U')
BEGIN
    CREATE TABLE transmission_types
    (
        Id TINYINT IDENTITY(1, 1) NOT NULL,
        Transmission VARCHAR(40) NOT NULL
    );
END;
GO

-- Создание таблицы типов кузовов
IF NOT EXISTS (SELECT * FROM sys.objects WHERE Name = 'body_types' AND Type = 'U')
BEGIN
    CREATE TABLE body_types
    (
        Id TINYINT IDENTITY(1, 1) NOT NULL,
        BodyType VARCHAR(40) NOT NULL
    );
END;
GO

-- Создание таблицы характеристик автомобилей
IF NOT EXISTS (SELECT * FROM sys.objects WHERE Name = 'features' AND Type = 'U')
BEGIN
    CREATE TABLE features
    (
        Id INT IDENTITY(1, 1) NOT NULL,
        Feature VARCHAR(70) NOT NULL
    );
END;
GO

-- Создание таблицы возможных статусов автомобилей
IF NOT EXISTS (SELECT * FROM sys.objects WHERE Name = 'reference_status' AND Type = 'U')
BEGIN
    CREATE TABLE reference_status
    (
        Id TINYINT IDENTITY(1, 1) NOT NULL,
        Status VARCHAR(15) NOT NULL
    );
END;
GO

-- Создание таблицы автомобилей
IF NOT EXISTS (SELECT * FROM sys.objects WHERE Name = 'cars' AND Type = 'U')
BEGIN
    CREATE TABLE cars
    (
        Id INT IDENTITY(1, 1) NOT NULL,
        LicensePlate CHAR(6) NOT NULL,
        BrandId TINYINT NOT NULL,
        CarModel VARCHAR(50) NOT NULL,
        RentPrice DECIMAL(10, 2) NOT NULL,
        StatusId TINYINT NOT NULL
    );
END;
GO

-- Создание таблицы дополнительных данных автомобилей
IF NOT EXISTS (SELECT * FROM sys.objects WHERE Name = 'cars_additional_details' AND Type = 'U')
BEGIN
    CREATE TABLE cars_additional_details
    (
        Id INT NOT NULL,
        ColorId TINYINT NOT NULL,
        FuelId TINYINT NOT NULL,
        TransmissionId TINYINT NOT NULL,
        BodyTypeId TINYINT NOT NULL,
        SeatCount TINYINT NOT NULL
    );
END;
GO

-- Создание таблицы ассоциации автомобилей и их характеристик
IF NOT EXISTS (SELECT * FROM sys.objects WHERE Name = 'car_features' AND Type = 'U')
BEGIN
    CREATE TABLE car_features
    (
        CarId INT NOT NULL,
        FeatureId INT NOT NULL
    );
END;
GO

-- Создание таблицы клиентов
IF NOT EXISTS (SELECT * FROM sys.objects WHERE Name = 'clients' AND Type = 'U')
BEGIN
    CREATE TABLE clients
    (
        Id INT IDENTITY(1, 1) NOT NULL,
        FirstName VARCHAR(30) NOT NULL,
        LastName VARCHAR(30) NOT NULL,
        PassportId CHAR(13) NOT NULL,
        PhoneNumber CHAR(9) NOT NULL,
        ClientAddress VARCHAR(70),
        BirthDate DATE NOT NULL
    );
END;
GO

-- Создание таблицы контрактов аренды автомобилей клиентами
IF NOT EXISTS (SELECT * FROM sys.objects WHERE Name = 'client_car_contracts' AND Type = 'U')
BEGIN
    CREATE TABLE client_car_contracts
    (
        ContractId BIGINT IDENTITY(1, 1) NOT NULL,
        ClientId INT NOT NULL,
        CarId INT NOT NULL,
        OrderDate DATE NOT NULL,
        StartDate DATE NOT NULL,
        EndDate DATE NOT NULL,
        TotalDays AS (DATEDIFF(DAY, StartDate, EndDate))
    );
END;
GO

-- 2.3
-- Установка ограничений целостности для таблицы марок автомобилей
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'PK_brands')
BEGIN
    ALTER TABLE brands ADD CONSTRAINT PK_brands PRIMARY KEY (Id); 
END;
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'UQ_brands_Brand')
BEGIN
    ALTER TABLE brands ADD CONSTRAINT UQ_brands_Brand UNIQUE (Brand); 
END;

-- Установка ограничений целостности для таблицы цветов автомобилей
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'PK_colors')
BEGIN
    ALTER TABLE colors ADD CONSTRAINT PK_colors PRIMARY KEY (Id); 
END;
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'UQ_colors_Color')
BEGIN
    ALTER TABLE colors ADD CONSTRAINT UQ_colors_Color UNIQUE (Color); 
END;

-- Установка ограничений целостности для таблицы типов топлива
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'PK_fuel_types')
BEGIN
    ALTER TABLE fuel_types ADD CONSTRAINT PK_fuel_types PRIMARY KEY (Id); 
END;
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'UQ_fuel_types_Fuel')
BEGIN
    ALTER TABLE fuel_types ADD CONSTRAINT UQ_fuel_types_Fuel UNIQUE (Fuel); 
END;

-- Установка ограничений целостности для таблицы типов трансмиссий
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'PK_transmission_types')
BEGIN
    ALTER TABLE transmission_types ADD CONSTRAINT PK_transmission_types PRIMARY KEY (Id); 
END;
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'UQ_transmission_types_Transmission')
BEGIN
    ALTER TABLE transmission_types ADD CONSTRAINT UQ_transmission_types_Transmission UNIQUE (Transmission); 
END;

-- Установка ограничений целостности для таблицы типов кузовов
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'PK_body_types')
BEGIN
    ALTER TABLE body_types ADD CONSTRAINT PK_body_types PRIMARY KEY (Id); 
END;
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'UQ_body_types_BodyType')
BEGIN
    ALTER TABLE body_types ADD CONSTRAINT UQ_body_types_BodyType UNIQUE (BodyType); 
END;

-- Установка ограничений целостности для таблицы характеристик автомобилей
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'PK_features')
BEGIN
    ALTER TABLE features ADD CONSTRAINT PK_features PRIMARY KEY (Id); 
END;
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'UQ_features_Feature')
BEGIN
    ALTER TABLE features ADD CONSTRAINT UQ_features_Feature UNIQUE (Feature); 
END;

-- Установка ограничений целостности для таблицы возможных статусов автомобилей
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'PK_reference_status')
BEGIN
    ALTER TABLE reference_status ADD CONSTRAINT PK_reference_status PRIMARY KEY (Id); 
END;
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'UQ_reference_status_Status')
BEGIN
    ALTER TABLE reference_status ADD CONSTRAINT UQ_reference_status_Status UNIQUE (Status); 
END;

-- Установка ограничений целостности для таблицы автомобилей
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'PK_cars')
BEGIN
    ALTER TABLE cars ADD CONSTRAINT PK_cars PRIMARY KEY (Id); 
END;
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'UQ_cars_LicensePlate')
BEGIN
    ALTER TABLE cars ADD CONSTRAINT UQ_cars_LicensePlate UNIQUE (LicensePlate); 
END;
IF NOT EXISTS (SELECT * FROM sys.check_constraints WHERE [name] = 'CHK_RentPrice')
BEGIN
    ALTER TABLE cars ADD CONSTRAINT CHK_RentPrice CHECK (RentPrice > 0); 
END;

-- Установка ограничений целостности для таблицы дополнительных данных автомобилей
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'PK_cars_additional_details')
BEGIN
    ALTER TABLE cars_additional_details ADD CONSTRAINT PK_cars_additional_details PRIMARY KEY (Id); 
END;
IF NOT EXISTS (SELECT * FROM sys.check_constraints WHERE [name] = 'CHK_SeatCount')
BEGIN
    ALTER TABLE cars_additional_details ADD CONSTRAINT CHK_SeatCount CHECK (SeatCount > 0); 
END;

-- Установка ограничений целостности для таблицы ассоциации автомобилей и их характеристик
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'PK_car_features')
BEGIN
    ALTER TABLE car_features ADD CONSTRAINT PK_car_features PRIMARY KEY (CarId, FeatureId); 
END;

-- Установка ограничений целостности для таблицы клиентов
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'PK_clients')
BEGIN
    ALTER TABLE clients ADD CONSTRAINT PK_clients PRIMARY KEY (Id); 
END;
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'UQ_clients_PassportId')
BEGIN
    ALTER TABLE clients ADD CONSTRAINT UQ_clients_PassportId UNIQUE (PassportId); 
END;
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'UQ_clients_PhoneNumber')
BEGIN
    ALTER TABLE clients ADD CONSTRAINT UQ_clients_PhoneNumber UNIQUE (PhoneNumber); 
END;
IF NOT EXISTS (SELECT * FROM sys.check_constraints WHERE [name] = 'CHK_Age')
BEGIN
    ALTER TABLE clients ADD CONSTRAINT CHK_Age CHECK (DATEDIFF(YEAR, BirthDate, GETDATE()) >= 18); 
END;
IF NOT EXISTS (SELECT * FROM sys.check_constraints WHERE [name] = 'CHK_PhoneNumber')
BEGIN
    ALTER TABLE clients ADD CONSTRAINT CHK_PhoneNumber 
    CHECK (
        PhoneNumber LIKE '0790%' OR PhoneNumber LIKE '0791%' OR PhoneNumber LIKE '0792%' OR 
        PhoneNumber LIKE '0793%' OR PhoneNumber LIKE '0794%' OR PhoneNumber LIKE '0795%' OR 
        PhoneNumber LIKE '0796%' OR PhoneNumber LIKE '0797%' OR PhoneNumber LIKE '0798%' OR 
        PhoneNumber LIKE '0799%' OR PhoneNumber LIKE '0780%' OR PhoneNumber LIKE '0781%' OR 
        PhoneNumber LIKE '0782%' OR PhoneNumber LIKE '0783%' OR PhoneNumber LIKE '0690%' OR 
        PhoneNumber LIKE '0691%' OR PhoneNumber LIKE '0692%' OR PhoneNumber LIKE '0693%' OR 
        PhoneNumber LIKE '0694%' OR PhoneNumber LIKE '0695%' OR PhoneNumber LIKE '0696%' OR 
        PhoneNumber LIKE '0697%' OR PhoneNumber LIKE '0698%' OR PhoneNumber LIKE '0699%' OR 
        PhoneNumber LIKE '0680%' OR PhoneNumber LIKE '0681%' OR PhoneNumber LIKE '0682%' OR 
        PhoneNumber LIKE '0683%' OR PhoneNumber LIKE '0684%' OR PhoneNumber LIKE '0685%' OR 
        PhoneNumber LIKE '0686%' OR PhoneNumber LIKE '0687%' OR PhoneNumber LIKE '0688%' OR 
        PhoneNumber LIKE '0689%' OR PhoneNumber LIKE '0600%' OR PhoneNumber LIKE '0601%' OR 
        PhoneNumber LIKE '0602%' OR PhoneNumber LIKE '0603%' OR PhoneNumber LIKE '0604%' OR 
        PhoneNumber LIKE '0671%' OR PhoneNumber LIKE '0672%'
    );
END;

-- Установка ограничений целостности для таблицы контрактов аренды автомобилей клиентами
IF NOT EXISTS (SELECT * FROM sys.key_constraints WHERE [name] = 'PK_client_car_contracts')
BEGIN
    ALTER TABLE client_car_contracts ADD CONSTRAINT PK_client_car_contracts PRIMARY KEY (ContractId); 
END;
IF NOT EXISTS (SELECT * FROM sys.check_constraints WHERE [name] = 'CHK_ContractDate')
BEGIN
    ALTER TABLE client_car_contracts ADD CONSTRAINT CHK_ContractDate CHECK (OrderDate < StartDate AND StartDate < EndDate);
END;
GO


-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

-- 2.5
-- Вставка брендов автомобилей
INSERT INTO brands (Brand) VALUES 
('Toyota'), 
('Honda'), 
('Ford'), 
('Chevrolet'), 
('BMW'), 
('Mercedes-Benz'), 
('Audi'), 
('Volkswagen'), 
('Nissan'), 
('Hyundai'), 
('Kia'), 
('Mazda'), 
('Subaru'), 
('Lexus'), 
('Jaguar');
GO

-- 2.5
-- Вставка цветов автомобилей
INSERT INTO colors (Color) VALUES 
('Red'), 
('Blue'), 
('Green'), 
('Black'), 
('White'), 
('Silver'), 
('Gray'), 
('Yellow'), 
('Orange'), 
('Purple'), 
('Brown'), 
('Gold'), 
('Pink'), 
('Beige'), 
('Maroon'),
('Olive');
GO

-- 2.5
-- Вставка типов топлива
INSERT INTO fuel_types (Fuel) VALUES 
('Petrol'), 
('Diesel'), 
('Electric'), 
('Hybrid'), 
('CNG'), 
('LPG'), 
('Hydrogen'), 
('Ethanol'), 
('Bio-diesel'), 
('Methanol'), 
('Propane'), 
('Butane'), 
('Natural Gas'), 
('Kerosene'), 
('Jet Fuel');
GO

-- 2.5
-- Вставка типов трансмиссий
INSERT INTO transmission_types (Transmission) VALUES 
('Manual'), 
('Automatic'), 
('Semi-Automatic'), 
('CVT'), 
('Dual-Clutch'), 
('Tiptronic'), 
('Direct Shift'), 
('Torque Converter'), 
('AMT'), 
('DSG'), 
('IVT'), 
('Hydraulic'), 
('Electric'), 
('Hydrostatic'), 
('Magnetic');
GO

-- 2.5
-- Вставка типов кузовов
INSERT INTO body_types (BodyType) VALUES 
('Sedan'), 
('Hatchback'), 
('SUV'), 
('Coupe'), 
('Convertible'), 
('Wagon'), 
('Van'), 
('Truck'), 
('Minivan'), 
('Crossover'), 
('Roadster'), 
('Pickup'), 
('Limousine'), 
('Microcar'), 
('Sportscar');
GO

-- 2.5
-- Вставка характеристик автомобилей
INSERT INTO features (Feature) VALUES 
('Air Conditioning'), 
('Leather Seats'), 
('Sunroof'), 
('Bluetooth'), 
('Backup Camera'), 
('Navigation System'), 
('Heated Seats'), 
('Cruise Control'), 
('Keyless Entry'), 
('Blind Spot Monitor'), 
('Lane Departure Warning'), 
('Parking Sensors'), 
('Remote Start'), 
('Apple CarPlay'), 
('Android Auto');
GO

-- 2.5
-- Вставка статусов
INSERT INTO reference_status (Status) VALUES 
('Available'), 
('Rented'), 
('Maintenance'), 
('Reserved');
GO

-- 2.5
-- Вставка автомобилей
INSERT INTO cars (LicensePlate, BrandId, CarModel, RentPrice, StatusId) VALUES 
('ABC123', 1, 'Corolla', 50.00, 1), -- Available
('DEF456', 2, 'Civic', 55.00, 1), -- Available
('GHI789', 3, 'Focus', 45.00, 2), -- Rented
('JKL012', 4, 'Malibu', 60.00, 1), -- Available
('MNO345', 5, '3 Series', 70.00, 3), -- Maintenance
('PQR678', 6, 'C-Class', 75.00, 1), -- Available
('STU901', 7, 'A4', 65.00, 1), -- Available
('VWX234', 8, 'Passat', 55.00, 2), -- Rented
('YZA567', 9, 'Altima', 50.00, 1), -- Available
('BCD890', 10, 'Elantra', 45.00, 1), -- Available
('EFG123', 11, 'Sportage', 60.00, 1), -- Available
('HIJ456', 12, 'CX-5', 65.00, 1), -- Available
('KLM789', 13, 'Outback', 70.00, 1), -- Available
('NOP012', 14, 'RX', 80.00, 1), -- Available
('QRS345', 15, 'XF', 90.00, 4), -- Available
('XYZ678', 1, 'Yaris', 120.50, 1); -- Available
GO

-- 2.5
-- Вставка дополнительных деталей автомобилей
INSERT INTO cars_additional_details (Id, ColorId, FuelId, TransmissionId, BodyTypeId, SeatCount) VALUES 
(1, 1, 1, 1, 1, 5), -- Toyota Corolla, Red, Petrol, Manual, Sedan, 5 seats
(2, 3, 2, 2, 2, 4), -- Honda Civic, Green, Diesel, Automatic, Hatchback, 4 seats
(3, 5, 3, 3, 3, 5), -- Tesla Model 3, White, Electric, Automatic, Sedan, 5 seats
(4, 2, 4, 4, 4, 2), -- Chevrolet Malibu, Blue, Hybrid, CVT, Coupe, 2 seats
(5, 4, 1, 5, 5, 4), -- BMW 3 Series, Black, Petrol, Dual-Clutch, Convertible, 4 seats
(6, 6, 2, 2, 6, 7), -- Mercedes-Benz C-Class, Silver, Diesel, Automatic, Wagon, 7 seats
(7, 8, 3, 2, 7, 8), -- Audi e-tron, Yellow, Electric, Automatic, SUV, 8 seats
(8, 7, 1, 1, 8, 3), -- Volkswagen Passat, Gray, Petrol, Manual, Sedan, 3 seats
(9, 9, 2, 2, 9, 6), -- Nissan Altima, Orange, Diesel, Automatic, Sedan, 6 seats
(10, 10, 3, 2, 10, 5), -- Hyundai Kona Electric, Purple, Electric, Automatic, Crossover, 5 seats
(11, 11, 1, 1, 11, 4), -- Kia Sportage, Brown, Petrol, Manual, SUV, 4 seats
(12, 12, 2, 2, 12, 2), -- Mazda CX-5, Gold, Diesel, Automatic, SUV, 2 seats
(13, 13, 3, 2, 13, 7), -- Subaru Outback, Pink, Electric, Automatic, Wagon, 7 seats
(14, 14, 1, 1, 14, 4), -- Lexus RX, Beige, Petrol, Manual, SUV, 4 seats
(15, 15, 2, 2, 15, 2), -- Jaguar XF, Maroon, Diesel, Automatic, Sedan, 2 seats
(16, 1, 2, 2, 15, 4); -- Toyaota Yaris, Red, Diesel, Automatic, Sedan, 2 seats
GO

-- 2.5
-- Вставка характеристик для автомобилей
INSERT INTO car_features (CarId, FeatureId) VALUES 
-- Toyota Corolla
(1, 1), -- Air Conditioning
(1, 2), -- Leather Seats
-- Honda Civic
(2, 3), -- Sunroof
(2, 4), -- Bluetooth
-- Tesla Model 3
(3, 5), -- Backup Camera
(3, 6), -- Navigation System
-- Chevrolet Malibu
(4, 7), -- Heated Seats
(4, 8), -- Cruise Control
-- BMW 3 Series
(5, 1),  -- Air Conditioning
(5, 2),  -- Leather Seats
(5, 3),  -- Sunroof
(5, 4),  -- Bluetooth
(5, 5),  -- Backup Camera
(5, 6),  -- Navigation System
(5, 7),  -- Heated Seats
(5, 8),  -- Cruise Control
(5, 9),  -- Keyless Entry
(5, 10), -- Blind Spot Monitor
(5, 11), -- Lane Departure Warning
(5, 12), -- Parking Sensors
(5, 13), -- Remote Start
(5, 14), -- Apple CarPlay
(5, 15), -- Android Auto
-- Mercedes-Benz C-Class
(6, 11), -- Lane Departure Warning
(6, 12), -- Parking Sensors
-- Audi e-tron
(7, 13), -- Remote Start
(7, 14), -- Apple CarPlay
-- Volkswagen Passat
(8, 15), -- Android Auto
(8, 1), -- Air Conditioning
-- Nissan Altima
(9, 2), -- Leather Seats
(9, 3), -- Sunroof
-- Hyundai Kona Electric
(10, 4), -- Bluetooth
(10, 5), -- Backup Camera
-- Kia Sportage
(11, 6), -- Navigation System
(11, 7), -- Heated Seats
-- Mazda CX-5
(12, 8), -- Cruise Control
(12, 9), -- Keyless Entry
-- Subaru Outback
(13, 10), -- Blind Spot Monitor
(13, 11), -- Lane Departure Warning
-- Lexus RX
(14, 12), -- Parking Sensors
(14, 13), -- Remote Start
-- Jaguar XF
(15, 14), -- Apple CarPlay
(15, 15), -- Android Auto
(15, 13); -- Remote Start
GO

-- 2.5
-- Заполнение таблицы клиентов
INSERT INTO clients (FirstName, LastName, PassportId, PhoneNumber, ClientAddress, BirthDate) VALUES 
('Ion', 'Popescu', 'A123456789012', '060123456', 'Strada Stefan cel Mare 1', '1980-01-01'), 
('Maria', 'Ionescu', 'B234567890123', '060234567', 'Strada Mihai Eminescu 2', '1985-02-02'), 
('Elena', 'Rusu', 'C345678901234', '060345678', 'Strada Alexandru cel Bun 3', '1990-03-03'), 
('Vasile', 'Munteanu', 'D456789012345', '060456789', 'Strada Grigore Vieru 4', '1995-04-04'), 
('Ana', 'Ciobanu', 'E567890123456', '060467890', 'Strada Veronica Micle 5', '2000-05-05'), 
('Gheorghe', 'Dumitru', 'F678901234567', '060078901', 'Strada Ion Creangă 6', '1982-06-06'), 
('Irina', 'Popa', 'G789012345678', '060289012', 'Strada Mihail Kogălniceanu 7', '1987-07-07'), 
('Andrei', 'Vasile', 'H890123456789', '060390123', 'Strada Nicolae Iorga 8', '1992-08-08'), 
('Cristina', 'Marin', 'I901234567890', '060101234', 'Strada Alexandru Donici 9', '1997-09-09'), 
('Mihai', 'Petrescu', 'J012345678901', '060112345', 'Strada Vasile Alecsandri 10', '1984-10-10'), 
('Tatiana', 'Stoica', 'K123456789012', '079923456', 'Strada George Cosbuc 11', '1989-11-11'), 
('Victor', 'Nistor', 'L234567890123', '069434567', 'Strada Tudor Vladimirescu 12', '1994-12-12'), 
('Olga', 'Bălan', 'M345678901234', '078045678', NULL, '1981-01-13'), 
('Sergiu', 'Lungu', 'N456789012345', '068056789', 'Strada Alexandru Lapusneanu 14', '1986-02-14'), 
('Natalia', 'Gheorghiu', 'O567890123456', '078067890', 'Strada Petru Rares 15', '1991-03-15'),
('Andrei', 'Popescu', 'O123456789012', '078067899', NULL, '1990-07-22');
GO

-- 2.5
-- Заполнение таблицы контрактов
INSERT INTO client_car_contracts (ClientId, CarId, OrderDate, StartDate, EndDate) VALUES 
(1, 3, '2024-10-01', '2024-10-02', '2024-10-10'), -- Ion Popescu арендует Ford Focus
(2, 8, '2024-09-15', '2024-09-16', '2024-09-20'), -- Maria Ionescu арендует Volkswagen Passat
(3, 1, '2024-10-05', '2024-10-06', '2024-10-12'), -- Elena Rusu арендует Toyota Corolla
(4, 5, '2024-09-25', '2024-09-26', '2024-09-30'), -- Vasile Munteanu арендует BMW 3 Series
(5, 2, '2024-10-10', '2024-10-11', '2024-10-15'), -- Ana Ciobanu арендует Honda Civic
(6, 4, '2024-10-03', '2024-10-04', '2024-10-08'), -- Gheorghe Dumitru арендует Chevrolet Malibu
(7, 6, '2024-09-20', '2024-09-21', '2024-09-25'), -- Irina Popa арендует Mercedes-Benz C-Class
(8, 7, '2024-10-07', '2024-10-08', '2024-10-14'), -- Andrei Vasile арендует Audi A4
(9, 9, '2024-09-28', '2024-09-29', '2024-10-03'), -- Cristina Marin арендует Nissan Altima
(10, 10, '2024-10-12', '2024-10-13', '2024-10-18'), -- Mihai Petrescu арендует Hyundai Elantra
(11, 11, '2024-09-22', '2024-09-23', '2024-09-27'), -- Tatiana Stoica арендует Kia Sportage
(12, 12, '2024-10-09', '2024-10-10', '2024-10-15'), -- Victor Nistor арендует Mazda CX-5
(13, 13, '2024-09-30', '2024-10-01', '2024-10-05'), -- Olga Bălan арендует Subaru Outback
(14, 14, '2024-10-11', '2024-10-12', '2024-10-16'), -- Sergiu Lungu арендует Lexus RX
(15, 15, '2024-10-04', '2024-10-05', '2024-10-09'); -- Natalia Gheorghiu арендует Jaguar XF
GO

-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


-- 3.1.1
-- Создание представления для отображения всех автомобилей, которые были арендованы клиентами, с отображением их моделей, марок и полных имен клиентов, а также дат начала и окончания аренды.
IF NOT EXISTS (SELECT * FROM sys.views WHERE Name = 'vw_CarRentals')
BEGIN
    EXEC('CREATE VIEW vw_CarRentals AS
    SELECT c.CarModel, 
           b.Brand AS CarBrand, 
           CONCAT(cl.FirstName, '', '', cl.LastName) AS FullName, 
           cc.StartDate AS ContractStartDate, 
           cc.EndDate AS ContractEndDate
    FROM cars c
    JOIN client_car_contracts cc ON c.Id = cc.CarId
    JOIN brands b ON c.BrandId = b.Id
    JOIN clients cl ON cl.Id = cc.ClientId;');
END;
GO

-- 3.1.2
-- Создание представления для отображения всех клиентов, у которых нет контрактов на аренду автомобилей.
IF NOT EXISTS (SELECT * FROM sys.views WHERE Name = 'vw_ClientsWithoutContracts')
BEGIN
    EXEC('CREATE VIEW vw_ClientsWithoutContracts AS
    SELECT Id, FirstName, LastName, PassportId, PhoneNumber, ClientAddress, BirthDate
    FROM clients
    WHERE Id NOT IN (
        SELECT DISTINCT ClientId 
        FROM client_car_contracts
    );');
END;
GO

-- 4.1.1
-- Создание некластеризованного индекса по столбцу RentPrice в таблице cars
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_cars_RentPrice')
BEGIN
    CREATE NONCLUSTERED INDEX IX_cars_RentPrice ON cars(RentPrice);
END;
GO

-- 4.1.2
-- Создание некластеризованного индекса по столбцу CarModel в таблице cars
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE name = 'IX_cars_CarModel')
BEGIN
    CREATE NONCLUSTERED INDEX IX_cars_CarModel ON cars(CarModel);
END;
GO

-- 5.1.1
-- Создание резервной копии базы данных CarRent
BACKUP DATABASE CarRent TO DISK = 'E:\Programming\UTM\SQL\Database systems 2\CarRent.bak';
GO

-- 6.1.1
-- Создание роли db_rent_admin
IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = 'db_rent_admin')
BEGIN
    CREATE ROLE db_rent_admin;
END;
GO

-- 6.1.2
-- Создание роли db_rent_user
IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = 'db_rent_user')
BEGIN
    CREATE ROLE db_rent_user;
END;
GO

-- 6.1.3
-- Создание пользователя user_admin с паролем, если он не существует
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = 'user_admin')
BEGIN
    CREATE LOGIN user_admin WITH PASSWORD = 'StrongPassword1!';
    CREATE USER user_admin FOR LOGIN user_admin WITH DEFAULT_SCHEMA=[dbo];
END;
GO

-- 6.1.4
-- Создание пользователя user_client с паролем, если он не существует
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = 'user_client')
BEGIN
    CREATE LOGIN user_client WITH PASSWORD = 'StrongPassword2!';
    CREATE USER user_client FOR LOGIN user_client WITH DEFAULT_SCHEMA=[dbo];
END;
GO

-- 6.1.5
-- Назначение ролей пользователям
IF EXISTS (SELECT * FROM sys.database_principals WHERE name = 'user_admin')
BEGIN
    ALTER ROLE db_rent_admin ADD MEMBER user_admin;
END;
IF EXISTS (SELECT * FROM sys.database_principals WHERE name = 'user_client')
BEGIN
    ALTER ROLE db_rent_user ADD MEMBER user_client;
END;
GO

-- 7.1.1
-- Создание схемы schema_rent
IF NOT EXISTS (SELECT * FROM sys.schemas WHERE name = 'schema_rent')
BEGIN
    EXEC('CREATE SCHEMA schema_rent AUTHORIZATION dbo;');
END;
GO

-- 8.1.1
-- Создание хранимой процедуры для добавления нового автомобиля
IF NOT EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'usp_AddCar')
BEGIN
    EXEC('CREATE PROCEDURE schema_rent.usp_AddCar
        @LicensePlate CHAR(6),
        @BrandId TINYINT,
        @CarModel VARCHAR(50),
        @RentPrice DECIMAL(10, 2),
        @StatusId TINYINT
    AS
    BEGIN
        INSERT INTO cars (LicensePlate, BrandId, CarModel, RentPrice, StatusId)
        VALUES (@LicensePlate, @BrandId, @CarModel, @RentPrice, @StatusId);
    END;');
END;
GO

EXEC schema_rent.usp_AddCar
    @LicensePlate = 'XYZ789',
    @BrandId = 3,
    @CarModel = 'Focus',
    @RentPrice = 45.00,
    @StatusId = 1;

-- 8.1.2
-- Создание хранимой процедуры для добавления нового клиента
IF NOT EXISTS (SELECT * FROM sys.objects WHERE type = 'P' AND name = 'usp_AddClient')
BEGIN
    EXEC('CREATE PROCEDURE schema_rent.usp_AddClient
        @FirstName VARCHAR(30),
        @LastName VARCHAR(30),
        @PassportId CHAR(13),
        @PhoneNumber CHAR(9),
        @ClientAddress VARCHAR(70),
        @BirthDate DATE
    AS
    BEGIN
        INSERT INTO clients (FirstName, LastName, PassportId, PhoneNumber, ClientAddress, BirthDate)
        VALUES (@FirstName, @LastName, @PassportId, @PhoneNumber, @ClientAddress, @BirthDate);
    END;');
END;
GO

EXEC schema_rent.usp_AddClient
    @FirstName = 'John',
    @LastName = 'Doe',
    @PassportId = 'A23456789012',
    @PhoneNumber = '079123456',
    @ClientAddress = '123 Main St, City, Country',
    @BirthDate = '1990-01-01';

-- 9.1.1
-- Создание функции для получения средней стоимости аренды автомобилей
IF NOT EXISTS (SELECT * FROM sys.objects WHERE type = 'FN' AND name = 'ufn_GetAverageRentPrice')
BEGIN
    EXEC('CREATE FUNCTION schema_rent.ufn_GetAverageRentPrice()
    RETURNS DECIMAL(10, 2)
    AS
    BEGIN
        DECLARE @AvgPrice DECIMAL(10, 2);
        SELECT @AvgPrice = AVG(RentPrice) FROM cars;
        RETURN @AvgPrice;
    END;');
END;
GO

SELECT schema_rent.ufn_GetAverageRentPrice() AS AverageRentPrice;

-- 9.1.2
-- Создание функции для получения количества автомобилей у определенного клиента
IF NOT EXISTS (SELECT * FROM sys.objects WHERE type = 'FN' AND name = 'ufn_GetCarCountByClient')
BEGIN
    EXEC('CREATE FUNCTION schema_rent.ufn_GetCarCountByClient(@ClientId INT)
    RETURNS INT
    AS
    BEGIN
        DECLARE @CarCount INT;
        SELECT @CarCount = COUNT(*) FROM client_car_contracts WHERE ClientId = @ClientId;
        RETURN @CarCount;
    END;');
END;
GO

SELECT schema_rent.ufn_GetCarCountByClient(2) AS NumberOfCars;

-- 10.1.1
-- Создание триггера для обновления статуса автомобиля при создании контракта
IF NOT EXISTS (SELECT * FROM sys.triggers WHERE name = 'trg_UpdateCarStatus')
BEGIN
    EXEC('CREATE TRIGGER trg_UpdateCarStatus
    ON client_car_contracts
    AFTER INSERT
    AS
    BEGIN
        UPDATE cars
        SET StatusId = 2
        WHERE Id IN (SELECT CarId FROM inserted);
    END;');
END;
GO

-- Тестирование триггера trg_UpdateCarStatus

-- Шаг 1: Вставка нового автомобиля в таблицу cars
INSERT INTO cars (LicensePlate, BrandId, CarModel, RentPrice, StatusId)
VALUES ('AB123', 1, 'ModelX', 150.00, 1);

-- Получение ID вставленного автомобиля
DECLARE @CarId INT;
SET @CarId = SCOPE_IDENTITY();

-- Шаг 2: Вставка нового клиента в таблицу clients
INSERT INTO clients (FirstName, LastName, PassportId, PhoneNumber, ClientAddress, BirthDate)
VALUES ('John', 'Doe', 'A12345789012', '079143456', '123 Main St', '1990-01-01');

-- Получение ID вставленного клиента
DECLARE @ClientId INT;
SET @ClientId = SCOPE_IDENTITY();

-- Шаг 3: Вставка нового контракта в таблицу client_car_contracts
INSERT INTO client_car_contracts (ClientId, CarId, OrderDate, StartDate, EndDate)
VALUES (@ClientId, @CarId, GETDATE(), DATEADD(DAY, 1, GETDATE()), DATEADD(DAY, 7, GETDATE()));

-- Шаг 4: Проверка, был ли обновлен StatusId автомобиля до 2
SELECT * FROM cars
WHERE Id = @CarId;

-- 10.1.2
-- Создание триггера для возврата автомобиля после завершения аренды
IF NOT EXISTS (SELECT * FROM sys.triggers WHERE name = 'trg_ReturnCarAfterContractEnd')
BEGIN
    EXEC('CREATE TRIGGER trg_ReturnCarAfterContractEnd
    ON client_car_contracts
    AFTER DELETE
    AS
    BEGIN
        UPDATE cars
        SET StatusId = 1
        WHERE Id IN (SELECT CarId FROM deleted);
    END;');
END;
GO

-- Шаг 1: Вставка нового автомобиля в таблицу cars
INSERT INTO cars (LicensePlate, BrandId, CarModel, RentPrice, StatusId)
VALUES ('XYZ889', 1, 'ModelY', 200.00, 1);

-- Получение ID вставленного автомобиля
DECLARE @CarIdForDelete INT;
SET @CarIdForDelete = SCOPE_IDENTITY();

-- Шаг 2: Вставка нового клиента в таблицу clients
INSERT INTO clients (FirstName, LastName, PassportId, PhoneNumber, ClientAddress, BirthDate)
VALUES ('Jane', 'Smith', 'B98765432012', '079987654', '456 Elm St', '1985-05-15');

-- Получение ID вставленного клиента
DECLARE @ClientId INT;
SET @ClientId = SCOPE_IDENTITY();

-- Шаг 3: Вставка нового контракта в таблицу client_car_contracts
INSERT INTO client_car_contracts (ClientId, CarId, OrderDate, StartDate, EndDate)
VALUES (@ClientId, @CarIdForDelete, GETDATE(), DATEADD(DAY, 1, GETDATE()), DATEADD(DAY, 7, GETDATE()));

-- Шаг 4: Удаление контракта из таблицы client_car_contracts
DELETE FROM client_car_contracts
WHERE ClientId = @ClientId AND CarId = @CarIdForDelete;

-- Шаг 5: Проверка, был ли обновлен StatusId автомобиля до 1
SELECT * FROM cars
WHERE Id = @CarIdForDelete;

-- 11.1.1
-- Пример транзакции для добавления автомобиля и его дополнительных деталей
BEGIN TRANSACTION AddCarTransaction;
BEGIN TRY
    -- Добавление автомобиля
    INSERT INTO cars (LicensePlate, BrandId, CarModel, RentPrice, StatusId)
    VALUES ('CCB123', 1, 'ModelX', 150.00, 1);

    -- Получение ID добавленного автомобиля
    DECLARE @CarId INT;
    SET @CarId = SCOPE_IDENTITY();

    -- Добавление дополнительных деталей автомобиля
    INSERT INTO cars_additional_details (Id, ColorId, FuelId, TransmissionId, BodyTypeId, SeatCount)
    VALUES (@CarId, 1, 1, 1, 1, 5);

    COMMIT TRANSACTION AddCarTransaction;

    -- Вывод записи автомобиля, который был добавлен
    SELECT * FROM cars WHERE Id = @CarId;
    SELECT * FROM cars_additional_details WHERE Id = @CarId;
END TRY
BEGIN CATCH
    ROLLBACK TRANSACTION AddCarTransaction;
    PRINT ERROR_MESSAGE();
END CATCH;
GO

-- Шаг 1: Вставка нового клиента в таблицу clients
INSERT INTO clients (FirstName, LastName, PassportId, PhoneNumber, ClientAddress, BirthDate)
VALUES ('Alice', 'Johnson', 'C123456789012', '079654321', '789 Pine St', '1992-02-20');

-- Получение ID вставленного клиента
DECLARE @ClientId INT;
SET @ClientId = SCOPE_IDENTITY();

-- Шаг 2: Вставка нового автомобиля в таблицу cars
INSERT INTO cars (LicensePlate, BrandId, CarModel, RentPrice, StatusId)
VALUES ('POF456', 2, 'ModelZ', 120.00, 1);

-- Получение ID вставленного автомобиля
DECLARE @CarId INT;
SET @CarId = SCOPE_IDENTITY();

-- Шаг 3: Вставка нового контракта в таблицу client_car_contracts
INSERT INTO client_car_contracts (ClientId, CarId, OrderDate, StartDate, EndDate)
VALUES (@ClientId, @CarId, GETDATE(), DATEADD(DAY, 1, GETDATE()), DATEADD(DAY, 7, GETDATE()));

-- Шаг 4: Вывод таблиц до удаления клиента и его контрактов
SELECT * FROM clients WHERE Id = @ClientId;
SELECT * FROM client_car_contracts WHERE ClientId = @ClientId;

-- Шаг 5: Проверка существования клиента и контрактов перед удалением
IF EXISTS (SELECT 1 FROM clients WHERE Id = @ClientId)
BEGIN
    IF EXISTS (SELECT 1 FROM client_car_contracts WHERE ClientId = @ClientId)
    BEGIN
        -- Шаг 6: Транзакция для удаления клиента и его контрактов
        BEGIN TRANSACTION DeleteClientTransaction;
        BEGIN TRY
            -- Удаление контрактов клиента
            DELETE FROM client_car_contracts WHERE ClientId = @ClientId;

            -- Удаление клиента
            DELETE FROM clients WHERE Id = @ClientId;

            COMMIT TRANSACTION DeleteClientTransaction;
        END TRY
        BEGIN CATCH
            ROLLBACK TRANSACTION DeleteClientTransaction;
            THROW;
        END CATCH;
    END
END
GO

-- Шаг 7: Проверка, были ли удалены клиент и его контракты
-- Проверка, что клиент удален
DECLARE @ClientIdCheck INT = (SELECT Id FROM clients WHERE PassportId = 'C123456789012');
SELECT * FROM clients WHERE Id = @ClientIdCheck;

-- Проверка, что контракты клиента удалены
SELECT * FROM client_car_contracts WHERE ClientId = @ClientIdCheck;
