package com.yhtomit.brayo.bleserial.magic;

import java.util.Calendar;

/**
 * Created by Brayo on 7/9/2015.
 */
public class GetDate {
    private String ampm;
    private String date;
    private String a;

    Calendar c = Calendar.getInstance();
    int day = c.get(Calendar.DATE);
    int month = c.get(Calendar.MONTH);
    int year = c.get(Calendar.YEAR);
    int time = c.get(Calendar.HOUR);
    int min = c.get(Calendar.MINUTE);
    int am = c.get(Calendar.AM_PM);


    public String getDate() {
        if (am == 1)
            ampm = "PM";
         else
            ampm = "AM";

        if (min < 10) {
            if (time == 0)
                date = day + "/ " + month + "/ " + year + " @ " + "0" + time + " : 0" + min + " " + ampm;
            else
                date = day + "/ " + month + "/ " + year + " @ " + time + " : 0" + min + " " + ampm;
        } else {
            if (time == 0)
                date = day + "/ " + month + "/ " + year + " @ " + "0" + time + " : " + min + " " + ampm;
            else
                date = day + "/ " + month + "/ " + year + " @ " + time + " : " + min + " " + ampm;
        }
        return date;
    }



    public String getTime() {
        if (am == 1)
            a = "PM";
         else
            a = "AM";

        if (min < 10) {
            if (time == 0)
                date = "0" + time + " : 0" + min + " " + a;
             else
                date = time + " : 0" + min + " " + a;
        } else {
            if (time == 0)
                date = "0" + time + " : " + min + " " + a;
             else
                date = time + " : " + min + " " + a;
        }
        return date;
    }
}
