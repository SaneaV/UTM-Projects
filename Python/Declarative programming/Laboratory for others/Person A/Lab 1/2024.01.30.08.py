# Определяем тестовую строку
текст_теста = "abcdef"

# Определяем список лямбда-функций
список_функций = [
    lambda s: s[1],  # Возвращает второй символ из строки
    lambda s: s.upper(),  # Возвращает строку в верхнем регистре
    lambda s, char: s.find(char)  # Возвращает позицию заданного символа в строке
]

# Вызываем каждую функцию из списка и выводим результаты
for индекс, функция in enumerate(список_функций, start=1):
    if индекс == 3:
        # Для вызова третьей функции, которая принимает второй аргумент, передаем значение "c"
        результат = функция(текст_теста, "c")
    else:
        результат = функция(текст_теста)

    print(f"Результат функции {индекс}: {результат}")
