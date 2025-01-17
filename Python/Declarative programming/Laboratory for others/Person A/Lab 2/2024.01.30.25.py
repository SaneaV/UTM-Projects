import requests
from bs4 import BeautifulSoup
import csv
import re

# URL веб-страницы
url = "https://glovoapp.com/md/ro/chisinau/magic-kebab-ksn?content=meniu-c.1555016353&section=kebab-s.3285254610"

# Отправка GET-запроса на URL
response = requests.get(url)

# Проверка успешности запроса (статус код 200)
if response.status_code == 200:
    # Разбор HTML-контента с помощью BeautifulSoup
    soup = BeautifulSoup(response.text, "html.parser")
    soup = soup.find("div", id="kebab-s.3285254610")

    # Извлечение информации на основе структуры HTML
    products = []

    list_title_element = soup.find("p", class_="list__title")
    if list_title_element:
        list_title = list_title_element.text.strip()

        # Поиск всех элементов продуктов
        product_elements = soup.find_all("div", class_="product-row")
        for product_element in product_elements:
            product_name_element = product_element.find(
                "span", {"data-test-id": "product-row-name__highlighter"}
            )
            product_bottom_element = product_element.find(
                "div", class_="product-row__bottom"
            )
            product_price_element = product_bottom_element.find(
                "span", {"data-test-id": "product-price-effective"}
            )

            if product_name_element:
                product_name = product_name_element.text.strip()

                product_price = None
                if product_price_element:
                    # Использование регулярного выражения для извлечения числовой части
                    match = re.search(r"\d+,\d+", product_price_element.text)
                    if match:
                        product_price = match.group()

                products.append(
                    {
                        "Название списка": list_title,
                        "Название продукта": product_name,
                        "Цена продукта": product_price,
                    }
                )

    # Запись информации в файл CSV
    csv_filename = "Lab 2/Magic Kebab.csv"
    with open(csv_filename, "w", newline="", encoding="utf-8") as csvfile:
        fieldnames = ["Название списка", "Название продукта", "Цена продукта"]
        writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

        # Запись заголовка
        writer.writeheader()

        # Запись данных
        for product in products:
            writer.writerow(product)

    print(f"Информация записана в {csv_filename}")
else:
    print("Не удалось получить веб-страницу. Код статуса:", response.status_code)
