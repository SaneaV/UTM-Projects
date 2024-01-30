from sympy import symbols, sin, cos, simplify

x = symbols('x')
expression = sin(x) / cos(x)
result = simplify(expression)
print(f"Упрощенное тригонометрическое выражение: {result}")
