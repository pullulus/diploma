# diploma

Описание автоматизированных сценариев можно посмотреть здесь - [Plan](https://github.com/pullulus/diploma/tree/master/documents)

Перед запуском автотестов необходимо убедиться, что на компьютере, где они будут запускаться, установлены Docker Desktop, IntelliJ IDEA и Java 11.

#### Как запустить автотесты

1. Открыть в IntelliJ IDEA терминал(Terminal) и запустить контейнеры командой `docker-compose up`
1. После того как контейнеры запустятся, в соседней вкладке терминала запустить приложение командой:
   _вариант 1_ (с подключением к MySQL)`java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -Dspring.datasource.username=app -Dspring.datasource.password=pass -jar ./artifacts/aqa-shop.jar` 
   _вариант 2_ (с подключением к PostgreSQL)`java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/postgres -Dspring.datasource.username=postgres -Dspring.datasource.password=mysecretpassword -jar ./artifacts/aqa-shop.jar`
1. После того как приложение запустится, в следующей соседней вкладке терминала запустить тесты командой:
    _вариант 1_ (с подключением к MySQL) `gradlew test -Dproperty.url=database_jdbc:mysql://localhost:3306/app -Dproperty.name=database_app -Dproperty.password=database_pass`
    _вариант 2_(с подключением к PostgreSQL) `gradlew test -Dproperty.url=database_jdbc:postgresql:localhost:5432/postgres -Dproperty.name=database_postgres -Dproperty.password=database_mysecretpassword`


