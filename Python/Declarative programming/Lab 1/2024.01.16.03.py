# Define two float objects and calculate their sum, difference using lamda() functions. 
# Call all the functions in the list in turn, and with other data types, use the map() function.

num1 = 10.5
num2 = 5.3

sum_function = lambda x, y: x + y
difference_function = lambda x, y: x - y

data_types = [5, 8.7, 1, -3.2]

sum_results_num1 = list(map(lambda x: sum_function(num1, x), data_types))
sum_results_num2 = list(map(lambda x: sum_function(num2, x), data_types))
diff_results_num1 = list(map(lambda x: difference_function(num1, x), data_types))
diff_results_num2 = list(map(lambda x: difference_function(num2, x), data_types))

print("Initial numbers:", num1, "and", num2)
print(num1, "+", num2, " = ", sum_function(num1, num2))
print(num1, "-", num2, " = ", difference_function(num1, num2))
print("Sum results for various data types for num1 (", num1, ")", sum_results_num1)
print("Sum results for various data types for num2 (", num2, ")", sum_results_num2)
print("Difference results for various data types for num1 (", num1, "):", diff_results_num1)
print("Difference results for various data types for num2 (", num2, "):", diff_results_num2)