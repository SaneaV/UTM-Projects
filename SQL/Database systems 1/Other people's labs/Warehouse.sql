-- 2.1
-- Установить соединение с master базой данных
USE master;
GO

-- Проверка и удаление базы данных WarehouseService, если она существует
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'WarehouseService')
BEGIN
    ALTER DATABASE WarehouseService SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE WarehouseService;
END;
GO

-- Создание новой базы данных WarehouseService
CREATE DATABASE WarehouseService;
GO

-- Использовать новую базу данных WarehouseService
USE WarehouseService;
GO

-- 2.2
-- Создание таблиц для базы данных WarehouseService

-- Создание таблицы складов
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'warehouses' 
               AND Type = 'U')
BEGIN
    CREATE TABLE warehouses
    (
        WarehouseID INT IDENTITY(1, 1) NOT NULL,
        WarehouseName NVARCHAR(100) NOT NULL,
        Location NVARCHAR(255) NOT NULL
    );
END;
GO

-- Создание таблицы менеджеров
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'managers' 
               AND Type = 'U')
BEGIN
    CREATE TABLE managers
    (
        ManagerID INT IDENTITY(1, 1) NOT NULL,
        WarehouseID INT NOT NULL,
        ManagerName NVARCHAR(100) NOT NULL,
        ContactInfo NVARCHAR(255) NOT NULL
    );
END;
GO

-- Создание таблицы товаров
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'products' 
               AND Type = 'U')
BEGIN
    CREATE TABLE products
    (
        ProductID INT IDENTITY(1, 1) NOT NULL,
        WarehouseID INT NOT NULL,
        ProductName NVARCHAR(100) NOT NULL,
        Quantity INT NOT NULL,
        Price DECIMAL(10, 2) NOT NULL
    );
END;
GO

-- Создание таблицы поставщиков
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'suppliers' 
               AND Type = 'U')
BEGIN
    CREATE TABLE suppliers
    (
        SupplierID INT IDENTITY(1, 1) NOT NULL,
        SupplierName NVARCHAR(100) NOT NULL,
        ContactInfo NVARCHAR(255) NOT NULL
    );
END;
GO

-- Создание таблицы связей между товарами и поставщиками
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'products_suppliers' 
               AND Type = 'U')
BEGIN
    CREATE TABLE products_suppliers
    (
        ProductID INT NOT NULL,
        SupplierID INT NOT NULL
    );
END;
GO

-- 2.3
-- Установка первичных ключей
ALTER TABLE warehouses ADD CONSTRAINT PK_warehouses PRIMARY KEY (WarehouseID); 
GO

ALTER TABLE managers ADD CONSTRAINT PK_managers PRIMARY KEY (ManagerID); 
GO

ALTER TABLE products ADD CONSTRAINT PK_products PRIMARY KEY (ProductID); 
GO

ALTER TABLE suppliers ADD CONSTRAINT PK_suppliers PRIMARY KEY (SupplierID); 
GO

ALTER TABLE products_suppliers ADD CONSTRAINT PK_products_suppliers PRIMARY KEY (ProductID, SupplierID); 
GO

-- Установка ограничений целостности для таблицы складов
ALTER TABLE warehouses ADD CONSTRAINT UQ_warehouses_name UNIQUE (WarehouseName); 
GO

ALTER TABLE warehouses ADD CONSTRAINT UQ_warehouses_location UNIQUE (Location); 
GO

-- Установка ограничений целостности для таблицы менеджеров
ALTER TABLE managers ADD CONSTRAINT CHK_managers_contact CHECK (LEN(ContactInfo) > 0); 
GO

ALTER TABLE managers ADD CONSTRAINT UQ_managers_warehouse_id UNIQUE (WarehouseID); 
GO

-- Установка ограничений целостности для таблицы товаров
ALTER TABLE products ADD CONSTRAINT CHK_products_quantity CHECK (Quantity > 0); 
GO

ALTER TABLE products ADD CONSTRAINT CHK_products_price CHECK (Price > 0); 
GO

-- Установка значений по умолчанию для таблицы товаров
ALTER TABLE products ADD CONSTRAINT DF_products_quantity DEFAULT 1 FOR Quantity; 
GO

-- Установка ограничений целостности для таблицы поставщиков
ALTER TABLE suppliers ADD CONSTRAINT CHK_suppliers_contact CHECK (LEN(ContactInfo) > 0); 
GO

-- 2.4
-- Установка внешних ключей
ALTER TABLE managers 
ADD CONSTRAINT FK_managers_warehouse_id FOREIGN KEY (WarehouseID) 
REFERENCES warehouses (WarehouseID) 
ON DELETE CASCADE ON UPDATE CASCADE; 
GO

ALTER TABLE products 
ADD CONSTRAINT FK_products_warehouse_id FOREIGN KEY (WarehouseID) 
REFERENCES warehouses (WarehouseID) 
ON DELETE CASCADE ON UPDATE CASCADE; 
GO

ALTER TABLE products_suppliers 
ADD CONSTRAINT FK_products_suppliers_product_id FOREIGN KEY (ProductID) 
REFERENCES products (ProductID) 
ON DELETE CASCADE ON UPDATE CASCADE; 
GO

ALTER TABLE products_suppliers 
ADD CONSTRAINT FK_products_suppliers_supplier_id FOREIGN KEY (SupplierID) 
REFERENCES suppliers (SupplierID) 
ON DELETE CASCADE ON UPDATE CASCADE; 
GO

SET IDENTITY_INSERT dbo.warehouses ON;

-- 2.5
-- Заполнение таблиц
-- Warehouses
INSERT INTO warehouses (WarehouseID, WarehouseName, Location)
VALUES 
(1, 'Chisinau Central Depot', 'Chisinau'),
(2, 'Balti Storage Facility', 'Balti'),
(3, 'Cahul Regional Hub', 'Cahul'),
(4, 'Comrat Logistics Center', 'Comrat'),
(5, 'Soroca Warehouse', 'Soroca'),
(6, 'Ungheni Storage', 'Ungheni'),
(7, 'Edinet Distribution Point', 'Edinet'),
(8, 'Orhei Logistics Base', 'Orhei'),
(9, 'Tiraspol Hub', 'Tiraspol'),
(10, 'Hincesti Depot', 'Hincesti'),
(11, 'Floresti Storage', 'Floresti'),
(12, 'Cimislia Facility', 'Cimislia'),
(13, 'Rezina Warehouse', 'Rezina'),
(14, 'Taraclia Logistics Hub', 'Taraclia'),
(15, 'Leova Central Depot', 'Leova');

SELECT * FROM products_suppliers 

-- Suppliers
INSERT INTO suppliers (SupplierName, ContactInfo)
VALUES 
('AgroMoldova SRL', 'info@agromoldova.md'),
('Vinaria Purcari', 'sales@purcari.md'),
('Carmez Balti', 'contact@carmez.md'),
('Moldtelecom Logistics', 'logistics@moldtelecom.md'),
('TransOil Group', 'info@transoil.md'),
('Naturalis Moldova', 'naturalis@md.md'),
('Sun Communications', 'support@suncom.md'),
('Dulcinella Supply', 'dulcinella@dulci.md'),
('AlfaNistru Partners', 'partners@alfanistru.md'),
('Lapmol SRL', 'lapmol@milk.md'),
('Franzeluta Distribution', 'distribution@franzeluta.md'),
('Drochia Sugar Factory', 'drochia@sugar.md'),
('EuroCredit Logistics', 'logistics@eurocredit.md'),
('Cricova Export', 'export@cricova.md'),
('Moldcoop Import', 'import@moldcoop.md');

-- Products
INSERT INTO products (WarehouseID, ProductName, Quantity, Price)
VALUES 
(1, 'Wine Purcari Red', 200, 150.00),
(2, 'Sugar Drochia', 300, 20.50),
(3, 'Sunflower Oil', 150, 35.99),
(4, 'Corn Flour', 100, 25.00),
(5, 'Honey Pure Moldova', 250, 120.00),
(6, 'Mineral Water AquaUni', 500, 10.00),
(7, 'Natural Juice Naturalis', 400, 15.00),
(8, 'Canned Peaches', 100, 50.00),
(9, 'Franzeluta Bread', 300, 8.50),
(10, 'Milk Lapmol', 150, 12.00),
(11, 'Cognac Barza Alba', 50, 250.00),
(12, 'Cricova Sparkling Wine', 200, 180.00),
(13, 'Grape Jam', 90, 60.00),
(14, 'Apple Compote', 80, 40.00),
(15, 'Tomato Paste', 120, 30.00);

-- Managers
INSERT INTO managers (WarehouseID, ManagerName, ContactInfo)
VALUES 
(1, 'Andrei Popescu', 'andrei.popescu@centraldepot.md'),
(2, 'Ionel Stratulat', 'ionel.stratulat@balti.md'),
(3, 'Vasile Scurtu', 'vasile.scurtu@cahul.md'),
(4, 'Maria Leanca', 'maria.leanca@comrat.md'),
(5, 'Alina Rusu', 'alina.rusu@soroca.md'),
(6, 'Dumitru Croitoru', 'dumitru.croitoru@ungheni.md'),
(7, 'Tatiana Gherman', 'tatiana.gherman@edinet.md'),
(8, 'Cristina Ciobanu', 'cristina.ciobanu@orhei.md'),
(9, 'Sergiu Voronin', 'sergiu.voronin@tiraspol.md'),
(10, 'Iulia Mocanu', 'iulia.mocanu@hincesti.md'),
(11, 'Stefan Caranfil', 'stefan.caranfil@floresti.md'),
(12, 'Alexei Cebanu', 'alexei.cebanu@cimislia.md'),
(13, 'Elena Vizir', 'elena.vizir@rezina.md'),
(14, 'Mihai Arion', 'mihai.arion@taraclia.md'),
(15, 'Lidia Tofan', 'lidia.tofan@leova.md');

-- Products and Suppliers
INSERT INTO products_suppliers (ProductID, SupplierID)
VALUES 
(1, 2), (1, 14), (2, 12), (3, 9), (4, 3), (5, 1), 
(6, 6), (7, 8), (8, 9), (9, 11), (10, 10), (11, 14),
(12, 13), (13, 5), (14, 4), (15, 7), (2, 15), (8, 3), 
(7, 2), (12, 14);

SET IDENTITY_INSERT dbo.warehouses OFF;


-- 3.1.1  
-- Написать запрос, который выполняет операцию объединения алгебры отношений.  
-- Объединение товаров, которые хранятся в складах с определенными ProductID, и товаров с количеством больше 100.  
SELECT ProductID, WarehouseID, ProductName, Quantity, Price  
FROM products  
WHERE ProductID IN (1, 2, 3)  -- Товары с определёнными ID  
UNION  
SELECT ProductID, WarehouseID, ProductName, Quantity, Price  
FROM products  
WHERE Quantity > 100;  

-- 3.1.2  
-- Написать запрос, который выполняет операцию пересечения алгебры отношений.  
-- Пересечение товаров, которые хранятся на складах с определенными ProductID, и товаров с количеством больше 100.  
SELECT ProductID, WarehouseID, ProductName, Quantity, Price  
FROM products  
WHERE ProductID IN (1, 2, 3)  -- Товары с определёнными ID  
INTERSECT  
SELECT ProductID, WarehouseID, ProductName, Quantity, Price  
FROM products  
WHERE Quantity > 100;  

-- 3.1.3  
-- Написать запрос, который выполняет операцию разности алгебры отношений.  
-- Разность товаров, которые хранятся на складах с определенными ProductID, и товаров с количеством больше 200.  
SELECT ProductID, WarehouseID, ProductName, Quantity, Price  
FROM products  
WHERE ProductID IN (1, 2, 3)  -- Товары с определёнными ID  
EXCEPT  
SELECT ProductID, WarehouseID, ProductName, Quantity, Price  
FROM products  
WHERE Quantity > 200;  

-- 3.1.4  
-- Написать запрос, который выполняет операцию активного дополнения алгебры отношений.  
-- Найти все товары, которые не хранятся на складе с WarehouseID = 2.  
SELECT p.ProductID, p.ProductName, p.WarehouseID  
FROM products p  
WHERE NOT EXISTS (  
    SELECT 1  
    FROM products p2  
    WHERE p2.WarehouseID = 2 AND p2.ProductID = p.ProductID  
);  

-- 3.1.5  
-- Написать запрос, который выполняет операцию декартова произведения алгебры отношений.  
-- Выполнить декартово произведение товаров и поставщиков.  
SELECT p.ProductID, p.ProductName, s.SupplierID, s.SupplierName  
FROM products p, suppliers s  
ORDER BY s.SupplierName;  

-- 3.1.6  
-- Написать запрос, который выполняет операцию селекции и проекции алгебры отношений.  
-- Найти все товары на складе 'Hincesti Depot' и проекцировать только ProductID и ProductName.  
SELECT p.ProductID, p.ProductName  
FROM products p  
JOIN warehouses w ON p.WarehouseID = w.WarehouseID  
WHERE w.WarehouseName = 'Hincesti Depot';  

-- 3.1.7  
-- Написать запрос, который выполняет операцию тета-соединения алгебры отношений.  
-- Соединить таблицы товаров и складов по WarehouseID, где количество товара больше 100.  
SELECT p.ProductID, p.ProductName, w.WarehouseName, p.Quantity  
FROM products p  
JOIN warehouses w ON p.WarehouseID = w.WarehouseID  
WHERE p.Quantity > 100;  

-- 3.1.8  
-- Написать запрос, который выполняет операцию естественного соединения алгебры отношений.  
-- Естественное соединение таблиц товаров и складов по WarehouseID.  
SELECT p.ProductID, p.ProductName, w.WarehouseName  
FROM products p  
INNER JOIN warehouses w ON p.WarehouseID = w.WarehouseID;  

-- 3.1.9  
-- Написать запрос, который выполняет операцию полусоединения алгебры отношений.  
-- Найти все товары, которые не поставляются определённым поставщиком (например, SupplierID = 5).  
SELECT p.ProductID, p.ProductName  
FROM products p  
RIGHT JOIN products_suppliers ps ON p.ProductID = ps.ProductID  
WHERE ps.SupplierID = 5  -- Определённый поставщик  
AND p.ProductID IS NOT NULL;  

-- 3.1.10  
-- Написать запрос, который выполняет операцию левого внешнего соединения алгебры отношений.  
-- Найти все товары и информацию о поставщиках, которые их поставляют (если таковые есть).  
SELECT p.ProductID, p.ProductName, s.SupplierName  
FROM products p  
LEFT JOIN products_suppliers ps ON p.ProductID = ps.ProductID  
LEFT JOIN suppliers s ON ps.SupplierID = s.SupplierID;  

-- 3.1.11  
-- Написать запрос, который выполняет операцию правого внешнего соединения алгебры отношений.  
-- Найти все поставки и товары, которые поставляются этими поставщиками (если таковые есть).  
SELECT s.SupplierID, s.SupplierName, p.ProductID, p.ProductName  
FROM suppliers s  
RIGHT JOIN products_suppliers ps ON s.SupplierID = ps.SupplierID  
RIGHT JOIN products p ON ps.ProductID = p.ProductID;  

-- 3.1.12  
-- Написать запрос, который выполняет операцию полного внешнего соединения алгебры отношений.  
-- Найти все товары и поставщиков, которые могут или не могут поставлять эти товары.  
SELECT s.SupplierID, s.SupplierName, p.ProductID, p.ProductName  
FROM suppliers s  
FULL OUTER JOIN products_suppliers ps ON s.SupplierID = ps.SupplierID  
FULL OUTER JOIN products p ON ps.ProductID = p.ProductID;

-- 2.6
-- Добавление товара, который поставляется всеми поставщиками

-- Добавим новый товар в таблицу products
INSERT INTO products (WarehouseID, ProductName, Quantity, Price)
VALUES (1, 'Universal Product', 100, 50.00);

-- Получим ProductID только что добавленного товара
DECLARE @ProductID INT = SCOPE_IDENTITY();

-- Связываем новый товар с каждым поставщиком
INSERT INTO products_suppliers (ProductID, SupplierID)
SELECT @ProductID, SupplierID
FROM suppliers;

-- 3.1.13  
-- Написать запрос, который выполняет операцию деления алгебры отношений.  
-- Найти все товары, которые поставляются всеми поставщиками.  
SELECT p.ProductID, p.ProductName  
FROM products p  
WHERE NOT EXISTS (  
    SELECT 1  
    FROM suppliers s  
    WHERE NOT EXISTS (  
        SELECT 1  
        FROM products_suppliers ps  
        WHERE ps.ProductID = p.ProductID AND ps.SupplierID = s.SupplierID  
    )  
);

-- 3.2.1
-- Написать запрос, который выводит минимальную, максимальную и среднюю цену товаров,
-- общую сумму цен всех товаров и количество товаров.
SELECT 
    MIN(Price) AS MinPrice,
    MAX(Price) AS MaxPrice,
    AVG(Price) AS AvgPrice
FROM products;

-- 3.2.2
-- Написать запрос, который использует агрегатную функцию SUM.
-- Запрос должен вернуть общую сумму цен всех товаров.
SELECT 
    SUM(Price) AS TotalPrice
FROM products;

-- 3.2.3
-- Написать запрос, который использует агрегатную функцию COUNT.
-- Запрос должен вернуть количество товаров.
SELECT 
    COUNT(*) AS TotalProducts
FROM products;

-- 3.3.1
-- Написать запрос с группировкой для одной таблицы.
-- Запрос должен вернуть количество товаров (COUNT) на каждом складе (WarehouseID).
SELECT WarehouseID, COUNT(*) AS TotalProducts
FROM products
GROUP BY WarehouseID;

-- 3.3.2
-- Написать запрос с группировкой для нескольких таблиц.
-- Запрос должен вернуть количество товаров (COUNT) для каждого поставщика (SupplierID).
SELECT s.SupplierID, s.SupplierName, COUNT(ps.ProductID) AS TotalProductsSupplied
FROM suppliers s
JOIN products_suppliers ps ON s.SupplierID = ps.SupplierID
JOIN products p ON ps.ProductID = p.ProductID
GROUP BY s.SupplierID, s.SupplierName;

-- 3.4.1
-- Написать запрос с подзапросом, использующим операторы сравнения.
-- Запрос должен вернуть все товары, которые имеют цену больше средней цены всех товаров.
SELECT ProductID, ProductName, Price
FROM products
WHERE Price > (SELECT AVG(Price) FROM products);

-- 3.4.2
-- Написать запрос с подзапросом, использующим оператор IN.
-- Запрос должен вернуть все товары, которые были поставлены поставщиками из определенного региона.
SELECT ProductID, ProductName
FROM products
WHERE ProductID IN (
    SELECT ps.ProductID
    FROM products_suppliers ps
    JOIN suppliers s ON ps.SupplierID = s.SupplierID
    WHERE s.ContactInfo LIKE '%@purcari.md%'
);

-- 3.4.3
-- Написать запрос с подзапросом, использующим оператор ALL.
-- Запрос должен вернуть все товары, которые стоят дороже, чем все товары, поставленные в определенном регионе.
SELECT ProductID, ProductName, Price
FROM products
WHERE Price > ALL (
    SELECT p.Price
    FROM products p
    JOIN products_suppliers ps ON p.ProductID = ps.ProductID
    JOIN suppliers s ON ps.SupplierID = s.SupplierID
    WHERE s.ContactInfo LIKE '%@purcari.md%'
);

-- 3.4.4
-- Написать запрос с подзапросом, использующим оператор ANY.
-- Запрос должен вернуть все товары, которые стоят дороже, чем хотя бы один товар, поставленный в определенном регионе.
SELECT ProductID, ProductName, Price
FROM products
WHERE Price > ANY (
    SELECT p.Price
    FROM products p
    JOIN products_suppliers ps ON p.ProductID = ps.ProductID
    JOIN suppliers s ON ps.SupplierID = s.SupplierID
    WHERE s.ContactInfo LIKE '%@purcari.md%'
);

-- 3.4.5
-- Написать запрос с подзапросом, использующим оператор EXISTS.
-- Запрос должен вернуть все товары, которые были поставлены хотя бы одним поставщиком из определенного региона.
SELECT ProductID, ProductName
FROM products p
WHERE EXISTS (
    SELECT 1
    FROM products_suppliers ps
    JOIN suppliers s ON ps.SupplierID = s.SupplierID
    WHERE ps.ProductID = p.ProductID
    AND s.ContactInfo LIKE '%@purcari.md%'
);

-- 3.4.6
-- Написать запрос с коррелированным подзапросом в WHERE.
-- Запрос должен вернуть все товары, которые были поставлены поставщиками, имеющими определенный контакт.
SELECT ProductID, ProductName
FROM products p
WHERE EXISTS (
    SELECT 1
    FROM products_suppliers ps
    JOIN suppliers s ON ps.SupplierID = s.SupplierID
    WHERE ps.ProductID = p.ProductID
    AND s.ContactInfo LIKE '%@purcari.md%'
);

-- 3.4.7
-- Написать запрос с подзапросом в HAVING.
-- Запрос должен вернуть все склады, на которых количество товаров больше среднего по всем складам.
SELECT WarehouseID, COUNT(ProductID) AS TotalProducts
FROM products
GROUP BY WarehouseID
HAVING COUNT(ProductID) > (
    SELECT AVG(TotalProducts)
    FROM (
        SELECT COUNT(ProductID) AS TotalProducts
        FROM products
        GROUP BY WarehouseID
    ) AS AvgProducts
);

-- 3.4.8
-- Написать запрос с подзапросом в SELECT.
-- Запрос должен вернуть информацию о товарах с количеством поставок для каждого товара.
SELECT 
    p.ProductID, 
    p.ProductName, 
    (SELECT COUNT(*) 
     FROM products_suppliers ps 
     WHERE ps.ProductID = p.ProductID) AS TotalSupplies
FROM products p;


-- 4.1.1
-- Удалить первые 5 записей, где количество товара меньше 300.
DELETE TOP (5)
FROM products
WHERE Quantity < 300;

-- 4.1.2
-- Написать запрос для условного удаления с подзапросом для одной таблицы.
-- Удалить все товары, которые не связаны ни с одной записью в таблице поставок.
DELETE FROM products
WHERE ProductID NOT IN (
    SELECT ProductID
    FROM products_suppliers
);

-- 4.1.3
-- Написать запрос для обновления записей в таблице с использованием TOP.
-- Обновить первые 5 записей в таблице products, установив цену на 5.0.
UPDATE TOP (5) products
SET Price = 5.0;

-- 4.1.4
-- Написать запрос, который обновляет записи на основе подзапроса с несколькими таблицами.
-- Обновить цену товаров, которые были поставлены поставщиками из определенного региона.
UPDATE products
SET Price = Price + 1
WHERE ProductID IN (
    SELECT DISTINCT p.ProductID
    FROM products p
    JOIN products_suppliers ps ON p.ProductID = ps.ProductID
    JOIN suppliers s ON ps.SupplierID = s.SupplierID
    WHERE s.ContactInfo LIKE '%@purcari.md%'
);

-- 4.2.1
-- Вставить новую запись в таблицу products, используя данные из products_suppliers
INSERT INTO products (WarehouseID, ProductName, Quantity, Price)
SELECT p.WarehouseID, 'New Product', 100, 25.00
FROM products p
JOIN products_suppliers ps ON p.ProductID = ps.ProductID
WHERE ps.SupplierID = 1;

-- 4.2.2
-- Создать новую таблицу recent_products и вставить в неё товары с количеством более 50
SELECT ProductID, ProductName, WarehouseID, Quantity, Price
INTO recent_products
FROM products
WHERE Quantity > 50;

