# Вычисление расширенной формы выражения (x + y)^6.

from sympy import Symbol, expand

# Определение переменных x и y
x = Symbol('x')
y = Symbol('y')

# Вычисление расширенной формы выражения (x + y)^6 с помощью sympy
expanded_form = expand((x + y)**6)

# Вывод результата
print("Расширенная форма выражения (x + y)^6:", expanded_form)
