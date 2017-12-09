package com.example.androidapp1d;


import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by ASUS on 12/7/2017.
 */

public class randomTest {
    public static void main(String[] args) {
        //Test 0
//        if(DateUtils.isToday(1512604800)){
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm:ss:S");
//            String time = simpleDateFormat.format(1512604800);
//            System.out.println(time);
//        }

        //Test 1
//        LocalDate localDate = new LocalDate(2017, 12, 8);
//        DateTimeFormatter date = DateTimeFormat.forPattern("EEEE, d MMMM Y");
//        System.out.println("date : " + date.print(localDate));
//        String day = localDate.dayOfWeek().getAsText(Locale.ENGLISH);
//        System.out.println("day: " + day);

        //Test 2
//        LocalDateTime localDateTime = new LocalDateTime();
//        String day = localDateTime.dayOfWeek().getAsText(Locale.ENGLISH);
//        System.out.println("day: " + day);
//        DateTimeFormatter time = DateTimeFormat.forPattern("h:m:s a");
//        System.out.println("date : " + time.print(localDateTime));

        //Test 3
        String startTime = "10:55PM";
        String date = "Friday, 9 December 2017";
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("h:ma, EEEE, d MMMM Y");
        LocalDateTime localDateTime = LocalDateTime.parse(startTime + ", " + date, dateFormat);
        DateTimeFormatter format2 = DateTimeFormat.forPattern("EEEE, d MMMM Y, h:m a");
        System.out.println("date : " + format2.print(localDateTime));
        DateTime utc = localDateTime.toDateTime(DateTimeZone.UTC);
        long timestamp = utc.getMillis() / 1000;
        System.out.println("timestamp : " + timestamp);
    }
}