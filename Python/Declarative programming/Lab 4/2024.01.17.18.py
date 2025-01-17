# Find the minimum value of 𝑥 to optimize the expression cos(𝑥) − 3𝑒^(−(𝑥−0.2))^2. 
# Call the function scipy.optimize.fmin which takes as an argument a function f 
# to minimize and an initial value x0 from which to start the search for the minimum and 
# which returns the value of x for which f(x) is (locally) minimized. 
# Repeat the search for the minimum for two values (x0 = 1.0 and x0 = 2.0, respectively) 
# to show that depending on the starting value we can find different minima of the function f

import numpy as np
from scipy.optimize import fmin

# Define the function to be minimized
def objective_function(x):
    return np.cos(x) - 3 * np.exp(-(x - 0.2)**2)

# Find the minimum for x0 = 1.0
x_min_1 = fmin(objective_function, x0=1.0)

# Find the minimum for x0 = 2.0
x_min_2 = fmin(objective_function, x0=2.0)

# Display the results
print("Minimum for x0 = 1.0:", x_min_1)
print("Function value for x0 = 1.0:", objective_function(x_min_1))
print()
print("Minimum for x0 = 2.0:", x_min_2)
print("Function value for x0 = 2.0:", objective_function(x_min_2))