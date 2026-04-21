package homework;

import annotation.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogging implements TestLoggingInterface {

    private final Logger log = LoggerFactory.getLogger(TestLogging.class);

    @Log
    @Override
    public void calculation(int param) {
        log.info("do calculation 1 param");
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        log.info("do calculation 2 params");
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        log.info("do calculation 3 params");
    }
}
