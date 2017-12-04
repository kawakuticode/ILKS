package kawakuticode.com.ilks.utilities;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.Period;
import org.threeten.bp.ZoneId;

import java.text.DateFormat;
import java.util.Date;


/**
 * Created by russeliusernestius on 30/10/17.
 */

public class DateUtilities {

    public static String formattDateEvent(Date date){
        return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT).format(date).substring(0,12);
    }

    public static String timeLeft(Date date) {

        LocalDate dateTimeNow = LocalDate.now();
        LocalDate event_date = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(dateTimeNow , event_date );

        String time_left = period.getYears() + "//" + period.getMonths() + "//" + period.getDays();

      return time_left;
    }


    public static boolean datePassed( Date date) {
        LocalDate dateTimeNow = LocalDate.now();
        LocalDate event_date = Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

        return dateTimeNow.isAfter(event_date);
    }


}
