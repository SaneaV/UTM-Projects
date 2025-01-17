# Make requests to at least 3 sites to get the desired information
# (your choice: weather data, exchange rate, product price, hotel rating, restaurants, etc.).
# As a result you should have 3 .csv files with similar data,
# e.g. weather data for the month of October from 3 weather forecast sites, exchange rate for a month from 3 different banks,
# rating of a list of hostels from 3 different sites, etc.

import requests
from bs4 import BeautifulSoup
from datetime import datetime, timedelta
import csv
import logging

logging.basicConfig(level=logging.INFO)

url = "https://www.curs.md/ru/curs_valutar_banci"


def format_date(date):
    return date.strftime("%Y-%m-%d")


def scrape_bank_data(bank, current_date):
    payload = {"CotDate": format_date(current_date), "ofType": "all"}
    headers = {"Cookie": "appVersion=v2"}

    response = requests.post(url, headers=headers, data=payload)
    soup = BeautifulSoup(response.text, "html.parser")

    bank_element = soup.find("tr", {"class": bank})
    if bank_element:
        return extract_data_from_bank_element(bank_element, bank, current_date)
    else:
        logging.warning(
            f"{current_date.date()} - bank {bank.strip().upper()} not found on the page."
        )
        return []


def extract_data_from_bank_element(bank_element, bank, current_date):
    data = []
    currencies = ["USD", "EUR", "RUB", "RON", "UAH", "GBP", "CHF", "TRY", "CAD"]
    open_currencies = ["USD", "EUR", "RUB", "RON"]

    for currency in currencies:
        rate_buy_class = (
            f"column-{currency} column-ind-{currencies.index(currency)}"
            if currency in open_currencies
            else f"column-{currency} column-ind-hidden"
        )
        rate_buy = bank_element.find_next("td", {"class": rate_buy_class})

        if rate_buy:
            rate_sell = rate_buy.find_next("td")
            if (
                rate_sell
                and rate_buy.text.strip() != "-"
                and rate_sell.text.strip() != "-"
            ):
                data.append(
                    [
                        format_date(current_date),
                        bank.strip().upper(),
                        currency,
                        rate_buy.text.strip(),
                        rate_sell.text.strip(),
                    ]
                )
            else:
                logging.warning(
                    f"{bank.strip().upper()} - {currency} rates not available for {format_date(current_date)}."
                )

    return data


def main():
    start_date = datetime(2023, 12, 1)
    end_date = datetime(2023, 12, 31)
    delta = timedelta(days=1)

    banks = [
        "maib",
        "micb",
        "victoriabank",
        "bcr",
        "comertbank",
        "energbank",
        "ecb",
        "eximbank",
        "fincombank",
        "mobiasbanca",
    ]

    for bank in banks:
        with open(f"currency_data_{bank}.csv", "w", newline="") as csvfile:
            csv_writer = csv.writer(csvfile)
            csv_writer.writerow(["Date", "Bank", "Currency", "Buy Rate", "Sell Rate"])

            current_date = start_date
            while current_date <= end_date:
                try:
                    data = scrape_bank_data(bank, current_date)
                    csv_writer.writerows(data)
                except Exception as e:
                    logging.error(f"Error processing {bank.strip().upper()} - {e}")

                current_date += delta

        logging.info(f"Data has been successfully written to currency_data_{bank}.csv.")


if __name__ == "__main__":
    main()
