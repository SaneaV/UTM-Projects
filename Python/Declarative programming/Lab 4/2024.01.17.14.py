# Are there boolean values x, y that make the expression (𝑥∨¬𝑦)∧(𝑦∨¬𝑥) true? Justify your answer, use sym.satisfiable.

from sympy import symbols, And, Or, Not, satisfiable

x, y = symbols('x y')

expression = And(Or(x, Not(y)), Or(y, Not(x)))
solution = satisfiable(expression)

if solution:
    print(f"Yes, there are boolean values that make the expression true.")
    print("Example solution:", solution)
else:
    print("No, there are no boolean values that make the expression true.")
