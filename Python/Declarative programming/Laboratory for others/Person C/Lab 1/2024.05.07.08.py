# Определение функции для расчета среднего значения трех оценок
def calculate_average(grade1=4, grade2=4, grade3=4):
    # Расчет среднего значения
    average = (grade1 + grade2 + grade3) / 3
    return average

# Пример вызова функции с различными комбинациями аргументов
print("Среднее значение оценок 5, 4, 3:", calculate_average(5, 4, 3))
print("Среднее значение оценок 5, 4, 4:", calculate_average(5, 4))
print("Среднее значение оценок 5, 4, 4:", calculate_average(5, grade2=4))
print("Среднее значение оценок по умолчанию:", calculate_average())