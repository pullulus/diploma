# diploma

Описание автоматизированных сценариев можно посмотреть здесь - [Plan](https://github.com/pullulus/diploma/tree/master/documents)

Перед запуском автотестов необходимо убедиться, что на компьютере, где они будут запускаться, установлены Docker Desktop, IntelliJ IDEA и Java 11.

#### Как запустить автотесты

1. Открыть в IntelliJ IDEA терминал(Terminal) и запустить контейнеры командой `docker-compose up`
1. После того как контейнеры запустятся, в соседней вкладке терминала запустить приложение командой `java -jar ./artifacts/aqa-shop.jar`
1. Открыть файл `BuyWithCardTest.java` и запустить тесты
1. Открыть файл `BuyWithCreditTest.java` и запустить тесты
