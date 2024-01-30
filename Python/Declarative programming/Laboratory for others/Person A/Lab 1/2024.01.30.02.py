import random

# Генерируем случайное количество секунд от 0 до 10000
numar_secunde = random.randint(0, 10000)

# Вычисляем часы, минуты и секунды
ore = numar_secunde // 3600
minute = (numar_secunde % 3600) // 60
secunde = numar_secunde % 60

# Выводим отформатированный результат
print(f"Сгенерированное количество секунд: {numar_secunde}")
print(f"Представление в формате чч:мм:сс: {ore:02d}:{minute:02d}:{secunde:02d}")
