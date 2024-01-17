# Create a signal as a superposition of a 50 Hz and 70 Hz sine wave (with a slight phase shift in between). 
# Then Fourier transform the signal and plot the absolute value of the discrete (complex) 
# Fourier transform coefficients as a function of frequency (notice peaks at 50Hz and 70Hz)

import numpy as np
import matplotlib.pyplot as plt

sampling_rate = 1000  # Sampling rate in Hz
duration = 1.0       # Duration of the signal in seconds

# Generate time values
time = np.arange(0, duration, 1/sampling_rate)

# Create a signal as a superposition of 50 Hz and 70 Hz sine waves with a phase shift
frequency_1 = 50
frequency_2 = 70
phase_shift = np.pi/4  # Phase shift in radians

signal = np.sin(2 * np.pi * frequency_1 * time) + np.sin(2 * np.pi * frequency_2 * time + phase_shift)

fourier_transform = np.fft.fft(signal)
frequencies = np.fft.fftfreq(len(fourier_transform), 1/sampling_rate)

# Plot the absolute value of Fourier transform coefficients
plt.plot(np.abs(frequencies), np.abs(fourier_transform))
plt.title('Fourier Transform of the Signal')
plt.xlabel('Frequency (Hz)')
plt.ylabel('Amplitude')
plt.show()