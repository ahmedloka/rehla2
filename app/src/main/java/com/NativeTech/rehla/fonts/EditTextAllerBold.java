package com.NativeTech.rehla.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class EditTextAllerBold extends android.support.v7.widget.AppCompatEditText {

    public EditTextAllerBold(Context context, AttributeSet attrs,
                             int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditTextAllerBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextAllerBold(Context context) {
        super(context);
        init();
    }

    private void init() {
     /*   Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Aller_Bd.ttf");
        setTypeface(tf);*/
    }

}