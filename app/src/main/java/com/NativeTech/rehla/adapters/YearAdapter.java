package com.NativeTech.rehla.adapters;


import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.NativeTech.rehla.R;

import java.util.ArrayList;

public class YearAdapter extends ArrayAdapter<String> {

    private final ArrayList<String> yearData;
    private final Resources res;

    private final LayoutInflater inflater;

    public YearAdapter(Activity activity, int textViewResourceId, ArrayList<String> yearData, Resources res) {

        super(activity, textViewResourceId, yearData);
        this.yearData = yearData;
        this.res = res;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, parent);
    }


    private View getCustomView(int position, ViewGroup parent) {


        View row = inflater.inflate(R.layout.calendar_data, parent, false);

        TextView label  = (TextView)row.findViewById(R.id.year_txt);


        String yearTxt = yearData.get(position);

        label.setText(yearTxt);

        return row;
    }

}
