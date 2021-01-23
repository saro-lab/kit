package me.saro.test.old;

import me.saro.kit.dates.ConstDateTime;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConstDateTimeTest {

    @Test
    public void test() throws Exception {

        final String ymd = "yyyy-MM-dd";
        final String ymdhms = "yyyy-MM-dd HH:mm:ss";

        assertEquals(ConstDateTime.parse("2018-09-23 21:53:00", ymdhms), ConstDateTime.parse("2018-09-23 21:53:00", ymdhms));
        assertNotEquals(ConstDateTime.parse("2018-09-23 21:53:00", ymdhms), ConstDateTime.parse("2018-09-24 21:53:00", ymdhms));

        assertEquals(
                ConstDateTime.parse("2019-12-01 21:53:00", ymdhms).diffDays(ConstDateTime.parse("2017-01-03 01:22:10", ymdhms)),
                ConstDateTime.parse("2017-01-03 01:22:10", ymdhms).diffDays(ConstDateTime.parse("2019-12-01 21:53:00", ymdhms))
        );

        assertEquals(
                ConstDateTime.parse("2019-12-01 21:53:00", ymdhms).diffMonths(ConstDateTime.parse("2017-01-03 01:22:10", ymdhms)),
                ConstDateTime.parse("2017-01-03 01:22:10", ymdhms).diffMonths(ConstDateTime.parse("2019-12-01 21:53:00", ymdhms))
        );

        assertEquals(ConstDateTime.parse("2019-12-31", ymd).remainDaysUntilLastDayOfMonth(), 0);
        assertEquals(ConstDateTime.parse("2019-12-30", ymd).remainDaysUntilLastDayOfMonth(), 1);

        assertTrue(ConstDateTime.parse("2019-12-31", ymd).equalsYearMonth(ConstDateTime.parse("2019-12-01", ymd)));
        assertFalse(ConstDateTime.parse("2019-11-30", ymd).equalsYearMonth(ConstDateTime.parse("2019-12-01", ymd)));
    }

    @Test
    public void abc() {
        String ymd = "yyyy-MM-dd";
        final String ymdhms = "yyyy-MM-dd HH:mm:ss";

        System.out.println(
                ConstDateTime.parse("2019-12-01 21:53:00", ymdhms).diffDays(ConstDateTime.parse("2017-01-03 01:22:10", ymdhms))
        );
        System.out.println(
                ConstDateTime.parse("2017-01-03 01:22:10", ymdhms).diffDays(ConstDateTime.parse("2019-12-01 21:53:00", ymdhms))
        );

        System.out.println(
                ConstDateTime.parse("2019-12-02 21:53:00", ymdhms).diffDays(ConstDateTime.parse("2019-12-01 01:22:10", ymdhms))
        );


    }

}
