package com.NativeTech.rehla.interfaces;

import android.view.View;
import android.widget.AdapterView;


public interface ItemClickListener {
    void onClick(View view, int position);
    void onClick_en(View view, int position);
    void onClick_message(View view, int position);
    void onCardClick(AdapterView<?> parent, View view, int position, long id);

}
