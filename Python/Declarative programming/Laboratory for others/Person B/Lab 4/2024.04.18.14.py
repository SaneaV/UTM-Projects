# Вычисление lim(x->0) sin(x)/x.

# Импорт модуля sympy
import sympy as sp

# Определение символа x
x = sp.symbols('x')

# Определение выражения sin(x)/x
expr = sp.sin(x) / x

# Вычисление предела выражения при x стремящемся к 0
limit_value = sp.limit(expr, x, 0)

# Вывод результата
print("Предел выражения при x стремящемся к 0:", limit_value)