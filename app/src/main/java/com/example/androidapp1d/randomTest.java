package com.example.androidapp1d;


import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.util.ArrayList;

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
//        String startTime = "10:55PM";
//        String date = "Friday, 9 December 2017";
//        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("h:ma, EEEE, d MMMM Y");
//        LocalDateTime localDateTime = LocalDateTime.parse(startTime + ", " + date, dateFormat);
//        DateTimeFormatter format2 = DateTimeFormat.forPattern("EEEE, d MMMM Y, h:m a");
//        System.out.println("date : " + format2.print(localDateTime));
//        DateTime utc = localDateTime.toDateTime(DateTimeZone.UTC);
//        long timestamp = utc.getMillis() / 1000;
//        System.out.println("timestamp : " + timestamp);

        //Test 4
//        String startTime = "9:00AM";
//        String endTime = "12:00PM";
//        DateTimeFormatter dtf2 = new DateTimeFormatterBuilder().appendPattern("h:mma").toFormatter();
//        LocalTime localstartTime = LocalTime.parse(startTime, dtf2);
//        LocalTime localendTime = LocalTime.parse(endTime, dtf2);
//        System.out.println(printTimeSlots(localstartTime, localendTime, 30).toString());

//        boolean mBool = true;
//        System.out.println(mBool == true);

        //Test 5
        long timestamp = System.currentTimeMillis() / 1000;
    }
    public static ArrayList<String> printTimeSlots(LocalTime startTime, LocalTime endTime, int slotSizeInMinutes) {
        ArrayList<String> timeArray = new ArrayList<>();
        for (LocalTime time = startTime, nextTime; time.isBefore(endTime); time = nextTime) {
            nextTime = time.plusMinutes(slotSizeInMinutes);
            if (nextTime.isAfter(endTime)) {
                break; // time slot crosses end time
            }
            DateTimeFormatter dtf1 = new DateTimeFormatterBuilder().appendPattern("h:mma").toFormatter();
            timeArray.add(dtf1.print(time) + "-" + dtf1.print(nextTime));
        }
        return timeArray;
    }
}