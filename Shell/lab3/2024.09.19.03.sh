#!/bin/bash
# Считывать символы с клавиатуры, пока не будет введен символ "v". После этого вывести последовательность считанных символов в виде одной строки.
# Read characters from the keyboard until we enter \"v\"

echo "Input your text:"
input=""
while true; do
	read -n 1 char
	if [ "$char" == "v" ]; then
		break
	fi
	input+="$char"
done
echo -e "\nInput string is: $input"
