package com.Islomdan.savolvajavab.fontpackage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class MyButtonFont extends Button {

    public MyButtonFont(Context context) {
        super(context);
    }

    public MyButtonFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

    public MyButtonFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }
}
