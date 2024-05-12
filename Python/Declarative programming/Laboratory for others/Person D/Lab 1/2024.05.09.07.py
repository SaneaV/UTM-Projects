# Определение списка lambda-функций
lambda_functions = [
    lambda s: s[1],                    # Второй символ из строки
    lambda s: s.upper(),               # Строка с прописными буквами
    lambda s, char: s.find(char)      # Позиция, на которой находится заданный символ в строке
]

# Пример строки и символа
test_string = "привет"
target_char = "е"

# Вызов каждой функции из списка
for func in lambda_functions:
    result = func(test_string, target_char) if len(func.__code__.co_varnames) == 2 else func(test_string)
    print("Результат выполнения функции:", result)