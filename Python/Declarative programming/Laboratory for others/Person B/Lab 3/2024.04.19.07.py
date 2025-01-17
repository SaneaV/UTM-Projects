# Определите минимум, максимум и среднее значение для значений, 
# сгруппированных по каждой отдельной дате, а также минимум, максимум и среднее значение для всех хранимых данных.

import pandas as pd

# Чтение данных из CSV файла
df = pd.read_csv('Lab 3/merged_laptop_data.csv')

# Создание словаря для хранения цен ноутбуков каждой марки
prices_by_brand = {'Apple': [], 'Asus': [], 'Lenovo': []}

# Перебор строк данных и анализ цен ноутбуков каждой марки
for index, row in df.iterrows():
    if 'Apple' in row['Название']:
        prices_by_brand['Apple'].append(float(row['Цена']))
    elif 'Asus' in row['Название']:
        prices_by_brand['Asus'].append(float(row['Цена']))
    elif 'Lenovo' in row['Название']:
        prices_by_brand['Lenovo'].append(float(row['Цена']))

# Нахождение минимума, максимума и средней цены для каждой марки
for brand, prices in prices_by_brand.items():
    if prices:
        min_price = min(prices)
        max_price = max(prices)
        avg_price = round(sum(prices) / len(prices))
        print(f"Марка: {brand}")
        print(f"Минимальная цена: {min_price}")
        print(f"Максимальная цена: {max_price}")
        print(f"Средняя цена: {avg_price}\n")
    else:
        print(f"Нет данных о ценах для марки {brand}")

# Нахождение минимума, максимума и средней цены для всех ноутбуков
all_prices = [float(price) for price in df['Цена']]
min_price_all = min(all_prices)
max_price_all = max(all_prices)
avg_price_all = round(sum(all_prices) / len(all_prices))

print("Для всех ноутбуков:")
print(f"Минимальная цена: {min_price_all}")
print(f"Максимальная цена: {max_price_all}")
print(f"Средняя цена: {avg_price_all}")
