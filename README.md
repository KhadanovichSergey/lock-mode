Как проверять кейсы?)

1. запустить postgres через docker-compose run -d
2. запустить приложение
3. использовать скрипты из директории sh для проверки различных кейсов

Например, хотим проверить кейс: первая транзация заблокировала запись через PESSIMISTIC_WRITE,
другая транзация пытается выполнить такую же блокироку (PESSIMISTIC_WRITE). Вторая транзакция
вынуждена ждать окончания первой транзации (ее выполнение блокируется до снятие блокировки).
Для проверки запускаем скрипт sh/sc_p_write_load.sh (чтение записи по первичному ключу
с lockMode=PESSIMISTIC_WRITE и эмуляцией полезной нагрузки в 10 секунд). Не дожидаясь окончания
выполнения крипта, запускаем второй скрипт sh/sc_p_write.sh (чтение записи по первичному ключу
с lockMode=PESSIMISTIC_WRITE без эмуляции нагрузки). Вторая транзация вынуждена ждать окочания
первой для того чтобы взять исключающий лок на запись.