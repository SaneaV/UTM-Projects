# Перейдите на веб-сайт: http://books.toscrape.com/index.html, который специально предназначен для тестирования веб-скрапинга.
# Получите название каждой книги, которая имеет рейтинг две звезды, и в конце получите простой список Python из всех их названий.
# Найдите структуру URL для обхода каждой страницы;
# Разберите каждую страницу в каталоге;
# Найдите, какая метка/класс представляет рейтинг звезд;
# Отфильтруйте с помощью рейтинга звезд;
# Сохраните результаты в списке.


import requests
from bs4 import BeautifulSoup

# Номер страницы каталога
page_Number = 1
# Список для хранения заголовков книг с двумя звездами
books_With_Two_Stars = []

# Цикл для обхода всех страниц каталога
while True:
    # Формирование URL-адреса текущей страницы
    url = f"http://books.toscrape.com/catalogue/page-{page_Number}.html"
    # Отправка запроса на получение HTML-кода страницы
    response = requests.get(url)

    # Проверка успешности запроса
    if response.status_code == 200:
        # Создание объекта BeautifulSoup для парсинга HTML-кода
        soup = BeautifulSoup(response.text, "html.parser")
        # Поиск всех элементов книг на странице
        books = soup.find_all("article", class_="product_pod")

        # Цикл для обхода каждой найденной книги на странице
        for book in books:
            # Поиск рейтинга книги
            star_rating = book.find("p")["class"][1]
            # Проверка, является ли рейтинг книги двумя звездами
            if star_rating == "Two":
                # Если да, то получаем заголовок книги и добавляем его в список
                title = book.find("h3").find("a")["title"]
                books_With_Two_Stars.append(title)

        # Выводим информацию о том, на какой странице мы находимся
        print("Анализ страницы:", page_Number)
        # Увеличиваем номер страницы для перехода к следующей странице
        page_Number += 1
    else:
        # Если запрос неудачен, значит мы достигли конца каталога
        print("Анализ завершен на странице:", page_Number)
        # Прерываем цикл
        break

# Выводим заголовки книг с двумя звездами
print("Заголовки книг с 2-звездочным рейтингом:")
for title in books_With_Two_Stars:
    print(title)