# Generate a random number between 0 and 10000 that represents a number of seconds. 
# Calculate the representation of the number of seconds in hours, 
# minutes and seconds and display the result formatted as hh:mm:ss. Alternatively, use the datetime module.

import random
import datetime

random_seconds = random.randint(0, 10000)

time_delta = datetime.timedelta(seconds=random_seconds)

hours, remainder = divmod(time_delta.seconds, 3600)
minutes, seconds = divmod(remainder, 60)

formatted_time = "{:02}:{:02}:{:02}".format(hours, minutes, seconds)
print("Number of seconds:", random_seconds)
print("Representation in hours, minutes, and seconds (hh:mm:ss):", formatted_time)