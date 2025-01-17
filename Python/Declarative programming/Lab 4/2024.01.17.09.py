# Calculate the expanded form of the expression (𝑥 + 𝑦)^6

from sympy import symbols, expand

x, y = symbols('x y')
expression = (x + y)**6
expanded_form = expand(expression)

print(expanded_form)