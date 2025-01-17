import pandas as pd

def filter_by_price_range(input_file, min_price, max_price):
    # Считываем данные из CSV-файла в объект DataFrame
    df = pd.read_csv(input_file)

    # Преобразуем столбец 'Цена продукта' в числовой формат
    df['Цена продукта'] = df['Цена продукта'].str.replace(',', '.').astype(float)

    # Фильтруем DataFrame по заданному диапазону цен
    filtered_df = df[(df['Цена продукта'] >= min_price) & (df['Цена продукта'] <= max_price)]

    if not filtered_df.empty:
        # Выводим результаты, если записи найдены
        print(filtered_df)
    else:
        # Сообщаем, что таких кебабов нет
        print(f"Кебабы в ценовом диапазоне от {min_price} до {max_price} не найдены.")

# Пример использования
input_file = "Lab 3/merged_data.csv"
min_price = float(input("Введите минимальную цену: "))
max_price = float(input("Введите максимальную цену: "))
filter_by_price_range(input_file, min_price, max_price)