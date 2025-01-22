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

-- 2.5
-- Вставка статусов
INSERT INTO reference_status (Status) VALUES
('Available'),
('Rented'),
('Maintenance'),
('Reserved');

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
(16, 1, 2, 2, 15, 4); -- Toyota Yaris, Red, Diesel, Automatic, Sedan, 2 seats

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
-- Hyundai Kona Electric
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