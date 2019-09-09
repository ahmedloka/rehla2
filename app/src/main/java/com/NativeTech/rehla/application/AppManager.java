package com.NativeTech.rehla.application;

import android.app.Activity;
import android.content.Context;

import java.util.Calendar;


public class AppManager {

    private static AppManager appManager;
    private static Context appContext;
    private static Activity appActivity;

    private static int selectedYear;
    private static int selectedMonth;

    private static String selectedHoliday;

    public static void getInstance(Context context) {

        if (appManager == null) {

            appManager = new AppManager();
            appContext = context;
        }
    }

    public static Context getContext() {
        return appContext;
    }
    public static void setActivity(Activity activity){
        appActivity = activity;
    }

    public static Activity getActivity(){
        return appActivity;
    }

    public static int getSelectedYear() {
        return selectedYear;
    }

    public static void setSelectedYear(int selectedYear) {
        AppManager.selectedYear = selectedYear;
    }

    public static int getSelectedMonth() {
        return selectedMonth;
    }

    public static void setSelectedMonth(int selectedMonth) {
        AppManager.selectedMonth = selectedMonth;
    }

    public static String getSelectedHoliday() {
        return selectedHoliday;
    }

    public static void setSelectedHoliday(String selectedHoliday) {
        AppManager.selectedHoliday = selectedHoliday;
    }

    public static boolean checkIfHoliday(int year,int month, int day) {

        String mn = "";

        if(month<10){
            mn = "0"+month;
        } else {
            mn = ""+month;
        }

        int yr = year - 2000;


        String dy = "";
        if(day<10){
            dy = "0"+day;
        } else {
            dy = ""+day;
        }

            String date1 = dy+"-"+mn+"-"+yr;
            String date2 = dy+"-"+mn+"-"+year;

        return false;
    }

    public static String getCurrentDate(int flag) {

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);

        int month = calendar.get(Calendar.MONTH);

        int date = calendar.get(Calendar.DAY_OF_MONTH);

        int hour = calendar.get(Calendar.HOUR);

        int min = calendar.get(Calendar.MINUTE);

        if(flag==1) {

            return Constant.month[month] + " " + date + " , " + year + " - " + hour + ":" + min;
        } else if(flag==2) {

            int months = month + 1;

            return year+"-"+months+"-"+date;

        } else if(flag==3) {

            int months = month + 1;

            return year+"-"+months;
        } else  {

            return Constant.month[month] + " " + date + " , " + year;
        }
    }

    public static int getCurrentMonth(){
        Calendar cal = Calendar.getInstance();

        return cal.get(Calendar.MONTH);

    }

    public static int getCurrentYear(){
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    public static int getCurrentDayOfMonth(){

        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);

    }


    public static String getCurrentTime(){

        Calendar cal = Calendar.getInstance();

        int hr = cal.get(Calendar.HOUR);

        int mn = cal.get(Calendar.MINUTE);

        int am_or_pm = cal.get(Calendar.AM_PM);

        String minTXT = ""+mn;

        String hrTXT = ""+hr;

        String am_pm = "";

        if(am_or_pm==0){
            am_pm = "AM";
        } else {
            am_pm = "PM";
        }

        if(mn<10){
            minTXT = "0"+mn;
        }

        if(hr<10){
            hrTXT = "0"+hr;
        }

        return hrTXT+":"+minTXT+" "+am_pm;

    }


}
