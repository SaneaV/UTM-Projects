#!/bin/bash
# Если скрипт запущен из домашнего директория, вывести на экран путь к домашнему директорию и выйти с кодом 0. В противном случае вывести сообщение об ошибке и выйти с кодом 1.
# Check if script was run from the /home

if [[ "$PWD" == *"$HOME"* ]]; then
	echo "Current folder: $PWD that contains $HOME path"
	exit 0
else
	echo "Error: script is not running from the home folder"
	exit 1
fi
