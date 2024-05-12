import requests
from bs4 import BeautifulSoup
import os

# Определение URL-адреса страницы Википедии
url = 'https://en.wikipedia.org/wiki/James_Joyce'

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
    
# Загрузка изображения
image_url = 'https:' + images[4]['src']
response = requests.get(image_url)
image_content = response.content
    
# Определение пути для сохранения изображения
image_path = os.path.join('Lab 2', 'wikipedia_image.jpg')
    
# Сохранение изображения на диск
with open(image_path, 'wb') as file:
    file.write(image_content)

print("\nИзображение успешно загружено и сохранено по пути:", image_path)