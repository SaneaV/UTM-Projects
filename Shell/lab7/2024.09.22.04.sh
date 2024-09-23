# 4)Скрипт upback 
# Скрипт должен скопировать в каталог /home/user/restore/ все файлы из актуального на данный момент каталога резервного копирования (имеющего в имени наиболее свежую дату), за исключением файлов с предыдущими версиями.


#!/bin/bash

# Устанавливаем переменные для каталогов
backup_dir="$HOME"
restore_dir="$HOME/restore"

# Создаем каталог для восстановления, если он не существует
mkdir -p "$restore_dir"

# Находим наиболее свежий каталог резервного копирования
latest_backup_dir=$(ls -1d "$backup_dir"/Backup-* 2>/dev/null | sort -V | tail -n 1)

# Проверка, найден ли каталог резервного копирования
if [ -z "$latest_backup_dir" ]; then
    echo "No backup directory found."
    exit 1
fi

echo "Using latest backup directory: $latest_backup_dir"

# Копируем файлы из последнего резервного каталога в каталог восстановления
shopt -s nullglob  # Позволяет массиву быть пустым, если нет совпадений
for file in "$latest_backup_dir"/*; do
    # Проверяем, является ли файл версионным
    if [[ "$file" =~ \.[0-9]{4}-[0-9]{2}-[0-9]{2}$ ]]; then
        echo "Skipping versioned file: $(basename "$file")"
        continue
    fi

    cp "$file" "$restore_dir/"  # Копируем файл в каталог восстановления
    echo "Restored: $(basename "$file")"
done

echo "Restoration completed."

