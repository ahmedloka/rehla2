package com.NativeTech.rehla.Utills;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

class FontHelperbold {


    static void setCustomFont(TextView textview, Context context) {
        /*String font = "Cairo-Bold.ttf";
        setCustomFont(textview, font, context);*/
    }

    private static void setCustomFont(TextView textview, String font, Context context) {
        if(font == null) {
            return;
        }
   /*      Typeface tf = FontCache.get(font, context);
        if(tf != null) {
            textview.setTypeface(tf);
        }*/
    }

}