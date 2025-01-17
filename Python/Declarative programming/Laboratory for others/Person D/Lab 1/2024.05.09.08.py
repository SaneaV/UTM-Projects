# Определение функции для расчета среднего значения списка
def calculate_average(grade1=4, grade2=4, grade3=4):
    # Расчет среднего значения
    average = (grade1 + grade2 + grade3) / 3
    return average

# Пример вызова функции с различными комбинациями аргументов
print("Среднее значение 1, 2, 3:", calculate_average(1, 2, 3))
print("Среднее значение 4, 5, 6:", calculate_average(4, 5, 6))
print("Среднее значение 1, 4, 4:", calculate_average(1, grade2=4))
print("Среднее значение по умолчанию:", calculate_average())