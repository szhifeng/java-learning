package basics;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalQuery;

/**
 * @author szf
 * @describe:
 * @Date 2022/9/1 11:01
 *
 */
public class TimeTest {

    /**
     * Obtains the current instant from the system clock.
     * @see java.time.Instant
     */
    @Test
    void instantTest() {
        Instant now = Instant.now();
        long milli = now.toEpochMilli();
        System.out.println(milli);
        LocalDateTime time = LocalDateTime.ofInstant(now, ZoneOffset.ofHours(8));
        LocalDateTime time1 = LocalDateTime.ofInstant(Instant.ofEpochMilli(milli), ZoneOffset.ofHours(8));
        LocalDateTime time2 = LocalDateTime.ofInstant(Instant.ofEpochMilli(milli), ZoneOffset.ofHours(8)).withMinute(0).withSecond(0).withNano(0);;
        LocalDateTime time3 = LocalDateTime.ofInstant(Instant.ofEpochSecond(milli/1000), ZoneOffset.ofHours(8));
        LocalDateTime timeSe = LocalDateTime.ofEpochSecond(milli/1000, 0, ZoneOffset.ofHours(8));
        System.out.println(time + "<===>" + time1 + "===>" +time2  + "===>" +time3 + "====>" + timeSe);
    }

    /**
     * A date-based amount of time in the ISO-8601 calendar system, such as '2 years, 3 months and 4 days'.
     * @see java.time.Period
     */
    @Test
    void periodTest() {
        LocalDate startTime = LocalDate.of(2021, 7,21);
        LocalDate endTime = LocalDate.of(2022, 7, 1);
        Period period = Period.between(startTime, endTime);
        Period period2 = Period.between(endTime, startTime);
        System.out.println(period.getYears() + "年==" +period.getMonths() + "月 ==" + period.getDays() + "天");
        System.out.println(period2.getDays());
    }

    /**
     * A time-based amount of time, such as '34.5 seconds'.
     * This class models a quantity or amount of time in terms of seconds and nanoseconds. It can be accessed using other duration-based units,
     * such as minutes and hours. In addition, the DAYS unit can be used and is treated as exactly equal to 24 hours, thus ignoring daylight savings effects.
     * See Period for the date-based equivalent to this class.
      * @see java.time.Duration
     */
    @Test
    void durationTest() {
        LocalDateTime startTime = LocalDateTime.of(2022, 6,21, 12,0);
        LocalDateTime endTime = LocalDateTime.of(2022, 7, 1,10,0);
        Duration duration = Duration.between(startTime, endTime);
        //时间
        LocalTime time = LocalTime.now();
        System.out.println(time);
        System.out.println(duration.toHours());
        System.out.println(duration.toDays());
    }

    /**
     * A clock providing access to the current instant, date and time using a time-zone.
     * Instances of this class are used to find the current instant,
     * which can be interpreted using the stored time-zone to find the current date and time. As such,
     * a clock can be used instead of System.currentTimeMillis() and TimeZone.getDefault().
     * @see java.time.Clock
     */
    @Test
    void clockTest() {
        Clock clock = Clock.systemDefaultZone();
        System.out.println("当前瞬时：" + clock.millis() + "；当前时区：" + clock.getZone());
        Clock clock2 = Clock.system(ZoneId.systemDefault());
        System.out.println("当前瞬时：" + clock2.millis() + "；当前时区：" + clock.getZone());
        System.out.println("当前瞬时：" + clock.instant());
        System.out.println("当前20 分钟后：" + Clock.offset(clock, Duration.ofMinutes(20)).instant());
        System.out.println("当前纽约时间：" + Clock.system(ZoneId.of("America/New_York")).instant());
        System.out.println("当前 UTC 时间：" + Clock.systemUTC().instant());
        System.out.println("相同时刻：" + Clock.fixed(clock.instant(), clock.getZone()));
    }

    /**
     * A time-zone ID, such as Europe/Paris.
     * @see java.time.ZoneId
     */
    @Test
    void zonedTest() {
        // create TemporalAccessor object
        ZonedDateTime zoneddatetime = ZonedDateTime.parse("2018-10-25T23:12:31.123+02:00[Europe/Paris]");
        // get ZoneId from this TemporalAccessor
        ZoneId response = ZoneId.from(zoneddatetime);
        // print result
        System.out.println("Zone Id got from TemporalAccessor object：" + zoneddatetime + " is " + response);
        System.out.println(ZoneId.from(ZonedDateTime.now()));
    }

    /**
     * A standard set of date periods units.
     * @see java.time.temporal.ChronoUnit
     */
    @Test
    void cronUnitTest() {
        LocalDate today = LocalDate.now();
        System.out.println("当前时间: " + today); //2021-12-02
        //LocalDate plus(long amountToAdd, TemporalUnit unit)
        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
        System.out.println("下周的时间: " + nextWeek); //2021-12-09
        LocalDate nextMonth = today.plus(1, ChronoUnit.MONTHS);
        System.out.println("下个月的时间: " + nextMonth); //2022-01-02
        LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
        System.out.println("下一年的时间: " + nextYear);//2022-12-02
        LocalDate nextDecade = today.plus(1, ChronoUnit.DECADES);
        System.out.println("10年后的时间: " + nextDecade);//2031-12-02
    }

    /**
     * Strategy for querying a temporal object.
     * @see java.time.LocalDate#query(TemporalQuery)
     */
    @Test
    void temporalQueryTest() {
        TemporalQuery<String> remainDaysOfYear = temporal -> {
            LocalDate d = LocalDate.from(temporal);
            LocalDate lastDayOfYear = d.with(TemporalAdjusters.lastDayOfYear());
            Period period = d.until(lastDayOfYear);
            return period.getMonths() + "月" + period.getDays() + "天";
        };
        System.out.println("到年底还有多久:" + LocalDate.now().query(remainDaysOfYear));
    }
}
