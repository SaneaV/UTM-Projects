# Define a list of integer values and display only the distinct values from it (use the filter() function).

integer_list = [1, 2, 3, 4, 2, 3, 5, 6, 7, 5, 8, 9, 10, 1]

distinct_values = list(filter(lambda x: integer_list.count(x) == 1, integer_list))

print("Initial list:", integer_list)
print("Distinct values in the list:", distinct_values)