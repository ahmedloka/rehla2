package com.NativeTech.rehla.Utills;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class MyTextViewLight extends AppCompatTextView {

    public MyTextViewLight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextViewLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextViewLight(Context context) {
        super(context);
        init();
    }

    private void init() {
       /* Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "Montserrat-Light.otf");
        setTypeface(tf );*/

    }
}