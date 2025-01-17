# Solve the system of equations 2𝑥 + 3𝑦 = 5, 4𝑥 − 3𝑦 = −4

from sympy import symbols, Eq, solve

x, y = symbols('x y')

eq1 = Eq(2*x + 3*y, 5)
eq2 = Eq(4*x - 3*y, -4)

solution = solve((eq1, eq2), (x, y))

print("Solution:")
print(f"x = {solution[x]}")
print(f"y = {solution[y]}")