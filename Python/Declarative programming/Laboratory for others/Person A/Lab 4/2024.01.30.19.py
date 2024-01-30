from sympy import symbols, Eq, solve

x, y = symbols('x y')
eq1 = Eq(2*x + 3*y, 5)
eq2 = Eq(4*x - 3*y, -4)
result = solve((eq1, eq2), (x, y))
print(f"Решение системы уравнений: {result}")
