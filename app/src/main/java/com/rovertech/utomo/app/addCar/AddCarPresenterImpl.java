package com.rovertech.utomo.app.addCar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.rovertech.utomo.app.helper.Functions;

import java.util.Calendar;

/**
 * Created by sagartahelyani on 09-03-2016.
 */
public class AddCarPresenterImpl implements AddCarPresenter {

    AddcarView addcarView;

    public AddCarPresenterImpl(AddcarView addcarView) {
        this.addcarView = addcarView;
    }

    @Override
    public void selectPUCDate(Context context) {
        Calendar cal = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                String convertedDate = Functions.parseDate(date, "dd-MM-yyyy", "dd MMMM, yyyy");
                addcarView.setPUCDate(convertedDate);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        dialog.show();
    }

    @Override
    public void selectInsuranceDate(Context context) {
        Calendar cal = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                String convertedDate = Functions.parseDate(date, "dd-MM-yyyy", "dd MMMM, yyyy");
                addcarView.setInsuranceDate(convertedDate);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        dialog.show();
    }
}
