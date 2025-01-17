# Определение словаря с ключами типа строка и значениями типа float
dictionary = {
    "key1": 3.14,
    "key2": 2.718,
    "key3": 1.618,
    # Добавьте больше пар ключ-значение при необходимости
}

# Вывод только ключей словаря
print("Ключи словаря:")
for key in dictionary.keys():
    print(key)

# Вывод кортежей ключей и значений
print("\nКортежи ключей и значений:")
key_value_tuples = [(key, value) for key, value in dictionary.items()]
for key, value in key_value_tuples:
    print(f"({key}, {value})")