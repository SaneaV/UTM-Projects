# Define a dictionary that uses strings as keys and floats as values. Show only dictionary keys and then tuples of keys and values.

my_dictionary = {
    "key1": 1.23,
    "key2": 4.56,
    "key3": 7.89,
    "key4": 10.11
}

print("Keys:", list(my_dictionary.keys()))

key_value_tuples = [(key, value) for key, value in my_dictionary.items()]
print("Key-Value Tuples:", key_value_tuples)