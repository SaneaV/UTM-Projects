# Calculate √2 to 100 decimal places

from decimal import Decimal, getcontext

getcontext().prec = 101
sqrt_two = Decimal(2).sqrt()
print(sqrt_two)