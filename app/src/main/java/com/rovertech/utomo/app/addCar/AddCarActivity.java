package com.rovertech.utomo.app.addCar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.account.model.UserProfileOutput;
import com.rovertech.utomo.app.addCar.adapter.CustomSpinnerAdapter;
import com.rovertech.utomo.app.addCar.adapter.VehicleAdapter;
import com.rovertech.utomo.app.addCar.model.Vehicle;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.IntentConstant;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.booking.BookingActivity;
import com.rovertech.utomo.app.main.drawer.DrawerActivityRevised;
import com.rovertech.utomo.app.main.startup.StartupActivity;
import com.rovertech.utomo.app.profile.carlist.CarPojo;
import com.rovertech.utomo.app.widget.Odometer;

import java.io.File;
import java.util.ArrayList;

public class AddCarActivity extends AppCompatActivity implements AddcarView, View.OnClickListener {

    private Toolbar toolbar;
    private TextView txtCustomTitle, txtSkip;
    private Button btnAdd, btnUpdate;
    private View parentView;
    private RelativeLayout imageSelectLayout;
    private ImageView imageCar;
    private MaterialEditText edtVehicleNo, edtServiceDate, edtPUC, edtInsuranceDate, edtPermitsDate;

    private TextView edtKms;
    private AddCarPresenter presenter;
    private Odometer odometer;

    private AppCompatSpinner yearSpinner, modelSpinner, makeSpinner;

    private String selectedMake = "", selectedYear = "", selectedModel = "", selectModelYear = "";

    private LinearLayout yearCardView, modelCardView;
    private ProgressDialog progressDialog;
    private VehicleAdapter modelAdapter;

    private CustomSpinnerAdapter makeAdapter, yearAdapter;

    private boolean isSkip;
    private int vehicleId = 0;
    private File file = null;
    int carMode = 0;


    @IntDef({addCar, editCar})
    public @interface CarMode {
    }

    public static final int addCar = 0;
    public static final int editCar = 1;
    private CarPojo mCarPojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        isSkip = getIntent().getBooleanExtra(AppConstant.SKIP, false);

        if (!isSkip)
            vehicleId = getIntent().getIntExtra(AppConstant.VEHICLE_ID, 0);

        init();

        presenter = new AddCarPresenterImpl(this, this);


        // fetch makes
        presenter.fetchMakes(this);

        // this is used for edit car
        getDataFromIntent();


        // call this method after getDataFromIntent it will set toolbar name and Update/add button .
        initToolbar();

        setTypeface();

        clickListeners();

    }

    private void clickListeners() {
        imageSelectLayout.setOnClickListener(this);

        edtPUC.setOnClickListener(this);
        edtInsuranceDate.setOnClickListener(this);
        edtPermitsDate.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        edtServiceDate.setOnClickListener(this);
        txtSkip.setOnClickListener(this);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    selectedYear = parent.getSelectedItem().toString();
                    modelCardView.setVisibility(View.GONE);
                    presenter.fetchModels(selectedMake, selectedYear, AddCarActivity.this);
                  //  Log.e("selectedYear", selectedYear);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        modelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Vehicle model = (Vehicle) parent.getAdapter().getItem(position);
                    selectedModel = model.Model;
                    selectModelYear = model.VehicleModelYearID;
                 //   Log.e("selectedModel", selectedModel + " " + selectModelYear);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void init() {

        yearCardView = (LinearLayout) findViewById(R.id.yearCardView);
        modelCardView = (LinearLayout) findViewById(R.id.modelCardView);
        modelSpinner = (AppCompatSpinner) findViewById(R.id.modelSpinner);
        yearSpinner = (AppCompatSpinner) findViewById(R.id.yearSpinner);
        makeSpinner = (AppCompatSpinner) findViewById(R.id.makeSpinner);
        parentView = findViewById(android.R.id.content);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        imageSelectLayout = (RelativeLayout) findViewById(R.id.imageSelectLayout);
        imageCar = (ImageView) findViewById(R.id.imageCar);
        edtVehicleNo = (MaterialEditText) findViewById(R.id.edtVehicleNo);
        edtServiceDate = (MaterialEditText) findViewById(R.id.edtServiceDate);
        edtPUC = (MaterialEditText) findViewById(R.id.edtPUC);
        edtInsuranceDate = (MaterialEditText) findViewById(R.id.edtInsuranceDate);
        edtPermitsDate = (MaterialEditText) findViewById(R.id.edtPermitsDate);
        edtKms = (TextView) findViewById(R.id.edtKms);
        odometer = (Odometer) findViewById(R.id.odometer);


    }

    private void setTypeface() {
        btnAdd.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        edtVehicleNo.setTypeface(Functions.getRegularFont(this));
        edtServiceDate.setTypeface(Functions.getRegularFont(this));
        edtPUC.setTypeface(Functions.getRegularFont(this));
        edtInsuranceDate.setTypeface(Functions.getRegularFont(this));
        edtPermitsDate.setTypeface(Functions.getRegularFont(this));
        edtKms.setTypeface(Functions.getRegularFont(this));
        txtCustomTitle.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
    }

    private void initToolbar() {

        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        txtSkip = (TextView) findViewById(R.id.txtSkip);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        txtSkip.setVisibility(View.GONE);

        if (isSkip) {
            toolbar.setNavigationIcon(null);
        } else {
            toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        }

        setSupportActionBar(toolbar);

        if (carMode == AddCarActivity.addCar) {
            txtCustomTitle.setText("Add Car");
        } else if (carMode == AddCarActivity.editCar) {
            txtCustomTitle.setText("Edit Car");
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edtPUC:
                presenter.selectPUCDate(AddCarActivity.this, Functions.toStr(edtPUC));
                break;

            case R.id.edtInsuranceDate:
                presenter.selectInsuranceDate(AddCarActivity.this, Functions.toStr(edtInsuranceDate));
                break;

            case R.id.edtPermitsDate:
                presenter.selectPermitsDate(AddCarActivity.this, Functions.toStr(edtPermitsDate));
                break;

            case R.id.btnAdd:
                presenter.addCar(AddCarActivity.this, file, Functions.toStr(edtVehicleNo), selectedMake, selectedYear, selectModelYear, Functions.toStr(edtServiceDate),
                        Functions.toStr(edtPUC), Functions.toStr(edtInsuranceDate), Functions.toStr(edtPermitsDate), odometer.getValue());
                break;
            case R.id.btnUpdate:
                presenter.updateCar(AddCarActivity.this, file, Functions.toStr(edtVehicleNo), selectedMake, selectedYear, selectModelYear, Functions.toStr(edtServiceDate),
                        Functions.toStr(edtPUC), Functions.toStr(edtInsuranceDate), Functions.toStr(edtPermitsDate), odometer.getValue());
                break;

            case R.id.edtServiceDate:
                presenter.selectServiceDate(AddCarActivity.this, Functions.toStr(edtServiceDate));
                break;

            case R.id.txtSkip:
                presenter.navigateDashboard(this);
                break;

            case R.id.imageSelectLayout:

                Functions.setPermission(this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                presenter.selectImage();
                                //  selectImageRevised();
                            }

                            @Override
                            public void onPermissionDenied(ArrayList<String> arrayList) {

                                Toast.makeText(AddCarActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                            }
                        });

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            presenter.getImage(data, AddCarActivity.this, requestCode);
        }
    }

    @Override
    public void setInsuranceDate(String convertedDate) {
        edtInsuranceDate.setText(convertedDate);
    }

    @Override
    public void setPUCDate(String convertedDate) {
        edtPUC.setText(convertedDate);
    }

    @Override
    public void setMakeAdapter(CustomSpinnerAdapter adapter) {
        this.makeAdapter = adapter;
        makeSpinner.setAdapter(makeAdapter);

        makeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             //   Log.e("makeAdapter", String.valueOf(position));
                if (position != 0) {
                    selectedMake = parent.getSelectedItem().toString();
                    yearCardView.setVisibility(View.GONE);
                    modelCardView.setVisibility(View.GONE);
                    presenter.fetchYears(selectedMake, AddCarActivity.this);
            //        Log.e("selectedMake", selectedMake);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void setYearAdapter(CustomSpinnerAdapter adapter) {
        yearCardView.setVisibility(View.VISIBLE);
        yearSpinner.setAdapter(adapter);
        yearAdapter = adapter;
    }

    @Override
    public void setModelAdapter(VehicleAdapter adapter) {
        modelCardView.setVisibility(View.VISIBLE);
        modelSpinner.setAdapter(adapter);
        modelAdapter = adapter;
    }

    @Override
    public void setServiceDate(String convertedDate) {
        edtServiceDate.setText(convertedDate);
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(this, "Loading", "Please wait..", false);
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void navigateToDashboard() {

        // TODO: 25-04-2016 AppConstant changes values

        if (PrefUtils.getRedirectLogin(this) == AppConstant.FROM_START) {

            Intent intent = new Intent(this, DrawerActivityRevised.class);
            intent.putExtra(AppConstant.FRAGMENT_VALUE, AppConstant.HOME_FRAGMENT);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

        } else if (PrefUtils.getRedirectLogin(this) == AppConstant.FROM_SKIP) {
            Intent intent = new Intent(this, BookingActivity.class);
            intent.putExtra(IntentConstant.BOOKING_PAGE, AppConstant.FROM_LOGIN);
            startActivity(intent);
            finish();

        } else {
            finish();
        }
    }

    @Override
    public void setImage(Bitmap thumbnail, File finalFile) {
        Glide.clear(imageCar);
        Glide.with(this).load(finalFile).into(imageCar);
        file = finalFile;
    }

    @Override
    public void setRxImage(File finalFile) {
        file = finalFile;
        Bitmap bitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
        Bitmap resizedBitmap = Functions.getResizedBitmap(bitmap, 640, 640);
        imageCar.setImageBitmap(resizedBitmap);
    }

    @Override
    public void success() {

        PrefUtils.setCarAdded(this, true);

        Functions.showToast(this, "Your car has been added successfully.");

        if (isSkip) {
            navigateToDashboard();
        } else {
            PrefUtils.setRefreshDashboard(this, true);
            finish();
        }
    }

    @Override
    public void fail(String responseMessage) {
        Functions.showToast(this, responseMessage);
    }

    @Override
    public void setVehicleError() {
        edtVehicleNo.setError("Like GJ 11 AA 1111");
    }

    @Override
    public void setPermitsDate(String date) {
        edtPermitsDate.setText(date);
    }

    @Override
    public void getDataFromIntent() {

        Intent intent = getIntent();
        if (intent.getIntExtra("EditCar", 0) == AddCarActivity.editCar) {
            modelCardView.setVisibility(View.VISIBLE);
            yearCardView.setVisibility(View.VISIBLE);
            mCarPojo = (CarPojo) intent.getSerializableExtra("CarPojo");
            presenter.setEditCarDetails(mCarPojo, AddCarActivity.editCar);
            carMode = AddCarActivity.editCar;
            btnAdd.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.VISIBLE);

            edtVehicleNo.setEnabled(false);
            makeSpinner.setEnabled(false);
            yearSpinner.setEnabled(false);
            modelSpinner.setEnabled(false);

        } else {
            carMode = AddCarActivity.addCar;
            btnAdd.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);

            edtVehicleNo.setEnabled(true);
            makeSpinner.setEnabled(true);
            yearSpinner.setEnabled(true);
            modelSpinner.setEnabled(true);
        }
    }

    @Override
    public void setCarDetails(CarPojo carPojo) {
        try {
            modelCardView.setVisibility(View.VISIBLE);
            yearCardView.setVisibility(View.VISIBLE);

            Glide.clear(imageCar);
            Glide.with(AddCarActivity.this).load(mCarPojo.CarImage).asBitmap().override(480, 320).centerCrop().into(imageCar);

            //Functions.LoadImage(imageCar, mCarPojo.CarImage, this);
            if (!TextUtils.isEmpty(mCarPojo.VehicleNo)) {
                edtVehicleNo.setText(mCarPojo.VehicleNo);
            }

            if (!TextUtils.isEmpty(carPojo.LastServiceDate)) {
                edtServiceDate.setText(carPojo.LastServiceDate);
            }

            if (!TextUtils.isEmpty(carPojo.PUCStartDate)) {
                edtPUC.setText(carPojo.PUCStartDate);
            }

            if (!TextUtils.isEmpty(carPojo.InsuranceStartDate)) {
                edtInsuranceDate.setText(carPojo.InsuranceStartDate);
            }

            if (!TextUtils.isEmpty(carPojo.PermitsStartDate)) {
                edtPermitsDate.setText(carPojo.PermitsStartDate);
            }

            if (!TextUtils.isEmpty(carPojo.TravelledKM)) {
                odometer.setValue(carPojo.TravelledKM);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setMakeSpinner(CarPojo carPojo) {

        for (int i = 0; i < makeAdapter.getCount(); i++) {
            if (carPojo.Make.trim().equalsIgnoreCase(makeAdapter.getItem(i).trim())) {
                makeSpinner.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void setYearSpinner(CarPojo carPojo) {

        for (int i = 0; i < yearAdapter.getCount(); i++) {
            if (carPojo.Year.trim().equalsIgnoreCase(yearAdapter.getItem(i).trim())) {
                yearSpinner.setSelection(i);
                break;
            }
        }
    }

    @Override
    public void setModelSpinner(CarPojo carPojo) {

        for (int i = 0; i < modelAdapter.getCount(); i++) {
            if (carPojo.Model.trim().equalsIgnoreCase(modelAdapter.getItem(i).Model.trim())) {
                modelSpinner.setSelection(i);
                break;
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (isSkip) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    AddCarActivity.this);
            alertDialogBuilder.setTitle("Alert!!")
                    .setMessage("You have to add a car to proceed further.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("Logout", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            UserProfileOutput profileOutput = new UserProfileOutput();
                            PrefUtils.setLoggedIn(AddCarActivity.this, false);
                            PrefUtils.setUserFullProfileDetails(AddCarActivity.this, profileOutput);

                            Intent startupIntent = new Intent(AddCarActivity.this, StartupActivity.class);
                            startupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(startupIntent);
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            super.onBackPressed();
        }
    }
}
