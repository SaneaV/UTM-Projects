# Определите функцию, которая возвращает количество вхождений символа в строку (используйте функцию reduce()).

from functools import reduce

# Определение функции, которая возвращает количество вхождений символа в строку
def count_occurrences(char, string):
    # Использование функции reduce() для подсчета вхождений символа в строку
    occurrences = reduce(lambda count, c: count + 1 if c == char else count, string, 0)
    return occurrences

# Пример использования функции
# Заданная строка
input_string = "hello world"
# Символ для подсчета вхождений
character = "o"
# Вызов функции count_occurrences() для подсчета вхождений символа в строку
result = count_occurrences(character, input_string)
# Вывод результата
print(f"Количество вхождений символа '{character}' в строку: {result}")