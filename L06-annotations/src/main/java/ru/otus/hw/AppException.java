package ru.otus.hw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppException extends RuntimeException {
    private static final Logger log = LoggerFactory.getLogger(AppException.class);

    public AppException(String message) {
        super(message);
        log.error("Ошибка {}", message);
    }
}
