package ru.otus.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Message;

import java.time.LocalDateTime;

public class ProcessorThrowExceptionEvenSecond implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(ProcessorThrowExceptionEvenSecond.class);
    private final DateTimeProvider dateTimeProvider;

    public ProcessorThrowExceptionEvenSecond(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        int seconds = dateTimeProvider.getDate().getSecond();

        logger.info("Секунды {} ", seconds);

        if (seconds%2==0){
            throw new RuntimeException("Seconds are even");
        }

        return message;
    }
}
