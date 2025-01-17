# Define a list of lambda functions that return: the second character from a string; 
# the string with uppercase letters; the position at which a given character is found in the input. 
# Call all the functions in the list in turn

string = "this is my string."
letter = "g"

second_letter_function = lambda str: str[1]
capitalize_letters_function = lambda str: str.upper()
find_letter_position = lambda str, letter: str.find(letter)

print("Initial string: ", string)
print("Second letter in string: ", second_letter_function(string))
print("Upper case string: ", capitalize_letters_function(string))
print("Position of '" + letter +"' in string: ", find_letter_position(string, letter))