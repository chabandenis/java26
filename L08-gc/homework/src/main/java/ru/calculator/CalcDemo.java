package ru.calculator;

/*
-Xms256m
-Xmx256m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/heapdump.hprof
-XX:+UseG1GC
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
*/

/*
-Xms256m
-Xmx256m
spend msec:200297, sec:200

-Xms512m
-Xmx512m
spend msec:231633, sec:231

-Xms768m
-Xmx768m
spend msec:203657, sec:203

-Xms1024m
-Xmx1024m
spend msec:206808, sec:206

-Xms1280m
-Xmx1280m

-Xms1536m
-Xmx1536m

-Xms1792m
-Xmx1792m

-Xms2048m
-Xmx2048m

-Xms4096m
-Xmx4096m



 */


import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalcDemo {
    private static final Logger log = LoggerFactory.getLogger(CalcDemo.class);

    public static void main(String[] args) {
        long counter = 500_000_000;
        var summator = new Summator();
        long startTime = System.currentTimeMillis();

        for (var idx = 0; idx < counter; idx++) {
            var data = new Data(idx);
            summator.calc(data);

            if (idx % 10_000_000 == 0) {
                log.info("{} current idx:{}", LocalDateTime.now(), idx);
            }
        }

        long delta = System.currentTimeMillis() - startTime;
        log.info("PrevValue:{}", summator.getPrevValue());
        log.info("PrevPrevValue:{}", summator.getPrevPrevValue());
        log.info("SumLastThreeValues:{}", summator.getSumLastThreeValues());
        log.info("SomeValue:{}", summator.getSomeValue());
        log.info("Sum:{}", summator.getSum());
        log.info("spend msec:{}, sec:{}", delta, (delta / 1000));
    }
}
