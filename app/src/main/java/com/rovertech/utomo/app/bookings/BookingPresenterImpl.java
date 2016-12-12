package com.rovertech.utomo.app.bookings;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.bookings.model.BookingRequest;
import com.rovertech.utomo.app.bookings.model.RequestForBooking;
import com.rovertech.utomo.app.bookings.model.RequestForBookingResponse;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.helper.RetrofitErrorHelper;
import com.rovertech.utomo.app.main.booking.FetchAddressApi;
import com.rovertech.utomo.app.main.booking.model.AddressResponse;
import com.rovertech.utomo.app.main.drawer.DrawerActivityRevised;
import com.rovertech.utomo.app.profile.carlist.CarPojo;
import com.rovertech.utomo.app.profile.carlist.model.FetchVehicleRequest;
import com.rovertech.utomo.app.profile.carlist.model.VehicleListResponse;
import com.rovertech.utomo.app.profile.carlist.service.FetchVehicleListService;
import com.rovertech.utomo.app.widget.dialog.CarListDialog;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 17-03-2016.
 */
public class BookingPresenterImpl implements BookingPresenter {

    private BookingView bookingView;
    private Context context;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM, yyyy");

    public BookingPresenterImpl(BookingView bookingView, Context context) {
        this.bookingView = bookingView;
        this.context = context;
    }

    @Override
    public void fetchDetails() {
        // get service centre details and user-car details

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Loading..", "Please wait..", false);

        FetchAddressApi api = UtomoApplication.retrofit.create(FetchAddressApi.class);
        Call<AddressResponse> call = api.fetchAddress(PrefUtils.getUserID(context));
        call.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                progressDialog.dismiss();

                if (response.body() == null) {
                    //Functions.showToast(context, "Cannot load previous address");
                } else {
                    Log.e("address", Functions.jsonString(response.body()));

                    AddressResponse res = response.body();

                    if (res.GetLastPickupDropOfUser.ResponseCode == 1) {
                        bookingView.setAddress(res.GetLastPickupDropOfUser.Data);
                    } else {
                        //  Functions.showToast(context, "No records for previous address");
                    }
                }
            }

            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                progressDialog.dismiss();
                RetrofitErrorHelper.showErrorMsg(t, context);
            }
        });

        bookingView.setDetails();
    }

    @Override
    public void selectTime(Context context, String date) {

        if (date == null || date.length() == 0) {
            Functions.showErrorAlert(context, "Error", "Select date first");

        } else {
            Date todayDate = new Date();
            Date selectedDate;
            boolean isMinimum = false;

            try {
                selectedDate = dateFormat.parse(date);
                if (selectedDate.compareTo(todayDate) <= 0) {
                    isMinimum = true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();

            TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
                    getSelectedTime(hourOfDay, minute);
                }
            }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), calendar.get(Calendar.MINUTE), false);
            if (isMinimum) {
                timePickerDialog.setMinTime(calendar.get(Calendar.HOUR_OF_DAY) + 1, calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND));
            }
            timePickerDialog.show(((Activity) context).getFragmentManager(), "Select Time");
        }

    }

    private void getSelectedTime(int hours, int mins) {

        String timeStamp;
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

        String minutes;
        if (mins < 10)
            minutes = "0" + mins;
        else
            minutes = String.valueOf(mins);

        bookingView.setTime(hours + ":" + minutes + " " + timeStamp);
    }

    @Override
    public void selectDate(Context context) {
        Calendar now = Calendar.getInstance();

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        String convertedDate = Functions.parseDate(date, "dd-MM-yyyy", "dd MMMM, yyyy");
                        bookingView.setDate(convertedDate);
                    }
                }, now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setMinDate(now);
        datePickerDialog.show(((Activity) context).getFragmentManager(), "Select Date");
    }

    @Override
    public void book(final Context context, BookingRequest bookingRequest) {

        if (bookingRequest == null) {
            return;
        }

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Booking", "Wait while Booking a request.", false, false);

        BookingRequestAPI bookingRequestAPI = UtomoApplication.retrofit.create(BookingRequestAPI.class);
        Call<RequestForBooking> requestForBookingCall = bookingRequestAPI.bookingService(bookingRequest);
        requestForBookingCall.enqueue(new Callback<RequestForBooking>() {
            @Override
            public void onResponse(Call<RequestForBooking> call, Response<RequestForBooking> response) {

                Log.e("res", response.body().toString());

                if (response.isSuccess()) {

                    RequestForBookingResponse requestForBooking = response.body().RequestForBooking;
                    if (requestForBooking.ResponseCode == 1) {

                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle(context.getResources().getString(R.string.Success));
                        alert.setCancelable(false);
                        alert.setMessage(requestForBooking.ResponseMessage);
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent intent = new Intent(context, DrawerActivityRevised.class);
                                intent.putExtra(AppConstant.FRAGMENT_VALUE, AppConstant.HOME_FRAGMENT);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);

                            }
                        });
                        alert.show();

                    } else {
                        Functions.showErrorAlert(context, context.getString(R.string.failed), false);
                    }

                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<RequestForBooking> call, Throwable t) {
                progressDialog.dismiss();
                RetrofitErrorHelper.showErrorMsg(t, context);
            }
        });


    }

    @Override
    public void openCarList(final Context context, final String dealership) {

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Loading.", "Please Wait fetching your vehicles.", false, false);

        final ArrayList<CarPojo> carList = new ArrayList<>();
        FetchVehicleRequest request = new FetchVehicleRequest(context);
        Log.e("req", Functions.jsonString(request));

        FetchVehicleListService service = UtomoApplication.retrofit.create(FetchVehicleListService.class);
        Call<VehicleListResponse> responseCall = service.doFetchVehicleList(request);
        responseCall.enqueue(new Callback<VehicleListResponse>() {
            @Override
            public void onResponse(Call<VehicleListResponse> call, Response<VehicleListResponse> response) {
                progressDialog.dismiss();

                if (response.body() == null) {
                    Functions.showToast(context, "Error occurred.");
                } else {
                    Log.e("res", Functions.jsonString(response.body()));

                    VehicleListResponse vehicleListResponse = response.body();
                    if (vehicleListResponse.FetchVehicleList.ResponseCode == 1) {

                        if (vehicleListResponse.FetchVehicleList.Data.size() > 0) {

                            for (CarPojo carPojo : vehicleListResponse.FetchVehicleList.Data) {

                                if (carPojo.Make.equals(dealership)) {
                                    carList.add(carPojo);
                                }
                            }

                            final CarListDialog dialog = new CarListDialog(context, carList, dealership);
                            dialog.setOnSubmitListener(new CarListDialog.onSubmitListener() {
                                @Override
                                public void onSubmit(CarPojo carPojo) {
                                    PrefUtils.setCurrentCarSelected(context, carPojo);
                                    bookingView.setSelectedCar(carPojo);
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();

                        } else {
                            Functions.showToast(context, "You don't have any car");
                        }

                    } else {
                        Functions.showToast(context, vehicleListResponse.FetchVehicleList.ResponseMessage);
                    }
                }
            }

            @Override
            public void onFailure(Call<VehicleListResponse> call, Throwable t) {
                progressDialog.dismiss();
                RetrofitErrorHelper.showErrorMsg(t, context);

            }
        });

    }

    @Override
    public void fetchCarList(final Context context, final String dealership) {

        final ProgressDialog progressDialog = ProgressDialog.show(context, "Loading.", "Please Wait..", false, false);

        final ArrayList<CarPojo> carList = new ArrayList<>();
        FetchVehicleRequest request = new FetchVehicleRequest(context);
        Log.e("req", Functions.jsonString(request));

        FetchVehicleListService service = UtomoApplication.retrofit.create(FetchVehicleListService.class);
        Call<VehicleListResponse> responseCall = service.doFetchVehicleList(request);
        responseCall.enqueue(new Callback<VehicleListResponse>() {
            @Override
            public void onResponse(Call<VehicleListResponse> call, Response<VehicleListResponse> response) {
                progressDialog.dismiss();

                if (response.body() == null) {
                    Functions.showToast(context, "Error occurred.");
                } else {
                    Log.e("res", Functions.jsonString(response.body()));

                    VehicleListResponse vehicleListResponse = response.body();

                    if (vehicleListResponse.FetchVehicleList.ResponseCode == 1) {

                        if (vehicleListResponse.FetchVehicleList.Data.size() > 0) {

                            for (CarPojo carPojo : vehicleListResponse.FetchVehicleList.Data) {

                                if (carPojo.Make.equals(dealership)) {
                                    carList.add(carPojo);
                                }
                            }
                            bookingView.setCarList(carList);

                        } else {
                            Functions.showToast(context, "You don't have any car");
                        }

                    } else {
                        Functions.showToast(context, vehicleListResponse.FetchVehicleList.ResponseMessage);
                    }
                }
            }

            @Override
            public void onFailure(Call<VehicleListResponse> call, Throwable t) {
                progressDialog.dismiss();
                RetrofitErrorHelper.showErrorMsg(t, context);

            }
        });

    }
}
