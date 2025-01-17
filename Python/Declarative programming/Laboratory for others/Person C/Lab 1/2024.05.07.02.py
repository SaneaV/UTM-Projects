import datetime
import random

# Генерация случайного числа от 0 до 10000, представляющего количество секунд
random_seconds = random.randint(0, 10000)

# Создание объекта timedelta с заданным количеством секунд
time_delta = datetime.timedelta(seconds=random_seconds)

# Преобразование timedelta в объект datetime, начиная с полуночи
base_time = datetime.datetime(1, 1, 1, 0, 0) + time_delta

# Извлечение часов, минут и секунд из объекта datetime
hours = base_time.hour
minutes = base_time.minute
seconds = base_time.second

# Форматирование вывода в виде hh:mm:ss
formatted_time = '{:02}:{:02}:{:02}'.format(hours, minutes, seconds)

print("Случайное число:", random_seconds)
print("В форме времени:", formatted_time)