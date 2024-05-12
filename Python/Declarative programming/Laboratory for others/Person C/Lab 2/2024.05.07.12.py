import csv
from bs4 import BeautifulSoup
import requests

# URL адрес страницы с каталогом смартфонов на сайте eshop.moldcell.md
url = "https://eshop.moldcell.md/ru/telefoane/smartphone"

# Отправка запроса на получение HTML-кода страницы
response = requests.get(url)

# Проверка успешности запроса
print("Response Status Code:", response.status_code)  # Print out the response status code

if response.status_code == 200:
    # Создание объекта BeautifulSoup для парсинга HTML-кода
    soup = BeautifulSoup(response.text, "html.parser")

    # Открытие CSV файла для записи
    with open("Lab 2/moldcell_smartphones.csv", "w", newline="", encoding="utf-8") as csvfile:
        writer = csv.writer(csvfile)
        # Запись заголовков столбцов в CSV файл
        writer.writerow(["Название", "Цена"])

        # Поиск всех блоков с информацией о смартфонах
        smartphones = soup.find_all("div", class_="phone-info")

        # Обход каждого блока с информацией о смартфоне
        for smartphone in smartphones:
            # Извлечение названия смартфона
            title_element = smartphone.find("p", class_="phone-model")
            title = title_element.text.strip() if title_element else "Название не найдено"

            # Извлечение цены смартфона
            price_element = smartphone.find("div", class_="online-price").find("h5", class_="price")
            price = price_element.text.strip() if price_element else "Цена не указана"
            # Убираем неразрывные пробелы из цены
            price = price.replace(" ", "").replace("лей", "")

            # Запись данных в CSV файл
            writer.writerow([title, price])

        # Вывод сообщения о завершении записи данных
        print("Данные успешно записаны в файл moldcell_smartphones.csv")
else:
    # Вывод сообщения об ошибке при получении доступа к странице
    print("Не удалось получить доступ к странице.")