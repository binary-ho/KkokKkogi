package com.blossom.alpacapaca.kkokkkogi;

import android.os.Build;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.time.format.DateTimeFormatter;

public class  MyTime {
    public static String timeNow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            java.time.LocalDateTime date = java.time.LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("h:mm a");
            String str = date.format(dateTimeFormatter);
            return str;
        } else {
            org.joda.time.LocalDateTime date = org.joda.time.LocalDateTime.now();
            org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern("h:mm a");
            DateTime jodaTime = dtf.parseDateTime(date.toString());
            String str = dtf.print(jodaTime);
            return str;
        }
    }
}
