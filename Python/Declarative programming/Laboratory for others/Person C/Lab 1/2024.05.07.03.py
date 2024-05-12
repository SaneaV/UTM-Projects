# Определение двух вещественных чисел
float1 = 10.5
float2 = 5.3

# Определение lambda-функций для суммы и разности
sum_func = lambda x, y: x + y
diff_func = lambda x, y: x - y

# Создание списка lambda-функций
functions = [sum_func, diff_func]

# Вызов lambda-функций с использованием map() и других типов данных
other_data = [15, 7.5]  # Пример других чисел

# Вызов функций для вещественных чисел
results_float = list(map(lambda func: func(float1, float2), functions))

# Вызов функций для других типов данных
results_other = list(map(lambda x: list(map(lambda func: func(x, float2), functions)), other_data))

print("Результаты для вещественных чисел:")
print("Сумма:", results_float[0])  # Вывод суммы
print("Разность:", results_float[1])  # Вывод разности
print("\nРезультаты для других типов данных:")
for idx, data in enumerate(other_data):
    print(f"Для данных {data}:")  # Вывод данных
    print("Сумма:", results_other[idx][0])  # Вывод суммы для других данных
    print("Разность:", results_other[idx][1])  # Вывод разности для других данных