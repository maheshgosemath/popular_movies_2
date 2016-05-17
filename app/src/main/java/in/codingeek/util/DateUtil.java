package in.codingeek.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by root on 14/3/16.
 */
public class DateUtil {
    public static String beautifyDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd MMM, yyyy");
        try {
            return sdf1.format(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
