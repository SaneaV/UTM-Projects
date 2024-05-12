import requests
from os.path  import basename
from bs4 import BeautifulSoup
from urllib.request import urlopen

# Задаем URL-адрес страницы, которую будем парсить
url = "https://en.wikipedia.org/wiki/Earth"

# Получаем содержимое страницы
html_page = requests.get(url, headers={"User-Agent":"Mozilla/5.0"})
html = html_page.content

# Создаем объект BeautifulSoup для парсинга страницы
soup = BeautifulSoup(html, "html.parser")

# Выводим название сайта
print("\nНазвание сайта: ", soup.find("title").get_text())
print("\nSite name: ", soup.find("title").get_text())

# Выводим список всех тем
print("\nВсе темы: ", )
print("\nAll topics: ", )
topics = soup.find_all("span", {"class": "mw-headline"})
for topic in topics:
    print(topic.get_text())

# Скачиваем изображение
print("\nИзображение: ", end="")
print("\nImage: ", end="")
imageHtml = soup.find_all("img", {"class": "mw-file-element"})[3]
image = "https:" + imageHtml.get("src")

# Сохраняем изображение
with open(basename(image), "wb") as f:
    f.write(requests.get(image).content)
print("Скачано!", )
