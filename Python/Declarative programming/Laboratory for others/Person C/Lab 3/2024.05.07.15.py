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
files = ['Lab 2/maximum_smartphones.csv', 'Lab 2/moldcell_smartphones.csv', 'Lab 2/prime-pc_smartphones.csv']

# Считывание данных из CSV файлов
dataframes = read_csv_files(files)

# Объединение данных из трех файлов в один DataFrame
merged_data = pd.concat(dataframes, ignore_index=True)

# Группировка данных по общей характеристике
merged_data = merged_data.sort_values(by=['Название', 'Цена'])

merged_data.to_csv('Lab 3/merged_smartphone_data.csv',  index=False)