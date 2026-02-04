package homework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogging implements TestLoggingInterface {

    private final Logger log = LoggerFactory.getLogger(TestLogging.class);

    @Log
    public void calculation(int param) {
        log.info("do calculation");
    }
}
