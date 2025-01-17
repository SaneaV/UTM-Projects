# Определение функции для подсчета количества вхождений символа в строку
def count_char_occurrences(string, char):
    # Суммируем 1 для каждого символа, равного целевому символу
    return sum(1 for ch in string if ch == char)

# Пример строки и символа для подсчета
test_string = "hello world"
target_char = "o"

# Получение количества вхождений символа в строку
occurrences = count_char_occurrences(test_string, target_char)

# Вывод результата
print(f"Количество вхождений символа '{target_char}' в строку '{test_string}': {occurrences}")