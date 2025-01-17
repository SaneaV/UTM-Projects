import pandas as pd

# Считывание данных из трех CSV-файлов
df1 = pd.read_csv("Lab 2/Fast Kebab.csv")
df2 = pd.read_csv("Lab 2/Magic Kebab.csv")
df3 = pd.read_csv("Lab 2/MML Food.csv")

# Объединение фреймов данных на основе столбца "Product Name"
merged_df = pd.concat([df1, df2, df3], ignore_index=True)

# Запись объединенного фрейма данных в новый CSV-файл
merged_df.to_csv("Lab 3/merged_data.csv", index=False)

# Вывод объединенного фрейма данных
print(merged_df)
