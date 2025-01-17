from sympy import symbols, limit, sin

x = symbols('x')
result = limit(sin(x)/x, x, 0)
print(f"Предел lim𝑥→0 sin(𝑥)/𝑥: {result}")
