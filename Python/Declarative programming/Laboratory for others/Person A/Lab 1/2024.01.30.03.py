import random

from datetime import timedelta

# Генерируем случайное количество секунд от 0 до 10000
numar_secunde = random.randint(0, 10000)

# Преобразуем количество секунд в объект timedelta
durata = timedelta(seconds=numar_secunde)

# Выводим отформатированный результат, используя str() для объекта timedelta
print(f"Сгенерированное количество секунд: {numar_secunde}")
print(f"Представление в формате чч:мм:сс: {str(durata)}")