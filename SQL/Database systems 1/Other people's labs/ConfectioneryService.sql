-- 2.1 
-- Установить соединение с master базой данных
-- Переход на базу данных master, так как операции по удалению и изменению базы данных можно проводить только из неё
USE master;
GO

-- Проверка и удаление базы данных ConfectioneryService, если она существует
-- Если база данных ConfectioneryService существует, выполняется её удаление
IF EXISTS (SELECT * FROM sys.databases WHERE name = 'ConfectioneryService')
BEGIN
    -- Перевод базы данных ConfectioneryService в режим однопользовательского доступа
    -- Это необходимо для принудительного завершения всех открытых транзакций и соединений с базой данных
    ALTER DATABASE ConfectioneryService SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    
    -- Удаление базы данных ConfectioneryService
    DROP DATABASE ConfectioneryService;
END
GO

-- Создание новой базы данных ConfectioneryService
-- Создаётся новая база данных с именем ConfectioneryService
CREATE DATABASE ConfectioneryService;
GO

-- Использовать новую базу данных ConfectioneryService
-- После создания новой базы данных, переключение на неё для дальнейших операций
USE ConfectioneryService;
GO

-- 2.2
-- Создание таблицы клиентов
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'customers' 
               AND Type = 'U')
BEGIN
    CREATE TABLE customers
    (
        customer_id INT IDENTITY(1, 1) NOT NULL,
        name NVARCHAR(100) NOT NULL,
        email NVARCHAR(100) NOT NULL,
        phone NVARCHAR(20) NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы адресов
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'addresses' 
               AND Type = 'U')
BEGIN
    CREATE TABLE addresses
    (
        address_id INT IDENTITY(1, 1) NOT NULL,
        customer_id INT NOT NULL,
        street NVARCHAR(100) NOT NULL,
        city NVARCHAR(50) NOT NULL,
        postal_code NVARCHAR(10) NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы заказов
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'orders' 
               AND Type = 'U')
BEGIN
    CREATE TABLE orders
    (
        order_id INT IDENTITY(1, 1) NOT NULL,
        customer_id INT NOT NULL,
        order_date DATE NOT NULL,
        total_amount DECIMAL(10, 2) NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы продуктов
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'products' 
               AND Type = 'U')
BEGIN
    CREATE TABLE products
    (
        product_id INT IDENTITY(1, 1) NOT NULL,
        product_name NVARCHAR(100) NOT NULL,
        price DECIMAL(10, 2) NOT NULL
    );
END;
GO

-- 2.2
-- Создание таблицы ассоциации заказов и продуктов
IF NOT EXISTS (SELECT * 
               FROM sys.objects 
               WHERE Name = 'orders_products' 
               AND Type = 'U')
BEGIN
    CREATE TABLE orders_products
    (
        order_id INT NOT NULL,
        product_id INT NOT NULL,
        quantity INT NOT NULL
    );
END;
GO

-- 2.3
-- Установка первичных ключей для таблицы customers
ALTER TABLE customers ADD CONSTRAINT PK_customers PRIMARY KEY (customer_id); 
GO

-- Установка первичных ключей для таблицы addresses
ALTER TABLE addresses ADD CONSTRAINT PK_addresses PRIMARY KEY (address_id); 
GO

-- Установка первичных ключей для таблицы orders
ALTER TABLE orders ADD CONSTRAINT PK_orders PRIMARY KEY (order_id); 
GO

-- Установка первичных ключей для таблицы products
ALTER TABLE products ADD CONSTRAINT PK_products PRIMARY KEY (product_id); 
GO

-- Установка первичных ключей для таблицы orders_products
ALTER TABLE orders_products ADD CONSTRAINT PK_orders_products PRIMARY KEY (order_id, product_id); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы клиентов
-- Установка уникальности для email
ALTER TABLE customers ADD CONSTRAINT UQ_customers_email UNIQUE (email); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы продуктов
-- Установка уникальности для product_name
ALTER TABLE products ADD CONSTRAINT UQ_products_product_name UNIQUE (product_name); 
GO

-- 2.3
-- Установка ограничений целостности для таблицы заказов
-- Добавление ограничения проверки для order_date
ALTER TABLE orders ADD CONSTRAINT CHK_order_date CHECK (order_date <= GETDATE()); 
GO

-- Установка значения по умолчанию для столбца order_date в таблице orders
-- Установка текущей даты в качестве значения по умолчанию для order_date
ALTER TABLE orders 
ADD CONSTRAINT DF_orders_order_date DEFAULT GETDATE() FOR order_date; 
GO

-- 2.4
-- Установка внешних ключей для таблицы addresses
-- Добавление внешнего ключа для связи с таблицей customers
ALTER TABLE addresses 
ADD CONSTRAINT FK_addresses_customer_id FOREIGN KEY (customer_id) 
REFERENCES customers (customer_id) 
ON DELETE CASCADE ON UPDATE CASCADE; 
GO

-- Установка внешних ключей для таблицы orders
-- Добавление внешнего ключа для связи с таблицей customers
ALTER TABLE orders 
ADD CONSTRAINT FK_orders_customer_id FOREIGN KEY (customer_id) 
REFERENCES customers (customer_id) 
ON DELETE CASCADE ON UPDATE CASCADE; 
GO

-- Установка внешних ключей для таблицы orders_products
-- Добавление внешнего ключа для связи с таблицей orders
ALTER TABLE orders_products 
ADD CONSTRAINT FK_orders_products_order_id FOREIGN KEY (order_id) 
REFERENCES orders (order_id) 
ON DELETE CASCADE ON UPDATE CASCADE; 
GO

-- Добавление внешнего ключа для связи с таблицей products
ALTER TABLE orders_products 
ADD CONSTRAINT FK_orders_products_product_id FOREIGN KEY (product_id) 
REFERENCES products (product_id) 
ON DELETE CASCADE ON UPDATE CASCADE; 
GO

-- 2.5
-- Заполнение таблицы customers
INSERT INTO customers (name, email, phone)
VALUES
('Ion Popescu', 'ion.popescu@example.md', '060-123-456'),
('Maria Ionescu', 'maria.ionescu@example.md', '061-234-567'),
('Vasile Rusu', 'vasile.rusu@example.md', '062-345-678'),
('Elena Ciobanu', 'elena.ciobanu@example.md', '063-456-789'),
('Andrei Munteanu', 'andrei.munteanu@example.md', '064-567-890'),
('Ana Sandu', 'ana.sandu@example.md', '065-678-901'),
('Gheorghe Mihai', 'gheorghe.mihai@example.md', '066-789-012'),
('Irina Pavel', 'irina.pavel@example.md', '067-890-123'),
('Sergiu Luca', 'sergiu.luca@example.md', '068-901-234'),
('Tatiana Dumitru', 'tatiana.dumitru@example.md', '069-012-345'),
('Victor Stoica', 'victor.stoica@example.md', '060-234-567'),
('Natalia Marin', 'natalia.marin@example.md', '061-345-678'),
('Mihai Grigore', 'mihai.grigore@example.md', '062-456-789'),
('Cristina Vasile', 'cristina.vasile@example.md', '063-567-890'),
('Alexandru Petrescu', 'alexandru.petrescu@example.md', '064-678-901');
GO

-- 2.5
-- Заполнение таблицы addresses
INSERT INTO addresses (customer_id, street, city, postal_code)
VALUES
(1, 'Strada Stefan cel Mare, 1', 'Chisinau', 'MD-2001'),
(2, 'Strada Mihai Eminescu, 2', 'Balti', 'MD-3100'),
(3, 'Strada Alexandru cel Bun, 3', 'Cahul', 'MD-3900'),
(4, 'Strada Vasile Alecsandri, 4', 'Orhei', 'MD-3500'),
(5, 'Strada Ion Creanga, 5', 'Ungheni', 'MD-3600'),
(6, 'Strada Grigore Vieru, 6', 'Soroca', 'MD-3000'),
(7, 'Strada Nicolae Iorga, 7', 'Hincesti', 'MD-3400'),
(8, 'Strada Mihail Kogalniceanu, 8', 'Edinet', 'MD-3700'),
(9, 'Strada Alexandru Ioan Cuza, 9', 'Drochia', 'MD-3800'),
(10, 'Strada Bogdan Petriceicu Hasdeu, 10', 'Falesti', 'MD-3200'),
(11, 'Strada Dimitrie Cantemir, 11', 'Ialoveni', 'MD-3300'),
(12, 'Strada Petru Rares, 12', 'Anenii Noi', 'MD-3400'),
(13, 'Strada Tudor Vladimirescu, 13', 'Causeni', 'MD-3500'),
(14, 'Strada Mihai Viteazul, 14', 'Rezina', 'MD-3600'),
(15, 'Strada Alexandru Lapusneanu, 15', 'Leova', 'MD-3700');
GO

-- 2.5
-- Заполнение таблицы products
INSERT INTO products (product_name, price)
VALUES
('Tort de ciocolata', 150.00),
('Cupcake de vanilie', 35.00),
('Tarta cu capsuni', 50.00),
('Placinta cu lamaie', 70.00),
('Briose cu afine', 25.00),
('Cheesecake cu zmeura', 80.00),
('Placinta cu mere', 60.00),
('Paine cu banane', 40.00),
('Tort de morcovi', 75.00),
('Placinta cu dovleac', 55.00),
('Rulada cu scortisoara', 30.00),
('Cobbler cu piersici', 65.00),
('Placinta cu cirese', 70.00),
('Tarta cu mure', 55.00),
('Placinta cu nuci pecan', 85.00);
GO

-- 2.5
-- Заполнение таблицы orders
INSERT INTO orders (customer_id, order_date, total_amount)
VALUES
(1, '2024-12-01', 300.00),
(2, '2024-12-02', 105.00),
(3, '2024-12-03', 150.00),
(4, '2024-12-04', 210.00),
(5, '2024-12-05', 75.00),
(6, '2024-12-06', 180.00),
(7, '2024-12-07', 250.00),
(8, '2024-12-08', 125.00),
(9, '2024-12-09', 200.00),
(10, '2024-12-09', 140.00),
(11, '2024-12-09', 225.00),
(12, '2024-12-09', 160.00),
(13, '2024-12-09', 195.00),
(14, '2024-12-09', 230.00),
(15, '2024-12-09', 115.00);
GO

-- 2.5
-- Заполнение таблицы orders_products
INSERT INTO orders_products (order_id, product_id, quantity)
VALUES
(1, 1, 2),
(1, 2, 3),
(2, 3, 2),
(2, 4, 1),
(3, 5, 6),
(3, 6, 1),
(4, 7, 2),
(4, 8, 1),
(5, 9, 1),
(5, 10, 2),
(6, 11, 3),
(6, 12, 1),
(7, 13, 2),
(7, 14, 1),
(8, 15, 1),
(8, 1, 2),
(9, 2, 3),
(9, 3, 1),
(10, 4, 2),
(10, 5, 1),
(11, 6, 2),
(11, 7, 1),
(12, 8, 3),
(12, 9, 1),
(13, 10, 2),
(13, 11, 1),
(14, 12, 2),
(14, 13, 1),
(15, 14, 3),
(15, 15, 1);
GO

-- 3.1.1
-- Написать запрос, который выполняет операцию объединения алгебры отношений.
-- Объединение заказов с общей суммой больше 200 и заказов, сделанных в декабре 2024 года
SELECT order_id, customer_id, order_date, total_amount
FROM orders
WHERE total_amount > 200  -- Заказы с общей суммой больше 200
UNION
SELECT order_id, customer_id, order_date, total_amount
FROM orders
WHERE order_date BETWEEN '2024-12-01' AND '2024-12-31';  -- Заказы, сделанные в декабре 2024 года

-- 3.1.1 (альтернатива)
-- Написать запрос, который выполняет операцию объединения алгебры отношений.
-- Объединение заказов с общей суммой больше 200 и заказов, сделанных в декабре 2024 года
SELECT order_id, customer_id, order_date, total_amount
FROM orders
WHERE total_amount > 200  -- Заказы с общей суммой больше 200
OR order_date BETWEEN '2024-12-01' AND '2024-12-31';  -- Заказы, сделанные в декабре 2024 года

-- 3.1.2
-- Написать запрос, который выполняет операцию пересечения алгебры отношений.
-- Пересечение заказов с общей суммой больше 200 и заказов, сделанных в декабре 2024 года
SELECT order_id, customer_id, order_date, total_amount
FROM orders
WHERE total_amount > 200  -- Заказы с общей суммой больше 200
INTERSECT
SELECT order_id, customer_id, order_date, total_amount
FROM orders
WHERE order_date BETWEEN '2024-12-01' AND '2024-12-31';  -- Заказы, сделанные в декабре 2024 года

-- 3.1.2 (альтернатива)
-- Написать запрос, который выполняет операцию пересечения алгебры отношений.
-- Пересечение заказов с общей суммой больше 200 и заказов, сделанных в декабре 2024 года
SELECT order_id, customer_id, order_date, total_amount
FROM orders
WHERE total_amount > 200  -- Заказы с общей суммой больше 200
AND order_date BETWEEN '2024-12-01' AND '2024-12-31';  -- Заказы, сделанные в декабре 2024 года

-- 3.1.3
-- Написать запрос, который выполняет операцию разности алгебры отношений.
-- Разность заказов с общей суммой больше 200 и заказов, сделанных в ноябре 2024 года
SELECT order_id, customer_id, order_date, total_amount
FROM orders
WHERE total_amount > 200  -- Заказы с общей суммой больше 200
EXCEPT
SELECT order_id, customer_id, order_date, total_amount
FROM orders
WHERE order_date BETWEEN '2024-11-01' AND '2024-11-30';  -- Заказы, сделанные в ноябре 2024 года

-- 3.1.3 (альтернатива)
-- Написать запрос, который выполняет операцию разности алгебры отношений.
-- Разность заказов с общей суммой больше 200 и заказов, сделанных в ноябре 2024 года
SELECT o.order_id, o.customer_id, o.order_date, o.total_amount
FROM orders o
WHERE o.total_amount > 200  -- Заказы с общей суммой больше 200
AND o.order_id NOT IN (
    SELECT o2.order_id
    FROM orders o2
    WHERE o2.order_date BETWEEN '2024-11-01' AND '2024-11-30'  -- Заказы, сделанные в ноябре 2024 года
);

-- 3.1.4
-- Написать запрос, который выполняет операцию активного дополнения алгебры отношений.
-- Найти все комбинации заказов и продуктов, которые не присутствуют в таблице orders_products.
SELECT o.order_id AS OrderID, p.product_id AS ProductID
FROM orders o, products p
WHERE NOT EXISTS (
    SELECT 1
    FROM orders_products op
    WHERE op.order_id = o.order_id AND op.product_id = p.product_id
);

-- 3.1.5
-- Написать запрос, который выполняет операцию декартова произведения алгебры отношений.
-- Выполнить декартово произведение таблиц orders и products
SELECT o.order_id, o.order_date, p.product_id, p.product_name
FROM orders o, products p
ORDER BY o.order_date;

-- 3.1.6
-- Написать запрос, который выполняет операцию селекции и проекции алгебры отношений.
-- Найти все заказы, сделанные клиентами с определённым именем, и проекцировать только order_id и order_date
SELECT o.order_id, o.order_date
FROM orders o
JOIN customers c ON o.customer_id = c.customer_id
WHERE c.name = 'Ion Popescu';

-- 3.1.7
-- Написать запрос, который выполняет операцию тета-соединения алгебры отношений.
-- Соединить таблицы orders и orders_products по order_id, где quantity больше 2
SELECT o.order_id, o.order_date, op.product_id, op.quantity
FROM orders o
JOIN orders_products op ON o.order_id = op.order_id
WHERE op.quantity > 2;

-- 3.1.8
-- Написать запрос, который выполняет операцию естественного соединения алгебры отношений.
-- Естественное соединение таблиц orders и orders_products по полю order_id
SELECT o.order_id, o.order_date, op.product_id, op.quantity
FROM orders o
INNER JOIN orders_products op ON o.order_id = op.order_id;

-- 3.1.9
-- Написать запрос, который выполняет операцию полусоединения алгебры отношений.
-- Найти все заказы, которые не содержат определённый продукт (например, с product_id = 5)
SELECT o.order_id, o.order_date
FROM orders o
RIGHT JOIN orders_products op
    ON o.order_id = op.order_id
WHERE op.product_id = 5  -- Определённый продукт
    AND o.order_id IS NOT NULL;  -- Заказы, которые не содержат данный продукт

-- 3.1.10
-- Написать запрос, который выполняет операцию левого внешнего соединения алгебры отношений.
-- Найти все заказы и информацию о продуктах, которые в них содержатся (если таковые есть)
SELECT o.order_id, o.order_date, p.product_id, p.product_name
FROM orders o
LEFT JOIN orders_products op 
    ON o.order_id = op.order_id
LEFT JOIN products p
    ON op.product_id = p.product_id;

-- 3.1.11
-- Написать запрос, который выполняет операцию правого внешнего соединения алгебры отношений.
-- Найти все продукты и заказы, в которых они содержатся (если таковые есть)
SELECT p.product_id, p.product_name, o.order_id, o.order_date
FROM products p
RIGHT JOIN orders_products op 
    ON p.product_id = op.product_id
RIGHT JOIN orders o
    ON op.order_id = o.order_id;

-- 3.1.12
-- Написать запрос, который выполняет операцию полного внешнего соединения алгебры отношений.
-- Найти все заказы и все продукты, которые содержатся или не содержатся в заказах
SELECT o.order_id, o.order_date, p.product_id, p.product_name
FROM orders o
FULL OUTER JOIN orders_products op 
    ON o.order_id = op.order_id
FULL OUTER JOIN products p
    ON op.product_id = p.product_id;

-- 3.1.13
-- Написать запрос, который выполняет операцию деления алгебры отношений.
-- Найти все заказы, которые содержат хотя бы 2 продукта.
SELECT o.order_id, o.order_date
FROM orders o
WHERE (
    SELECT COUNT(DISTINCT op.product_id)
    FROM orders_products op
    WHERE op.order_id = o.order_id
) >= 2;

-- 3.2.1
-- Написать запрос, который выводит минимальную, максимальную и среднюю цену продуктов.
-- Запрос должен вернуть минимальную цену (MIN), максимальную цену (MAX) и среднюю цену (AVG) продуктов.
SELECT 
    MIN(price) AS MinPrice,
    MAX(price) AS MaxPrice,
    AVG(price) AS AvgPrice
FROM products;

-- 3.2.2
-- Написать запрос, который использует агрегатную функцию SUM.
-- Запрос должен вернуть общую стоимость всех продуктов (SUM).
SELECT 
    SUM(price) AS TotalPrice
FROM products;

-- 3.2.3
-- Написать запрос, который использует агрегатную функцию COUNT.
-- Запрос должен вернуть количество продуктов (COUNT).
SELECT 
    COUNT(*) AS TotalProducts
FROM products;

-- 3.3.1
-- Написать запрос с группировкой для одной таблицы.
-- Запрос должен вернуть количество заказов (COUNT) для каждого клиента (customer_id).
SELECT customer_id, COUNT(*) AS TotalOrders
FROM orders
GROUP BY customer_id;

-- 3.3.2
-- Написать запрос с группировкой для нескольких таблиц.
-- Запрос должен вернуть количество заказов (COUNT) для каждого города (city).
SELECT a.city, COUNT(o.order_id) AS TotalOrders
FROM addresses a
JOIN customers c ON a.customer_id = c.customer_id
JOIN orders o ON c.customer_id = o.customer_id
GROUP BY a.city;

-- 3.4.1
-- Написать запрос с подзапросом, использующим операторы сравнения.
-- Запрос должен вернуть все продукты, которые имеют цену больше средней цены всех продуктов.
SELECT product_id, price
FROM products
WHERE price > (SELECT AVG(price) FROM products);

-- 3.4.2
-- Написать запрос с подзапросом, использующим оператор IN.
-- Запрос должен вернуть все заказы, сделанные клиентами из Кишинева.
SELECT order_id, order_date
FROM orders
WHERE customer_id IN (
    SELECT c.customer_id
    FROM customers c
    JOIN addresses a ON c.customer_id = a.customer_id
    WHERE a.city = 'Chisinau'
);

-- 3.4.3
-- Написать запрос с подзапросом, использующим оператор ALL.
-- Запрос должен вернуть все продукты, которые имеют цену больше, чем у всех продуктов, заказанных клиентами из Кишинева.
SELECT product_id, price
FROM products
WHERE price > ALL (
    SELECT p.price
    FROM products p
    JOIN orders_products op ON p.product_id = op.product_id
    JOIN orders o ON op.order_id = o.order_id
    JOIN customers c ON o.customer_id = c.customer_id
    JOIN addresses a ON c.customer_id = a.customer_id
    WHERE a.city = 'Chisinau'
);

-- 3.4.4
-- Написать запрос с подзапросом, использующим оператор ANY.
-- Запрос должен вернуть все продукты, которые имеют цену больше, чем у любого продукта, заказанного клиентами из Balti.
SELECT product_id, price
FROM products
WHERE price > ANY (
    SELECT p.price
    FROM products p
    JOIN orders_products op ON p.product_id = op.product_id
    JOIN orders o ON op.order_id = o.order_id
    JOIN customers c ON o.customer_id = c.customer_id
    JOIN addresses a ON c.customer_id = a.customer_id
    WHERE a.city = 'Balti'
);

-- 3.4.5
-- Написать запрос с подзапросом, использующим оператор EXISTS.
-- Запрос должен вернуть все заказы, сделанные клиентами из Кишинева.
SELECT order_id, order_date
FROM orders o
WHERE EXISTS (
    SELECT 1
    FROM customers c
    JOIN addresses a ON c.customer_id = a.customer_id
    WHERE o.customer_id = c.customer_id
    AND a.city = 'Chisinau'
);

-- 3.4.6
-- Написать запрос с коррелированным подзапросом в WHERE.
-- Запрос должен вернуть все заказы, содержащие продукты с названием, содержащим слово "tort".
SELECT order_id, order_date
FROM orders o
WHERE EXISTS (
    SELECT 1
    FROM orders_products op
    JOIN products p ON op.product_id = p.product_id
    WHERE op.order_id = o.order_id
    AND p.product_name LIKE '%tort%'
);

-- 3.4.6
-- Написать запрос с подзапросом в HAVING.
-- Запрос должен вернуть все города, у которых общее количество заказов превышает среднее количество заказов по всем городам.
SELECT a.city, COUNT(o.order_id) AS TotalOrders
FROM addresses a
JOIN customers c ON a.customer_id = c.customer_id
JOIN orders o ON c.customer_id = o.customer_id
GROUP BY a.city
HAVING COUNT(o.order_id) > (
    SELECT AVG(OrderCount)
    FROM (
        SELECT COUNT(o.order_id) AS OrderCount
        FROM addresses a
        JOIN customers c ON a.customer_id = c.customer_id
        JOIN orders o ON c.customer_id = o.customer_id
        GROUP BY a.city
    ) AS AvgOrders
);

-- 3.4.6
-- Написать запрос с подзапросом в BETWEEN.
-- Запрос должен вернуть все заказы, сделанные в определенный временной промежуток.
SELECT order_id, order_date
FROM orders
WHERE order_date BETWEEN '2024-12-01' AND '2024-12-15';

-- 3.4.7
-- Написать запрос с подзапросом в FROM.
-- Запрос должен вернуть количество заказов для каждого клиента.
SELECT customer_id, COUNT(order_id) AS TotalOrders
FROM (
    SELECT customer_id, order_id
    FROM orders
) AS SubQuery
GROUP BY customer_id;

-- 3.4.8
-- Написать запрос с подзапросом в SELECT.
-- Запрос должен вернуть информацию о клиентах вместе с количеством заказов, которые они сделали.
SELECT 
    c.customer_id, 
    c.name, 
    (SELECT COUNT(*) 
     FROM orders o 
     WHERE o.customer_id = c.customer_id) AS TotalOrders
FROM 
    customers c;

-- 4.1.1
-- Написать запрос, который удаляет первые несколько записей с условием.
-- Запрос должен удалить первые 5 записей, где название продукта содержит слово "tort".
DELETE TOP (5)
FROM products
WHERE product_name LIKE '%tort%';

-- 4.1.2
-- Написать запрос для условного удаления с подзапросом для одной таблицы.
-- Удалить все продукты, которые не связаны ни с одной записью в таблице orders_products.
DELETE FROM products
WHERE product_id NOT IN (
    SELECT product_id
    FROM orders_products
);

-- 4.1.3
-- Написать запрос для обновления записей в таблице с использованием TOP.
-- Обновить первые 5 записей в таблице products, установив значение price на 10.0.
UPDATE TOP (5) products
SET price = 10.0;

-- 4.1.4
-- Написать запрос, который обновляет записи на основе подзапроса с несколькими таблицами.
-- Обновить цену продуктов, которые были заказаны в текущем месяце.
UPDATE products
SET price = price + 5.0
WHERE product_id IN (
    SELECT DISTINCT p.product_id
    FROM products p
    JOIN orders_products op ON p.product_id = op.product_id
    JOIN orders o ON op.order_id = o.order_id
    WHERE MONTH(o.order_date) = MONTH(GETDATE()) 
      AND YEAR(o.order_date) = YEAR(GETDATE())
);

-- 4.2.1
-- Написать запрос для создания новой записи INSERT INTO с SELECT.
-- Создать новую запись в таблице orders, копируя данные из таблицы orders_products.
INSERT INTO orders (customer_id, order_date, total_amount)
SELECT 1, GETDATE(), 100.00
FROM orders_products
WHERE product_id = 3;

-- 4.2.2
-- Написать запрос для вставки результата запроса SELECT в новую таблицу.
-- Создать новую таблицу recent_orders и вставить в нее данные из таблицы orders для заказов, сделанных после 1 декабря 2024 года.
SELECT order_id, customer_id, order_date, total_amount
INTO recent_orders
FROM orders
WHERE order_date > '2024-12-01';