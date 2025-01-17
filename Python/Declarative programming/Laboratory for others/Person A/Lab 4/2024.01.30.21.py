from sympy import symbols, dsolve, Function

x = symbols('x')
f = Function('f')
equation = x*f(x).diff(x) + f(x) - f(x)**2
result_general = dsolve(equation)
result_hint = dsolve(equation, hint='Bernoulli')
print(f"Общее решение: {result_general}")
print(f"Решение с подсказкой 'Bernoulli': {result_hint}")
