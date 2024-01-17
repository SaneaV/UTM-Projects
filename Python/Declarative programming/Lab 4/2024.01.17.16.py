# Using the quad() function from the SciPy library, write a program that solves the following numerical integral: 
# I = ∫ cos(2πx) dx from 0 to 1. Why is it important to have an estimate of the precision (or error) of the numerical integral?

from scipy.integrate import quad
import numpy as np

def integrand(x):
    return np.cos(2 * np.pi * x)

result, error = quad(integrand, 0, 1)

print(f"Result of the integral: {result}")
print(f"Estimated error: {error}")

"""
Having an estimate of the precision (or error) is important because it provides information about the reliability of
 the numerical integration result. It helps to understand how much confidence we can have in the obtained value of the integral. 
 If the estimated error is small, it indicates that the numerical result is likely to be accurate.
"""