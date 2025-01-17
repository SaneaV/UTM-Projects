# Define a function that calculates the average of three grades given as input. 
# If the call does not send all grades, default values equal to 4 will be used. 
# Call the function with different combinations of positional and keyword arguments.

def find_average(intOne = 4, intTwo = 4, intThree = 4):
    return (intOne+intTwo+intThree)/3

print("Average for 1, 2, 3: ", find_average(1, 2, 3))
print("Average for 1, 2, (4): ", find_average(1, 2))
print("Average for 1, (4), (4): ", find_average(1))
print("Average for (4), (4), (4): ", find_average())