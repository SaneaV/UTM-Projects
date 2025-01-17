from scipy.integrate import quad
from numpy import cos, pi

result, _ = quad(lambda x: cos(2*pi*x), 0, 1)
print(f"Значение определенного интеграла: {result}")
