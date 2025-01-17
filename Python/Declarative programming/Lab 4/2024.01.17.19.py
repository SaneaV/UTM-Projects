# Perform various manipulations on an image: change orientation, resolution 
# (scipy.ndimage provides manipulation of n-dimensional arrays as images). 
# Noise the image, then use Gaussian, Median, Wiener filters in turn. Observe the effectiveness of each filter.

import numpy as np
import matplotlib.pyplot as plt
from scipy import ndimage
from skimage import data, img_as_float, restoration

# Load the camera image from skimage
image = img_as_float(data.horse()) 
# camera
# brick 
# grass
# gravel
# text
# checkerboard
# cell
# coins
# kidney
# microaneurysms
# moon
# page

# Change the orientation of the image
rotated_image = ndimage.rotate(image, 45, reshape=False)

# Modify the resolution of the image
resized_image = ndimage.zoom(image, (0.5, 0.5))

# Generate noise on the image
noisy_image = image + 0.5 * np.random.random(image.shape)

# Apply Gaussian filter
gaussian_filtered_image = ndimage.gaussian_filter(noisy_image, sigma=1)

# Apply median filter
median_filtered_image = ndimage.median_filter(noisy_image, size=3)

# Apply Wiener filter
psf = np.ones((5, 5)) / 25
wiener_filtered_image, _ = restoration.unsupervised_wiener(noisy_image, psf)

# Visualize the results
plt.figure(figsize=(12, 10))

plt.subplot(3, 4, 1), plt.imshow(image, cmap='gray'), plt.title('Original')
plt.subplot(3, 4, 2), plt.imshow(rotated_image, cmap='gray'), plt.title('Rotated')
plt.subplot(3, 4, 3), plt.imshow(resized_image, cmap='gray'), plt.title('Resized')
plt.subplot(3, 4, 4), plt.imshow(noisy_image, cmap='gray'), plt.title('Noisy')

plt.subplot(3, 4, 5), plt.imshow(gaussian_filtered_image, cmap='gray'), plt.title('Gaussian Filtered')
plt.subplot(3, 4, 6), plt.imshow(median_filtered_image, cmap='gray'), plt.title('Median Filtered')
plt.subplot(3, 4, 7), plt.imshow(wiener_filtered_image, cmap='gray'), plt.title('Wiener Filtered')

plt.show()