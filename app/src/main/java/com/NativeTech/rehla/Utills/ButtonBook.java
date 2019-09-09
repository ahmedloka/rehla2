package com.NativeTech.rehla.Utills;

import android.content.Context;
import android.util.AttributeSet;

public class ButtonBook extends android.support.v7.widget.AppCompatButton {

    public ButtonBook(Context context) {
        super(context);
    }

    public ButtonBook(Context context, AttributeSet attrs) {
        super(context, attrs);
        FontHelperbook.setCustomFont(this, context);
    }

    public ButtonBook(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        FontHelperbook.setCustomFont(this, context);
    }
}
