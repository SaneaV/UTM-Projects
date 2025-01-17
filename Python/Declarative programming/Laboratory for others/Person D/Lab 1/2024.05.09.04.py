import collections

# Определение списка целочисленных значений
values = [1, 2, 3, 4, 5, 1, 2, 3, 6, 7, 8, 9, 9]

# Подсчет количества каждого элемента в списке
counter = collections.Counter(values)

# Фильтрация списка, оставляя только уникальные значения
distinct_values = [value for value, count in counter.items() if count == 1]

# Вывод уникальных значений
print("Уникальные значения из списка:")
for value in distinct_values:
    print(value)