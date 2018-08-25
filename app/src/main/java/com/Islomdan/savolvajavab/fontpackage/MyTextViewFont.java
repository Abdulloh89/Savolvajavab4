package com.Islomdan.savolvajavab.fontpackage;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextViewFont extends TextView {
	
    public MyTextViewFont(Context context) {
        super(context);
    }

    public MyTextViewFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }

    public MyTextViewFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        CustomFontHelper.setCustomFont(this, context, attrs);
    }



}
