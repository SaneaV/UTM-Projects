# Go to the website: http://books.toscrape.com/index.html which is specially designed for web scraping testing.
# Get the title of each book that has a 2 star rating and at the end just have a Python list of all their titles.
# find the URL structure to traverse each page;
# parse each page in the catalog;
# find what label/class represents the star rating ;
# filter with if star rating;
# store the results in a list.

import requests
from bs4 import BeautifulSoup
from urllib.request import urlopen

page_Number = 1
books_With_Two_Stars = list()

while True:
    url = "https://books.toscrape.com/catalogue/page-{0}.html".format(page_Number)
    response = requests.get(url, headers={"User-Agent": "Mozilla/5.0"})

    if response.status_code == 200:
        page = urlopen(url)
        html = page.read().decode("utf-8")
        soup = BeautifulSoup(html, "html.parser")

        books = soup.find_all("p", {"class": "star-rating Two"})

        for book in books:
            parent_element = book.find_parent("article")
            if parent_element:
                books_With_Two_Stars.append(parent_element)

        print("Analyzing page:", page_Number)
        page_Number += 1
    else:
        print("End analysis on page:", page_Number)
        break

for book_element in books_With_Two_Stars:
    title = book_element.find("h3").find("a").get("title")
    image_url = book_element.find("img").get("src")
    star_rating = book_element.find("p", class_="star-rating").get("class")[1]
    price = book_element.find("p", class_="price_color").text.strip()
    availability = book_element.find("p", class_="instock availability").text.strip()

    # Printing or storing the extracted information
    print("Title:", title)
    print("Image URL:", image_url)
    print("Star Rating:", star_rating)
    print("Price:", price)
    print("Availability:", availability)
    print("\n-----------------------------\n")
