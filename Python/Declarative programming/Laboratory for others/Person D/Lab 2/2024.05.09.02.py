import requests
from bs4 import BeautifulSoup

# URL-адрес для веб-сайта с книгами
base_url = "https://books.toscrape.com/catalogue"

# Список, в котором будут храниться названия книг с рейтингом "Two"
titles_two_star = []

# Общее количество страниц с книгами
num_pages = 50  # Можете изменить это значение в соответствии с вашими потребностями

# Проходим по каждой странице
for page in range(1, num_pages + 1):
    # Строим URL для текущей страницы
    url_page = f"{base_url}/page-{page}.html"

    # Отправляем HTTP-запрос, чтобы получить содержимое страницы
    response = requests.get(url_page)
    soup = BeautifulSoup(response.text, 'html.parser')

    # Ищем все книги на странице
    books = soup.find_all('article', class_='product_pod')

    # Проходим по каждой книге
    for book in books:
        # Ищем рейтинг книги
        star_rating = book.find('p', class_='star-rating')
        
        # Проверяем, что рейтинг 'Two'
        if star_rating and 'Two' in star_rating['class']:
            # Ищем название книги
            title_book = book.find('h3').find('a')['title']
            titles_two_star.append(title_book)

# Выводим список с названиями книг с рейтингом "Two"
print("Названия книг с рейтингом 'Two':")
for title_book in titles_two_star:
    print(title_book)
