# Define a recursive function that returns the sum of the first N natural numbers.

def sum_of_natural_numbers(n):
    return 0 if n == 0 else n + sum_of_natural_numbers(n - 1)

N = 5

result = sum_of_natural_numbers(N)
print(f"The sum of the first {N} natural numbers is: {result}")