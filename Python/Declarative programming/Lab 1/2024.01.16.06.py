# Define a function that concatenates as many strings as its input

def concatenate_strings(*args):
    result = ''.join(args)
    return result

string1 = "Hello, "
string2 = "world!"
string3 = " How are you?"

concatenated_string = concatenate_strings(string1, string2, string3)
print("Concatenated String:", concatenated_string)