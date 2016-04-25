package com.rovertech.utomo.app.addCar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.IntDef;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.addCar.adapter.VehicleAdapter;
import com.rovertech.utomo.app.addCar.model.Vehicle;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.IntentConstant;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.booking.BookingActivity;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;
import com.rovertech.utomo.app.profile.carlist.CarPojo;
import com.rovertech.utomo.app.widget.Odometer;

import java.io.File;

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

    private AppCompatSpinner makeSpinner, yearSpinner, modelSpinner;
    private ProgressBar makeProgressBar, yearProgressBar, modelProgressBar;
    private String selectedMake = "", selectedYear = "", selectedModel = "", selectModelYear = "";

    private LinearLayout yearCardView, modelCardView;
    private ProgressDialog progressDialog;
    private ArrayAdapter<String> makeAdapter, yearAdapter;
    private VehicleAdapter modelAdapter;

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

        presenter = new AddCarPresenterImpl(this);


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

        makeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    selectedMake = parent.getSelectedItem().toString();
                    presenter.fetchYears(selectedMake, AddCarActivity.this);
                    Log.e("selectedMake", selectedMake);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    selectedYear = parent.getSelectedItem().toString();
                    presenter.fetchModels(selectedMake, selectedYear, AddCarActivity.this);
                    Log.e("selectedYear", selectedYear);
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
                    Log.e("selectedModel", selectedModel + " " + selectModelYear);
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
        modelProgressBar = (ProgressBar) findViewById(R.id.modelProgressBar);
        yearProgressBar = (ProgressBar) findViewById(R.id.yearProgressBar);
        makeProgressBar = (ProgressBar) findViewById(R.id.makeProgressBar);
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
        btnAdd.setTypeface(Functions.getBoldFont(this));
        edtVehicleNo.setTypeface(Functions.getRegularFont(this));
        edtServiceDate.setTypeface(Functions.getRegularFont(this));
        edtPUC.setTypeface(Functions.getRegularFont(this));
        edtInsuranceDate.setTypeface(Functions.getRegularFont(this));
        edtPermitsDate.setTypeface(Functions.getRegularFont(this));
        edtKms.setTypeface(Functions.getRegularFont(this));
        txtCustomTitle.setTypeface(Functions.getBoldFont(this));
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
                selectImage();
                break;
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, AppConstant.REQUEST_CAMERA);

                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_PICK);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), AppConstant.PICK_IMAGE);
                }
            }
        });
        builder.show();
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
    public void setMakeAdapter(ArrayAdapter<String> adapter) {
        this.makeAdapter = adapter;
        makeSpinner.setAdapter(adapter);
    }

    @Override
    public void showMakeProgress() {
        makeProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideMakeProgress() {
        makeProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showYearProgress() {
        yearProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideYearProgress() {
        yearProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showModelProgress() {
        modelProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideModelProgress() {
        modelProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setYearAdapter(ArrayAdapter<String> adapter) {
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

        if (PrefUtils.getRedirectLogin(this) == AppConstant.FROM_START) {
            Intent intent = new Intent(this, DrawerActivity.class);
            intent.putExtra(AppConstant.FRAGMENT_VALUE, AppConstant.HOME_FRAGMENT);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);

        } else {
            Intent intent = new Intent(this, BookingActivity.class);
            intent.putExtra(IntentConstant.BOOKING_PAGE, AppConstant.FROM_LOGIN);
            startActivity(intent);
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
    public void success() {

        PrefUtils.setCarAdded(this, true);

        Functions.showToast(this, "Your car has been added successfully.");
        if (isSkip) {
            navigateToDashboard();
        } else {
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
        } else {
            carMode = AddCarActivity.addCar;
            btnAdd.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.GONE);
        }
    }

    @Override
    public void setCarDetails(CarPojo carPojo) {
        try {
            modelCardView.setVisibility(View.VISIBLE);
            yearCardView.setVisibility(View.VISIBLE);
            Functions.LoadImage(imageCar, mCarPojo.CarImage, this);
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
            Toast.makeText(AddCarActivity.this, "Add a car", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }
}
