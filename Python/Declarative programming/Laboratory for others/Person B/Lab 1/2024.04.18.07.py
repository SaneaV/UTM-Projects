# Определение списка лямбда-функций, которые возвращают: 
# второй символ из строки; 
# строку в верхнем регистре; 
# позицию, на которой находится заданный символ на входе. 
# Вызов всех функций из списка поочередно.

# Определение списка лямбда-функций
lambda_functions = [
    lambda s: s[1],                                       # Второй символ из строки
    lambda s: s.upper(),                                  # Строка в верхнем регистре
    lambda s, char: s.find(char) if char in s else -1     # Позиция символа в строке
]

# Заданная строка
input_string = "hello world"
# Заданный символ
char_to_find = "o"

# Вызов всех функций из списка
for func in lambda_functions:
    if func.__code__.co_argcount == 1:  # Проверка на количество аргументов функции
        result = func(input_string)
    else:
        result = func(input_string, char_to_find)
    print(result)
