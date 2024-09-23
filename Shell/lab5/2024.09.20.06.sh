# В полученном на предыдущем шаге файле после каждой группы записей с одинаковым идентификатором родительского процесса вставить строку вида
Sum_switches_of_ParentID=N is M, где N = PPID, а M – сума, посчитанная как voluntary_ctxt_switches + nonvoluntary_ctxt_switches для данного процесса. Содержимое файла выведите на экран.


#!/bin/bash

# Определяем имя выходного файла
outfile="process_info_with_switches.out"

# Очищаем файл, если он уже существует
> $outfile  # Очищаем (перезаписываем) файл, чтобы не было старых данных

# Используем ассоциативный массив для хранения сумм переключений для каждого PPID
declare -A ppid_switches  # Объявляем ассоциативный массив для хранения переключений контекста для каждого PPID

# Проходим по всем процессам
for pid in $(ps -axo pid)  # Команда ps выводит все PID процессов, и мы проходим по каждому PID
do
    # Пути к файлам /proc/[pid]/status и /proc/[pid]/sched
    # status содержит информацию о процессе, в том числе PPID
    # sched содержит статистику времени выполнения, переключений контекста и др.
    status_file="/proc/$pid/status"
    sched_file="/proc/$pid/sched"
    
    # Извлекаем PPID из файла status
    ppid=$(grep -E -s -h "^PPid:" $status_file | awk '{print $2}')  # Извлекаем родительский процесс (PPID)
    
    # Извлекаем se.sum_exec_runtime (время выполнения) и nr_switches (количество переключений контекста)
    sum_exec_runtime=$(grep -E -s -h "^se.sum_exec_runtime" $sched_file | awk '{print $3}')
    nr_switches=$(grep -E -s -h "^nr_switches" $sched_file | awk '{print $3}')
    
    # Извлекаем voluntary и nonvoluntary контекстные переключения из status
    voluntary_switches=$(grep -E -s -h "^voluntary_ctxt_switches" $status_file | awk '{print $2}')
    nonvoluntary_switches=$(grep -E -s -h "^nonvoluntary_ctxt_switches" $status_file | awk '{print $2}')

    # Если значения не найдены, пропускаем процесс
    if [[ -z $ppid || -z $sum_exec_runtime || -z $nr_switches || -z $voluntary_switches || -z $nonvoluntary_switches ]]; then
        continue  # Пропускаем процессы, для которых данные не были найдены
    fi

    # Рассчитываем avg_atom — среднее время выполнения между переключениями
    if [[ $nr_switches -gt 0 ]]; then
        avg_atom=$(echo "scale=2; $sum_exec_runtime / $nr_switches" | bc)  # Вычисляем среднее время
    else
        avg_atom=0  # Если переключений не было, устанавливаем в 0
    fi

    # Суммируем переключения контекста для данного процесса
    total_switches=$((voluntary_switches + nonvoluntary_switches))  # Сумма добровольных и принудительных переключений

    # Записываем сумму переключений для текущего PPID в ассоциативный массив
    if [[ -n ${ppid_switches[$ppid]} ]]; then
        ppid_switches[$ppid]=$((ppid_switches[$ppid] + total_switches))  # Добавляем переключения к текущему PPID
    else
        ppid_switches[$ppid]=$total_switches  # Инициализируем запись для нового PPID
    fi

    # Записываем результат в файл
    echo "ProcessID=$pid : Parent_ProcessID=$ppid : Average_Time=$avg_atom" >> $outfile  # Записываем информацию о процессе

done

# Теперь проходим по файлу, группируем по PPID и вставляем строки с суммами переключений
tempfile="sorted_process_info.tmp"  # Временный файл для хранения отсортированных данных
> $tempfile  # Очищаем временный файл

current_ppid=""  # Переменная для отслеживания текущего PPID
while IFS= read -r line  # Читаем файл построчно
do
    # Извлекаем PPID из строки
    ppid=$(echo $line | awk -F "=" '{print $3}' | awk '{print $1}')  # Получаем PPID из строки

    # Если PPID изменился (новая группа процессов)
    if [[ $ppid != $current_ppid ]]; then
        # Если уже была группа, добавляем строку с суммой переключений для предыдущей группы
        if [[ -n $current_ppid ]]; then
            echo "Sum_switches_of_ParentID=$current_ppid is ${ppid_switches[$current_ppid]}" >> $tempfile  # Добавляем сумму переключений для предыдущего PPID
        fi
        current_ppid=$ppid  # Обновляем текущий PPID
    fi

    # Записываем текущую строку в файл
    echo "$line" >> $tempfile  # Записываем строку с информацией о процессе

done < <(sort -n -t "=" -k3 $outfile)  # Сортируем файл по PPID перед обработкой

# Добавляем финальную строку с суммой переключений для последней группы
if [[ -n $current_ppid ]]; then
    echo "Sum_switches_of_ParentID=$current_ppid is ${ppid_switches[$current_ppid]}" >> $tempfile  # Добавляем сумму для последнего PPID
fi

# Переносим содержимое временного файла в основной файл
mv $tempfile $outfile  # Заменяем исходный файл отсортированным и дополненным

# Выводим содержимое итогового файла на экран
cat $outfile  # Выводим результат
