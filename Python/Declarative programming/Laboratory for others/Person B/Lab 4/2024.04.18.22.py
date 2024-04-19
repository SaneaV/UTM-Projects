# Выполните различные манипуляции с изображением: измените его ориентацию, разрешение 
# (scipy.ndimage позволяет работать с массивами n-мерных данных как с изображениями). 
# Сгенерируйте шум на изображении, а затем последовательно примените фильтры Гаусса, медианный и Винера. 
# Наблюдайте за эффективностью каждого фильтра.

import numpy as np
import matplotlib.pyplot as plt
from scipy import ndimage
from skimage import data, img_as_float
from skimage.filters import gaussian, median, wiener

# Загрузка тестового изображения из библиотеки skimage
image = img_as_float(data.camera())

# Отображение исходного изображения
plt.figure(figsize=(10, 4))
plt.subplot(131)
plt.imshow(image, cmap='gray')
plt.title('Исходное изображение')

# Изменение ориентации изображения
rotated_image = ndimage.rotate(image, angle=45, reshape=False)
plt.subplot(132)
plt.imshow(rotated_image, cmap='gray')
plt.title('Повернутое изображение')

# Изменение разрешения изображения
resized_image = ndimage.zoom(image, zoom=(0.5, 0.5))
plt.subplot(133)
plt.imshow(resized_image, cmap='gray')
plt.title('Изображение измененного размера')

plt.show()

# Создание шума на изображении
noisy_image = image + 0.4 * np.random.random(image.shape)
plt.figure(figsize=(12, 4))
plt.subplot(131)
plt.imshow(noisy_image, cmap='gray')
plt.title('Изображение с шумом')

# Применение фильтра Гаусса
filtered_gaussian = gaussian(noisy_image, sigma=1)
plt.subplot(132)
plt.imshow(filtered_gaussian, cmap='gray')
plt.title('Гауссовский фильтр')

# Применение медианного фильтра
filtered_median = median(noisy_image)
plt.subplot(133)
plt.imshow(filtered_median, cmap='gray')
plt.title('Медианный фильтр')

plt.show()

# Применение фильтра Винера
filtered_wiener = wiener(noisy_image)
plt.figure(figsize=(8, 4))
plt.subplot(121)
plt.imshow(filtered_wiener, cmap='gray')
plt.title('Фильтр Винера')

# Отображение исходного изображения для сравнения
plt.subplot(122)
plt.imshow(image, cmap='gray')
plt.title('Исходное изображение')

plt.show()
