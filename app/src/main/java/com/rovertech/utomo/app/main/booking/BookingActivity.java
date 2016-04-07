package com.rovertech.utomo.app.main.booking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.account.model.UserProfileOutput;
import com.rovertech.utomo.app.bookings.BookingPresenter;
import com.rovertech.utomo.app.bookings.BookingPresenterImpl;
import com.rovertech.utomo.app.bookings.BookingView;
import com.rovertech.utomo.app.bookings.CurrentBooking.BookingRequest;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.IntentConstant;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.centreDetail.model.FetchServiceCentreDetailPojo;
import com.rovertech.utomo.app.profile.carlist.CarPojo;
import com.rovertech.utomo.app.widget.dialog.AddressDialog;

public class BookingActivity extends AppCompatActivity implements BookingView, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Toolbar toolbar;
    //private TextView txtCustomTitle;
    private View parentView;
    private LinearLayout linearLayout;
    private BookingPresenter presenter;
    private Button btnBook;
    private TextView btnPickUpAddressAdd, btnDropAddressAdd;
    private EditText edtDescription;

    // Service Centre Section
    private TextView txtTitle, txtAddress;
    private ImageView imgCenter;

    // User Section
    //private ImageView imgCar;
    private TextView txtUsername, txtCarName, txtCarNo;

    // Schedule Section
    private TextView txtSchedule;
    private TextView txtDate, txtTime;
    private CheckBox checkService, checkBodyWash;

    // Address Section
    private CheckBox checkPickup, checkDropoff;
    private TextView txtSelectPickup, txtSelectDropoff, txtDropoffAddress, txtPickupAddress;

    // Promo Section
    private CardView promoCardView, edtPromoCard;
    private RadioGroup radioGroup;
    private RadioButton radioDefault, radioPromo;
    private EditText edtPromoCode;
    private TextView txtApply, txtPromo;

    private int ADDRESS_PICK_UP = 1;
    private int ADDRESS_DROP_OFF = 2;
    private BookingRequest bookingRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        init();

        presenter = new BookingPresenterImpl(this);

        presenter.fetchDetails();
    }

    private void init() {

        initToolbar();

        edtDescription = (EditText) findViewById(R.id.edtDescription);
        parentView = findViewById(android.R.id.content);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        btnBook = (Button) findViewById(R.id.btnBook);
        btnPickUpAddressAdd = (TextView) findViewById(R.id.btnPickUpAddressAdd);
        btnDropAddressAdd = (TextView) findViewById(R.id.btnDropUpAddressAdd);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        imgCenter = (ImageView) findViewById(R.id.imgCenter);

        // imgCar = (ImageView) findViewById(R.id.imgCar);
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtCarName = (TextView) findViewById(R.id.txtCarName);
        txtCarNo = (TextView) findViewById(R.id.txtCarNo);

        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtSchedule = (TextView) findViewById(R.id.txtSchedule);
        checkService = (CheckBox) findViewById(R.id.checkService);
        checkBodyWash = (CheckBox) findViewById(R.id.checkBodyWash);
        checkPickup = (CheckBox) findViewById(R.id.checkPickup);
        checkDropoff = (CheckBox) findViewById(R.id.checkDropoff);
        txtSelectPickup = (TextView) findViewById(R.id.txtSelectPickup);
        txtSelectDropoff = (TextView) findViewById(R.id.txtSelectDropoff);
        txtPickupAddress = (TextView) findViewById(R.id.txtPickupAddress);
        txtDropoffAddress = (TextView) findViewById(R.id.txtDropoffAddress);

        promoCardView = (CardView) findViewById(R.id.promoCardView);
        edtPromoCard = (CardView) findViewById(R.id.edtPromoCard);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioDefault = (RadioButton) findViewById(R.id.radioDefault);
        radioPromo = (RadioButton) findViewById(R.id.radioPromo);
        edtPromoCode = (EditText) findViewById(R.id.edtPromoCode);
        txtApply = (TextView) findViewById(R.id.txtApply);
        txtPromo = (TextView) findViewById(R.id.txtPromo);

        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        txtApply.setOnClickListener(this);
        btnBook.setOnClickListener(this);

        txtSelectPickup.setOnClickListener(this);
        txtSelectDropoff.setOnClickListener(this);

        checkPickup.setOnCheckedChangeListener(this);
        checkDropoff.setOnCheckedChangeListener(this);
        btnPickUpAddressAdd.setOnClickListener(this);
        btnDropAddressAdd.setOnClickListener(this);
        radioDefault.setOnClickListener(this);
        radioPromo.setOnClickListener(this);

        //setTypeface();

        bookingRequest = new BookingRequest();

    }

    private void setTypeface() {
        btnBook.setTypeface(Functions.getBoldFont(this));
        txtPromo.setTypeface(Functions.getNormalFont(this));
        edtDescription.setTypeface(Functions.getNormalFont(this));
        edtPromoCode.setTypeface(Functions.getNormalFont(this));
        radioDefault.setTypeface(Functions.getNormalFont(this));
        radioPromo.setTypeface(Functions.getNormalFont(this));
        txtApply.setTypeface(Functions.getNormalFont(this));
        txtTitle.setTypeface(Functions.getBoldFont(this));
        txtAddress.setTypeface(Functions.getNormalFont(this));
        txtUsername.setTypeface(Functions.getBoldFont(this));
        txtCarName.setTypeface(Functions.getNormalFont(this));
        txtCarNo.setTypeface(Functions.getNormalFont(this));
        txtDate.setTypeface(Functions.getNormalFont(this));
        txtTime.setTypeface(Functions.getNormalFont(this));
        txtSchedule.setTypeface(Functions.getNormalFont(this));
        checkService.setTypeface(Functions.getNormalFont(this));
        checkBodyWash.setTypeface(Functions.getNormalFont(this));
        checkPickup.setTypeface(Functions.getNormalFont(this));
        checkDropoff.setTypeface(Functions.getNormalFont(this));
        txtSelectPickup.setTypeface(Functions.getNormalFont(this));
        txtSelectDropoff.setTypeface(Functions.getNormalFont(this));
        txtPickupAddress.setTypeface(Functions.getNormalFont(this));
        txtDropoffAddress.setTypeface(Functions.getNormalFont(this));
    }

    private void initToolbar() {
       // txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);

        // txtCustomTitle.setText("Schedule Booking");
       // txtCustomTitle.setTypeface(Functions.getBoldFont(this));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });
    }

    @Override
    public void setDetails() {

        CarPojo carPojo = PrefUtils.getCurrentCarSelected(this);
        UserProfileOutput userProfileOutput = PrefUtils.getUserFullProfileDetails(this);
        if (userProfileOutput != null) {

            txtUsername.setText(String.format("%s", userProfileOutput.Name));
        }

        if (carPojo != null) {
            txtCarName.setText(String.format("%s %s", carPojo.Make, carPojo.Model));
            txtCarNo.setText(String.format("%s", carPojo.VehicleNo));
            // Functions.LoadImage(imgCar, carPojo.CarImage, this);
        }
        Intent intent = getIntent();
        FetchServiceCentreDetailPojo centreDetailPojo = (FetchServiceCentreDetailPojo) intent.getSerializableExtra(IntentConstant.FetchServiceCentreDetailPojo);

    }

    @Override
    public void setDate(String convertedDate) {
        txtDate.setText(convertedDate);
    }

    @Override
    public void setTime(String strTime) {
        txtTime.setText(strTime);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtDate:
                presenter.selectDate(this);
                break;

            case R.id.txtTime:
                presenter.selectTime(this);
                break;

            case R.id.btnPickUpAddressAdd:
                AddressDialog addressDialog = new AddressDialog(this, ADDRESS_PICK_UP);
                addressDialog.setOnSubmitListener(new AddressDialog.onSubmitListener() {
                    @Override
                    public void onSubmit(String address, String area, String city, String zipCode, boolean isSame) {
                        txtPickupAddress.setVisibility(View.VISIBLE);
                        String addressString = String.format("%s ,%s \n%s %s", address, area, city, zipCode);
                        txtPickupAddress.setText(addressString);

                        bookingRequest.PickAddress = address;
                        bookingRequest.PickArea = area;
                        bookingRequest.PickCity = city;
                        bookingRequest.PickZipCode = zipCode;
                    }


                });
                addressDialog.show();
                addressDialog.setAddressDetails(bookingRequest, true);
                break;

            case R.id.btnDropUpAddressAdd:
                AddressDialog addressDialog1 = new AddressDialog(this, ADDRESS_DROP_OFF);
                addressDialog1.setOnSubmitListener(new AddressDialog.onSubmitListener() {
                    @Override
                    public void onSubmit(String address, String area, String city, String zipCode, boolean isSame) {
                        if (isSame) {

                            if (txtPickupAddress.getText().toString().length() == 0) {
                                txtDropoffAddress.setVisibility(View.GONE);
                                Functions.showToast(BookingActivity.this, "No Pick-up address");
                            } else {
                                txtDropoffAddress.setVisibility(View.VISIBLE);
                                txtDropoffAddress.setText(txtPickupAddress.getText().toString());
                            }

                            bookingRequest.PickAddress = address;
                            bookingRequest.PickArea = area;
                            bookingRequest.PickCity = city;
                            bookingRequest.PickZipCode = zipCode;
                        } else {
                            txtDropoffAddress.setVisibility(View.VISIBLE);
                            String addressString = String.format("%s ,%s \n%s %s", address, area, city, zipCode);
                            txtDropoffAddress.setText(addressString);
                            bookingRequest.DropAddress = address;
                            bookingRequest.DropArea = area;
                            bookingRequest.DropCity = city;
                            bookingRequest.DropZipCode = zipCode;
                        }
                    }

                });
                addressDialog1.show();
                addressDialog1.setAddressDetails(bookingRequest, false);
                break;

            case R.id.txtApply:
                break;

            case R.id.radioDefault:
                edtPromoCard.setVisibility(View.GONE);
                break;

            case R.id.radioPromo:
                edtPromoCard.setVisibility(View.VISIBLE);
                break;

            case R.id.btnBook:
                presenter.book(this);
                break;

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        switch (buttonView.getId()) {

            case R.id.checkPickup:
                if (isChecked) {
                    txtSelectPickup.setVisibility(View.VISIBLE);
                    if (txtPickupAddress.getText().toString().length() != 0)
                        txtPickupAddress.setVisibility(View.VISIBLE);

                } else {
                    txtSelectPickup.setVisibility(View.GONE);
                    txtPickupAddress.setVisibility(View.GONE);
                }
                break;

            case R.id.checkDropoff:
                if (isChecked) {
                    txtSelectDropoff.setVisibility(View.VISIBLE);
                    if (txtDropoffAddress.getText().toString().length() != 0)
                        txtDropoffAddress.setVisibility(View.VISIBLE);

                } else {
                    txtDropoffAddress.setVisibility(View.GONE);
                    txtSelectDropoff.setVisibility(View.GONE);
                }
                break;
        }
    }
}
