# Сделать запросы к минимуму 3 сайтам для получения нужной информации 
# (на выбор: погодные данные, курсы валют, цены на товары, рейтинг отелей, ресторанов и т. д.). 
# В результате должно быть получено 3 файла .csv с подобными данными, например, 
# погодные данные за октябрь с 3 сайтов прогноза погоды, курсы валют за месяц с 3 различных банков, 
# рейтинги для списка отелей с 3 разных сайтов и т. д. Примечание: убедитесь, что сайты разрешают веб-скрапинг

import csv
from bs4 import BeautifulSoup
import requests

url = "https://darwin.md/laptopuri"

response = requests.get(url)

if response.status_code == 200:
    soup = BeautifulSoup(response.text, "html.parser")
    
    # Открываем CSV файл для записи
    with open("Lab 2/darwin_laptops.csv", "w", newline="", encoding="utf-8") as csvfile:
        writer = csv.writer(csvfile)
        # Записываем заголовки столбцов
        writer.writerow(["Название", "Цена"])
        
        # Находим все блоки с информацией о ноутбуках
        laptops = soup.find_all("figure", class_="card-product")
        
        for laptop in laptops:
            # Находим название ноутбука
            title = laptop.find("a", class_="d-block").text.strip()
            
            # Находим цену ноутбука
            price_container = laptop.find("div", class_="price-wrap")
            old_price = price_container.find("span", class_="last-price")
            new_price = price_container.find("span", class_="price-new").find("b")
            
            # Если есть новая цена, значит товар со скидкой
            if new_price:
                price_text = new_price.text.strip().replace(" ", "")  # Удаляем пробелы из строки с ценой
            # Если нет новой цены, используем старую цену
            elif old_price:
                price_text = old_price.text.strip().replace(" ", "")  # Удаляем пробелы из строки с ценой
            else:
                price_text = "Цена не указана"
            
            # Записываем данные в CSV файл
            writer.writerow([title, price_text])
            
        print("Данные успешно записаны в файл darwin_laptops.csv")
else:
    print("Не удалось получить доступ к странице.")
