# Упростите тригонометрическое выражение sin(x)/cos(x).

# Импорт модуля sympy
import sympy as sp

# Определение символа x
x = sp.symbols('x')

# Определение выражения sin(x)/cos(x)
expr = sp.sin(x) / sp.cos(x)

# Упрощение выражения
simplified_expr = sp.simplify(expr)

# Вывод упрощенного выражения
print("Упрощенное выражение:", simplified_expr)