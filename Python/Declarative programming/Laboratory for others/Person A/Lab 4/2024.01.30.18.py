from sympy import symbols, diff, log

x = symbols('x')
result = diff(log(x), x)
print(f"Производная для log(𝑥) по 𝑥: {result}")
