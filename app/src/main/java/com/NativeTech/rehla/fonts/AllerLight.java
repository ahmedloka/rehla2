package com.NativeTech.rehla.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class AllerLight extends android.support.v7.widget.AppCompatTextView {

    public AllerLight(Context context, AttributeSet attrs,
                      int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AllerLight(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AllerLight(Context context) {
        super(context);
        init();
    }

    private void init() {
      /*  Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Aller_Lt.ttf");
        setTypeface(tf);*/
    }

}