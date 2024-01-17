# Simplify the trigonometric expression sin(𝑥)⁄cos(𝑥)

from sympy import symbols, sin, cos, simplify

x = symbols('x')
trig_expression = sin(x) / cos(x)
simplified_expression = simplify(trig_expression)

print(simplified_expression)