# Процесс «Генератор» передает информацию процессу «Обработчик» с помощью файла. Процесс «Обработчик» должен осуществлять следующую обработку новых строк в этом файле: если строка содержит единственный символ «+», то процесс «Обработчик» переключает режим на сложение и ждет ввода численных данных. Если строка содержит единственный символ «*», то обработчик переключает режим на умножение и ждет ввода численных данных. Если строка содержит целое число, то обработчик осуществляет текущую активную операцию (выбранный режим) над текущим значением вычисляемой переменной и считанным значением (например, складывает или перемножает результат предыдущего вычисления со считанным числом). При запуске скрипта режим устанавливается в сложение, а вычисляемая переменная приравнивается к 1. В случае получения строки QUIT скрипт выдает сообщение о плановой остановке и завершает работу. В случае получения любых других значений строки скрипт завершает работу с сообщением об ошибке входных данных.

#!/bin/bash

# Файл, из которого будут считываться команды
file="commands.txt"

# Начальные значения: по умолчанию режим сложения и переменная value установлена в 1
mode="add"
value=1

# Бесконечный цикл, который будет обрабатывать команды до тех пор, пока не будет введена команда "QUIT"
while true
do
  # Чтение первой строки из файла 'commands.txt'. Если строка успешно прочитана:
  if read -r line < "$file"; then
    # Очистка файла после того, как строка была прочитана, чтобы не обрабатывать ее повторно
    > "$file"
    
    # Проверка содержимого прочитанной строки с помощью конструкции case
    case $line in
      "+")
        # Если строка содержит "+", переключаем режим на сложение
        mode="add"
        echo "Режим: сложение"
        ;;
      "*")
        # Если строка содержит "*", переключаем режим на умножение
        mode="multiply"
        echo "Режим: умножение"
        ;;
      "QUIT")
        # Если строка содержит "QUIT", выводим сообщение и завершаем работу обработчика
        echo "Обработчик завершает работу."
        break
        ;;
      [0-9]*)
        # Если строка содержит число, выполняем текущую операцию (сложение или умножение)
        if [ "$mode" == "add" ]; then
          # Если режим сложения, добавляем число к переменной 'value'
          value=$(($value + $line))
        elif [ "$mode" == "multiply" ]; then
          # Если режим умножения, умножаем переменную на введенное число
          value=$(($value * $line))
        fi
        # Выводим текущее значение переменной после операции
        echo "Текущее значение: $value"
        ;;
      *)
        # Если строка содержит некорректные данные, выводим сообщение об ошибке и завершаем работу
        echo "Ошибка входных данных: $line"
        break
        ;;
    esac
  fi
  
  # Делаем паузу в 1 секунду перед следующей итерацией цикла
  sleep 1
done