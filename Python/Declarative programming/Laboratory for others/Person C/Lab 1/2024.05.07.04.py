# Определение списка целочисленных значений
values = [1, 2, 3, 4, 5, 1, 2, 3, 6, 7, 8, 9, 9]

# Функция для фильтрации уникальных значений
def is_unique(value):
    return values.count(value) == 1

# Фильтрация списка, оставляя только уникальные значения
distinct_values = list(filter(is_unique, values))

# Вывод уникальных значений
print("Уникальные значения из списка:")
for value in distinct_values:
    print(value)