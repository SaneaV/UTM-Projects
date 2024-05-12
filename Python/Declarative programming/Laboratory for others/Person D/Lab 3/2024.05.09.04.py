import pandas as pd
import re

def filter_rolls_by_ingredient(input_file, ingredient):
    # Считываем данные из CSV-файла в объект DataFrame
    df = pd.read_csv(input_file)

    # Фильтруем DataFrame на основе вхождения ингредиента в название продукта
    filtered_rolls = df[df["Название продукта"].str.contains(fr"\b{re.escape(ingredient)}\b", case=False)]

    if not filtered_rolls.empty:
        # Выводим результаты, если записи найдены
        print(filtered_rolls)
    else:
        # Сообщаем, что роллов с таким ингредиентом нет
        print(f"Роллы с ингредиентом '{ingredient}' не найдены.")

# Пример использования
input_file = "common.csv"
ingredient_to_find = input("Введите ингредиент роллов: ")
filter_rolls_by_ingredient(input_file, ingredient_to_find)
