import pandas as pd
import re

def filter_by_ingredient(input_file, ingredient):
    # Считываем данные из CSV-файла в объект DataFrame
    df = pd.read_csv(input_file)

    # Задаем регулярное выражение для поиска ингредиента в названии продукта
    regex_ingredient = re.compile(fr"\b{re.escape(ingredient)}\b", re.IGNORECASE)

    # Фильтруем DataFrame на основе регулярного выражения
    filtered_df = df[df["Название продукта"].str.contains(regex_ingredient, regex=True)]

    if not filtered_df.empty:
        # Выводим результаты, если записи найдены
        print(filtered_df)
    else:
        # Сообщаем, что такого кебаба нет
        print(f"Кебаб с ингредиентом '{ingredient}' не найден.")

# Пример использования
input_file = "Lab 3/merged_data.csv"
ingredient_to_find = input("Введите ингредиент кебаба: ")
filter_by_ingredient(input_file, ingredient_to_find)
