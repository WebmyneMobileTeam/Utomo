package com.rovertech.utomo.app.addCar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.widget.Odometer;

public class AddCarActivity extends AppCompatActivity implements AddcarView, View.OnClickListener {

    private Toolbar toolbar;
    private TextView txtCustomTitle, txtAdd;
    private View parentView;
    private RelativeLayout imageSelectLayout;
    private ImageView imageCar;
    private EditText edtVehicleNo, edtMakeId, edtModelId, edtYear, edtRegNo, edtPUC, edtInsuranceDate;
    private TextView edtKms;
    private AddCarPresenter presenter;
    private Odometer odometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        presenter = new AddCarPresenterImpl(this);

        init();

    }

    private void init() {
        initToolbar();

        parentView = findViewById(android.R.id.content);
        txtAdd = (TextView) findViewById(R.id.txtAdd);
        imageSelectLayout = (RelativeLayout) findViewById(R.id.imageSelectLayout);
        imageCar = (ImageView) findViewById(R.id.imageCar);
        edtVehicleNo = (EditText) findViewById(R.id.edtVehicleNo);
        edtMakeId = (EditText) findViewById(R.id.edtMakeId);
        edtModelId = (EditText) findViewById(R.id.edtModelId);
        edtYear = (EditText) findViewById(R.id.edtYear);
        edtRegNo = (EditText) findViewById(R.id.edtRegNo);
        edtPUC = (EditText) findViewById(R.id.edtPUC);
        edtInsuranceDate = (EditText) findViewById(R.id.edtInsuranceDate);
        edtKms = (TextView) findViewById(R.id.edtKms);
        odometer = (Odometer) findViewById(R.id.odometer);

        setTypeface();

        edtPUC.setOnClickListener(this);
        edtInsuranceDate.setOnClickListener(this);
        txtAdd.setOnClickListener(this);
    }

    private void setTypeface() {
        txtAdd.setTypeface(Functions.getNormalFont(this));
        edtVehicleNo.setTypeface(Functions.getNormalFont(this));
        edtMakeId.setTypeface(Functions.getNormalFont(this));
        edtModelId.setTypeface(Functions.getNormalFont(this));
        edtYear.setTypeface(Functions.getNormalFont(this));
        edtRegNo.setTypeface(Functions.getNormalFont(this));
        edtPUC.setTypeface(Functions.getNormalFont(this));
        edtInsuranceDate.setTypeface(Functions.getNormalFont(this));
        edtKms.setTypeface(Functions.getNormalFont(this));
        txtCustomTitle.setTypeface(Functions.getNormalFont(this));
    }

    private void initToolbar() {

        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
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

            case R.id.txtAdd:
                Toast.makeText(AddCarActivity.this, odometer.getValue(), Toast.LENGTH_SHORT).show();
                //odometer.getValue();
                break;
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
}
