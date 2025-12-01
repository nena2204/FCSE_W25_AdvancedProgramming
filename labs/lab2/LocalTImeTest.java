package lab.lab_2;

import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

/**
 * LocalTime API tests
 */
public class LocalTimeTest {
    public static void main(String[] args) {
        System.out.println(localTimeOfHourToMinute());
        System.out.println(localTimeOfHourToNanoSec());
        System.out.println(localTimeParse());
        System.out.println(localTimeWith());
        System.out.println(localTimePlus());
        System.out.println(localTimeMinus());
        System.out.println(localTimeMinusDuration());
        System.out.println(localDateIsBefore());
        System.out.println(localTimeTruncatedTo());
    }

    static LocalTime localTimeOfHourToMinute() {
        //23:7:30:5000 lt
        return LocalTime.of(23,7);
    }
    //2  23:07:03.100 to print

    static LocalTime localTimeOfHourToNanoSec() {

        return LocalTime.of(23,7,3,100000000);
    }

    static LocalTime localTimeParse() {
        //3  23:07:03.100
        return LocalTime.parse("23:07:03.100") ;
    }

    static LocalTime localTimeWith() {
        LocalTime lt = DateAndTimes.LT_23073050;
        //4 21:7:30.500
        return lt.withHour(21);
    }

    static LocalTime localTimePlus() {
        LocalTime lt = DateAndTimes.LT_23073050;
        //5 23:37:30.500

        return lt.plusMinutes(30);
    }

    static LocalTime localTimeMinus() {
        LocalTime lt = DateAndTimes.LT_23073050;
        //6 20:07:30.500
        return lt.minusHours(3);
    }

    static LocalTime localTimeMinusDuration() {
        LocalTime lt = DateAndTimes.LT_23073050;
        //7 19:37:10.300
        //s 23:07:30.500
        //ralzika  3.30.20.200
        Duration duration= Duration.ofHours(3).plusMinutes(30).plusSeconds(20).plusNanos(200000000);
        return lt.minus(duration);
    }

    static boolean localDateIsBefore() {
        LocalTime lt = DateAndTimes.LT_23073050;
        LocalTime lt2 = DateAndTimes.LT_12100000;


        return lt2.isBefore(lt);
    }

    static LocalTime localTimeTruncatedTo() {
        LocalTime lt = DateAndTimes.LT_23073050;

        return lt.truncatedTo(ChronoUnit.MINUTES);
    }

    static class DateAndTimes {
        public static final LocalTime LT_23073050 = LocalTime.of(23, 7, 30, 500000000);
        public static final LocalTime LT_12100000 = LocalTime.of(12, 10);
    }

}
