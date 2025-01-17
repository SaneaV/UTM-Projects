import requests
from bs4 import BeautifulSoup

# Номер страницы каталога
page_number = 1

# Список для хранения заголовков книг с двумя звездами
books_with_two_stars = []

# Цикл для обхода всех страниц каталога
while True:
    # Формирование URL-адреса текущей страницы
    url = f"http://books.toscrape.com/catalogue/page-{page_number}.html"
    
    # Отправка запроса на получение HTML-кода страницы
    response = requests.get(url)
    
    # Проверка успешности запроса
    if response.status_code == 200:
        # Создание объекта BeautifulSoup для парсинга HTML-кода
        soup = BeautifulSoup(response.text, "html.parser")
        
        # Поиск всех элементов книг на странице
        books = soup.find_all("article", class_="product_pod")
        
        # Проверка наличия книг с двумя звездами
        for book in books:
            star_rating = book.find("p")["class"][1]
            if star_rating == "Two":
                title = book.find("h3").find("a")["title"]
                books_with_two_stars.append(title)
        
        # Вывод информации о текущей странице
        print("Страница:", page_number)
        
        # Увеличение номера страницы
        page_number += 1
    else:
        # Если запрос неудачен, завершаем анализ
        print("Анализ завершен. Произошла ошибка при получении страницы.")
        break

# Вывод заголовков книг с двумя звездами
print("\nЗаголовки книг с 2-звездочным рейтингом:")
for title in books_with_two_stars:
    print(title)