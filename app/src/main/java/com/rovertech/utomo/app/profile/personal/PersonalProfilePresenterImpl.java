package com.rovertech.utomo.app.profile.personal;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.util.Log;
import android.widget.DatePicker;

import com.google.gson.Gson;
import com.mlsdev.rximagepicker.RxImageConverters;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.account.adapter.CityAdapter;
import com.rovertech.utomo.app.account.model.CityOutput;
import com.rovertech.utomo.app.account.model.CityRequest;
import com.rovertech.utomo.app.account.model.ResetPasswordOutput;
import com.rovertech.utomo.app.account.service.FetchCityService;
import com.rovertech.utomo.app.account.service.ResetPwdService;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.profile.personal.model.UpdateProfileRequest;
import com.rovertech.utomo.app.profile.personal.model.UpdateProfileResponse;
import com.rovertech.utomo.app.widget.dialog.ChangePasswordDialog;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by sagartahelyani on 31-03-2016.
 */
public class PersonalProfilePresenterImpl implements PersonalProfilePresenter {

    private PersonalProfileView personalProfileView;
    private Context context;

    public PersonalProfilePresenterImpl(PersonalProfileView personalProfileView, Context context) {
        this.personalProfileView = personalProfileView;
        this.context = context;
    }

    @Override
    public void doUpdate(final Context context, final String name, final String birthDate, final String address, final int cityId, final File file, final String email) {

        if (personalProfileView != null)
            personalProfileView.showProgress();

        if (name.length() == 0) {
            Functions.showToast(context, "Name cannot be empty");

        } else if (cityId == 0) {
            Functions.showToast(context, "City cannot be empty");

        } else {

            new AsyncTask<Void, Void, Void>() {

                private String responseFromMultipart = null;
                UpdateProfileRequest request = new UpdateProfileRequest();

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    request.FName = name;

                    if (birthDate.length() != 0)
                        request.DOB = birthDate;
                    else
                        request.DOB = "";
                    if (address.length() != 0)
                        request.Address = address;
                    else
                        request.Address = "";
                    if (email.length() != 0)
                        request.EmailID = email;
                    else
                        request.EmailID = "";

                    request.CityID = cityId;
                    request.GCMToken = PrefUtils.getGcmId(context);
                    request.DeviceID = PrefUtils.getDeviceId(context);
                    request.UserID = PrefUtils.getUserID(context);
                    request.MobileNo = PrefUtils.getUserFullProfileDetails(context).MobileNo;

                    Log.e("request", Functions.jsonString(request));
                }

                @Override
                protected Void doInBackground(Void... params) {
                    try {
                        responseFromMultipart = doFileUploadAnother(file, context, request);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("Error_bg", e.getMessage());
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if (personalProfileView != null)
                        personalProfileView.hideProgress();

                    UpdateProfileResponse response = new Gson().fromJson(responseFromMultipart, UpdateProfileResponse.class);

                    if (response.UpdateProfileDetails.ResponseCode == 1) {
                        PrefUtils.setUserFullProfileDetails(context, response.UpdateProfileDetails.Data.get(0));
                        personalProfileView.success();

                    } else {
                        personalProfileView.fail(response.UpdateProfileDetails.ResponseMessage);
                    }
                }
            }.execute();

        }
    }

    private String doFileUploadAnother(File file, Context context, UpdateProfileRequest request) throws Exception {

        String doResponse = null;
        ByteArrayBody bab = null;
        HttpEntity entity;

        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost httppost = new HttpPost(AppConstant.UPDATE_PROFILE);
        String boundary = "--";
        httppost.setHeader("Content-type", "multipart/form-data; boundary=" + boundary);

        if (file != null) {

            Bitmap b = BitmapFactory.decodeFile(file.getAbsolutePath());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 85, baos);
            byte[] imageBytes = baos.toByteArray();
            bab = new ByteArrayBody(imageBytes, new File(file.getAbsolutePath()).getName() + ".jpg");

            entity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .setBoundary(boundary)
                    .addPart("UserID", new StringBody(request.UserID + ""))
                    .addPart("ProfileImg", bab)
                    .addPart("FName", new StringBody(request.FName))
                    .addPart("MobileNo", new StringBody(request.MobileNo))
                    .addPart("EmailID", new StringBody(request.EmailID))
                    .addPart("Address", new StringBody(request.Address))
                    .addPart("CityID", new StringBody(request.CityID + ""))
                    .addPart("DOB", new StringBody(request.DOB))
                    .addPart("GCMToken", new StringBody(request.GCMToken))
                    .addPart("DeviceID", new StringBody(request.DeviceID))
                    .build();

        } else {

            entity = MultipartEntityBuilder.create()
                    .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                    .setBoundary(boundary)
                    .addPart("UserID", new StringBody(request.UserID + ""))
                    .addPart("FName", new StringBody(request.FName))
                    .addPart("MobileNo", new StringBody(request.MobileNo))
                    .addPart("EmailID", new StringBody(request.EmailID))
                    .addPart("Address", new StringBody(request.Address))
                    .addPart("CityID", new StringBody(request.CityID + ""))
                    .addPart("DOB", new StringBody(request.DOB))
                    .addPart("GCMToken", new StringBody(request.GCMToken))
                    .addPart("DeviceID", new StringBody(request.DeviceID))
                    .build();
        }

        Log.e("req", Functions.jsonString(request));

        httppost.setEntity(entity);
        try {
            HttpResponse response = httpclient.execute(httppost);
            entity = response.getEntity();
            doResponse = EntityUtils.toString(entity);
            Log.e("doResponse", doResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return doResponse;

    }

    @Override
    public void selectDOB(Context context) {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                personalProfileView.setDOB(date);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
        dialog.getDatePicker().setMaxDate(cal.getTimeInMillis());
        dialog.show();
    }

    @Override
    public void fetchCity(final Context context, String string) {
        CityRequest request = new CityRequest();
        request.CityName = string;

        Log.e("city_req", Functions.jsonString(request));

        FetchCityService service = UtomoApplication.retrofit.create(FetchCityService.class);
        Call<CityOutput> call = service.doFetchCity(request);
        call.enqueue(new Callback<CityOutput>() {
            @Override
            public void onResponse(Call<CityOutput> call, Response<CityOutput> response) {
                if (response.body() == null) {
                    Functions.showToast(context, "Error");
                } else {
                    Log.e("json_res", Functions.jsonString(response.body()));
                    CityAdapter adapter = new CityAdapter(context, R.layout.layout_adapter_item, response.body().FetchCity.Data);
                    personalProfileView.setCityAdapter(adapter, response.body().FetchCity.Data);
                }
            }

            @Override
            public void onFailure(Call<CityOutput> call, Throwable t) {

            }
        });
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
                        personalProfileView.setRxImage(file);
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
                        personalProfileView.setRxImage(file);
                    }
                });
    }

    private File createTempFile() {
        return new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), System.currentTimeMillis() + "_image.jpeg");
    }

    @Override
    public void setImage(Intent data, Context context, int requestCode) {
        if (requestCode == AppConstant.REQUEST_CAMERA) {

            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            Uri tempUri = Functions.getImageUri(context, thumbnail);
            File finalFile = new File(Functions.getRealPathFromURI(context, tempUri));
            personalProfileView.setImage(thumbnail, finalFile);

        } else if (requestCode == AppConstant.PICK_IMAGE) {
            Uri selectedImageUri = data.getData();
            Bitmap bitmap = null;
            File finalFile = new File(Functions.getRealPathFromURI(context, selectedImageUri));
            try {
                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            personalProfileView.setImage(bitmap, finalFile);

        }
    }

    @Override
    public void changePwd(final Context context) {
        final ChangePasswordDialog dialog = new ChangePasswordDialog(context);
        dialog.setOnSubmitListener(new ChangePasswordDialog.onSubmitListener() {
            @Override
            public void onSubmit(String password) {
                doResetPwd(context, password);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void doResetPwd(final Context context, String password) {
        ResetPwdService service = UtomoApplication.retrofit.create(ResetPwdService.class);
        Call<ResetPasswordOutput> call = service.resetPassword(PrefUtils.getUserFullProfileDetails(context).MobileNo, password);
        call.enqueue(new Callback<ResetPasswordOutput>() {
            @Override
            public void onResponse(Call<ResetPasswordOutput> call, Response<ResetPasswordOutput> response) {
                if (response.body() != null) {
                    ResetPasswordOutput output = response.body();
                    if (output.ResetPassword.ResponseCode == 1) {
                        Functions.showToast(context, "Password changed successfully.");
                    } else {
                        Functions.showToast(context, output.ResetPassword.ResponseMessage);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResetPasswordOutput> call, Throwable t) {
                Functions.showToast(context, t.toString());
            }
        });
    }
}
