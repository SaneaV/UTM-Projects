
# Выберите интересующую вас тему на wikipedia.org и выполните следующие задачи:

# Получите заголовок страницы;
# Получите заголовки всех разделов;
# Получите как минимум одно изображение с этого сайта.

import requests
from bs4 import BeautifulSoup
import os

# Определение URL-адреса страницы Википедии
url = 'https://ru.m.wikipedia.org/wiki/%D0%93%D0%BE%D0%BC%D1%83%D0%BD%D0%BA%D1%83%D0%BB'

# Получение содержимого веб-страницы
response = requests.get(url)
content = response.content

# Парсинг содержимого с помощью BeautifulSoup
soup = BeautifulSoup(content, 'html.parser')

# Захват заголовка страницы
page_title = soup.find('h1').text
print("Заголовок страницы:", page_title)

# Захват заголовков разделов
section_titles = soup.find_all('span', class_='mw-headline')
print("\nЗаголовки разделов:")
for title in section_titles:
    print(title.text)

# Получение ссылки на второе изображение с веб-страницы Wikipedia
images = soup.find_all('img')
if len(images) > 1:
    image_src = images[1]['src']
    image_url = 'https:' + image_src
    print("\nСсылка на второе изображение на странице:", image_url)

    # Загрузка изображения
    response = requests.get(image_url)
    image_content = response.content

    # Определение пути для сохранения изображения
    image_path = os.path.join('Lab 2', 'wikipedia_image2.jpg')

    # Сохранение изображения на диск
    with open(image_path, 'wb') as file:
        file.write(image_content)

    print("\nВторое изображение успешно загружено и сохранено по пути:", image_path)
else:
    print("\nВторого изображения на странице не найдено.")