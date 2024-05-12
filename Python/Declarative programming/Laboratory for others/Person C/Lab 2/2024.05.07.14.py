import csv
from bs4 import BeautifulSoup
import requests

# URL адрес страницы с каталогом смартфонов на сайте prime-pc.md
url = "https://prime-pc.md/catalog/mobilnye-telefony-i-smartfony"

# Отправка запроса на получение HTML-кода страницы
response = requests.get(url)

# Проверка успешности запроса
print("Response Status Code:", response.status_code)  # Print out the response status code

if response.status_code == 200:
    # Создание объекта BeautifulSoup для парсинга HTML-кода
    soup = BeautifulSoup(response.text, "html.parser")

    # Открытие CSV файла для записи
    with open("Lab 2/prime-pc_smartphones.csv", "w", newline="", encoding="utf-8") as csvfile:
        writer = csv.writer(csvfile)
        # Запись заголовков столбцов в CSV файл
        writer.writerow(["Название", "Цена"])

        # Поиск всех блоков с информацией о смартфонах
        smartphones = soup.find_all("div", class_="product")

        # Обход каждого блока с информацией о смартфоне
        for smartphone in smartphones:
            # Извлечение названия смартфона
            title = smartphone.find("h3").text.strip()

            # Извлечение цены смартфона
            price = smartphone.find("p", class_="product_price").text.strip()
            # Убираем лишние символы из цены
            price = price.replace("Lei", "").strip()

            # Запись данных в CSV файл
            writer.writerow([title, price])

        # Вывод сообщения о завершении записи данных
        print("Данные успешно записаны в файл prime-pc_smartphones.csv")
else:
    # Вывод сообщения об ошибке при получении доступа к странице
    print("Не удалось получить доступ к странице.")