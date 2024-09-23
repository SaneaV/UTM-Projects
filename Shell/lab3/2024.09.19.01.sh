#!/bin/bash
# В параметрах скрипта передаются две строки. Вывести сообщение о равенстве или неравенстве переданных строк.
# Compare two strings passed in parameters

if [ "$1" == "$2" ]; then
	echo "The string are equal"
else
	echo "The strings are inequal"
fi
