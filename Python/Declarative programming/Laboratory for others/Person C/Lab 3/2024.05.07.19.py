import pandas as pd

def get_smarthphone_data(file_path, brand):
    # Чтение данных из CSV файла в DataFrame
    dataframe = pd.read_csv(file_path)
    
    # Фильтрация DataFrame для строк, которые содержат указанную марку смартфона
    filtered_data = dataframe[dataframe['Название'].str.contains(brand)]
    
    # Проверка, содержит ли отфильтрованный DataFrame какие-либо строки
    if not filtered_data.empty:
        return filtered_data
    else:
        return None

# Пример использования:
# file_path - путь к CSV файлу с данными о смартфона
# brand - марка смартфона, для которой мы хотим получить данные
# Например, мы можем вызвать функцию следующим образом:
smarthphone_data = get_smarthphone_data('Lab 3/merged_smartphone_data.csv', 'Apple')
if smarthphone_data is not None:
    print(smarthphone_data.to_string(index=False))
else:
    print(None)
# Если марка 'Apple' найдена в DataFrame, smartphone_data будет содержать соответствующие строки,
# в противном случае будет возвращено значение None.