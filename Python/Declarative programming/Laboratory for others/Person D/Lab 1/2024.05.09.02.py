import datetime
import random

# Генерация случайного числа от 0 до 10000, представляющего количество секунд
random_seconds = random.randint(0, 10000)

# Создание объекта timedelta с заданным количеством секунд и добавление его к полуночи
base_time = datetime.datetime.combine(datetime.date.today(), datetime.time.min) + datetime.timedelta(seconds=random_seconds)

# Форматирование вывода в виде hh:mm:ss
formatted_time = base_time.strftime('%H:%M:%S')

print("Рандомное число:", random_seconds)
print("Время:", formatted_time)
