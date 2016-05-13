package com.rovertech.utomo.app.addCar;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.mlsdev.rximagepicker.RxImageConverters;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.addCar.adapter.CustomSpinnerAdapter;
import com.rovertech.utomo.app.addCar.adapter.VehicleAdapter;
import com.rovertech.utomo.app.addCar.model.AddCarRequest;
import com.rovertech.utomo.app.addCar.model.MakeModel;
import com.rovertech.utomo.app.addCar.model.Vehicle;
import com.rovertech.utomo.app.addCar.model.VehicleModel;
import com.rovertech.utomo.app.addCar.model.YearModel;
import com.rovertech.utomo.app.addCar.service.FetchMakeService;
import com.rovertech.utomo.app.addCar.service.FetchModelService;
import com.rovertech.utomo.app.addCar.service.FetchYearService;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.profile.carlist.CarPojo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by sagartahelyani on 09-03-2016.
 */
public class AddCarPresenterImpl implements AddCarPresenter {

    AddcarView addcarView;
    CarPojo mCarPojo;
    int carMode = 0;
    private Context context;

    public AddCarPresenterImpl(AddcarView addcarView, Context context) {
        this.addcarView = addcarView;
        this.context = context;
    }

    @Override
    public void selectPUCDate(Context context, String selectedDate) {
        Calendar selectedDateCalender = Calendar.getInstance();
        if (!TextUtils.isEmpty(selectedDate)) {

            Date date = Functions.getDate(selectedDate, Functions.CalenderDateFormateddmyyyy, Functions.CalenderDateFormateddmmyyyy);
            selectedDateCalender.setTime(date);
        }
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        String day = String.valueOf(dayOfMonth);
                        if (dayOfMonth < 10) {
                            day = "0" + day;
                        }
                        String date = day + "-" + (monthOfYear + 1) + "-" + year;
                        date = Functions.parseDate(date, Functions.CalenderDateFormateddmyyyy, Functions.CalenderDateFormateddmmyyyy);
                        addcarView.setPUCDate(date);
                    }
                },
                selectedDateCalender.get(Calendar.YEAR),
                selectedDateCalender.get(Calendar.MONTH),
                selectedDateCalender.get(Calendar.DAY_OF_MONTH)
        );
        Calendar now = Calendar.getInstance();
        datePickerDialog.setMaxDate(now);
        datePickerDialog.show(((Activity) context).getFragmentManager(), "Select Date");
    }

    @Override
    public void selectInsuranceDate(Context context, String selectedDate) {

        Calendar selectedDateCalender = Calendar.getInstance();
        if (!TextUtils.isEmpty(selectedDate)) {

            Date date = Functions.getDate(selectedDate, Functions.CalenderDateFormateddmyyyy, Functions.CalenderDateFormateddmmyyyy);
            selectedDateCalender.setTime(date);
        }

        com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String day = String.valueOf(dayOfMonth);
                        if (dayOfMonth < 10) {
                            day = "0" + day;
                        }
                        String date = day + "-" + (monthOfYear + 1) + "-" + year;
                        date = Functions.parseDate(date, Functions.CalenderDateFormateddmyyyy, Functions.CalenderDateFormateddmmyyyy);

                        addcarView.setInsuranceDate(date);
                    }
                },
                selectedDateCalender.get(Calendar.YEAR),
                selectedDateCalender.get(Calendar.MONTH),
                selectedDateCalender.get(Calendar.DAY_OF_MONTH)
        );
        Calendar now = Calendar.getInstance();
        datePickerDialog.setMaxDate(now);
        datePickerDialog.show(((Activity) context).getFragmentManager(), "Select Date");
    }

    @Override
    public void fetchMakes(final Context context) {

        if (addcarView != null)
            addcarView.showProgress();

        FetchMakeService service = UtomoApplication.retrofit.create(FetchMakeService.class);
        Call<MakeModel> call = service.fetchMake();
        call.enqueue(new Callback<MakeModel>() {
            @Override
            public void onResponse(Call<MakeModel> call, Response<MakeModel> response) {

                if (response.body() == null) {
                    Functions.showToast(context, "Error occurred.");

                } else {
                    MakeModel makeModel = response.body();
                    ArrayList<String> makeList = new ArrayList<String>();
                    if (makeModel.FetchMake.Data.size() > 0) {
                        for (int i = 0; i < makeModel.FetchMake.Data.size(); i++) {
                            makeList.add(makeModel.FetchMake.Data.get(i).Make);
                        }
                    }
                    CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(context, R.layout.spinner_layout_transperent,
                            R.layout.spinner_dropview_layout, makeList);
                    addcarView.setMakeAdapter(adapter);
                    addcarView.hideProgress();

                    //edit car
                    if (carMode == AddCarActivity.editCar && mCarPojo != null) {
                        addcarView.setCarDetails(mCarPojo);
                        addcarView.setMakeSpinner(mCarPojo);

                    }
                }
            }

            @Override
            public void onFailure(Call<MakeModel> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }

    @Override
    public void fetchYears(String selectedMake, final Context context) {
        if (addcarView != null)
            addcarView.showProgress();

        FetchYearService service = UtomoApplication.retrofit.create(FetchYearService.class);
        Call<YearModel> call = service.fetchYear(selectedMake);
        call.enqueue(new Callback<YearModel>() {
            @Override
            public void onResponse(Call<YearModel> call, Response<YearModel> response) {

                if (response.body() == null) {
                    Functions.showToast(context, "Error occurred.");

                } else {
                    YearModel yearModel = response.body();
                    ArrayList<String> yearList = new ArrayList<String>();

                    if (yearModel.FetchYear.Data.size() > 0) {
                        for (int i = 0; i < yearModel.FetchYear.Data.size(); i++) {
                            yearList.add(yearModel.FetchYear.Data.get(i).Year);
                        }
                    }
                    CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(context, R.layout.spinner_layout_transperent,
                            R.layout.spinner_dropview_layout, yearList);
                    addcarView.setYearAdapter(adapter);
                    addcarView.hideProgress();

                    //edit car
                    if (carMode == AddCarActivity.editCar && mCarPojo != null) {
                        addcarView.setYearSpinner(mCarPojo);
                    }
                }
            }

            @Override
            public void onFailure(Call<YearModel> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });

    }

    @Override
    public void fetchModels(String selectedMake, String selectedYear, final Context context) {
        if (addcarView != null)
            addcarView.showProgress();

        FetchModelService modelService = UtomoApplication.retrofit.create(FetchModelService.class);
        Call<VehicleModel> call = modelService.fetchModels(selectedMake, selectedYear);
        call.enqueue(new Callback<VehicleModel>() {
            @Override
            public void onResponse(Call<VehicleModel> call, Response<VehicleModel> response) {
                if (response.body() == null) {
                    Functions.showToast(context, "Error occurred.");

                } else {
                    VehicleModel vehicleModel = response.body();
                    ArrayList<Vehicle> modelList = new ArrayList<Vehicle>();

                    if (vehicleModel.FetchModel.Data.size() > 0) {
                        for (int i = 0; i < vehicleModel.FetchModel.Data.size(); i++) {
                            modelList.add(vehicleModel.FetchModel.Data.get(i));
                        }
                    }

                    VehicleAdapter adapter = new VehicleAdapter(context, R.layout.spinner_layout_transperent,
                            R.layout.spinner_dropview_layout, modelList);
                    addcarView.setModelAdapter(adapter);
                    addcarView.hideProgress();

                    //edit car
                    if (carMode == AddCarActivity.editCar && mCarPojo != null) {
                        addcarView.setModelSpinner(mCarPojo);
                    }
                }
            }

            @Override
            public void onFailure(Call<VehicleModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void selectServiceDate(Context context, String selectedDate) {

        Calendar selectedDateCalender = Calendar.getInstance();
        if (!TextUtils.isEmpty(selectedDate)) {

            Date date = Functions.getDate(selectedDate, Functions.CalenderDateFormateddmyyyy, Functions.CalenderDateFormateddmmyyyy);
            selectedDateCalender.setTime(date);
        }
        com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        String day = String.valueOf(dayOfMonth);
                        if (dayOfMonth < 10) {
                            day = "0" + day;
                        }
                        String date = day + "-" + (monthOfYear + 1) + "-" + year;
                        date = Functions.parseDate(date, Functions.CalenderDateFormateddmyyyy, Functions.CalenderDateFormateddmmyyyy);
                        addcarView.setServiceDate(date);
                    }
                },
                selectedDateCalender.get(Calendar.YEAR),
                selectedDateCalender.get(Calendar.MONTH),
                selectedDateCalender.get(Calendar.DAY_OF_MONTH)
        );
        Calendar now = Calendar.getInstance();
        datePickerDialog.setMaxDate(now);
        datePickerDialog.show(((Activity) context).getFragmentManager(), "Select Date");
    }

    @Override
    public void navigateDashboard(Context context) {
        if (addcarView != null) {
            addcarView.navigateToDashboard();
        }
    }

    @Override
    public void getImage(Intent data, Context context, int requestCode) {

        if (requestCode == AppConstant.REQUEST_CAMERA) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            Uri tempUri = Functions.getImageUri(context, thumbnail);
            File finalFile = new File(Functions.getRealPathFromURI(context, tempUri));
            addcarView.setImage(thumbnail, finalFile);

        } else if (requestCode == AppConstant.PICK_IMAGE) {
            Uri selectedImageUri = data.getData();
            Bitmap bitmap = null;
            File finalFile = new File(Functions.getRealPathFromURI(context, selectedImageUri));
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            addcarView.setImage(bitmap, finalFile);

        }
    }

    @Override
    public void addCar(final Context context, final File file, String vehicleNo, String selectedMake, String selectedYear, String selectModelYear,
                       String serviceDate, String pucDate, String insuranceDate, String permitsDate, String odometerValue) {

        if (vehicleNo.equals("")) {
            Functions.showToast(context, "Enter Vehicle Number");

        } else if (selectedMake.equals("")) {
            Functions.showToast(context, "Select Dealership");

        } else if (selectedYear.equals("")) {
            Functions.showToast(context, "Select Year");

        } else if (selectModelYear.equals("")) {
            Functions.showToast(context, "Select Model");

        } else {

            final AddCarRequest request = new AddCarRequest();
            request.Make = selectedMake;

            if (pucDate.equals(""))
                request.PUCExpiryDate = "";
            else
                request.PUCExpiryDate = pucDate;

            if (insuranceDate.equals(""))
                request.InsuranceDate = "";
            else
                request.InsuranceDate = insuranceDate;

            if (permitsDate.equals(""))
                request.LastPermitsDate = "";
            else
                request.LastPermitsDate = permitsDate;

            if (serviceDate.equals(""))
                request.ServiceDate = "";
            else
                request.ServiceDate = serviceDate;

            request.TravelledKM = odometerValue;
            request.ClientID = PrefUtils.getUserFullProfileDetails(context).UserID;
            request.VehicleModelYearID = Integer.parseInt(selectModelYear);
            request.VehicleNo = vehicleNo;
            request.Year = Integer.parseInt(selectedYear);

            new AsyncTask<Void, String, String>() {

                private String responseFromMultipart = null;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    if (addcarView != null)
                        addcarView.showProgress();
                }

                @Override
                protected String doInBackground(Void... params) {
                    try {
                        responseFromMultipart = doFileUploadAnother(file, context, request);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return responseFromMultipart;
                }

                @Override
                protected void onPostExecute(String responseFromMultipart) {
                    super.onPostExecute(responseFromMultipart);

                    if (addcarView != null)
                        addcarView.hideProgress();

                    try {
                        JSONObject res = new JSONObject(responseFromMultipart);
                        // Log.e("res", Functions.jsonString(res));
                        if (res.getJSONObject("InsertVehicleDetails").getInt("ResponseCode") == 1) {
                            addcarView.success();
                        } else {
                            addcarView.fail(res.getJSONObject("InsertVehicleDetails").getString("ResponseMessage"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();

        }
    }

    @Override
    public void updateCar(final Context context, final File file, String vehicleNo, String selectedMake, String selectedYear, String selectModelYear, String serviceDate, String pucDate, String insuranceDate, String permitsDate, String odometerValue) {


        if (vehicleNo.equals("")) {
            Functions.showToast(context, "Vehicle number cannot be empty");

        } else if (selectedMake.equals("")) {
            Functions.showToast(context, "Select Dealership");

        } else if (selectedYear.equals("")) {
            Functions.showToast(context, "Select Year");

        } else if (selectModelYear.equals("")) {
            Functions.showToast(context, "Select Year");

        } else {

            final AddCarRequest request = new AddCarRequest();
            request.Make = selectedMake;

            if (pucDate.equals(""))
                request.PUCExpiryDate = "";
            else
                request.PUCExpiryDate = pucDate;

            if (insuranceDate.equals(""))
                request.InsuranceDate = "";
            else
                request.InsuranceDate = insuranceDate;

            if (permitsDate.equals(""))
                request.LastPermitsDate = "";
            else
                request.LastPermitsDate = permitsDate;

            if (serviceDate.equals(""))
                request.ServiceDate = "";
            else
                request.ServiceDate = serviceDate;

            request.TravelledKM = odometerValue;
            request.ClientID = PrefUtils.getUserFullProfileDetails(context).UserID;
            request.VehicleModelYearID = Integer.parseInt(selectModelYear);
            request.VehicleNo = vehicleNo;
            request.Year = Integer.parseInt(selectedYear);

            new AsyncTask<Void, Void, Void>() {

                private String responseFromMultipart = null;

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    if (addcarView != null)
                        addcarView.showProgress();
                }

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        responseFromMultipart = doFileUploadAnotherUpdate(file, context, request);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);

                    if (addcarView != null)
                        addcarView.hideProgress();

                    try {

                        UpdateVehicleDetails updateVehicleDetails = UtomoApplication.getInstance().getGson().fromJson(responseFromMultipart, UpdateVehicleDetails.class);
                        if (updateVehicleDetails.vehicleDetails != null && updateVehicleDetails.vehicleDetails.ResponseCode == 1) {

                            Functions.showErrorAlert(context, "Success", "Car Details has been Updated.", true);
                            PrefUtils.setRefreshDashboard(context, true);

                        } else {

                            Functions.showErrorAlert(context, "", "Failed to update car.", false);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        Functions.showErrorAlert(context, "", "Failed to update car.", false);
                    }
                }
            }.execute();

        }

    }

    @Override
    public void selectPermitsDate(Context context, String selectedDate) {
        Calendar selectedDateCalender = Calendar.getInstance();
        if (!TextUtils.isEmpty(selectedDate)) {

            Date date = Functions.getDate(selectedDate, Functions.CalenderDateFormateddmyyyy, Functions.CalenderDateFormateddmmyyyy);
            selectedDateCalender.setTime(date);
        }

        com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(
                new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        String day = String.valueOf(dayOfMonth);
                        if (dayOfMonth < 10) {
                            day = "0" + day;
                        }
                        String date = day + "-" + (monthOfYear + 1) + "-" + year;
                        date = Functions.parseDate(date, Functions.CalenderDateFormateddmyyyy, Functions.CalenderDateFormateddmmyyyy);
                        addcarView.setPermitsDate(date);
                    }
                },
                selectedDateCalender.get(Calendar.YEAR),
                selectedDateCalender.get(Calendar.MONTH),
                selectedDateCalender.get(Calendar.DAY_OF_MONTH)
        );
        Calendar now = Calendar.getInstance();
        datePickerDialog.setMaxDate(now);
        datePickerDialog.show(((Activity) context).getFragmentManager(), "Select Date");
    }

    @Override
    public void setEditCarDetails(CarPojo carPojo, @AddCarActivity.CarMode int carMode) {
        this.mCarPojo = carPojo;
        this.carMode = carMode;
    }

    @Override
    public void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    captureImage();

                } else if (items[item].equals("Choose from Gallery")) {
                    fromGallery();
                }
            }
        });
        builder.show();
    }

    private void fromGallery() {
        RxImagePicker.with(context).requestImage(Sources.GALLERY)
                .flatMap(new Func1<Uri, Observable<File>>() {
                    @Override
                    public Observable<File> call(Uri uri) {
                        return RxImageConverters.uriToFile(context, uri, createTempFile());
                    }
                })
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                        addcarView.setRxImage(file);
                    }
                });
    }

    private void captureImage() {
        RxImagePicker.with(context).requestImage(Sources.CAMERA)
                .flatMap(new Func1<Uri, Observable<File>>() {
                    @Override
                    public Observable<File> call(Uri uri) {
                        return RxImageConverters.uriToFile(context, uri, createTempFile());
                    }
                })
                .subscribe(new Action1<File>() {
                    @Override
                    public void call(File file) {
                       addcarView.setRxImage(file);
                    }
                });
    }

    private File createTempFile() {
        return new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + "_image.jpeg");
    }

    private String doFileUploadAnother(File f, final Context context, AddCarRequest request) throws Exception {
        String doResponse = null;
        HttpEntity entity;

        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost httppost = new HttpPost(AppConstant.INSERT_VEHICLE_DETAILS);
        String boundary = "--";
        httppost.setHeader("Content-type", "multipart/form-data; boundary=" + boundary);

        if (f != null) {

            Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 85, baos);
            byte[] imageBytes = baos.toByteArray();
            ByteArrayBody bab = new ByteArrayBody(imageBytes, new File(f.getAbsolutePath()).getName() + ".jpg");

            entity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .setBoundary(boundary)
                    .addPart("ClientID", new StringBody(request.ClientID + ""))
                    .addPart("Make", new StringBody(request.Make))
                    .addPart("VehicleModelYearID", new StringBody(request.VehicleModelYearID + ""))
                    .addPart("PUCStartDate", new StringBody(request.PUCExpiryDate))
                    .addPart("VehicleNo", new StringBody(request.VehicleNo))
                    .addPart("TravelledKM", new StringBody(request.TravelledKM))
                    .addPart("InsuranceStartDate", new StringBody(request.InsuranceDate))
                    .addPart("PermitsStartDate", new StringBody(request.LastPermitsDate))
                    .addPart("Year", new StringBody(request.Year + ""))
                    .addPart("ServiceDate", new StringBody(request.ServiceDate))
                    .addPart("Image", bab)
                    .build();
        } else {

            entity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .setBoundary(boundary)
                    .addPart("ClientID", new StringBody(request.ClientID + ""))
                    .addPart("Make", new StringBody(request.Make))
                    .addPart("VehicleModelYearID", new StringBody(request.VehicleModelYearID + ""))
                    .addPart("PUCStartDate", new StringBody(request.PUCExpiryDate))
                    .addPart("VehicleNo", new StringBody(request.VehicleNo))
                    .addPart("TravelledKM", new StringBody(request.TravelledKM))
                    .addPart("InsuranceStartDate", new StringBody(request.InsuranceDate))
                    .addPart("PermitsStartDate", new StringBody(request.LastPermitsDate))
                    .addPart("Year", new StringBody(request.Year + ""))
                    .addPart("ServiceDate", new StringBody(request.ServiceDate))
                    .build();
        }

        Log.e("req", Functions.jsonString(request));

        httppost.setEntity(entity);
        try {
            HttpResponse response = httpclient.execute(httppost);
            entity = response.getEntity();
            doResponse = EntityUtils.toString(entity);
            Log.e("doResponse", doResponse + " ### ##");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return doResponse;

    }

    private String doFileUploadAnotherUpdate(File f, final Context context, AddCarRequest request) throws Exception {
        String doResponse = null;
        HttpEntity entity;

        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost httppost = new HttpPost(AppConstant.UPDATE_VEHICLE_DETAILS);
        String boundary = "--";
        httppost.setHeader("Content-type", "multipart/form-data; boundary=" + boundary);

        if (f != null) {

            Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 85, baos);
            byte[] imageBytes = baos.toByteArray();
            ByteArrayBody bab = new ByteArrayBody(imageBytes, new File(f.getAbsolutePath()).getName() + ".jpg");

            entity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .setBoundary(boundary)
                    .addPart("VehicleID", new StringBody(mCarPojo.VehicleID + ""))
                    .addPart("ClientID", new StringBody(request.ClientID + ""))
                    .addPart("Make", new StringBody(request.Make))
                    .addPart("VehicleModelYearID", new StringBody(request.VehicleModelYearID + ""))
                    .addPart("PUCStartDate", new StringBody(request.PUCExpiryDate))
                    .addPart("VehicleNo", new StringBody(request.VehicleNo))
                    .addPart("TravelledKM", new StringBody(request.TravelledKM))
                    .addPart("InsuranceStartDate", new StringBody(request.InsuranceDate))
                    .addPart("PermitsStartDate", new StringBody(request.LastPermitsDate))
                    .addPart("Year", new StringBody(request.Year + ""))
                    .addPart("ServiceDate", new StringBody(request.ServiceDate))
                    .addPart("Image", bab)
                    .build();
        } else {

            entity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .setBoundary(boundary)
                    .addPart("VehicleID", new StringBody(mCarPojo.VehicleID + ""))
                    .addPart("ClientID", new StringBody(request.ClientID + ""))
                    .addPart("Make", new StringBody(request.Make))
                    .addPart("VehicleModelYearID", new StringBody(request.VehicleModelYearID + ""))
                    .addPart("PUCStartDate", new StringBody(request.PUCExpiryDate))
                    .addPart("VehicleNo", new StringBody(request.VehicleNo))
                    .addPart("TravelledKM", new StringBody(request.TravelledKM))
                    .addPart("InsuranceStartDate", new StringBody(request.InsuranceDate))
                    .addPart("PermitsStartDate", new StringBody(request.LastPermitsDate))
                    .addPart("Year", new StringBody(request.Year + ""))
                    .addPart("ServiceDate", new StringBody(request.ServiceDate))
                    .build();
        }

        Log.e("req", Functions.jsonString(request));

        httppost.setEntity(entity);
        try {
            HttpResponse response = httpclient.execute(httppost);
            entity = response.getEntity();
            doResponse = EntityUtils.toString(entity);
            Log.e("card Update", doResponse);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return doResponse;

    }

    public class UpdateVehicleDetails {

        @SerializedName("UpdateVehicleDetails")
        public VehicleDetails vehicleDetails;
    }

}
