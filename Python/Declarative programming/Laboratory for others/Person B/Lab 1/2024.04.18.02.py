# Условие программы: Сгенерировать случайное число между 0 и 10000, представляющее собой количество секунд. 
# Рассчитать представление количества секунд в часах, минутах и секундах и вывести результат в формате чч:мм:сс. 
# Возможно, использование модуля datetime.

# Генерация случайного числа секунд
import random
num_seconds = random.randint(0, 10000)

# Расчет времени в часах, минутах и секундах
hours = num_seconds // 3600
minutes = (num_seconds % 3600) // 60
seconds = (num_seconds % 3600) % 60

# Вывод результатов в формате чч:мм:сс
print("Представление времени:", f"{hours:02d}:{minutes:02d}:{seconds:02d}")

# Альтернативное решение с использованием модуля datetime
import datetime

# Создание объекта timedelta с количеством секунд
timedelta_seconds = datetime.timedelta(seconds=num_seconds)

# Преобразование timedelta в строку формата чч:мм:сс
print("Альтернативное представление времени:", str(timedelta_seconds))