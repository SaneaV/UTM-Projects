#!/bin/bash
# Считывать с клавиатуры целые числа, пока не будет введено четное число. После этого вывести количество считанных чисел.
# Read integers from the keyboard until an even number is entered.
# Then print the number of numbers read.

echo -e "Input a number\n"
count=0
while true; do
	read -n 1 num
	if ! [[ "$num" =~ ^-?[0-9]+$ ]]; then
		echo -e "\nError: input a number: \n"
		continue
	fi
	if [ $((num % 2)) -eq 0 ]; then
		break
	fi
	count=$((count + 1))
done
echo -e "\nThe number of numbers is: \$count"
