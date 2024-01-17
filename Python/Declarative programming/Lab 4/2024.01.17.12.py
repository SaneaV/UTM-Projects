# Calculate the derivative of log(𝑥) for x

from sympy import symbols, log, diff

x = symbols('x')
expression = log(x)
derivative = diff(expression, x)

print(derivative)