# Напишите последовательность кода, которая объединяет данные из трех файлов CSV 
# (полученных на предыдущей лабораторной работе) по базовой характеристике 
# (например, курс валюты от трех банков за один и тот же день).

import pandas as pd

# Функция для чтения данных из CSV файлов
def read_csv_files(files):
    # Список для хранения экземпляров DataFrame из каждого CSV файла
    dataframes = []

    # Чтение данных из каждого CSV файла и добавление их в список
    for file in files:
        df = pd.read_csv(file)
        dataframes.append(df)

    return dataframes
    
# Названия файлов CSV для трех банков
files = ['Lab 2/darwin_laptops.csv', 'Lab 2/enter_online_laptops.csv', 'Lab 2/ultra_laptops.csv']

# Считывание данных из CSV файлов
dataframes = read_csv_files(files)

# Объединение данных из трех файлов в один DataFrame
merged_data = pd.concat(dataframes, ignore_index=True)

# Группировка данных по общей характеристике
merged_data = merged_data.sort_values(by=['Название', 'Цена'])

merged_data.to_csv('Lab 3/merged_laptop_data.csv',  index=False)