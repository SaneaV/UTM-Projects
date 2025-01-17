import pandas as pd
import re

def create_roll_category_files(input_file):
    # Считываем данные из CSV-файла в объект DataFrame
    df = pd.read_csv(input_file)

    # Задаем регулярные выражения и соответствующие имена файлов
    regex_files = {
        r"\bCanada\b": "Canada-rolls.csv",
        r"\bPhiladelphia\b": "Philadelphia-rolls.csv",
        r"\bCalifornia\b": "California-rolls.csv"
    }

    # Создаем файлы для каждой категории
    for regex, filename in regex_files.items():
        rolls = df[df["Название продукта"].str.contains(regex, regex=True, na=False)]
        rolls.to_csv(filename, index=False, encoding="utf-8")

    # Фильтруем категорию "прочее" (остальные)
    other_rolls = df[~df["Название продукта"].str.contains("|".join(regex_files.keys()), regex=True, na=False)]
    other_rolls.to_csv("Other-rolls.csv", index=False, encoding="utf-8")

    print("Файлы созданы: Canada-rolls.csv, Philadelphia-rolls.csv, California-rolls.csv, Other-rolls.csv")

# Пример использования
input_file = "common.csv"
create_roll_category_files(input_file)
