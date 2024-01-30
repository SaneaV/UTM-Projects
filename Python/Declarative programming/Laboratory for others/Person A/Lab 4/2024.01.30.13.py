from mpmath import mp

mp.dps = 100  # устанавливаем количество знаков после запятой
result = mp.sqrt(2)
print(f"Квадратный корень из 2 с 100 знаками после запятой: {result}")
