## Тестовое задание на поиск файлов в определенных директориях с использованием FTP-протокола 

----

Стек: Java 17, Maven 3.0.4, JUnit 5

Разработаны модули настройки ftp-протокола через `application.yaml`, алгоритм обхода всех папок на сервере с помощью рекурсии, модуль поиска файла через фильтр `FTPFileFilter`.
При разработке использовалась дополнительные библиотеки:
`commons-net`, `Lombok`.

ОС:

- MacOS: macOS Big Sur, версия 11.7.2 Intel Core i5 1,3 GHz RAM 4 Gb

- Windows: ОС X (Home edition) intel core i5-7200U CPU 2.50 GHz, 2.70 GHz RAM 8GB 64-x

---

## Техническое задание


Основной стек Java, Spring, Spring Boot, Maven, Tomcat.
Допускается использование сторонних библиотек.

Необходимо составить REST API, который при обращении по эндпоинту (прим. /photos) будет подключаться по FTP к серверу и искать на нём файлы с определенным префиксом “GRP327_…” из определенных папок, названных “фотографии”
После этого необходимо вывести список найденных файлов с их полным путем, датой создания фото и размером.

Данные для подключения к FTP:

185.27.134.11:21

логин и пароль записаны в `application.yaml`

---

## Запуск программы

Для запуска программы необходимо скачать проект, в папке проекта через консоль выполнить команды:

- для Unix: `./mvnw clean package`
- для Windows: `mvnw.cmd clean package`
- далее:
  `java -jar target/lite-digital-0.0.1-SNAPSHOT.jar`

---

## API 
получить список файлов в папках `фотографии` с префиком `GRP327_`

`curl --location --request GET 'http://localhost:8080/photo`





