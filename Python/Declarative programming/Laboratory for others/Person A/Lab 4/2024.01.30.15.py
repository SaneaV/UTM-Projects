from sympy import symbols, expand

x, y = symbols('x y')
result = expand((x + y)**6)
print(f"Расширенная форма выражения (𝑥 + 𝑦)^6: {result}")
