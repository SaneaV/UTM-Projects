import pandas as pd

def filter_by_price_range(input_file, min_price, max_price):
    try:
        # Считываем данные из CSV-файла в объект DataFrame
        df = pd.read_csv(input_file)

        # Преобразуем столбец 'Цена продукта' в числовой формат
        df['Цена продукта'] = df['Цена продукта'].str.replace(',', '.').astype(float)

        # Фильтруем DataFrame по заданному диапазону цен
        filtered_df = df[(df['Цена продукта'] >= min_price) & (df['Цена продукта'] <= max_price)]

        if not filtered_df.empty:
            # Выводим результаты, если записи найдены
            print(filtered_df)
        else:
            # Сообщаем, что таких роллов нет
            print(f"Роллы в ценовом диапазоне от {min_price} до {max_price} не найдены.")
    except FileNotFoundError:
        print("Указанный файл не найден.")
    except pd.errors.EmptyDataError:
        print("Файл пуст.")
    except ValueError:
        print("Некорректный формат данных в столбце 'Цена продукта'. Проверьте, что цены указаны в числовом формате.")

# Пример использования
input_file = "common.csv"
min_price = float(input("Введите минимальную цену: ").replace(',', '.'))
max_price = float(input("Введите максимальную цену: ").replace(',', '.'))
filter_by_price_range(input_file, min_price, max_price)
