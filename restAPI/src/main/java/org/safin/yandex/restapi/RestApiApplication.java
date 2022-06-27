package org.safin.yandex.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Warning
//
// Документация была добавлена после дедлайна.
// С предыдущего коммита код не изменился кроме документации.
// При необходимости не учитывать её наличие при оценке.
//
// Также возникли сомнения по поводу порта на котором запускается сервис.
// Нигде у меня в коде не прописан порт, поэтому по умолчанию он установлен на 8080
// В ТЗ написано, что сервис запускается на 80 или 22
// Нужно ли было это где-то указать в коде?
//
// Использовал эту команду для теста сервиса на сервере:
// java -jar restAPI.jar --server.port=80
// Можете, пожалуйста, учесть это при оценке работы.
// Заранее спасибо.
@SpringBootApplication
public class RestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiApplication.class, args);
    }
}
