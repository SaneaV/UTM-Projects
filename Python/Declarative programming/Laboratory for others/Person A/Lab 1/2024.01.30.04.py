# Определяем два объекта типа float
numar1 = 10.5
numar2 = 5.3

# Определяем lambda-функции для суммы и разности
сумма_функция = lambda x, y: x + y
разность_функция = lambda x, y: x - y if isinstance(x, (int, float)) and isinstance(y, (int, float)) else None

# Список lambda-функций
функции_lambda = [сумма_функция, разность_функция]

# Вызываем lambda-функции с использованием функции map() для разных типов данных
for функция in функции_lambda:
    # Вызываем lambda-функцию с объектами типа float
    результат_float = list(map(функция, [numar1, numar2], [numar2, numar1]))
    print(f"Результат для типа данных float: {результат_float}")

    # Вызываем lambda-функцию с объектами типа int
    numar1_int = int(numar1)
    numar2_int = int(numar2)
    результат_int = list(map(функция, [numar1_int, numar2_int], [numar2_int, numar1_int]))
    print(f"Результат для типа данных int: {результат_int}")

    # Вызываем lambda-функцию с объектами типа str
    numar1_str = str(numar1)
    numar2_str = str(numar2)
    результат_str = list(map(функция, [numar1_str, numar2_str], [numar2_str, numar1_str]))
    print(f"Результат для типа данных str: {результат_str}")

    # Добавляем пустую строку для разделения результатов
    print()
