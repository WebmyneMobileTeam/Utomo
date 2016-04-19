package com.rovertech.utomo.app.tiles.serviceDate;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class ServiceDateTile extends LinearLayout implements View.OnClickListener {

    Context context;
    private View parentView;
    private LayoutInflater inflater;
    private String date;
    private TextView txtTitle, txtServiceDate, txtChangeDate;

    private onDateSetListener onDateSetListener;

    public void setOnDateSetListener(ServiceDateTile.onDateSetListener onDateSetListener) {
        this.onDateSetListener = onDateSetListener;
    }

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
        txtServiceDate.setTypeface(Functions.getRegularFont(context));
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

        Calendar now = Calendar.getInstance();

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        setDate(date);
                        if (onDateSetListener != null)
                            onDateSetListener.setDate(date, AppConstant.MODE_DATE);
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(((Activity) context).getFragmentManager(), "Select Date");
    }

    public void setDate(String date) {
        if (date.equals(""))
            txtServiceDate.setText("NA");
        else
            txtServiceDate.setText(date);
    }

    public interface onDateSetListener {
        void setDate(String date, int mode);
    }
}
