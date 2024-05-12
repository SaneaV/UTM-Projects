import pandas as pd

# Список путей к CSV-файлам
file_paths = ["../Lab 2/Nakiri Sushi.csv", "../Lab 2/Nori Sushi.csv", "../Lab 2/Origami Sushi.csv"]

# Чтение данных из всех CSV-файлов и объединение их
merged_df = pd.concat([pd.read_csv(file) for file in file_paths], ignore_index=True)

# Запись объединенного фрейма данных в новый CSV-файл
merged_df.to_csv("common.csv", index=False)
