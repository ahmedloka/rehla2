package com.NativeTech.rehla.fonts;

public class AllerBold extends android.support.v7.widget.AppCompatTextView {

    public AllerBold(android.content.Context context, android.util.AttributeSet attrs,
                     int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public AllerBold(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AllerBold(android.content.Context context) {
        super(context);
        init();
    }

    private void init() {
       /* android.graphics.Typeface tf = android.graphics.Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Aller_Bd.ttf");
        setTypeface(tf);*/
    }

}