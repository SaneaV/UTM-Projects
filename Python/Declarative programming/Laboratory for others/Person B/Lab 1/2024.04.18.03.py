# Определите два объекта типа float и вычислите их сумму и разность с помощью лямбда-функций.
# Последовательно вызовите все функции из списка и с другими типами данных, используя функцию map().

# Определение двух объектов типа float
number1 = 5.5
number2 = 3.0

# Определение лямбда-функции для вычисления суммы
sum_function = lambda x, y: x + y

# Определение лямбда-функции для вычисления разности
difference_function = lambda x, y: x - y

# Создание списка функций
functions = [sum_function, difference_function]

# Применение функций к объектам типа float
print("Результаты для чисел с плавающей точкой:")
for func in functions:
    result = list(map(func, [number1, number2], [number2, number1])) # Применение функции к парам значений
    print(result)

# Применение функций к другим типам данных
print("\nРезультаты для других типов данных:")
for func in functions:
    result = list(map(func, [1, 2], [3, 4]))  # Пример с целыми числами
    print(result)