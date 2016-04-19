package com.rovertech.utomo.app.addCar;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.addCar.adapter.VehicleAdapter;
import com.rovertech.utomo.app.addCar.model.Vehicle;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;
import com.rovertech.utomo.app.widget.Odometer;

import java.io.File;

public class AddCarActivity extends AppCompatActivity implements AddcarView, View.OnClickListener {

    private Toolbar toolbar;
    private TextView txtCustomTitle, txtSkip;
    private Button btnAdd;
    private View parentView;
    private RelativeLayout imageSelectLayout;
    private ImageView imageCar;
    private EditText edtVehicleNo, edtServiceDate, edtPUC, edtInsuranceDate, edtPermitsDate;
    private TextView edtKms;
    private AddCarPresenter presenter;
    private Odometer odometer;

    private AppCompatSpinner makeSpinner, yearSpinner, modelSpinner;
    private ProgressBar makeProgressBar, yearProgressBar, modelProgressBar;
    private String selectedMake = "", selectedYear = "", selectedModel = "", selectModelYear = "";

    private CardView yearCardView, modelCardView;
    private ProgressDialog progressDialog;

    private boolean isSkip;
    private int vehicleId = 0;
    private File file = null;

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

        clickListeners();

    }

    private void clickListeners() {
        imageSelectLayout.setOnClickListener(this);

        edtPUC.setOnClickListener(this);
        edtInsuranceDate.setOnClickListener(this);
        edtPermitsDate.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
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

        initToolbar();

        yearCardView = (CardView) findViewById(R.id.yearCardView);
        modelCardView = (CardView) findViewById(R.id.modelCardView);
        modelSpinner = (AppCompatSpinner) findViewById(R.id.modelSpinner);
        modelProgressBar = (ProgressBar) findViewById(R.id.modelProgressBar);
        yearProgressBar = (ProgressBar) findViewById(R.id.yearProgressBar);
        yearSpinner = (AppCompatSpinner) findViewById(R.id.yearSpinner);
        makeProgressBar = (ProgressBar) findViewById(R.id.makeProgressBar);
        makeSpinner = (AppCompatSpinner) findViewById(R.id.makeSpinner);
        parentView = findViewById(android.R.id.content);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        imageSelectLayout = (RelativeLayout) findViewById(R.id.imageSelectLayout);
        imageCar = (ImageView) findViewById(R.id.imageCar);

        edtVehicleNo = (EditText) findViewById(R.id.edtVehicleNo);

        edtServiceDate = (EditText) findViewById(R.id.edtServiceDate);
        edtPUC = (EditText) findViewById(R.id.edtPUC);
        edtInsuranceDate = (EditText) findViewById(R.id.edtInsuranceDate);
        edtPermitsDate = (EditText) findViewById(R.id.edtPermitsDate);
        edtKms = (TextView) findViewById(R.id.edtKms);
        odometer = (Odometer) findViewById(R.id.odometer);

        setTypeface();
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

        if (isSkip) {
            txtSkip.setVisibility(View.VISIBLE);
            toolbar.setNavigationIcon(null);
        } else {
            txtSkip.setVisibility(View.GONE);
            toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        }

        setSupportActionBar(toolbar);

        txtCustomTitle.setText("Add Car");

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
                presenter.selectPUCDate(AddCarActivity.this);
                break;

            case R.id.edtInsuranceDate:
                presenter.selectInsuranceDate(AddCarActivity.this);
                break;

            case R.id.edtPermitsDate:
                presenter.selectPermitsDate(AddCarActivity.this);
                break;

            case R.id.btnAdd:
                presenter.addCar(AddCarActivity.this, file, Functions.toStr(edtVehicleNo), selectedMake, selectedYear, selectModelYear, Functions.toStr(edtServiceDate),
                        Functions.toStr(edtPUC), Functions.toStr(edtInsuranceDate), Functions.toStr(edtPermitsDate), odometer.getValue());
                break;

            case R.id.edtServiceDate:
                presenter.selectServiceDate(AddCarActivity.this);
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
    }

    @Override
    public void setModelAdapter(VehicleAdapter adapter) {
        modelCardView.setVisibility(View.VISIBLE);
        modelSpinner.setAdapter(adapter);
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
        Intent intent = new Intent(this, DrawerActivity.class);
        intent.putExtra(AppConstant.FRAGMENT_VALUE, AppConstant.HOME_FRAGMENT);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    @Override
    public void setImage(Bitmap thumbnail, File finalFile) {
        imageCar.setImageBitmap(thumbnail);
        file = finalFile;
    }

    @Override
    public void success() {
        Functions.showToast(this, "Your car has been added successfully.");
        if (isSkip)
            navigateToDashboard();
        else
            finish();
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
    public void onBackPressed() {
        if (isSkip) {
        } else {
            super.onBackPressed();
        }
    }
}
