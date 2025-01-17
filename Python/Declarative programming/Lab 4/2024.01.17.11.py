# Calculate lim𝑥→0 sin(𝑥)⁄𝑥.

from sympy import symbols, sin, limit

x = symbols('x')
expression = sin(x) / x
limit_value = limit(expression, x, 0)

print(limit_value)