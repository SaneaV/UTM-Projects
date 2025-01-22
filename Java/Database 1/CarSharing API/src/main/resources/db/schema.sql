-- 2.2
-- Создание таблицы марок автомобилей
CREATE TABLE IF NOT EXISTS brands (
    Id TINYINT AUTO_INCREMENT PRIMARY KEY,
    Brand VARCHAR(50) NOT NULL UNIQUE
);

-- 2.2
-- Создание таблицы цветов автомобилей
CREATE TABLE IF NOT EXISTS colors (
    Id TINYINT AUTO_INCREMENT PRIMARY KEY,
    Color VARCHAR(30) NOT NULL UNIQUE
);

-- 2.2
-- Создание таблицы типов топлива
CREATE TABLE IF NOT EXISTS fuel_types (
    Id TINYINT AUTO_INCREMENT PRIMARY KEY,
    Fuel VARCHAR(60) NOT NULL UNIQUE
);

-- 2.2
-- Создание таблицы типов трансмиссий
CREATE TABLE IF NOT EXISTS transmission_types (
    Id TINYINT AUTO_INCREMENT PRIMARY KEY,
    Transmission VARCHAR(40) NOT NULL UNIQUE
);

-- 2.2
-- Создание таблицы типов кузовов
CREATE TABLE IF NOT EXISTS body_types (
    Id TINYINT AUTO_INCREMENT PRIMARY KEY,
    BodyType VARCHAR(40) NOT NULL UNIQUE
);

-- 2.2
-- Создание таблицы характеристик автомобилей
CREATE TABLE IF NOT EXISTS features (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    Feature VARCHAR(70) NOT NULL UNIQUE
);

-- 2.2
-- Создание таблицы возможных статусов автомобилей
CREATE TABLE IF NOT EXISTS reference_status (
    Id TINYINT AUTO_INCREMENT PRIMARY KEY,
    Status VARCHAR(15) NOT NULL UNIQUE
);

-- 2.2
-- Создание таблицы автомобилей
CREATE TABLE IF NOT EXISTS cars (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    LicensePlate CHAR(6) NOT NULL UNIQUE,
    BrandId TINYINT NOT NULL,
    CarModel VARCHAR(50) NOT NULL,
    RentPrice DECIMAL(10, 2) NOT NULL CHECK (RentPrice > 0),
    StatusId TINYINT NOT NULL,
    FOREIGN KEY (BrandId) REFERENCES brands(Id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (StatusId) REFERENCES reference_status(Id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 2.2
-- Создание таблицы дополнительных данных автомобилей
CREATE TABLE IF NOT EXISTS cars_additional_details (
    Id INT NOT NULL PRIMARY KEY,
    ColorId TINYINT NOT NULL,
    FuelId TINYINT NOT NULL,
    TransmissionId TINYINT NOT NULL,
    BodyTypeId TINYINT NOT NULL,
    SeatCount TINYINT NOT NULL DEFAULT 4 CHECK (SeatCount > 0),
    FOREIGN KEY (Id) REFERENCES cars(Id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (ColorId) REFERENCES colors(Id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (FuelId) REFERENCES fuel_types(Id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (TransmissionId) REFERENCES transmission_types(Id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (BodyTypeId) REFERENCES body_types(Id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- 2.2
-- Создание таблицы ассоциации автомобилей и их характеристик
CREATE TABLE IF NOT EXISTS car_features (
    CarId INT NOT NULL,
    FeatureId INT NOT NULL,
    PRIMARY KEY (CarId, FeatureId),
    FOREIGN KEY (CarId) REFERENCES cars_additional_details(Id) ON DELETE CASCADE,
    FOREIGN KEY (FeatureId) REFERENCES features(Id)
);

-- 2.2
-- Создание таблицы клиентов
CREATE TABLE IF NOT EXISTS clients (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(30) NOT NULL,
    LastName VARCHAR(30) NOT NULL,
    PassportId CHAR(13) NOT NULL UNIQUE,
    PhoneNumber CHAR(9) NOT NULL UNIQUE CHECK (
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
    ),
    ClientAddress VARCHAR(70),
    BirthDate DATE NOT NULL
);

-- 2.2
-- Создание таблицы контрактов аренды автомобилей клиентами
CREATE TABLE IF NOT EXISTS client_car_contracts (
    ContractId BIGINT AUTO_INCREMENT PRIMARY KEY,
    ClientId INT NOT NULL,
    CarId INT NOT NULL,
    OrderDate DATE NOT NULL DEFAULT CURRENT_DATE,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    TotalDays INT AS (DATEDIFF('DAY', EndDate, StartDate)),
    CHECK (OrderDate <= StartDate AND OrderDate < EndDate AND StartDate < EndDate),
    FOREIGN KEY (ClientId) REFERENCES clients(Id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (CarId) REFERENCES cars(Id) ON DELETE CASCADE ON UPDATE CASCADE
);