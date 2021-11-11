# Задание
 
## Структура данных

### В приложении должна быть сущность Account (счет) содержащая поля:
   #### ID (строковое) - идентификатор счета
   #### Money (целочисленное) - сумма средств на счете.

## Функциональные требования

  ### При запуске приложение должно создать четыре (или более) экземпляров объекта Account со случайными значениями ID и значениями money равным 10000.
    В приложении запускается несколько (не менее двух) независимых потоков. Потоки должны просыпаться каждые 1000-2000 мс. Время на которое засыпает поток выбирается случайно при каждом исполнении.
    Потоки должны выполнять перевод средств с одного счета на другой. Сумма списания или зачисления определяется случайным образом. Поле money не должно становиться отрицательным, сумма money на всех счетах не должна меняться.
    Решение должно быть масштабируемым по количеству счетов и потоков и обеспечивать возможность одновременного (параллельного) перевода средств со счета a1 на счет a2 и со счета a3 на счет а4 в разных потоках.
    Результаты всех транзакций должны записываться в лог.
    После 30 выполненных транзакций приложение должно завершиться.


# Стэк технологий:

 ### •	Язык программирования: Java 8(JDK 17);
 ### •	Frameworks: Spring Boot;
 ### •	База данных: Postgres 13 SQL;
 ### •	Библиотека для генерации документации: springdoc-openapi v1.5.9;
 ### •	Библиотека реализации логирования: Log4j.

# Запуск приложения:

#### 1)Загрузка приложения с данного репозитория;
#### 2) Создание базы данных, и после реализации подпункта 3, при запуске приложение само проинициализирует таблицу, хранящую информацуию о счетах;
#### 3) В файле application.properties прописываем свойства подключения к БД
##### \\Ссылка на бд
##### spring.datasource.url=jdbc:postgresql://localhost:5432/test21 
##### \\Логин пользователя
##### spring.datasource.username=postgres
##### \\Пароль пользователя
##### spring.datasource.password=060596
##### \\При необохдимости меняем порт
##### server.port = 8080;
#### 4)Запуск приложения;
#### 5)Открываем в браузере http://localhost:8090/swagger.html;
#### 6)Пользовательский интерфейс предоставляет 3 метода: на добавление счетов в БД, получение счетов из БД, и проведение транзакций между счетами;
#### 7)Для реализации задачи по проведению транзакций необоходимо:
##### a)Добавить в БД хотябы 2 счета, используя метод POST предоставленный пользовательским интерфейсом;
##### б)В методе PUT, пользовательского интерфейса, необходимо задать количество транзакций, которые не обходимо провести, и запустить метод на выполнение;
#### 8)Файл логирования расположен в корне проекта в папке logs


    © 2021 GitHub, Inc.
    Terms
    Privacy
    Security
    Status
    Docs

