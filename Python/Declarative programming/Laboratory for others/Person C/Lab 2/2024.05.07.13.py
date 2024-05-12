import csv
from bs4 import BeautifulSoup
import requests

# URL адрес страницы с каталогом смартфонов на сайте maximum.md
url = "https://maximum.md/ro/telefoane-si-gadgeturi/telefoane-si-comunicatii/smartphoneuri/"

# Отправка запроса на получение HTML-кода страницы
response = requests.get(url)

# Проверка успешности запроса
print("Response Status Code:", response.status_code)  # Print out the response status code

if response.status_code == 200:
    # Создание объекта BeautifulSoup для парсинга HTML-кода
    soup = BeautifulSoup(response.text, "html.parser")

    # Открытие CSV файла для записи
    with open("Lab 2/maximum_smartphones.csv", "w", newline="", encoding="utf-8") as csvfile:
        writer = csv.writer(csvfile)
        # Запись заголовков столбцов в CSV файл
        writer.writerow(["Название", "Цена"])

        # Поиск всех блоков с информацией о смартфонах
        smartphones = soup.find_all("div", class_="js-content product__item")

        # Обход каждого блока с информацией о смартфоне
        for smartphone in smartphones:
            # Извлечение названия смартфона
            title = smartphone.find("div", class_="product__item__title").text.strip()

            # Извлечение цены смартфона
            price = smartphone.find("div", class_="product__item__price-current").text.strip()
            # Убираем лишние символы из цены
            price = price.split()[0]

            # Запись данных в CSV файл
            writer.writerow([title, price])

        # Вывод сообщения о завершении записи данных
        print("Данные успешно записаны в файл maximum_smartphones.csv")
else:
    # Вывод сообщения об ошибке при получении доступа к странице
    print("Не удалось получить доступ к странице.")
