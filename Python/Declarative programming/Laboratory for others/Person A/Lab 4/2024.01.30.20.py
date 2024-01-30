from sympy import symbols, satisfiable

x, y = symbols('x y')
expression = (x | ~y) & (y | ~x)
result = satisfiable(expression)
print(f"Существуют ли значения x, y: {result}")
