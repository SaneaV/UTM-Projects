#!/bin/bash
# Создать текстовое меню с четырьмя пунктами. При вводе пользователем номера пункта меню происходит запуск редактора nano, редактора xed, браузера Firefox или выход из меню.
# Create text menu

while true; do
	echo "1. Open nano"
	echo "2. Open xed"
	echo "3. Open Firefox"
	echo "4. Exit"
	read -p "Select the menu item: " choice

	case $choice in
		1) nano ;;
		2) xed ;;
		3) firefox ;;
		4) exit 0 ;;
		*) echo "Wrong input";;
	esac
done
