import pandas as pd
import re

def create_category_files(input_file):
    # Считываем данные из CSV-файла в объект DataFrame
    df = pd.read_csv(input_file)

    # Задаем регулярные выражения для категорий pui, vita и arabic
    regex_pui = re.compile(r"\bpui\b", re.IGNORECASE)
    regex_vita = re.compile(r"\bvita\b", re.IGNORECASE)
    regex_arabic = re.compile(r"\barabic\b", re.IGNORECASE)

    # Фильтруем DataFrame на основе регулярных выражений
    kebabs_pui = df[df["Название продукта"].str.contains(regex_pui, regex=True)]
    kebabs_vita = df[df["Название продукта"].str.contains(regex_vita, regex=True)]
    kebabs_arabic = df[df["Название продукта"].str.contains(regex_arabic, regex=True)]

    # Создаем файлы для каждой категории
    kebabs_pui.to_csv("Lab 3/pui-kebabs.csv", index=False, encoding="utf-8")
    kebabs_vita.to_csv("Lab 3/vita-kebabs.csv", index=False, encoding="utf-8")
    kebabs_arabic.to_csv("Lab 3/arabic-kebabs.csv", index=False, encoding="utf-8")

    # Категория "прочее" (остальные)
    other_kebabs = df[~(df["Название продукта"].str.contains(regex_pui, regex=True) |
                        df["Название продукта"].str.contains(regex_vita, regex=True) |
                        df["Название продукта"].str.contains(regex_arabic, regex=True))]
    other_kebabs.to_csv("Lab 3/other-kebabs.csv", index=False, encoding="utf-8")

    print("Файлы созданы: Lab 3/pui-kebabs.csv, Lab 3/vita-kebabs.csv, Lab 3/arabic-kebabs.csv, Lab 3/other-kebabs.csv")

# Пример использования
input_file = "Lab 3/merged_data.csv"
create_category_files(input_file)
