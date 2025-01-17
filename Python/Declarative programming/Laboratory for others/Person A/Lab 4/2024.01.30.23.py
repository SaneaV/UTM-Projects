import numpy as np
import matplotlib.pyplot as plt
from scipy.fft import fft, fftfreq

# Параметры сигнала
fs = 1000  # частота дискретизации
t = np.arange(0, 1, 1/fs)  # временной вектор

# Создание сигнала как смешение синусоид с частотами 50 Гц и 70 Гц
signal = np.sin(2*np.pi*50*t) + np.sin(2*np.pi*70*t + np.pi/4)

# Вычисление дискретного преобразования Фурье
fft_result = fft(signal)
freq = fftfreq(len(signal), 1/fs)  # частоты, соответствующие коэффициентам Фурье

# Построение графика
plt.figure(figsize=(10, 6))

plt.subplot(2, 1, 1)
plt.plot(t, signal)
plt.title('Сигнал: Смешение синусоид 50 Гц и 70 Гц')
plt.xlabel('Время (с)')
plt.ylabel('Амплитуда')

plt.subplot(2, 1, 2)
plt.plot(freq, np.abs(fft_result))
plt.title('Преобразование Фурье: Амплитуда в зависимости от частоты')
plt.xlabel('Частота (Гц)')
plt.ylabel('Амплитуда')

plt.tight_layout()
plt.show()
