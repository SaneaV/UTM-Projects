import pandas as pd
import re

# Считываем данные из CSV-файла в объект DataFrame
df = pd.read_csv("common.csv")

# Задаем регулярные выражения для категорий "Канада", "Филадельфия" и "Калифорния"
regex_patterns = {
    "Ролл Канада": re.compile(r"\bCanada\b", re.IGNORECASE),
    "Ролл Филадельфия": re.compile(r"\bPhiladelphia\b", re.IGNORECASE),
    "Ролл Калифорния": re.compile(r"\bCalifornia\b", re.IGNORECASE)
}

# Создаем пустой словарь для результатов
results = {}

# Проходимся по каждой категории и фильтруем DataFrame, находим статистику и сохраняем результаты
for category, regex_pattern in regex_patterns.items():
    rolls = df[df["Название продукта"].str.contains(regex_pattern, regex=True)]
    prices = rolls["Цена продукта"].str.replace(',', '.').astype(float)
    results[category] = {
        "Минимальная цена": prices.min(),
        "Максимальная цена": prices.max(),
        "Средняя цена": prices.mean()
    }

# Создаем DataFrame с результатами
results_df = pd.DataFrame(results).T

# Записываем DataFrame с результатами в новый CSV-файл
results_df.to_csv("roll_prices.csv", encoding="utf-8")

print("Результаты записаны в roll_prices.csv")