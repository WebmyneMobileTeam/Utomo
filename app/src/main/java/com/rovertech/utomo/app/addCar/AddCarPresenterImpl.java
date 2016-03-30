package com.rovertech.utomo.app.addCar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.addCar.adapter.VehicleAdapter;
import com.rovertech.utomo.app.addCar.model.MakeModel;
import com.rovertech.utomo.app.addCar.model.Vehicle;
import com.rovertech.utomo.app.addCar.model.VehicleModel;
import com.rovertech.utomo.app.addCar.model.YearModel;
import com.rovertech.utomo.app.addCar.service.FetchMakeService;
import com.rovertech.utomo.app.addCar.service.FetchModelService;
import com.rovertech.utomo.app.addCar.service.FetchYearService;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;

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
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    @Override
    public void fetchMakes(final Context context) {

        if (addcarView != null)
            addcarView.showMakeProgress();

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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, makeList);
                    addcarView.setMakeAdapter(adapter);
                    addcarView.hideMakeProgress();
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
            addcarView.showYearProgress();

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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, yearList);
                    addcarView.setYearAdapter(adapter);
                    addcarView.hideYearProgress();
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
            addcarView.showModelProgress();

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

                    VehicleAdapter adapter = new VehicleAdapter(context, R.layout.layout_adapter_item, modelList);
                    //ArrayAdapter<Vehicle> adapter = new ArrayAdapter<Vehicle>(context, android.R.layout.simple_list_item_1, modelList);
                    addcarView.setModelAdapter(adapter);
                    addcarView.hideModelProgress();
                }
            }

            @Override
            public void onFailure(Call<VehicleModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void selectServiceDate(Context context) {
        Calendar cal = Calendar.getInstance();

        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                String convertedDate = Functions.parseDate(date, "dd-MM-yyyy", "dd MMMM, yyyy");
                addcarView.setServiceDate(convertedDate);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMinDate(cal.getTimeInMillis());
        dialog.show();
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

    private void doFileUploadAnother(File f, final Context context) throws Exception {

        HttpClient httpclient = new DefaultHttpClient();
        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
        HttpPost httppost = new HttpPost("http://ws-srv-net.in.webmyne.com/Applications/CarBell/Development/UTOMOService_V01/Service/UserActivities.svc/json/InsertVehicleDetails");
        String boundary = "--";
        httppost.setHeader("Content-type", "multipart/form-data; boundary=" + boundary);

        Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 85, baos);
        byte[] imageBytes = baos.toByteArray();
        ByteArrayBody bab = new ByteArrayBody(imageBytes, new File(f.getAbsolutePath()).getName() + ".jpg");

        HttpEntity entity = MultipartEntityBuilder.create()
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                .setBoundary(boundary)
                .addPart("ClientID", new StringBody("2"))
                .addPart("Image", bab)
                .addPart("Make", new StringBody("Ford"))
                .addPart("VehicleNo", new StringBody("GJ 6 XX 4343"))
                .addPart("TravelledKM", new StringBody("3456.67"))
                .addPart("Year", new StringBody("2015"))
                .addPart("VehicleModelYearID", new StringBody("1"))

                .build();

        httppost.setEntity(entity);
        try {
            HttpResponse response = httpclient.execute(httppost);

            entity = response.getEntity();
            final String response_str = EntityUtils.toString(entity);
            if (entity != null) {
                Log.e("RESPONSE", response_str);
                ((Activity) context).runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            /*res.setTextColor(Color.GREEN);
                            res.setText("n Response from server : n " + response_str);*/
                            Toast.makeText(context, "Upload Complete. Check the server uploads directory.", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (Exception e) {

        }

    }
}
