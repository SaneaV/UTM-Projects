import pandas as pd
import re

# Считываем данные из CSV-файла в объект DataFrame
df = pd.read_csv("Lab 3/merged_data.csv")

# Задаем регулярные выражения для категорий pui, vita и arabic
regex_pui = re.compile(r"\bpui\b", re.IGNORECASE)
regex_vita = re.compile(r"\bvita\b", re.IGNORECASE)
regex_arabic = re.compile(r"\barabic\b", re.IGNORECASE)

# Фильтруем DataFrame на основе регулярных выражений
kebabs_pui = df[df["Название продукта"].str.contains(regex_pui, regex=True)]
kebabs_vita = df[df["Название продукта"].str.contains(regex_vita, regex=True)]
kebabs_arabic = df[df["Название продукта"].str.contains(regex_arabic, regex=True)]

# Находим минимальные и максимальные цены для каждой категории
min_price_pui = kebabs_pui["Цена продукта"].apply(lambda x: float(x.replace(',', '.'))).min()
max_price_pui = kebabs_pui["Цена продукта"].apply(lambda x: float(x.replace(',', '.'))).max()
avg_price_pui = kebabs_pui["Цена продукта"].apply(lambda x: float(x.replace(',', '.'))).mean()

min_price_vita = kebabs_vita["Цена продукта"].apply(lambda x: float(x.replace(',', '.'))).min()
max_price_vita = kebabs_vita["Цена продукта"].apply(lambda x: float(x.replace(',', '.'))).max()
avg_price_vita = kebabs_vita["Цена продукта"].apply(lambda x: float(x.replace(',', '.'))).mean()

min_price_arabic = kebabs_arabic["Цена продукта"].apply(lambda x: float(x.replace(',', '.'))).min()
max_price_arabic = kebabs_arabic["Цена продукта"].apply(lambda x: float(x.replace(',', '.'))).max()
avg_price_arabic = kebabs_arabic["Цена продукта"].apply(lambda x: float(x.replace(',', '.'))).mean()

# Создаем DataFrame с результатами
results_df = pd.DataFrame({
    "Категория": ["Кебаб с курицей", "Кебаб с говядиной", "Арабский кебаб"],
    "Минимальная цена": [min_price_pui, min_price_vita, min_price_arabic],
    "Максимальная цена": [max_price_pui, max_price_vita, max_price_arabic],
    "Средняя цена": [avg_price_pui, avg_price_vita, avg_price_arabic]
})

# Записываем DataFrame с результатами в новый CSV-файл
results_df.to_csv("Lab 3/kebab_prices.csv", index=False, encoding="utf-8")

print("Результаты записаны в kebab_prices.csv")
