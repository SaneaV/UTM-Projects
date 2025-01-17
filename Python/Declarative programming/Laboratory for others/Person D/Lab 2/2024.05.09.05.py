import requests
from bs4 import BeautifulSoup
import csv
import re

def extract_product_info(product_element):
    product_name_element = product_element.find("span", {"data-test-id": "product-row-name__highlighter"})
    product_bottom_element = product_element.find("div", class_="product-row__bottom")
    product_price_element = product_bottom_element.find("span", {"data-test-id": "product-price-effective"})

    product_name = product_name_element.text.strip() if product_name_element else None
    product_price = None
    if product_price_element:
        match = re.search(r"\d+,\d+", product_price_element.text)
        product_price = match.group() if match else None
    return {"Название продукта": product_name, "Цена продукта": product_price}

url = "https://glovoapp.com/md/ro/chisinau/origami-sushi?content=meniu-c.134752512&section=roll-s.459958791"
response = requests.get(url)
if response.status_code == 200:
    soup = BeautifulSoup(response.text, "html.parser")
    soup = soup.find("div", id="roll-s.459958791")

    products = []
    list_title_element = soup.find("p", class_="list__title")
    if list_title_element:
        product_elements = soup.find_all("div", class_="product-row")
        products = [extract_product_info(product) for product in product_elements if extract_product_info(product)]

    csv_filename = "Origami Sushi.csv"
    with open(csv_filename, "w", newline="", encoding="utf-8") as csvfile:
        fieldnames = ["Название продукта", "Цена продукта"]
        writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(products)

    print(f"Информация записана в {csv_filename}")
else:
    print("Не удалось получить веб-страницу. Код статуса:", response.status_code)
