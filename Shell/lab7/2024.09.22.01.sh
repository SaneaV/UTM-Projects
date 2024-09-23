# 1) Скрипт rmtrash 
# a) Скрипту передается один параметр – имя файла в текущем каталоге (lab7) вызова скрипта. 
# b) Скрипт проверяет, создан ли скрытый каталог trash в домашнем каталоге пользователя. Если он не создан – создает его.
# c) Далее, в текущем каталоге, скрипт создает файл с именем параметра. Создает жесткую ссылку на созданный файл и скрытый каталог trash с уникальным именем, состоящим из цифр (используйте - (date +%s)) и удаляет файл из текущего каталога. 
# d) Затем в скрытый файл trash.log в домашнем каталоге пользователя помещается запись, содержащая полный исходный путь к удаленному файлу и имя созданной жесткой ссылки

#!/bin/bash

# Имя файла для перемещения
scriptName=$1

# Каталог корзины и лог-файл в домашнем каталоге
homeTrash="$HOME/.trash"
homeLog="$HOME/trash.log"

# Проверка, передано ли имя файла
if [ $# -ne 1 ]; then
  echo "Error: Empty parameter."
  exit 1
fi

echo "Create new $scriptName file in the $PWD folder"
touch $scriptName

# Создание каталога корзины, если он не существует
[ -d "$homeTrash" ] || (echo "Create new $homeTrash folder" &&  mkdir "$homeTrash")

# Генерация уникального имени для файла в корзине (используем timestamp)
currentTrashFile=$(date +%s)

# Создание жесткой ссылки в каталоге корзины и удаление исходного файла
ln "$scriptName" "$homeTrash/$currentTrashFile"
rm "$scriptName"

# Создание лог-файла, если он не существует
[ -e "$homeLog" ] || (echo "Create new $homeLog file" && touch "$homeLog")

# Запись исходного пути и имени файла в корзине в лог
echo "$PWD/$scriptName:$currentTrashFile" >> "$homeLog"
echo "$1 with a new name $currentTrashFile was successfuly removed from $PWD folder"
