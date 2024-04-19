# Вычисление производной для log(x) по x.

# Импорт модуля sympy
import sympy as sp

# Определение символа x
x = sp.symbols('x')

# Определение выражения log(x)
expr = sp.log(x)

# Вычисление производной выражения по x
derivative = sp.diff(expr, x)

# Вывод результата
print("Производная выражения log(x) по x:", derivative)