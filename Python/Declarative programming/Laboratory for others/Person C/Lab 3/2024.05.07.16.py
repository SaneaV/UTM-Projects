import pandas as pd

# Чтение данных из CSV файла
df = pd.read_csv('Lab 3/merged_smartphone_data.csv')

# Создание словаря для хранения цен смартфонов каждой марки
prices_by_brand = {'Apple': [], 'OPPO': [], 'OnePlus': [], 'Samsung': [], 'Doogee': [], 'Huawei': [], 'Realme': [], 'Xiaomi': []}

# Перебор строк данных и анализ цен смартфонов каждой марки
for index, row in df.iterrows():
    if 'Apple' in row['Название']:
        prices_by_brand['Apple'].append(float(row['Цена']))
    elif 'OPPO' in row['Название']:
        prices_by_brand['OPPO'].append(float(row['Цена']))
    elif 'OnePlus' in row['Название']:
        prices_by_brand['OnePlus'].append(float(row['Цена']))
    elif 'Samsung' in row['Название']:
        prices_by_brand['Samsung'].append(float(row['Цена']))
    elif 'Doogee' in row['Название']:
        prices_by_brand['Doogee'].append(float(row['Цена']))
    elif 'Huawei' in row['Название']:
        prices_by_brand['Huawei'].append(float(row['Цена']))
    elif 'Realme' in row['Название']:
        prices_by_brand['Realme'].append(float(row['Цена']))
    elif 'Xiaomi' in row['Название']:
        prices_by_brand['Xiaomi'].append(float(row['Цена']))

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

# Нахождение минимума, максимума и средней цены для всех смартфонов
all_prices = [float(price) for price in df['Цена']]
min_price_all = min(all_prices)
max_price_all = max(all_prices)
avg_price_all = round(sum(all_prices) / len(all_prices))

print("Для всех смартфонов:")
print(f"Минимальная цена: {min_price_all}")
print(f"Максимальная цена: {max_price_all}")
print(f"Средняя цена: {avg_price_all}")