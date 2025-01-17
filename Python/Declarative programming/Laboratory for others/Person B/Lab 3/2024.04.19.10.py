import pandas as pd

def display_data_for_price_range(file_path, min_price, max_price):
    # Чтение данных из CSV файла в DataFrame
    dataframe = pd.read_csv(file_path)
    
    # Фильтрация данных для указанного диапазона цен
    filtered_data = dataframe[(dataframe['Цена'] >= min_price) & (dataframe['Цена'] <= max_price)]

    # Проверка, содержит ли отфильтрованный DataFrame какие-либо строки
    if not filtered_data.empty:
        return filtered_data
    else:
        return None

# Пример использования:
# file_path - путь к CSV файлу с данными о цене
# min_price - минимальная цена в диапазоне
# max_price - максимальная цена в диапазоне
# Например, мы можем вызвать функцию следующим образом:
result = display_data_for_price_range('Lab 3/merged_laptop_data.csv', 1000, 10000)
if result is not None:
    print(result.to_string(index=False))
else:
    print (None)
# Это вернет все строки с ценами от 1000 до 2000 из файла 'merged_laptop_data.csv'
