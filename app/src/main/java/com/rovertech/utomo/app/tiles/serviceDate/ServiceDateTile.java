package com.rovertech.utomo.app.tiles.serviceDate;

import android.app.DatePickerDialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

import java.util.Calendar;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class ServiceDateTile extends LinearLayout implements View.OnClickListener {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private TextView txtTitle, txtServiceDate, txtChangeDate;

    public ServiceDateTile(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ServiceDateTile(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_tile_service_date, this, true);

        findViewById();

        setTypeface();

        txtChangeDate.setOnClickListener(this);

    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(context));
        txtServiceDate.setTypeface(Functions.getNormalFont(context));
        txtChangeDate.setTypeface(Functions.getBoldFont(context));
    }

    private void findViewById() {
        txtChangeDate = (TextView) parentView.findViewById(R.id.txtChangeDate);
        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        txtServiceDate = (TextView) parentView.findViewById(R.id.txtServiceDate);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtChangeDate:
                selectDate();
                break;
        }
    }

    private void selectDate() {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                setDate(date);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        dialog.show();
    }

    public void setDate(String date) {
        txtServiceDate.setText(date);
    }
}
