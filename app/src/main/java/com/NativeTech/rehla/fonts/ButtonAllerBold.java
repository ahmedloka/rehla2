package com.NativeTech.rehla.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class ButtonAllerBold extends android.support.v7.widget.AppCompatButton {

    public ButtonAllerBold(Context context, AttributeSet attrs,
                           int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ButtonAllerBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonAllerBold(Context context) {
        super(context);
        init();
    }

    private void init() {
       /* Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Aller_Bd.ttf");
        setTypeface(tf);*/
    }

}