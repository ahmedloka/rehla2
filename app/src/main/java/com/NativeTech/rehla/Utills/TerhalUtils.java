package com.NativeTech.rehla.Utills;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

import com.NativeTech.rehla.adapters.RecyclerViewAdapterChatRoom;

import static com.NativeTech.rehla.activities.ChatDetails.adapter2;
import static com.NativeTech.rehla.activities.ChatDetails.messageListView;
import static com.NativeTech.rehla.activities.ChatDetails.messagesView;

public class TerhalUtils {



    public static String getDateWithUTCZone(String date)
    {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date date1 = dateFormat.parse(date);
            return  dateFormat.format(date1);
        } catch (ParseException e) {
            return  dateFormat.format(date);
        }
    }
    public static String getDateWithUCurrentZone(String date)
    {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        try {
            Date date1 = dateFormat.parse(date);
            date1.setTime(date1.getTime()+ TimeZone.getDefault().getRawOffset());
            return  dateFormat.format(date1);
        } catch (ParseException e) {
            return  dateFormat.format(date);
        }
    }

    public static String getTimeWithUTCZone(String time)
    {
        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.ENGLISH);

            SimpleDateFormat oldDateFormat = new SimpleDateFormat("HH:mm:ss",Locale.ENGLISH);
            Date date = inputDateFormat.parse(time);
            oldDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return oldDateFormat.format(date);
        }catch (Exception e)
        {
            return time;
        }

    }
    public static String getTimeWithCurrentZone(String time)
    {
        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                    Locale.ENGLISH);

            SimpleDateFormat oldDateFormat = new SimpleDateFormat("HH:mm:ss",Locale.ENGLISH);
            Date date = inputDateFormat.parse(time);
            date.setTime(date.getTime()+ TimeZone.getDefault().getRawOffset());
            //oldDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return oldDateFormat.format(date);
        }catch (Exception e)
        {
            return time;
        }

    }
    public static String parseDateTo_yyyyMMdd(String time) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss";
        String outputPattern = "yyyy-MM-dd";
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern,Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern,Locale.ENGLISH);

        Date date = null;
        String str = "";

        try {
            date = inputFormat.parse(time);
            date.setTime(date.getTime()+ TimeZone.getDefault().getRawOffset());
            str = outputFormat.format(date);
        } catch (ParseException e) {
            //Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return str;
    }
    public static void initMessages(Context context) {
        adapter2=new RecyclerViewAdapterChatRoom(context,messageListView);
        messagesView.setHasFixedSize(true);
        LinearLayoutManager mRecyclerViewLayoutManager2 = new LinearLayoutManager(context
                , LinearLayoutManager.VERTICAL, false);
        messagesView.setLayoutManager(mRecyclerViewLayoutManager2);
        messagesView.setAdapter(adapter2);
        messagesView.scrollToPosition(Objects.requireNonNull(messagesView.getAdapter()).getItemCount() - 1);
        adapter2.notifyDataSetChanged();
    }

    public static String getFormatedDate(String myDate) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss";
        String outputPattern = "dd MMM yyyy HH:mm a";
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern,Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern,Locale.ENGLISH);

        Date date = null;
        String str = "";

        try {
            date = inputFormat.parse(myDate);
            date.setTime(date.getTime()+ TimeZone.getDefault().getRawOffset());
            str = outputFormat.format(date);
        } catch (ParseException e) {
            //Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return str;
    }
}
