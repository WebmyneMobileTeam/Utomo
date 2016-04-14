package com.rovertech.utomo.app.tiles.serviceDate;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.rovertech.utomo.app.helper.Functions;

import java.util.Calendar;

/**
 * Created by sagartahelyani on 22-03-2016.
 */
public class ServiceDatePresenterImpl implements ServiceDatePresenter {

    ServiceDateView serviceDateView;

    public ServiceDatePresenterImpl(ServiceDateView view) {
        this.serviceDateView = view;
    }

    @Override
    public void setDate(Context context) {
        Calendar cal = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                serviceDateView.setDate(date);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        dialog.show();
    }
}
