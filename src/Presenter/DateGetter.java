package Presenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public interface DateGetter {
    default String getDateFormat1(){
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd | HH:mm:ss");
        return dateFormat.format(date);
    }

    default String getDateFormat2(){
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return dateFormat.format(date);
    }
}
