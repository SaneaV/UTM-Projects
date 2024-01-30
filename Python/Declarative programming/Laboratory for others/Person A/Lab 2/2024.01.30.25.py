import requests
from bs4 import BeautifulSoup
import csv
import re

# URL of the webpage
url = "https://glovoapp.com/md/ro/chisinau/magic-kebab-ksn?content=meniu-c.1555016353&section=kebab-s.3285254610"

# Send a GET request to the URL
response = requests.get(url)

# Check if the request was successful (status code 200)
if response.status_code == 200:
    # Parse the HTML content using BeautifulSoup
    soup = BeautifulSoup(response.text, "html.parser")
    soup = soup.find("div", id="kebab-s.3285254610")

    # Extract information based on the HTML structure
    products = []

    list_title_element = soup.find("p", class_="list__title")
    if list_title_element:
        list_title = list_title_element.text.strip()

        # Find all product elements
        product_elements = soup.find_all("div", class_="product-row")
        for product_element in product_elements:
            product_name_element = product_element.find(
                "span", {"data-test-id": "product-row-name__highlighter"}
            )
            product_bottom_element = product_element.find(
                "div", class_="product-row__bottom"
            )
            product_price_element = product_bottom_element.find(
                "span", {"data-test-id": "product-price-effective"}
            )

            if product_name_element:
                product_name = product_name_element.text.strip()

                product_price = None
                if product_price_element:
                    # Use regular expression to extract numeric part
                    match = re.search(r"\d+,\d+", product_price_element.text)
                    if match:
                        product_price = match.group()

                products.append(
                    {
                        "List Title": list_title,
                        "Product Name": product_name,
                        "Product Price": product_price,
                    }
                )

    # Write the information to a CSV file
    csv_filename = "Magic Kebab.csv"
    with open(csv_filename, "w", newline="", encoding="utf-8") as csvfile:
        fieldnames = ["List Title", "Product Name", "Product Price"]
        writer = csv.DictWriter(csvfile, fieldnames=fieldnames)

        # Write header
        writer.writeheader()

        # Write data
        for product in products:
            writer.writerow(product)

    print(f"Information written to {csv_filename}")
else:
    print("Failed to retrieve the webpage. Status code:", response.status_code)
