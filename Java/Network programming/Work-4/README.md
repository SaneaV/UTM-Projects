# UtmShopClient

## Описание программы

Программа **UtmShopClient** предназначена для взаимодействия с сервером приложения UtmShop для управления категориями и
продуктами. Она поддерживает операции создания, удаления, обновления и чтения категорий, а также добавление и просмотр продуктов в
категориях.

### Возможности программы:

- Список всех категорий.
- Просмотр деталей категории.
- Создание новой категории.
- Удаление категории.
- Обновление названия категории.
- Список продуктов в категории.
- Добавление продукта в категорию.

## Требования

Для работы программы вам потребуется:

- **Java Development Kit (JDK)** версии 20.
- **ASP.NET Core Runtime** для запуска сервера UtmShop.
- **.NET SDK** для импорта и запуска проекта UtmShop.

## Установка и запуск сервера UtmShop

### Установка необходимых компонентов

1. Скачайте и установите **ASP.NET Core Runtime**:
   [ASP.NET Core Runtime](https://dotnet.microsoft.com/ru-ru/download/dotnet/thank-you/runtime-aspnetcore-7.0.20-windows-x64-installer?cid=getdotnetcore)
2. Скачайте и установите **.NET Runtime**:
   [.NET Runtime](https://dotnet.microsoft.com/ru-ru/download/dotnet/thank-you/runtime-7.0.20-windows-x64-installer)
3. Скачайте и установите **.NET SDK**:
   [.NET SDK](https://dotnet.microsoft.com/ru-ru/download/dotnet/thank-you/sdk-9.0.102-windows-x64-installer)

### Запуск сервера UtmShop

1. Откройте терминал или Visual Studio.
2. Импортируйте проект UtmShop.
3. Выполните команду для запуска сервера:
   ```bash
   dotnet run
   ```
   
## Сборка и запуск клиента UtmShopClient

### Сборка проекта

Выполните команду для сборки проекта:

```bash
mvn clean package
```

### 

После успешной сборки выполните команду для запуска программы:

```bash
mvn exec:java
```

## Использование программы

После запуска программы появится меню с возможностями:

```plaintext
Choose an action:
1. List categories
2. Show category details
3. Create a new category
4. Delete a category
5. Update a category title
6. List products in a category
7. Add a product to a category
0. Exit
```

Выберите нужное действие, введя соответствующий номер. Программа запросит необходимые данные, такие как ID категории, название
категории, ID продукта, название продукта и т. д.

## Примеры использования

### Список всех категорий

```plaintext
Choose an action:
1. List categories
Categories:
ID: 1, Name: Electronics, Items Count: 10
ID: 2, Name: Books, Items Count: 5
```

### Просмотр деталей категории

```plaintext
Choose an action:
2. Show category details
Enter category ID: 1
Category Details:
ID: 1
Name: Electronics
Items Count: 10
```

### Создание новой категории

```plaintext
Choose an action:
3. Create a new category
Enter category name: Home Appliances
Category created successfully.
```

### Удаление категории

```plaintext
Choose an action:
4. Delete a category
Enter category ID to delete: 2
Category deleted successfully.
```

### Обновление названия категории

```plaintext
Choose an action:
5. Update a category title
Enter category ID to update: 1
Enter new category title: Consumer Electronics
Category updated successfully.
```

### Список продуктов в категории

```plaintext
Choose an action:
6. List products in a category
Enter category ID to list products: 1
Products:
ID: 1, Title: Smartphone, Price: 999.99
ID: 2, Title: Laptop, Price: 1499.99
```

### Добавление продукта в категорию

```plaintext
Choose an action:
7. Add a product to a category
Enter category ID to add product: 1
Enter product title: Tablet
Enter product price: 499.99
Product added successfully.
```

## Дополнительная информация

### Application URL

Для просмотра документации API сервера UtmShop используйте следующий URL:
[Swagger Documentation](https://localhost:5001/swagger/index.html)