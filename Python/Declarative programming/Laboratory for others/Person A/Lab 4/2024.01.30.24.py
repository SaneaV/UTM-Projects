from scipy.optimize import fmin
import numpy as np

# Определение функции для оптимизации
def objective_function(x):
    return np.cos(x) - 3 * np.exp(-(x - 0.2)**2)

# Вызов функции оптимизации для x0 = 1.0
x_min_1 = fmin(objective_function, 1.0, disp=False)

# Вывод результата
print(f"Минимальное значение для x0 = 1.0: {x_min_1}")

# Вызов функции оптимизации для x0 = 2.0
x_min_2 = fmin(objective_function, 2.0, disp=False)

# Вывод результата
print(f"Минимальное значение для x0 = 2.0: {x_min_2}")
