package com.example.androidapp1d;


import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by ASUS on 12/7/2017.
 */

public class randomTest {
    public static void main(String[] args) {
//        if(DateUtils.isToday(1512604800)){
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm:ss:S");
//            String time = simpleDateFormat.format(1512604800);
//            System.out.println(time);
//        }

        DateTimeFormatter date = DateTimeFormat.forPattern("EEEE, d MMMM Y");
        DateTimeFormatter time = DateTimeFormat.forPattern("h:m:s a");
        LocalDateTime localDateTime = new LocalDateTime();

        System.out.println("date : " + date.print(localDateTime));
        System.out.println("date : " + time.print(localDateTime));
    }
}