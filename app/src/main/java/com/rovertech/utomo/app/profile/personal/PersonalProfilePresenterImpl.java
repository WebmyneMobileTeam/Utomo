package com.rovertech.utomo.app.profile.personal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.DatePicker;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.account.adapter.CityAdapter;
import com.rovertech.utomo.app.account.model.CityOutput;
import com.rovertech.utomo.app.account.model.CityRequest;
import com.rovertech.utomo.app.account.service.FetchCityService;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.widget.dialog.ChangePasswordDialog;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 31-03-2016.
 */
public class PersonalProfilePresenterImpl implements PersonalProfilePresenter {

    private PersonalProfileView personalProfileView;

    public PersonalProfilePresenterImpl(PersonalProfileView personalProfileView) {
        this.personalProfileView = personalProfileView;
    }

    @Override
    public void doUpdate(Context context) {
        ChangePasswordDialog dialog = new ChangePasswordDialog(context);
        dialog.show();
    }

    @Override
    public void selectPUCDate(Context context) {
        Calendar cal = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                String convertedDate = Functions.parseDate(date, "dd-MM-yyyy", "dd MMMM, yyyy");
                personalProfileView.setDOB(convertedDate);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                personalProfileView.setDOB("");
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
    public void selectImage(final Context context) {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    ((Activity) context).startActivityForResult(intent, AppConstant.REQUEST_CAMERA);

                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);
                    ((Activity) context).startActivityForResult(Intent.createChooser(intent, "Select Picture"), AppConstant.PICK_IMAGE);
                }
            }
        });
        builder.show();
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
    public void changePwd(Context context) {
        ChangePasswordDialog dialog = new ChangePasswordDialog(context);
        dialog.show();
    }
}
