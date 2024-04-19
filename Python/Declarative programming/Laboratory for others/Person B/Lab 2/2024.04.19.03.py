# Сделать запросы к минимуму 3 сайтам для получения нужной информации 
# (на выбор: погодные данные, курсы валют, цены на товары, рейтинг отелей, ресторанов и т. д.). 
# В результате должно быть получено 3 файла .csv с подобными данными, например, 
# погодные данные за октябрь с 3 сайтов прогноза погоды, курсы валют за месяц с 3 различных банков, 
# рейтинги для списка отелей с 3 разных сайтов и т. д. Примечание: убедитесь, что сайты разрешают веб-скрапинг

import csv
from bs4 import BeautifulSoup
import requests
import re

url = "https://ultra.md/ru/category/notebooks"

response = requests.get(url)

if response.status_code == 200:
    soup = BeautifulSoup(response.text, "html.parser")
    
    # Открываем CSV файл для записи
    with open("Lab 2/ultra_laptops.csv", "w", newline="", encoding="utf-8") as csvfile:
        writer = csv.writer(csvfile)
        # Записываем заголовки столбцов
        writer.writerow(["Название", "Цена"])
        
        # Находим все блоки с товарами
        products = soup.find_all("div", class_="product-block-card-container")
        
        for product in products:
            # Находим название ноутбука
            title = product.find("a", class_="product-text").text.strip()
            
            # Находим цену ноутбука
            price_container = product.find("div", class_="flex flex-wrap gap-x-2 w-full items-center justify-between")
            price = price_container.find("span", class_="text-blue text-xl font-bold dark:text-white").text.strip().replace(" ", "")  # Удаляем пробелы из строки с ценой

            # Используем регулярное выражение для извлечения числа из строки с ценой
            price_match = re.search(r'\d+', price)
            if price_match:
                price = int(price_match.group())
            else:
                price = "Цена не указана"
            
            # Записываем данные в CSV файл
            writer.writerow([title, price])
            
        print("Данные успешно записаны в файл ultra_notebooks.csv")
else:
    print("Не удалось получить доступ к странице.")
