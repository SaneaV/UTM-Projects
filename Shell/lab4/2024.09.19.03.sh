# Создайте файл full.log, в котором будут сохранены строки из файла /var/log/Xorg.0.log, содержащие предупреждения и информационные сообщения, заменяя маркеры предупреждений и информационных сообщений словами Warning: и Information:, так что в результирующем файле сначала появлялись бы все информационные сообщения, а затем все предупреждения. Выведите этот файл на экран.

# Ищем все строки, содержащие "info" (независимо от регистра) в файле /var/log/Xorg.0.log
# Перенаправляем вывод ошибок в "пустоту"
# Перенаправляем вывод найденных строк в команду sed
# В команде sed заменяем каждую строку, содержащую "info", на "Information: <оригинальная строка>"
# Флаг I в sed делает поиск нечувствительным к регистру, символ & вставляет оригинальную строку
# Результат сохраняется в файл full.log (перезаписывается)
grep -i "info" /var/log/Xorg.0.log | sed 's/.*info.*/Information: &/I' > full.log

# Ищем все строки, содержащие "warning" (независимо от регистра) в том же файле /var/log/Xorg.0.log
# Также передаем результат в sed для замены найденных строк
# Заменяем на "Warning: <оригинальная строка>" (включаем флаг I для игнорирования регистра)
# Результат добавляем в конец файла full.log (>> добавляет в файл, не перезаписывая его)
grep -i "warning" /var/log/Xorg.0.log | sed 's/.*warning.*/Warning: &/I' >> full.log
