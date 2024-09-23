#!/bin/bash
# В параметрах при запуске скрипта передаются три целых числа. Вывести максимальное из них.
# FInd the maximum integer among 3 number

if [ "$1" -ge "$2" ] && [ "$1" -ge "$3" ]; then
	echo "Max number is: $1"
elif [ "$2" -ge "$1" ] && [ "$2" -ge "$3" ]; then
	echo "Max number is: $2"
else
	echo "Max number is $3"
fi
