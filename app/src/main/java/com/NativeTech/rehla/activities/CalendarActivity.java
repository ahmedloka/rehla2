package com.NativeTech.rehla.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.NativeTech.rehla.R;
import com.NativeTech.rehla.adapters.DayAdapter;
import com.NativeTech.rehla.adapters.MonthAdapter;
import com.NativeTech.rehla.adapters.YearAdapter;
import com.NativeTech.rehla.application.AppManager;
import com.NativeTech.rehla.application.Constant;
import com.NativeTech.rehla.calendarmanager.CalendarManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CalendarActivity extends AppCompatActivity {

    private Spinner mYearSpinner;
    private Spinner mMonthSpinner;
    private Spinner day_spinner;
    //GridView mCalendarView;

    private int selectedYear = 0;
    private int selectedMonth = 0;

    private ArrayList<String> yearDataList;
    private ArrayList<String> monthDataList;
    private final ArrayList<String>  dayDataList= new ArrayList<>();
    private ArrayList<HashMap<String, String>> mCalendarDatesList;

    private final String[] month = CalendarManager.month;

    private YearAdapter yearAdapter;
    private MonthAdapter monthAdapter;
    private DayAdapter dayAdapter;
    //CalendarAdapter mCalendarAdapter;


    private Resources res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mYearSpinner = findViewById(R.id.year_spinner);
        mMonthSpinner = findViewById(R.id.month_spinner);
        day_spinner = findViewById(R.id.day_spinner);
        //mCalendarView = findViewById(R.id.calendar_history);

        yearDataList = new ArrayList<>();
        monthDataList = new ArrayList<>();
        mCalendarDatesList = new ArrayList<>();

        selectedYear = AppManager.getCurrentYear();
        selectedMonth = AppManager.getCurrentMonth();

        int yearPos = 0;
        int c = 0;
        for (int i = 1900; i < 3000; i++) {
            yearDataList.add("" + i);

            if(i==selectedYear)
                yearPos = c;

            c++;
        }

        monthDataList.addAll(Arrays.asList(month));

        res = getResources();

        yearAdapter = new YearAdapter(this, R.layout.calendar_data, yearDataList, res);
        mYearSpinner.setAdapter(yearAdapter);

        monthAdapter = new MonthAdapter(this, R.layout.calendar_data, monthDataList, res);
        mMonthSpinner.setAdapter(monthAdapter);

        dayAdapter = new DayAdapter(this, R.layout.calendar_data, dayDataList, res);
        day_spinner.setAdapter(dayAdapter);

        /* mCalendarAdapter = new CalendarAdapter(this, mCalendarDatesList);
        mCalendarView.setAdapter(mCalendarAdapter);*/

        AppManager.setSelectedYear(selectedYear);
        AppManager.setSelectedMonth(selectedMonth + 1);
        prepareCalendar(selectedYear, selectedMonth);

        mYearSpinner.setSelection(yearPos);
        mMonthSpinner.setSelection(AppManager.getCurrentMonth());


        mYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = Integer.parseInt(yearDataList.get(position));
                AppManager.setSelectedYear(selectedYear);
                prepareCalendar(selectedYear, selectedMonth);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mMonthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMonth = position;
                AppManager.setSelectedMonth(selectedMonth + 1);
                prepareCalendar(selectedYear, selectedMonth);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void prepareCalendar(int sYear, int sMonth) {

        dayDataList.clear();
        mCalendarDatesList.clear();
        mCalendarDatesList.addAll(CalendarManager.prepareCalendar(sYear, sMonth));
       // mCalendarAdapter.notifyDataSetChanged();
        getDays();

    }
    private void getDays(){
        for (int i=0;i<mCalendarDatesList.size();i++)
        {
            HashMap<String, String> dateList = mCalendarDatesList.get(i);
            String result = "";
            if(dateList.containsKey(Constant.PRESENT_DAYS))
            {
                result = dateList.get(Constant.PRESENT_DAYS);
                dayDataList.add(String.valueOf(result));
            }

        }
        dayAdapter = new DayAdapter(this, R.layout.calendar_data, dayDataList, res);
        day_spinner.setAdapter(dayAdapter);

    }

}
