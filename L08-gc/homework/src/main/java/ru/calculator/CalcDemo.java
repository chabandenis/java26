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
Очень странные резальтаты.
Перепроверял. Несколько строк это несколько запусков с результатами

Результаты запуска исходного кода. 8Г достаточно, указать больше можно, но прирост минимальный.
Если указать больше памяти, то приводит наоборот к замедлению.
Скорее всего связано с продолжительным выделением памяти.

Минимальная необходимая память для запуска кода без изменений равна 7 мб
После отказа от обверток запускается на 5 мб. На меньшем количестве не запускается.
На 64 мегабайтах работает по скорости аналогично 8Г.

Результаты на модифицированном коде:
На меньших размерах не запускается

-Xms5m
-Xmx5m
spend msec:379739, sec:379

-Xms64m
-Xmx64m
spend msec:148740, sec:148


Результаты на исходном коде:

-Xms5m
-Xmx5m
OutOfMemoryError

-Xms6m
-Xmx6m
OutOfMemoryError

-Xms7m
-Xmx7m
spend msec:454540, sec:454

-Xms10m
-Xmx10m
spend msec:248740, sec:248

-Xms30m
-Xmx30m
spend msec:183260, sec:183

-Xms60m
-Xmx60m
spend msec:174441, sec:174


-Xms256m
-Xmx256m
spend msec:165992, sec:165
spend msec:168287, sec:168
spend msec:169811, sec:169


-Xms512m
-Xmx512m
spend msec:164955, sec:164
spend msec:163428, sec:163
spend msec:164633, sec:164

-Xms768m
-Xmx768m
spend msec:163067, sec:163
spend msec:164320, sec:164

-Xms1024m
-Xmx1024m
spend msec:164024, sec:164
spend msec:166010, sec:166
spend msec:165084, sec:165


-Xms1280m
-Xmx1280m
spend msec:165265, sec:165
spend msec:163591, sec:163
spend msec:162674, sec:162

-Xms1536m
-Xmx1536m
spend msec:168029, sec:168

-Xms1792m
-Xmx1792m
spend msec:160318, sec:160

-Xms2048m
-Xmx2048m
spend msec:159899, sec:159
spend msec:162720, sec:162

-Xms4096m
-Xmx4096m
spend msec:156035, sec:156
spend msec:149989, sec:149
spend msec:152814, sec:152

-Xms6000m
-Xmx6000m
spend msec:152969, sec:152
spend msec:149860, sec:149
spend msec:154777, sec:154

-Xms8192m
-Xmx8192m
spend msec:149388, sec:149
spend msec:149249, sec:149
spend msec:148429, sec:148

-Xms16384m
-Xmx16384m
spend msec:149030, sec:149
spend msec:144881, sec:144
spend msec:152675, sec:152

-Xms32768m
-Xmx32768m
spend msec:164262, sec:164
spend msec:161373, sec:161

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
