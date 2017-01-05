package com.rovertech.utomo.app.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by sagartahelyani on 19-12-2016.
 */

public class DigitTextView extends TextView {

    public DigitTextView(Context context) {
        super(context);
        init();
    }

    public DigitTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setTypeface(Typeface.createFromAsset(getContext().getAssets(), "fonts/digit_font.ttf"), Typeface.BOLD);
    }
}
