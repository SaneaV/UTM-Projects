from functools import reduce

# Определение функции для подсчета количества вхождений символа в строку
def count_char_occurrences(string, char):
    # Используем lambda-функцию в reduce, чтобы сравнивать каждый символ с целевым символом и суммировать результаты
    return reduce(lambda count, ch: count + 1 if ch == char else count, string, 0)

# Пример строки и символа для подсчета
test_string = "hello world"
target_char = "o"

# Получение количества вхождений символа в строку
occurrences = count_char_occurrences(test_string, target_char)

# Вывод результата
print(f"Количество вхождений символа '{target_char}' в строку '{test_string}': {occurrences}")