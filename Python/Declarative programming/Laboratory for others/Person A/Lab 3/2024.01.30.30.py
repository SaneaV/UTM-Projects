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
    kebabs_pui = df[df["Product Name"].str.contains(regex_pui, regex=True)]
    kebabs_vita = df[df["Product Name"].str.contains(regex_vita, regex=True)]
    kebabs_arabic = df[df["Product Name"].str.contains(regex_arabic, regex=True)]

    # Создаем файлы для каждой категории
    kebabs_pui.to_csv("pui-kebabs.csv", index=False, encoding="utf-8")
    kebabs_vita.to_csv("vita-kebabs.csv", index=False, encoding="utf-8")
    kebabs_arabic.to_csv("arabic-kebabs.csv", index=False, encoding="utf-8")

    # Категория "прочее" (остальные)
    other_kebabs = df[~(df["Product Name"].str.contains(regex_pui, regex=True) |
                        df["Product Name"].str.contains(regex_vita, regex=True) |
                        df["Product Name"].str.contains(regex_arabic, regex=True))]
    other_kebabs.to_csv("other-kebabs.csv", index=False, encoding="utf-8")

    print("Файлы созданы: pui-kebabs.csv, vita-kebabs.csv, arabic-kebabs.csv, other-kebabs.csv")

# Пример использования
input_file = "merged_data.csv"
create_category_files(input_file)
