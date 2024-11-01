# Выведите на экран список пользователей системы с их UID, отсортированный по UID. Информация о пользователях хранится в файле /etc/passwd. В каждой строке этого файла, первое поле содержит имя пользователя, а третье поле - UID. Разделитель – двоеточие (:).

# Выводим содержимое файла /etc/passwd
cat /etc/passwd |

# Используем cut, чтобы извлечь только первую и третью колонки, разделенные двоеточием
# -d: задаем двоеточие (:) как разделитель
# -f1,3: выбираем первую колонку (имя пользователя) и третью колонку (UID)
cut -d: -f1,3 |

# Сортируем по второй колонке (UID) численно
# -t: указываем двоеточие (:) как разделитель колонок для сортировки
# -k2n: сортируем по второй колонке (UID) численно (n означает числовую сортировку)
sort -t: -k2n
