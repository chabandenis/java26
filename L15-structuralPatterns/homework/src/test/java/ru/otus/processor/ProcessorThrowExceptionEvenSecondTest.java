package ru.otus.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.MyException;
import ru.otus.handler.ComplexProcessor;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.time.LocalDateTime;
import java.util.List;

class ProcessorThrowExceptionEvenSecondTest {

    private static final Logger logger = LoggerFactory.getLogger(ProcessorThrowExceptionEvenSecondTest.class);

    @Test
    void testProcessorThrowExceptionEvenSecondTest() {
        logger.info("testProcessorThrowExceptionEvenSecondTest");

        List<Processor> processors = List.of(
                new ProcessorThrowExceptionEvenSecond(() -> LocalDateTime.of(2026, 1, 1, 1, 1, 2)
                ));

        var complexProcessor = new ComplexProcessor(processors, ex -> {
            logger.info(ex.toString());
            throw new MyException("Выбросить исключение, для проверки теста");
        });

        ObjectForMessage objectForMessage = new ObjectForMessage();
        objectForMessage.setData(List.of("Матвей", "Роман"));

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field4("field4")
                .field5("field5")
                .field6("field6")
                .field7("field7")
                .field8("field8")
                .field9("field9")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(objectForMessage)
                .build();

        // Проверка выброса исключения
        Assertions.assertThrows(MyException.class, () -> {
            var result = complexProcessor.handle(message);
            logger.info("result:{}", result);
        }, "Ожидается исключение MyException");
    }
}