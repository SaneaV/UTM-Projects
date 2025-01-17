# Define a function that returns the number of occurrences of a character in a string (use the reduce() function)

from functools import reduce

def count_char_occurrences(input_string, target_char):
    count = reduce(lambda acc, char: acc + 1 if char == target_char else acc, input_string, 0)
    return count

input_str = "Hello, World!"
target_character = "o"

result = count_char_occurrences(input_str, target_character)
print(f"The number of occurrences of the character '{target_character}' in the string '{input_str}': {result}")