package ru.fmd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        GregorianCalendar calendar = new GregorianCalendar();
        Date firstDate = getDateFromConsole("Введите дату в формате dd.MM.yyyy:");
        Date secondDate;

        calendar.setTime(firstDate);

        System.out.printf("Дата после увеличения на 45 дней: %s\n",
                addDays((GregorianCalendar) calendar.clone(), sdf, 45));

        System.out.printf("Дата после сдвига на начало года: %s\n",
                backToTheNewYear((GregorianCalendar) calendar.clone(), sdf));

        System.out.printf("Дата после увеличения на 10 рабочих дней: %s\n",
                addBusinessDays((GregorianCalendar) calendar.clone(), sdf, 10) );

        secondDate = getDateFromConsole("Введите вторую дату в формате dd.MM.yyyy:");
        System.out.printf("Количество рабочих дней между введенными датами: %d\n",
                calcBusinessDaysBetweenDates(firstDate, secondDate));
    }

    public static Date getDateFromConsole(String msg){
        Scanner scan = new Scanner(System.in);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String strDate;
        boolean inputFlag = true;
        System.out.println(msg);
        while(inputFlag){
            try{
                strDate = scan.nextLine();
                date = sdf.parse(strDate);
                inputFlag = false;
            } catch (ParseException e) {
                System.out.println("Введён неверный формат даты. Повторите ввод в формате dd.MM.yyyy:");
            }
        }
        return date;
    }

    public static String addDays(GregorianCalendar calendar, SimpleDateFormat sdf, int days){
        calendar.add(Calendar.DATE, days);
        return sdf.format(calendar.getTime());
    }

    public static String backToTheNewYear(GregorianCalendar calendar, SimpleDateFormat sdf){
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        return sdf.format(calendar.getTime());
    }

    public static String addBusinessDays(GregorianCalendar calendar, SimpleDateFormat sdf, int businessDaysCount){
        int addedDays = 0;
        while (addedDays < businessDaysCount){
            calendar.add(Calendar.DATE, 1);
            if(isBusinessDay(calendar))
                addedDays++;
        }
        return sdf.format(calendar.getTime());
    }

    public static int calcBusinessDaysBetweenDates(Date firstDate, Date secondDate){
        if(firstDate.compareTo(secondDate) > 0){
            Date tmp = firstDate;
            firstDate = secondDate;
            secondDate = tmp;
        }

        int countDays = (int)((secondDate.getTime() - firstDate.getTime()) / (1000 * 60 * 60 * 24));
        int countBusinessDays = 0;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(firstDate);

        for(int i = 0; i < countDays; i++){
            if(isBusinessDay(calendar))
                countBusinessDays++;
            calendar.add(Calendar.DATE, 1);
        }
        return countBusinessDays;
    }

    public static boolean isBusinessDay(GregorianCalendar calendar){
        return calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY
                && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY;
    }
}