-- 2.1 
-- Установить соединение с master базой данных
-- Переход на базу данных master, так как операции по удалению и изменению базы данных можно проводить только из неё
USE master;
GO

-- Проверка и удаление базы данных CarRent, если она существует
-- Если база данных CarRent существует, выполняется её удаление
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'CarRent')
BEGIN
    -- Перевод базы данных CarRent в режим однопользовательского доступа
    -- Это необходимо для принудительного завершения всех открытых транзакций и соединений с базой данных
    ALTER DATABASE CarRent SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    
    -- Удаление базы данных CarRent
    DROP DATABASE CarRent;
END
GO

-- Создание новой базы данных CarRent
-- Создаётся новая база данных с именем CarRent
CREATE DATABASE CarRent;
GO

-- Использовать новую базу данных CarRent
-- После создания новой базы данных, переключение на неё для дальнейших операций
USE CarRent;
GO

-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

-- 2.2
-- Создание таблицы марок автомобилей
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'brands' 
               AND Type = 'U')
BEGIN
    CREATE TABLE brands
    (
        Id TINYINT IDENTITY(1, 1) NOT NULL,
        Brand VARCHAR(50) NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы цветов автомобилей
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'colors' 
               AND Type = 'U')
BEGIN
    CREATE TABLE colors
    (
        Id TINYINT IDENTITY(1, 1) NOT NULL,
        Color VARCHAR(30) NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы типов топлива
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'fuel_types' 
               AND Type = 'U')
BEGIN
    CREATE TABLE fuel_types
    (
        Id TINYINT IDENTITY(1, 1) NOT NULL,
        Fuel VARCHAR(60) NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы типов трансмиссий
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'transmission_types' 
               AND Type = 'U')
BEGIN
    CREATE TABLE transmission_types
    (
        Id TINYINT IDENTITY(1, 1) NOT NULL,
        Transmission VARCHAR(40) NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы типов кузовов
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'body_types' 
               AND Type = 'U')
BEGIN
    CREATE TABLE body_types
    (
        Id TINYINT IDENTITY(1, 1) NOT NULL,
        BodyType VARCHAR(40) NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы характеристик автомобилей
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'features' 
               AND Type = 'U')
BEGIN
    CREATE TABLE features
    (
        Id INT IDENTITY(1, 1) NOT NULL,
        Feature VARCHAR(70) NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы возможных статусов автомобилей
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'reference_status' 
               AND Type = 'U')
BEGIN
    CREATE TABLE reference_status
    (
        Id TINYINT IDENTITY(1, 1) NOT NULL,
        Status VARCHAR(15) NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы автомобилей
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'cars' 
               AND Type = 'U')
BEGIN
    CREATE TABLE cars
    (
        Id INT IDENTITY(1, 1) NOT NULL,
        LicensePlate CHAR(6)  NOT NULL,
        BrandId TINYINT  NOT NULL,
        CarModel VARCHAR(50)  NOT NULL,
        RentPrice DECIMAL(10, 2)  NOT NULL,
        StatusId TINYINT  NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы дополнительных данных автомобилей
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'cars_additional_details' 
               AND Type = 'U')
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

-- 2.2
-- Создание таблицы ассоциации автомобилей и их характеристик
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'car_features' 
               AND Type = 'U')
BEGIN
    CREATE TABLE car_features
    (
        CarId INT NOT NULL,
        FeatureId INT NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы клиентов
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'clients' 
               AND Type = 'U')
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

-- 2.2
-- Создание таблицы контрактов аренды автомобилей клиентами
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'client_car_contracts' 
               AND Type = 'U')
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

-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

-- 2.3
-- Установка ограничений целостности для таблицы марок автомобилей
-- Добавление первичного ключа для таблицы brands
ALTER TABLE brands ADD CONSTRAINT PK_brands PRIMARY KEY (Id); 
GO
-- Добавление уникального ограничения для столбца Brand
ALTER TABLE brands ADD CONSTRAINT UQ_brands_Brand UNIQUE (Brand); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы цветов автомобилей
-- Добавление первичного ключа для таблицы colors
ALTER TABLE colors ADD CONSTRAINT PK_colors PRIMARY KEY (Id); 
GO
-- Добавление уникального ограничения для столбца Color
ALTER TABLE colors ADD CONSTRAINT UQ_colors_Color UNIQUE (Color); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы типов топлива
-- Добавление первичного ключа для таблицы fuel_types
ALTER TABLE fuel_types ADD CONSTRAINT PK_fuel_types PRIMARY KEY (Id); 
GO
-- Добавление уникального ограничения для столбца Fuel
ALTER TABLE fuel_types ADD CONSTRAINT UQ_fuel_types_Fuel UNIQUE (Fuel); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы типов трансмиссий
-- Добавление первичного ключа для таблицы transmission_types
ALTER TABLE transmission_types ADD CONSTRAINT PK_transmission_types PRIMARY KEY (Id); 
GO
-- Добавление уникального ограничения для столбца Transmission
ALTER TABLE transmission_types ADD CONSTRAINT UQ_transmission_types_Transmission UNIQUE (Transmission); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы типов кузовов
-- Добавление первичного ключа для таблицы body_types
ALTER TABLE body_types ADD CONSTRAINT PK_body_types PRIMARY KEY (Id); 
GO
-- Добавление уникального ограничения для столбца BodyType
ALTER TABLE body_types ADD CONSTRAINT UQ_body_types_BodyType UNIQUE (BodyType); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы характеристик автомобилей
-- Добавление первичного ключа для таблицы features
ALTER TABLE features ADD CONSTRAINT PK_features PRIMARY KEY (Id); 
GO
-- Добавление уникального ограничения для столбца Feature
ALTER TABLE features ADD CONSTRAINT UQ_features_Feature UNIQUE (Feature); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы возможных статусов автомобилей
-- Добавление первичного ключа для таблицы reference_status
ALTER TABLE reference_status ADD CONSTRAINT PK_reference_status PRIMARY KEY (Id); 
GO
-- Добавление уникального ограничения для столбца Status
ALTER TABLE reference_status ADD CONSTRAINT UQ_reference_status_Status UNIQUE (Status); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы автомобилей
-- Добавление первичного ключа для таблицы cars
ALTER TABLE cars ADD CONSTRAINT PK_cars PRIMARY KEY (Id); 
GO
-- Добавление уникального ограничения для столбца LicensePlate
ALTER TABLE cars ADD CONSTRAINT UQ_cars_LicensePlate UNIQUE (LicensePlate); 
GO
-- Добавление ограничения проверки для RentPrice
ALTER TABLE cars ADD CONSTRAINT CHK_RentPrice CHECK (RentPrice > 0); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы дополнительных данных автомобилей
-- Добавление первичного ключа для таблицы cars_additional_details
ALTER TABLE cars_additional_details ADD CONSTRAINT PK_cars_additional_details PRIMARY KEY (Id); 
GO
-- Добавление ограничения проверки для SeatCount
ALTER TABLE cars_additional_details ADD CONSTRAINT CHK_SeatCount CHECK (SeatCount > 0); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы ассоциации автомобилей и их характеристик
-- Добавление первичного ключа для таблицы car_features
ALTER TABLE car_features ADD CONSTRAINT PK_car_features PRIMARY KEY (CarId, FeatureId); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы клиентов
-- Добавление первичного ключа для таблицы clients
ALTER TABLE clients ADD CONSTRAINT PK_clients PRIMARY KEY (Id); 
GO
-- Добавление уникального ограничения для столбца PassportId
ALTER TABLE clients ADD CONSTRAINT UQ_clients_PassportId UNIQUE (PassportId); 
GO
-- Добавление уникального ограничения для столбца PhoneNumber
ALTER TABLE clients ADD CONSTRAINT UQ_clients_PhoneNumber UNIQUE (PhoneNumber); 
GO
-- Добавление ограничения проверки для возраста клиента
ALTER TABLE clients ADD CONSTRAINT CHK_Age CHECK (DATEDIFF(YEAR, BirthDate, GETDATE()) >= 18); 
GO

-- Добавление ограничения проверки для PhoneNumber
ALTER TABLE clients
ADD CONSTRAINT CHK_PhoneNumber 
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
    )
GO

-- 2.3
-- Установка ограничений целостности для таблицы контрактов аренды автомобилей клиентами
-- Добавление первичного ключа для таблицы client_car_contracts
ALTER TABLE client_car_contracts ADD CONSTRAINT PK_client_car_contracts PRIMARY KEY (ContractId); 
GO
-- Добавление ограничения проверки для дат контракта
ALTER TABLE client_car_contracts ADD CONSTRAINT CHK_ContractDate CHECK (OrderDate <= StartDate AND OrderDate < EndDate AND StartDate < EndDate); 
GO
-- Установка значения по умолчанию для OrderDate
ALTER TABLE client_car_contracts ADD CONSTRAINT DF_client_car_contracts_OrderDate DEFAULT GETDATE() FOR OrderDate; 
GO

-- 2.3
-- Установка значений по умолчанию для столбца SeatCount в таблице дополнительных данных автомобилей
-- Установка значения по умолчанию для SeatCount
ALTER TABLE cars_additional_details ADD CONSTRAINT DF_cars_additional_details_SeatCount DEFAULT 4 FOR SeatCount; 
GO

-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

-- 2.4
-- Установка внешних ключей для таблицы автомобилей
-- Добавление внешнего ключа для связи с таблицей brands
ALTER TABLE cars ADD CONSTRAINT FK_cars_BrandId FOREIGN KEY (BrandId) REFERENCES brands(Id) ON DELETE CASCADE ON UPDATE CASCADE; 
GO
-- Добавление внешнего ключа для связи с таблицей reference_status
ALTER TABLE cars ADD CONSTRAINT FK_cars_StatusId FOREIGN KEY (StatusId) REFERENCES reference_status(Id) ON DELETE CASCADE ON UPDATE CASCADE; 
GO

-- 2.4
-- Установка внешних ключей для таблицы дополнительных данных автомобилей
-- Добавление внешнего ключа для связи с таблицей cars
ALTER TABLE cars_additional_details ADD CONSTRAINT FK_cars_additional_details_CarId FOREIGN KEY (Id) REFERENCES cars(Id) ON DELETE CASCADE ON UPDATE CASCADE; 
GO
-- Добавление внешнего ключа для связи с таблицей colors
ALTER TABLE cars_additional_details ADD CONSTRAINT FK_cars_additional_details_ColorId FOREIGN KEY (ColorId) REFERENCES colors(Id) ON DELETE CASCADE ON UPDATE CASCADE; 
GO
-- Добавление внешнего ключа для связи с таблицей fuel_types
ALTER TABLE cars_additional_details ADD CONSTRAINT FK_cars_additional_details_FuelId FOREIGN KEY (FuelId) REFERENCES fuel_types(Id) ON DELETE CASCADE ON UPDATE CASCADE; 
GO
-- Добавление внешнего ключа для связи с таблицей transmission_types
ALTER TABLE cars_additional_details ADD CONSTRAINT FK_cars_additional_details_TransmissionId FOREIGN KEY (TransmissionId) REFERENCES transmission_types(Id) ON DELETE CASCADE ON UPDATE CASCADE; 
GO
-- Добавление внешнего ключа для связи с таблицей body_types
ALTER TABLE cars_additional_details ADD CONSTRAINT FK_cars_additional_details_BodyTypeId FOREIGN KEY (BodyTypeId) REFERENCES body_types(Id) ON DELETE CASCADE ON UPDATE CASCADE; 
GO

-- 2.4
-- Установка внешнего ключа для таблицы ассоциации автомобилей и их характеристик
-- Добавление внешнего ключа для связи с таблицей cars_additional_details
ALTER TABLE car_features ADD CONSTRAINT FK_car_features_CarId FOREIGN KEY (CarId) REFERENCES cars_additional_details(Id) ON DELETE CASCADE; 
GO
-- Добавление внешнего ключа для связи с таблицей features
ALTER TABLE car_features ADD CONSTRAINT FK_car_features_FeatureId FOREIGN KEY (FeatureId) REFERENCES features(Id); 
GO

-- 2.4
-- Установка внешнего ключа для таблицы контрактов аренды автомобилей клиентами
-- Добавление внешнего ключа для связи с таблицей clients
ALTER TABLE client_car_contracts ADD CONSTRAINT FK_client_car_contracts_ClientId FOREIGN KEY (ClientId) REFERENCES clients(Id) ON DELETE CASCADE ON UPDATE CASCADE; 
GO
-- Добавление внешнего ключа для связи с таблицей cars
ALTER TABLE client_car_contracts ADD CONSTRAINT FK_client_car_contracts_CarId FOREIGN KEY (CarId) REFERENCES cars(Id) ON DELETE CASCADE ON UPDATE CASCADE; 
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
-- Написать запрос, который выполняет операцию объединения алгебры отношений.
-- Найти все уникальные полные имена клиентов, чей номер телефона начинается на '07',
-- и полные имена клиентов, рожденных после 1995 года и до 2000, в одном списке.
SELECT CONCAT(FirstName, ' ', LastName) AS FullName 
FROM clients
WHERE PhoneNumber LIKE '07%'
UNION
SELECT CONCAT(FirstName, ' ', LastName) AS FullName 
FROM clients
WHERE YEAR(BirthDate) BETWEEN 1995 AND 2000;

-- 3.1.1 (альтернатива)
-- Написать запрос, который выполняет операцию объединения алгебры отношений.
-- Найти все уникальные полные имена клиентов, чей номер телефона начинается на '07',
-- и полные имена клиентов, рожденных после 1995 года и до 2000, в одном списке.
SELECT CONCAT(FirstName, ' ', LastName) AS FullName 
FROM clients
WHERE PhoneNumber LIKE '07%'
   OR YEAR(BirthDate) BETWEEN 1995 AND 2000;

-- 3.1.2
-- Написать запрос, который выполняет операцию пересечения алгебры отношений.
-- Найти все машины, которые имеют как Apple CarPlay, так и Remote Start.
SELECT c.*
FROM cars c
JOIN car_features cf1 ON c.Id = cf1.CarID
JOIN features f1 ON cf1.FeatureID = f1.Id
WHERE f1.Feature = 'Apple CarPlay'
INTERSECT
SELECT c.*
FROM cars c
JOIN car_features cf2 ON c.Id = cf2.CarID
JOIN features f2 ON cf2.FeatureID = f2.Id
WHERE f2.Feature = 'Remote Start';

-- 3.1.2 (альтернатива)
-- Написать запрос, который выполняет операцию пересечения алгебры отношений.
-- Найти все машины, которые имеют как Apple CarPlay, так и Remote Start.
SELECT c.*
FROM cars c
JOIN car_features cf1 ON c.Id = cf1.CarID
JOIN features f1 ON cf1.FeatureID = f1.Id
WHERE f1.Feature = 'Apple CarPlay'
AND c.Id IN (
    SELECT c2.Id
    FROM cars c2
    JOIN car_features cf2 ON c2.Id = cf2.CarID
    JOIN features f2 ON cf2.FeatureID = f2.Id
    WHERE f2.Feature = 'Remote Start'
);

-- 3.1.3
-- Написать запрос, который выполняет операцию разности алгебры отношений.
-- Найти машины, которые заняты, но не имеют характеристики 'Backup Camera'.
SELECT c.*
FROM cars c
WHERE StatusId = 2
EXCEPT
SELECT c2.*
FROM cars c2
JOIN car_features cf ON c2.Id = cf.CarID
JOIN features f ON cf.FeatureID = f.Id
WHERE f.Feature = 'Backup Camera';

-- 3.1.3 (альтернатива)
-- Написать запрос, который выполняет операцию разности алгебры отношений.
-- Найти машины, которые заняты, но не имеют характеристики 'Backup Camera'.
SELECT c.*
FROM cars c
WHERE c.StatusId = 2
AND c.Id NOT IN (
    SELECT c2.Id
    FROM cars c2
    JOIN car_features cf ON c2.Id = cf.CarID
    JOIN features f ON cf.FeatureID = f.Id
    WHERE f.Feature = 'Backup Camera'
);

-- 3.1.4
-- Написать запрос, который выполняет операцию активного дополнения алгебры отношений.
-- Найти все комбинации автомобилей и характеристик, которые не присутствуют в таблице car_features.
SELECT c1.Id AS CarID, c2.Id AS FeatureID
FROM cars c1, cars c2
WHERE NOT EXISTS (
    SELECT 1 
    FROM car_features cf
    WHERE cf.CarID = c1.Id AND cf.FeatureID = c2.Id
);

-- 3.1.5
-- Написать запрос, который выполняет операцию декартова произведения алгебры отношений.
-- Найти все комбинации автомобилей, их моделей и статусов.
SELECT b.Brand, c.CarModel, r.Status
FROM cars c, reference_status r, brands b
WHERE b.Id = c.BrandId
ORDER BY c.Id;

-- 3.1.6
-- Написать запрос, который выполняет операцию селекции и проекции алгебры отношений.
-- Найти все автомобили, которые используют бензин, и вывести их марки и модели.
SELECT b.Brand, c.CarModel
FROM cars c 
INNER JOIN brands b ON c.BrandId = b.Id
INNER JOIN cars_additional_details cad ON c.Id = cad.Id
WHERE cad.FuelId = (SELECT Id FROM fuel_types WHERE Fuel = 'Petrol')
ORDER BY b.Brand, c.CarModel;

-- 3.1.7
-- Написать запрос, который выполняет операцию тета-соединения алгебры отношений.
-- Найти все автомобили, арендованные клиентами, у которых телефон начинается с '07' и год рождения больше 1990.
SELECT c.*, cl.*
FROM cars c, client_car_contracts cc, clients cl
WHERE c.Id = cc.CarID 
AND cc.ClientID = cl.Id 
AND cl.PhoneNumber LIKE '07%' 
AND YEAR(cl.BirthDate) > 1990;

-- 3.1.8
-- Написать запрос, который выполняет операцию естественного соединения алгебры отношений.
-- Найти все автомобили, которые были арендованы клиентами, с отображением их моделей, марок и полных имен клиентов, а также дат начала и окончания аренды.
SELECT c.CarModel, 
       b.Brand AS CarBrand, 
       CONCAT(cl.FirstName, ' ', cl.LastName) AS FullName, 
       cc.StartDate AS ContractStartDate, 
       cc.EndDate AS ContractEndDate
FROM cars c
JOIN client_car_contracts cc ON c.Id = cc.CarID
JOIN brands b ON c.BrandID = b.Id
JOIN clients cl ON cl.Id = cc.ClientID;

-- 3.1.9
-- Написать запрос, который выполняет операцию полусоединения алгебры отношений.
-- Найти всех клиентов, у которых нет адреса, и которые имеют контракты на аренду автомобилей.
SELECT CONCAT(FirstName, ' ', LastName) AS FullName, ccc.ContractId
FROM clients c
RIGHT JOIN client_car_contracts ccc ON ccc.ClientId = c.Id

-- 3.1.10
-- Написать запрос, который выполняет операцию левого внешнего соединения алгебры отношений.
-- Найти всех клиентов, у которых нет адресов и которые не имеют ни одного контракта.
SELECT *
FROM clients
LEFT OUTER JOIN client_car_contracts ON clients.Id = client_car_contracts.ClientId
WHERE clients.ClientAddress IS NULL
  AND client_car_contracts.ClientId IS NULL;

-- 3.1.11
-- Написать запрос, который выполняет операцию правого внешнего соединения алгебры отношений.
-- Найти все автомобили и их цвета, включая цвета, которые не присвоены ни одному автомобилю.
SELECT *
FROM cars_additional_details
RIGHT OUTER JOIN colors ON colors.Id = cars_additional_details.ColorId;

-- 3.1.12
-- Написать запрос, который выполняет операцию полного внешнего соединения алгебры отношений.
-- Найти клиентов, у которых нет ни одного контракта, и все цвета машин.
SELECT cl.FirstName, cl.LastName, cl.PassportId, cl.PhoneNumber, cad.Id as CarId, colors.Color
FROM clients cl
FULL OUTER JOIN client_car_contracts ccc ON cl.Id = ccc.ClientId
FULL OUTER JOIN cars_additional_details cad ON ccc.CarId = cad.Id
FULL OUTER JOIN colors ON cad.ColorId = colors.Id

-- 3.1.13
-- Написать запрос, который выполняет операцию деления алгебры отношений.
-- Найти машину, у которой есть все характеристики из таблицы features.
SELECT *
FROM cars c
WHERE NOT EXISTS (
    SELECT f.Id
    FROM features f
    WHERE NOT EXISTS (
        SELECT 1
        FROM car_features cf
        WHERE cf.CarId = c.Id AND cf.FeatureId = f.Id
    )
);

-- 3.2.1 
-- Написать запрос, который выводит минимальную, максимальную и среднюю стоимость автомобилей. 
-- Запрос должен вернуть минимальную стоимость (MIN), максимальную стоимость (MAX) и среднюю стоимость (AVG) автомобилей.
SELECT MIN(RentPrice) AS MinPrice, 
       MAX(RentPrice) AS MaxPrice, 
       AVG(RentPrice) AS AvgPrice
FROM cars;

-- 3.2.2
-- Написать запрос, который использует агрегатную функцию SUM.
-- Запрос должен посчитать сумму стоимости всех автомобилей, которые были в аренде.
SELECT SUM(c.RentPrice*cc.TotalDays) AS TotalRentalPrice
FROM cars c
JOIN client_car_contracts cc ON c.Id = cc.CarID;

-- 3.2.3
-- Написать запрос, который использует агрегатную функцию COUNT.
-- Запрос должен вернуть количество автомобилей, у которых есть функция "Bluetooth".
SELECT COUNT(DISTINCT c.Id) AS BluetoothCarsCount
FROM cars c
JOIN car_features cf ON c.Id = cf.CarId
JOIN features f ON cf.FeatureId = f.Id
WHERE f.Feature = 'Bluetooth';

-- 3.3.1
-- Написать запрос с группировкой для одной таблицы.
-- Найти количество автомобилей каждого типа топлива.
SELECT cad.FuelId, COUNT(*) AS CarCount
FROM cars_additional_details cad 
GROUP BY cad.FuelId
HAVING COUNT(*) >= 1;

-- 3.3.2
-- Написать запрос с группировкой для нескольких таблиц.
-- Найти количество автомобилей каждого типа топлива, которые были арендованы, и у которых цена аренды между 10 и 150.
SELECT ft.Fuel, COUNT(*) AS RentedCarCount
FROM cars c
JOIN cars_additional_details cad ON c.Id = cad.Id
JOIN fuel_types ft ON cad.FuelId = ft.Id
JOIN client_car_contracts ccc ON c.Id = ccc.CarId
WHERE c.RentPrice BETWEEN 10 AND 150
GROUP BY ft.Fuel
HAVING COUNT(*) >= 1;

-- 3.4.1
-- Написать запрос с подзапросом, использующим операторы сравнения.
-- Найти все автомобили, у которых цена аренды выше средней цены аренды всех автомобилей.
SELECT * 
FROM cars 
WHERE RentPrice > (SELECT AVG(RentPrice) FROM cars);

-- 3.4.2
-- Написать запрос с подзапросом, использующим оператор IN.
-- Найти всех клиентов, которые арендовали автомобили с характеристикой 'Bluetooth'.
SELECT * 
FROM clients 
WHERE Id IN (
    SELECT ccc.ClientId 
    FROM client_car_contracts ccc
    JOIN car_features cf ON ccc.CarId = cf.CarId
    JOIN features f ON cf.FeatureId = f.Id
    WHERE f.Feature = 'Bluetooth'
);

-- 3.4.3
-- Написать запрос с подзапросом, использующим оператор ALL.
-- Найти все автомобили, у которых цена аренды выше, чем у всех автомобилей марки 'Volkswagen'.
SELECT * 
FROM cars 
WHERE RentPrice > ALL (
    SELECT RentPrice 
    FROM cars 
    JOIN brands ON cars.BrandId = brands.Id
    WHERE brands.Brand = 'Volkswagen'
);

-- 3.4.4
-- Написать запрос с подзапросом, использующим оператор ANY.
-- Найти все автомобили, у которых цена аренды выше, чем у любого автомобиля марки 'Honda'.
SELECT * 
FROM cars 
WHERE RentPrice > ANY (
    SELECT RentPrice 
    FROM cars 
    JOIN brands ON cars.BrandId = brands.Id
    WHERE brands.Brand = 'Honda'
);

-- 3.4.5
-- Написать запрос с подзапросом, использующим оператор EXISTS.
-- Найти всех клиентов, которые арендовали автомобили с характеристикой 'Navigation System'.
SELECT * 
FROM clients 
WHERE EXISTS (
    SELECT 1 
    FROM client_car_contracts ccc
    JOIN car_features cf ON ccc.CarId = cf.CarId
    JOIN features f ON cf.FeatureId = f.Id
    WHERE f.Feature = 'Navigation System' 
    AND clients.Id = ccc.ClientId
);

-- 3.4.6
-- Написать запрос с коррелированным подзапросом в WHERE.
-- Найти все автомобили, у которых цена аренды выше средней цены аренды автомобилей того же типа топлива.
SELECT * 
FROM cars c1
WHERE RentPrice > (
    SELECT AVG(c2.RentPrice) 
    FROM cars c2
    JOIN cars_additional_details cad ON c2.Id = cad.Id
    WHERE cad.FuelId = (SELECT cad2.FuelId FROM cars_additional_details cad2 WHERE cad2.Id = c1.Id)
);

-- 3.4.6
-- Написать запрос с подзапросом в HAVING.
-- Найти все типы топлива, у которых средняя цена аренды автомобилей выше 60.
SELECT cad.FuelId, AVG(c.RentPrice) AS AvgRentPrice
FROM cars c
JOIN cars_additional_details cad ON c.Id = cad.Id
GROUP BY cad.FuelId
HAVING AVG(c.RentPrice) > 60;

-- 3.4.6
-- Написать запрос с подзапросом в BETWEEN.
-- Найти все автомобили, у которых цена аренды находится между минимальной и максимальной ценой аренды автомобилей марки 'Ford'.
SELECT * 
FROM cars 
WHERE RentPrice BETWEEN (
    SELECT MIN(RentPrice) 
    FROM cars 
    JOIN brands ON cars.BrandId = brands.Id
    WHERE brands.Brand = 'Ford'
) AND (
    SELECT MAX(RentPrice) 
    FROM cars 
    JOIN brands ON cars.BrandId = brands.Id
    WHERE brands.Brand = 'Ford'
);

-- 3.4.7
-- Написать запрос с подзапросом в FROM.
-- Найти среднюю цену аренды автомобилей для каждого типа топлива.
SELECT FuelId, AVG(RentPrice) AS AvgRentPrice
FROM (
    SELECT c.RentPrice, cad.FuelId
    FROM cars c
    JOIN cars_additional_details cad ON c.Id = cad.Id
) AS FuelPrices
GROUP BY FuelId;

-- 3.4.8
-- Написать запрос с подзапросом в SELECT.
-- Найти все автомобили и указать количество характеристик для каждого автомобиля.
SELECT c.*, 
    (SELECT COUNT(*) 
     FROM car_features cf 
     WHERE cf.CarId = c.Id) AS FeatureCount
FROM cars c;

-- !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

-- 4.1.1
-- Написать запрос, который удаляет первые несколько записей с условием.
-- Удалить первых 5 клиентов, у которых номер телефона начинается на '079'.
WITH CTE AS (
    SELECT TOP (5) *
    FROM clients
    WHERE PhoneNumber LIKE '060%'
)
DELETE FROM CTE
OUTPUT DELETED.*;

-- 4.1.2
-- Написать запрос для условного удаления с подзапросом для одной таблицы.
-- Удалить всех клиентов, у которых нет контрактов на аренду автомобилей.
DELETE FROM clients
OUTPUT DELETED.*
WHERE Id NOT IN (
    SELECT DISTINCT ClientId 
    FROM client_car_contracts
);

-- 4.1.3
-- Написать запрос для обновления записей в таблице с использованием TOP.
-- Обновить статус 3 самых дорогих автомобилей на 'Unavailable'.
WITH CTE AS (
    SELECT TOP (3) *
    FROM cars
    WHERE StatusId <> (SELECT Id FROM reference_status WHERE Status = 'Available')
)
UPDATE CTE
SET StatusId = (SELECT Id FROM reference_status WHERE Status = 'Available')
OUTPUT INSERTED.*;


-- 4.1.4
-- Написать запрос, который обновляет записи на основе подзапроса с несколькими таблицами.
-- Обновить статус автомобилей, которые были арендованы клиентами, чьи контракты аренды суммарно составляют более 4 дней.
UPDATE cars
SET StatusId = (
    SELECT TOP 1 rs.Id
    FROM reference_status rs
    WHERE rs.Status = 'Maintenance'
)
OUTPUT INSERTED.*
WHERE Id IN (
    SELECT CarID
    FROM client_car_contracts
    WHERE ClientId IN (
        SELECT ClientId
        FROM client_car_contracts
        WHERE TotalDays > 4
        GROUP BY ClientId
    )
);

-- 4.2.1
-- Написать запрос для создания нового контракта через INSERT INTO с SELECT.
-- Вставить данные о новых контрактах, которые начинаются в следующем месяце, в таблицу client_car_contracts.
INSERT INTO client_car_contracts (ClientId, CarId, OrderDate, StartDate, EndDate)
OUTPUT INSERTED.*
SELECT ClientId, CarId, GETDATE(), DATEADD(MONTH, 1, GETDATE()), DATEADD(MONTH, 2, GETDATE())
FROM client_car_contracts;

-- 4.2.2
-- Написать запрос для вставки результата запроса SELECT в новую таблицу.
-- Создать новую таблицу new_clients и вставить в неё всех клиентов, у которых нет контрактов на аренду автомобилей.
SELECT Id, FirstName, LastName, PassportId, PhoneNumber, ClientAddress, BirthDate
INTO new_clients
FROM clients
WHERE Id NOT IN (
    SELECT DISTINCT ClientId 
    FROM client_car_contracts
);