# Сделать запросы к минимуму 3 сайтам для получения нужной информации 
# (на выбор: погодные данные, курсы валют, цены на товары, рейтинг отелей, ресторанов и т. д.). 
# В результате должно быть получено 3 файла .csv с подобными данными, например, 
# погодные данные за октябрь с 3 сайтов прогноза погоды, курсы валют за месяц с 3 различных банков, 
# рейтинги для списка отелей с 3 разных сайтов и т. д. Примечание: убедитесь, что сайты разрешают веб-скрапинг

import csv
from bs4 import BeautifulSoup
import requests
import re

url = "https://enter.online/ru/noutbuki"

response = requests.get(url)

if response.status_code == 200:
    soup = BeautifulSoup(response.text, "html.parser")
    
    # Открываем CSV файл для записи
    with open("Lab 2/enter_online_laptops.csv", "w", newline="", encoding="utf-8") as csvfile:
        writer = csv.writer(csvfile)
        # Записываем заголовки столбцов
        writer.writerow(["Название", "Цена"])
        
        # Находим все блоки с информацией о ноутбуках
        laptops = soup.find_all("div", class_="grid-item uk-box-shadow-hover-medium")
        
        for laptop in laptops:
            # Находим название ноутбука
            title = laptop.find("span", class_="product-title").text.strip()
            
            # Находим цену ноутбука
            price_container = laptop.find("span", class_="grid-price")

            default_price = price_container.find("span", class_="price")
            new_price = price_container.find("span", class_="price-new")

            # Если есть новая цена, значит товар со скидкой
            if new_price:
                price_text = re.search(r'(\d+\s\d+)', new_price.text.strip()).group()
                price_text = price_text.replace(" ", "")  # Удаляем пробелы из строки с ценой
            elif default_price:
                price_text = re.search(r'(\d+\s\d+)', default_price.text.strip()).group()  # Удаляем пробелы из строки с ценой
                price_text = price_text.replace(" ", "")  # Удаляем пробелы из строки с ценой
            else:
                price_text = "Цена не указана"
            
            # Записываем данные в CSV файл
            writer.writerow([title, price_text])
            
        print("Данные успешно записаны в файл enter_online_laptops.csv")
else:
    print("Не удалось получить доступ к странице.")
