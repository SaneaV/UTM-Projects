import requests
from bs4 import BeautifulSoup
from urllib.parse import urljoin

# URL страницы Wikipedia об искусственном интеллекте
url_pagina = "https://ru.wikipedia.org/wiki/%D0%98%D1%81%D0%BA%D1%83%D1%81%D1%81%D1%82%D0%B2%D0%B5%D0%BD%D0%BD%D1%8B%D0%B9_%D0%B8%D0%BD%D1%82%D0%B5%D0%BB%D0%BB%D0%B5%D0%BA%D1%82"

# Загружаем содержимое страницы
response = requests.get(url_pagina)
soup = BeautifulSoup(response.text, 'html.parser')

# Захватываем заголовок страницы
titlu_pagina = soup.title.text
print(f"Заголовок страницы: {titlu_pagina}\n")

# Захватываем все заголовки разделов
titluri_sectiuni = [sectiune.text.strip() for sectiune in soup.find_all('span', {'class': 'mw-headline'})]
print("Заголовки разделов:")
for titlu_sectiune in titluri_sectiuni:
    print(titlu_sectiune)

# Загружаем изображение с страницы (первое найденное изображение)
tag_imagine = soup.find('img')
if tag_imagine:
    url_imagine = urljoin(url_pagina, tag_imagine['src'])
    response_imagine = requests.get(url_imagine)
    with open('image_wikipedia.jpg', 'wb') as file:
        file.write(response_imagine.content)
    print("\nИзображение успешно загружено!")
else:
    print("\nНа странице не найдено изображений.")
