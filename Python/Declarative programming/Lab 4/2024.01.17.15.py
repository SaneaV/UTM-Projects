# Solve Bernoulli's differential equation x * df(x)/dx + f(x) - f(x)/2 = 0 Solve the same equation using hint='Bernoulli'.

from sympy import symbols, Function, dsolve

x = symbols('x')
f = Function('f')

equation = x * f(x).diff(x) + f(x) - f(x)/2
solution = dsolve(equation)

print("General solution:")
print(solution)

solution = dsolve(equation, hint='Bernoulli')
print("hint='Bernoulli':")
print(solution)

'''
Without using hint='Bernoulli', you solve the equation in an explicit way, using methods of solving ordinary differential equations. 
In this case, SymPy automatically selects the appropriate method for the solution and returns the answer.

Using hint='Bernoulli', you explicitly tell SymPy to use the Bernoulli method for solving equations. 
This method assumes that the equation can be reduced to the standard form of the Bernoulli equation, 
which can simplify the decision process.

In both cases, the result will be equivalent, but the methods of obtaining may differ. Using hint='Bernoulli' 
can be preferred when you know that Bernoulli's equation, as it can simplify the code and improve performance in some cases.
'''