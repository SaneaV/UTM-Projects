# Choose a topic that interests you on wikipedia.org and complete the following tasks:
# capture page title;
# capture all section headings;
# get at least one image from that site

import requests
from os.path import basename

from bs4 import BeautifulSoup
from urllib.request import urlopen

url = "https://en.wikipedia.org/wiki/Lightning"
page = urlopen(url)
html = page.read().decode("utf-8")

soup = BeautifulSoup(html, "html.parser")
html_page = requests.get(url, headers={"User-Agent": "Mozilla/5.0"})

print("\nSite name: ", soup.find("title").get_text())

print(
    "\nAll topics: ",
)
topics = soup.find_all("span", {"class": "mw-headline"})
for topic in topics:
    print(topic.get_text())

print("\nImage: ", end="")
imageHtml = soup.find_all("img", {"class": "mw-file-element"})[1]
image = "https:" + imageHtml.get("src")

with open(basename(image), "wb") as f:
    f.write(requests.get(image).content)
print(
    "Downloaded!",
)
