# Напишите код, который разделит объединенный CSV файл на несколько файлов, 
# где каждый файл будет содержать строки, соответствующие одной из трех марок ноутбуков: 
# Asus, Lenovo и Apple. Каждый файл должен называться просто именем марки ноутбука.

import pandas as pd

def split_csv_by_brand(csv_file):
    # Чтение данных из CSV файла
    data = pd.read_csv(csv_file)
    
    # Итерация по уникальным брендам
    for brand in ['Asus', 'Lenovo', 'Apple']:
        # Фильтрация данных по текущему бренду
        brand_data = data[data['Название'].str.contains(brand)]
        
        # Создание имени CSV файла для текущего бренда
        file_name = f"Lab 3/{brand}.csv"
        
        # Сохранение данных в соответствующем CSV файле
        brand_data.to_csv(file_name, index=False)

# Имя объединенного CSV файла
concatenated_csv = 'Lab 3/merged_laptop_data.csv'

# Разделение CSV файла по брендам
split_csv_by_brand(concatenated_csv)
