import pandas as pd

def filter_by_price_range(input_file, min_price, max_price):
    # Считываем данные из CSV-файла в объект DataFrame
    df = pd.read_csv(input_file)

    # Преобразуем столбец 'Product Price' в числовой формат
    df['Product Price'] = df['Product Price'].str.replace(',', '.').astype(float)

    # Фильтруем DataFrame по заданному диапазону цен
    filtered_df = df[(df['Product Price'] >= min_price) & (df['Product Price'] <= max_price)]

    if not filtered_df.empty:
        # Выводим результаты, если записи найдены
        print(filtered_df)
    else:
        # Сообщаем, что таких кебабов нет
        print(f"Кебабы в ценовом диапазоне от {min_price} до {max_price} не найдены.")

# Пример использования
input_file = "merged_data.csv"
min_price = float(input("Введите минимальную цену: "))
max_price = float(input("Введите максимальную цену: "))
filter_by_price_range(input_file, min_price, max_price)