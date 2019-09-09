package com.NativeTech.rehla.Utills;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class MyEditText extends AppCompatEditText {


    private final Context context;
    private AttributeSet attrs;

    public MyEditText(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        this.attrs = attrs;
        int defStyle1 = defStyle;
        init();
    }

    private void init() {
     /*   Typeface font = Typeface.createFromAsset(getContext().getAssets(), "Cairo-Regular.ttf");
        this.setTypeface(font);*/
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
       // tf = Typeface.createFromAsset(getContext().getAssets(), "Cairo-Regular.ttf");
        super.setTypeface(tf, style);
    }

    @Override
    public void setTypeface(Typeface tf) {
      //  tf = Typeface.createFromAsset(getContext().getAssets(), "Cairo-Regular.ttf");
        super.setTypeface(tf);
    }
}