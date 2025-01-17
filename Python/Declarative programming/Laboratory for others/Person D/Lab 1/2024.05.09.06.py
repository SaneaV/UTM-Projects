# Определение функции, которая конкатенирует произвольное количество строк
def concatenate_strings(*args):
    # Используем метод join() для конкатенации всех строк из списка args
    concatenated_string = ''.join(args)
    return concatenated_string

# Пример вызова функции с несколькими строками
result = concatenate_strings("привет!", ", ", "как дела?")
# Вывод результата
print("Результат конкатенации строк:", result)