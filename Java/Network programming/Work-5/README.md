# EmailApplication

## Описание программы

Программа **EmailApplication** предназначена для отправки и получения электронных писем через почтовые серверы Gmail. Она
поддерживает протоколы IMAP и POP3 для чтения писем и SMTP для отправки писем.

### Возможности программы:

- Отправка писем без вложений.
- Отправка писем с вложениями.
- Чтение писем из почтового ящика с использованием протоколов IMAP и POP3.
- Ответ на письмо с указанием адреса Reply-To.
- Обработка ошибок при отправке и получении писем.

## Требования

Для работы программы вам потребуется:

- **Java Development Kit (JDK)** версии 8 или выше.

## Как скомпилировать и запустить программу

### Сборка проекта

Выполните команду для сборки проекта:

```bash
mvn clean package
```

### Запуск программы

После успешной сборки выполните команду для запуска программы:

```bash
mvn exec:java -Dexec.mainClass="org.example.EmailApplication"
```

## Использование программы

После запуска программы появится меню с возможностями:

```plaintext
Choose action:
1. Send email without attachment
2. Send email with attachment
3. Read emails via POP3
4. Read emails via IMAP
5. Exit
   ````

Выберите нужное действие, введя соответствующий номер. Программа запросит необходимые данные, такие как хост, откуда и куда
отправляется письмо, тему, текст сообщения и т. д.

## Примеры использования

### Отправка письма без вложений

```plaintext
Choose action:
1. Send email without attachment
   Host: smtp.gmail.com
   From: your_email@gmail.com
   Password: your_app_password
   To: recipient@example.com
   Subject: Test Email
   Body: This is a test email.
   Email sent successfully!
   ````

### Отправка письма с вложениями

```plaintext
Choose action:
2. Send email with attachment
   Host: smtp.gmail.com
   From: your_email@gmail.com
   Password: your_app_password
   To: recipient@example.com
   Subject: Test Email with Attachment
   Body: This is a test email with attachment.
   Attachment path: /path/to/attachment/file.txt
   Email with attachment sent successfully!
   ````

### Чтение писем через IMAP

```plaintext
Choose action:
4. Read emails via IMAP
   Host: imap.gmail.com
   Username: your_email@gmail.com
   Password: your_app_password

Reading emails via IMAP...
Number of emails: 1

Subject: Test Email
From: user@example.com
Date: Thu Jan 16 14:45:26 EET 2025
Content: This is a test email.

What would you like to do?
1. Reply to this message
2. Go to the next message
3. Exit to main menu
   ````

### Ответ на письмо

```plaintext
SMTP Host: smtp.gmail.com
To: user@example.com
Subject: Re: Test Email
Body: Your response here.
Reply-To (optional): support@company.com
Reply sent successfully!
````

## Дополнительная информация

### Настройка доступа к Gmail API

Для подключения программы к вашему аккаунту Gmail выполните следующие шаги:

1. Войдите в свой аккаунт Google и перейдите в **Настройки безопасности**.
2. Включите двухфакторную аутентификацию (если она не включена).
3. Создайте пароль для приложения в разделе **Пароли приложений**.
4. Используйте этот пароль вместо обычного пароля при запуске программы.

### Проверка логов

Для отладки и проверки логов можно активировать режим отладки в коде программы. Для этого раскомментируйте строки
с `properties.put("mail.debug", "true");` в методах конфигурации SMTP, IMAP и POP3.

### Поддержка различных почтовых провайдеров

Программа написана для работы с Gmail, но может быть адаптирована для других почтовых провайдеров путем изменения значений хостов
и портов.
