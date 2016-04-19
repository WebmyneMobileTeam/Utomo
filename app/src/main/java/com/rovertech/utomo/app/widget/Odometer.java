package com.rovertech.utomo.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

import java.lang.reflect.Field;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;

/**
 * Created by sagartahelyani on 09-03-2016.
 */
public class Odometer extends LinearLayout {

    Context context;
    View parentView;
    private LayoutInflater inflater;
    private LinearLayout linearPicker;
    private NumberPicker.OnValueChangeListener changeListener;
    private onOdometerChangeListener onOdometerChangeListener;
    private String originalOdometerReading;

    public void setOnOdometerChangeListener(Odometer.onOdometerChangeListener onOdometerChangeListener) {
        this.onOdometerChangeListener = onOdometerChangeListener;
    }

    public Odometer(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public Odometer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Options);
        int title = a.getInt(R.styleable.Options_slots, 0);
        a.recycle();

        setSlots(title);

    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_odometer, this, true);

        linearPicker = (LinearLayout) parentView.findViewById(R.id.linearPicker);

        setGravity(Gravity.CENTER);

        changeListener = new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

                if (onOdometerChangeListener != null) {
                    onOdometerChangeListener.readingChange();
                }
            }
        };
    }

    public void setSlots(int num) {

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams((int) Functions.convertDpToPixel(36, context),
                (int) Functions.convertDpToPixel(76, context));
        params.addRule(RelativeLayout.CENTER_IN_PARENT);

        for (int i = 0; i < num; i++) {

            final MaterialNumberPicker numberPicker = new MaterialNumberPicker.Builder(context)
                    .minValue(0)
                    .maxValue(9)
                    .defaultValue(0)
                    .separatorColor(Color.TRANSPARENT)
                    .textColor(ContextCompat.getColor(context, R.color.theme_purple_accent))
                    .textSize(16)
                    .enableFocusability(true)
                    .wrapSelectorWheel(true)
                    .build();

            numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
            //  setTextColor(numberPicker, Color.WHITE);
            numberPicker.setBackgroundResource(R.drawable.rounded_shape);
            numberPicker.setGravity(Gravity.CENTER);
            numberPicker.setOnValueChangedListener(changeListener);

            linearPicker.addView(numberPicker, params);
        }

        requestLayout();
        invalidate();
    }

    private boolean setTextColor(MaterialNumberPicker numberPicker, int color) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public String getValue() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < linearPicker.getChildCount(); i++) {
            MaterialNumberPicker picker = (MaterialNumberPicker) linearPicker.getChildAt(i);
            sb.append(picker.getValue()).append("");
        }
        return sb.toString();
    }

    public void reset() {
        for (int i = 0; i < linearPicker.getChildCount(); i++) {
            MaterialNumberPicker picker = (MaterialNumberPicker) linearPicker.getChildAt(i);
            picker.setValue(0);
        }
    }

    public void setValue(String odometerReading) {

        originalOdometerReading = odometerReading;

        for (int i = 0; i < linearPicker.getChildCount(); i++) {
            MaterialNumberPicker picker = (MaterialNumberPicker) linearPicker.getChildAt(i);
            picker.setValue(Integer.parseInt(String.valueOf(odometerReading.charAt(i))));
        }
    }

    public interface onOdometerChangeListener {
        void readingChange();
    }
}
