# Определение рекурсивной функции для вычисления суммы первых N натуральных чисел
def sum_of_natural_numbers(n):
    return 1 if n == 1 else n + sum_of_natural_numbers(n - 1)

# Пример вызова функции с числом N
N = 100
result = sum_of_natural_numbers(N)
print(f"Сумма первых {N} натуральных чисел:", result)