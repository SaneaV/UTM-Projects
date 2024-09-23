# Для всех зарегистрированных в данный момент в системе процессов выведите в один файл строки
# ProcessID=PID : Parent_ProcessID=PPID : Average_Time=avg_atom. 
# Значения PPID и PID возьмите из файлов status, значение avg_atom (avg_atom=se.sum_exec_runtime / nr_switches) из файлов sched, которые находятся в директориях с названиями, соответствующими PID процессов в /proc. Отсортируйте эти строки по идентификаторам родительских процессов. Содержимое файла выведите на экран.


#!/bin/bash

# Определяем имя выходного файла
outfile="process_info.out"

# Очищаем файл, если он уже существует
> $outfile  # Перезаписываем (очищаем) файл, чтобы в нем не было старых данных

# Проходим по всем процессам
# Используем команду ps для получения списка всех PID в системе (-axo pid выведет все PID без заголовков)
for pid in $(ps -axo pid | tail -n +2)
do
    # Пути к файлам /proc/[pid]/status и /proc/[pid]/sched
    # /proc/[pid]/status содержит информацию о процессе, включая PPID (родительский процесс)
    # /proc/[pid]/sched содержит данные о времени выполнения процесса
    status_file="/proc/$pid/status"
    sched_file="/proc/$pid/sched"
    
    # Извлекаем PPID из файла status
    # PPID — родительский процесс, который запустил данный процесс
    ppid=$(grep -E -s -h "^PPid:" $status_file | awk '{print $2}')
    
    # Извлекаем se.sum_exec_runtime (время работы процесса) и nr_switches (количество переключений контекста)
    sum_exec_runtime=$(grep -E -s -h "^se.sum_exec_runtime" $sched_file | awk '{print $3}')
    nr_switches=$(grep -E -s -h "^nr_switches" $sched_file | awk '{print $3}')
    
    # Если значения не найдены, пропускаем процесс (continue переходит к следующей итерации цикла)
    if [[ -z $ppid || -z $sum_exec_runtime || -z $nr_switches ]]; then
        continue
    fi


    # Рассчитываем avg_atom — среднее время между переключениями контекста
    # avg_atom = se.sum_exec_runtime / nr_switches
    if [[ $nr_switches -gt 0 ]]; then
        # Если количество переключений больше нуля, вычисляем среднее
        avg_atom=$(echo "scale=2; $sum_exec_runtime / $nr_switches" | bc | xargs printf "%.2f")  # bc используется для точных вычислений с плавающей точкой
    else
        avg_atom=0  # Если переключений нет, среднее время равно нулю
    fi

    # Записываем результат в файл в формате "ProcessID=PID : Parent_ProcessID=PPID : Average_Time=avg_atom"
    echo "ProcessID=$pid : Parent_ProcessID=$ppid : Average_Time=$avg_atom" >> $outfile

done

# Сортируем файл по PPID (в алфавитном порядке по значению Parent_ProcessID) и выводим результат на экран
sort -n -t "=" -k3 $outfile  # -t "=" указывает на "=" как разделитель, -k3 означает сортировку по третьему полю (Parent_ProcessID)

