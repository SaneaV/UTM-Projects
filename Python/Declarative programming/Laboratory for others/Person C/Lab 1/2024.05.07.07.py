# Определение списка lambda-функций
lambda_functions = [
    lambda s: s[1],                                      # Второй символ из строки
    lambda s: s.upper(),                                 # Строка с прописными буквами
    lambda s, char: s.find(char) if char in s else -1    # Позиция, на которой находится заданный символ в строке
]

# Пример строки и символа
test_string = "hello"
target_char = "e"

# Вызов каждой функции из списка
for func in lambda_functions:
    # Если функция требует два аргумента, передаем оба, иначе передаем только один
    if func.__code__.co_argcount == 2:
        result = func(test_string, target_char)
    else:
        result = func(test_string)
    print("Результат выполнения функции:", result)