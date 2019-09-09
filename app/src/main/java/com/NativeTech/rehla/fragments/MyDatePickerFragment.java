package com.NativeTech.rehla.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MyDatePickerFragment extends DialogFragment {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }

    private final DatePickerDialog.OnDateSetListener dateSetListener =
            (view, year, month, day) -> Toast.makeText(getActivity(), "selected date is " + view.getYear() +
                    " / " + (view.getMonth()+1) +
                    " / " + view.getDayOfMonth(), Toast.LENGTH_SHORT).show();
}
