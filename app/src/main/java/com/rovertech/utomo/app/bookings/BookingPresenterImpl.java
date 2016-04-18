package com.rovertech.utomo.app.bookings;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.bookings.model.BookingRequest;
import com.rovertech.utomo.app.bookings.model.RequestForBooking;
import com.rovertech.utomo.app.bookings.model.RequestForBookingResponse;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    public void book(final Context context, BookingRequest bookingRequest) {

        if (bookingRequest == null) {
            return;
        }

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Booking", "Wait while Booking a request.", false, false);

        Log.d("bookrequest", UtomoApplication.getInstance().getGson().toJson(bookingRequest));
        BookingRequestAPI bookingRequestAPI = UtomoApplication.retrofit.create(BookingRequestAPI.class);
        Call<RequestForBooking> requestForBookingCall = bookingRequestAPI.bookingService(bookingRequest);
        requestForBookingCall.enqueue(new Callback<RequestForBooking>() {
            @Override
            public void onResponse(Call<RequestForBooking> call, Response<RequestForBooking> response) {


                if (response.isSuccess()) {

                    RequestForBookingResponse requestForBooking = response.body().RequestForBooking;
                    if (requestForBooking.ResponseCode == 1) {
                        Intent intent = new Intent(context, DrawerActivity.class);
                        intent.putExtra(AppConstant.FRAGMENT_VALUE, AppConstant.MY_BOOKING_FRAGMENT);
                        Functions.
                                showDialog(context,
                                        context.getResources().getString(R.string.Success),
                                        requestForBooking.ResponseMessage, true, intent);

                    } else {

                        Functions.showErrorAlert(context, context.getString(R.string.failed), false);
                    }

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<RequestForBooking> call, Throwable t) {
                progressDialog.dismiss();
                Functions.showErrorAlert(context, context.getString(R.string.failed), false);
            }
        });


    }
}
