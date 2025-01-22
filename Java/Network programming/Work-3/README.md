### Приложение для Разрешения DNS

#### Назначение Программы

Это консольное приложение позволяет пользователю разрешать домены в IP-адреса и наоборот, используя либо системный DNS-сервер,
либо указанный пользователем DNS-сервер. Основные функции включают:

- **Разрешение доменов в IP-адреса**:
    - Команда: `resolve <domain>`
    - Пример: `resolve google.com`
- **Разрешение IP-адресов в домены**:
    - Команда: `resolve <ip>`
    - Пример: `resolve 142.250.185.110`
- **Смена DNS-сервера для разрешения команд**:
    - Команда: `use dns <ip>`
    - Пример: `use dns 8.8.8.8`

#### Требования

Для запуска этой программы необходимо:

- Java Development Kit (JDK) версии 8 или выше.

#### Компиляция и Запуск Приложения

Запустите следующие команды для компиляции и запуска приложения:

```sh
mvn compile
mvn exec:java -Dexec.mainClass="org.example.DNSResolverApp"
```

#### Примеры Использования

##### Разрешение домена в IP-адреса:

```sh
Enter command (resolve <domain/IP> or use dns <ip>): resolve google.com
IP Addresses for google.com:
142.250.185.110
```

##### Разрешение IP-адреса в домены:

```sh
Enter command (resolve <domain/IP> or use dns <ip>): resolve 142.250.185.110
Hostnames for 142.250.185.110:
fra16s49-in-f14.1e100.net.
```

##### Смена DNS-сервера:

```sh
Enter command (resolve <domain/IP> or use dns <ip>): use dns 8.8.8.8
DNS server set to 8.8.8.8
```

#### Обработка Ошибок

##### Ошибка Валидации IP-Адреса:

- **Сообщение**: `Error: Invalid DNS server IP address '<ip>'. <сообщение_ошибки>.`
- **Пример**: `Error: Invalid DNS server IP address 'invalid_ip'. unknown host: invalid_ip.`

##### Ошибка Разрешения Домена:

- **Сообщение**: `Error resolving domain <domain>: <сообщение_ошибки>.`
- **Пример**: `Error resolving domain invalid_domain: invalid_domain: unknown host`

##### Ошибка Разрешения IP-Адреса:

- **Сообщение**: `Error resolving IP <ip>: <сообщение_ошибки>.`
- **Пример**: `Error resolving IP invalid_ip: invalid_ip: unknown host`