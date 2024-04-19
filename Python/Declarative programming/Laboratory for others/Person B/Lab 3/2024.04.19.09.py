import pandas as pd

def get_laptop_data(file_path, brand):
    # Чтение данных из CSV файла в DataFrame
    dataframe = pd.read_csv(file_path)
    
    # Фильтрация DataFrame для строк, которые содержат указанную марку ноутбука
    filtered_data = dataframe[dataframe['Название'].str.contains(brand)]
    
    # Проверка, содержит ли отфильтрованный DataFrame какие-либо строки
    if not filtered_data.empty:
        return filtered_data
    else:
        return None

# Пример использования:
# file_path - путь к CSV файлу с данными о ноутбуках
# brand - марка ноутбука, для которой мы хотим получить данные
# Например, мы можем вызвать функцию следующим образом:
laptop_data = get_laptop_data('Lab 3/merged_laptop_data.csv', 'Lenovo')
if laptop_data is not None:
    print(laptop_data.to_string(index=False))
else:
    print(None)
# Если марка 'Asus' найдена в DataFrame, laptop_data будет содержать соответствующие строки,
# в противном случае будет возвращено значение None.
