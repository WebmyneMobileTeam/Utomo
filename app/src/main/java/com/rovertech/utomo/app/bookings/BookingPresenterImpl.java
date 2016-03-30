package com.rovertech.utomo.app.bookings;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;
import com.rovertech.utomo.app.widget.dialog.SuccessDialog;

import java.util.Calendar;

/**
 * Created by sagartahelyani on 17-03-2016.
 */
public class BookingPresenterImpl implements BookingPresenter {

    private BookingView bookingView;

    public BookingPresenterImpl(BookingView bookingView) {
        this.bookingView = bookingView;
    }

    @Override
    public void fetchDetails() {
        // get service centre details and user-car details

        bookingView.setDetails();
    }

    @Override
    public void selectTime(Context context) {

        Calendar calendar = Calendar.getInstance();

        TimePickerDialog dialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hours, int minutes) {

                getSelectedTime(hours, minutes);

            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        dialog.show();
    }

    private void getSelectedTime(int hours, int mins) {

        String timeStamp = "";
        if (hours > 12) {
            hours -= 12;
            timeStamp = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeStamp = "AM";
        } else if (hours == 12)
            timeStamp = "PM";
        else
            timeStamp = "AM";

        String minutes = "";
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        bookingView.setTime(hours + ":" + minutes + " " + timeStamp);
    }

    @Override
    public void selectDate(Context context) {
        Calendar cal = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                String convertedDate = Functions.parseDate(date, "dd-MM-yyyy", "dd MMMM, yyyy");
                bookingView.setDate(convertedDate);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        dialog.show();
    }

    @Override
    public void book(final Context context) {
        // call ws for booking and alert for success
        final SuccessDialog dialog = new SuccessDialog(context, context.getResources().getString(R.string.success));
        dialog.setOnSubmitListener(new SuccessDialog.onSubmitListener() {
            @Override
            public void onSubmit() {
                Intent intent = new Intent(context, DrawerActivity.class);
                intent.putExtra(AppConstant.FRAGMENT_VALUE, AppConstant.MY_BOOKING_FRAGMENT);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        dialog.show();
    }
}
