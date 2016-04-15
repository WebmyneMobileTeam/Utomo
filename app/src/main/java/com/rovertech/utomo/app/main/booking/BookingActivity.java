package com.rovertech.utomo.app.main.booking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.account.model.UserProfileOutput;
import com.rovertech.utomo.app.bookings.BookingPresenter;
import com.rovertech.utomo.app.bookings.BookingPresenterImpl;
import com.rovertech.utomo.app.bookings.BookingView;
import com.rovertech.utomo.app.bookings.model.BookingRequest;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.IntentConstant;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.centreDetail.model.FetchServiceCentreDetailPojo;
import com.rovertech.utomo.app.profile.carlist.CarPojo;
import com.rovertech.utomo.app.widget.dialog.AddressDialog;

import java.text.SimpleDateFormat;

public class BookingActivity extends AppCompatActivity implements BookingView, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Toolbar toolbar;
    private TextView txtCustomTitle;
    private View parentView;
    private LinearLayout linearLayout;
    private BookingPresenter presenter;
    private Button btnBook;
    private TextView btnPickUpAddressAdd, btnDropAddressAdd;
    private TextView btnPickUpAddressRemove;
    private TextView btnDropAddressRemove;
    private EditText edtDescription;

    // Service Centre Section
    private TextView txtTitle;
    //private ImageView imgCenter;

    // User Section
    //private ImageView imgCar;
    private TextView txtUsername, txtCarName, txtCarNo;

    // Schedule Section
    private TextView txtSchedule;
    private TextView txtDate, txtTime;
    private CheckBox checkService, checkBodyWash;

    // Address Section
    private CheckBox checkPickup, checkDropoff;
    //private  CheckBox  checkIsPickUp,checkIsDrop;
    private TextView txtSelectPickup, txtSelectDropoff, txtDropoffAddress, txtPickupAddress, txtAddress;

    // Promo Section
    private CardView promoCardView, edtPromoCard;
    private RadioGroup radioGroup;
    // private RadioButton radioDefault, radioPromo;
    private EditText edtPromoCode;
    //private TextView txtApply, txtPromo;

    private int ADDRESS_PICK_UP = 1;
    private int ADDRESS_DROP_OFF = 2;
    private BookingRequest bookingRequest;
    private UserProfileOutput userProfileOutput;
    private FetchServiceCentreDetailPojo centreDetailPojo;
    private CarPojo carPojo;
    private LinearLayout addressHolder;
    private static final String bookingDateTimeFormate = "dd MMM, yyyy KK:mm a";

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
        addressHolder = (LinearLayout) findViewById(R.id.addressHolder);
        btnBook = (Button) findViewById(R.id.btnBook);
        btnPickUpAddressAdd = (TextView) findViewById(R.id.btnPickUpAddressAdd);
        btnDropAddressAdd = (TextView) findViewById(R.id.btnDropUpAddressAdd);

        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        //imgCenter = (ImageView) findViewById(R.id.imgCenter);

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


        //checkIsPickUp = (CheckBox) findViewById(R.id.checkIsPickUp);
        //checkIsDrop = (CheckBox) findViewById(R.id.checkIsDrop);
        txtSelectPickup = (TextView) findViewById(R.id.txtSelectPickup);
        txtSelectDropoff = (TextView) findViewById(R.id.txtSelectDropoff);
        txtPickupAddress = (TextView) findViewById(R.id.txtPickupAddress);
        txtDropoffAddress = (TextView) findViewById(R.id.txtDropoffAddress);


        btnPickUpAddressRemove = (TextView) findViewById(R.id.btnPickUpAddressRemove);

        btnDropAddressRemove = (TextView) findViewById(R.id.btnDropAddressRemove);

        promoCardView = (CardView) findViewById(R.id.promoCardView);
        edtPromoCard = (CardView) findViewById(R.id.edtPromoCard);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        // radioDefault = (RadioButton) findViewById(R.id.radioDefault);
        // radioPromo = (RadioButton) findViewById(R.id.radioPromo);
        edtPromoCode = (EditText) findViewById(R.id.edtPromoCode);
        //txtApply = (TextView) findViewById(R.id.txtApply);
        // txtPromo = (TextView) findViewById(R.id.txtPromo);

        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        //txtApply.setOnClickListener(this);
        btnBook.setOnClickListener(this);

        txtSelectPickup.setOnClickListener(this);
        txtSelectDropoff.setOnClickListener(this);

        btnPickUpAddressAdd.setOnClickListener(this);
        btnDropAddressAdd.setOnClickListener(this);

        btnPickUpAddressRemove.setOnClickListener(this);

        btnDropAddressRemove.setOnClickListener(this);

        checkPickup.setOnCheckedChangeListener(this);
        checkDropoff.setOnCheckedChangeListener(this);
        /*checkIsPickUp.setOnCheckedChangeListener(this);
        checkIsDrop.setOnCheckedChangeListener(this);*/

        // radioDefault.setOnClickListener(this);
        // radioPromo.setOnClickListener(this);
        //setTypeface();
        bookingRequest = new BookingRequest();

    }

    private void setTypeface() {
        btnBook.setTypeface(Functions.getBoldFont(this));
        //txtPromo.setTypeface(Functions.getNormalFont(this));
        edtDescription.setTypeface(Functions.getNormalFont(this));
        edtPromoCode.setTypeface(Functions.getNormalFont(this));
        //  radioDefault.setTypeface(Functions.getNormalFont(this));
        //  radioPromo.setTypeface(Functions.getNormalFont(this));
        //txtApply.setTypeface(Functions.getNormalFont(this));
        txtTitle.setTypeface(Functions.getBoldFont(this));
        //txtAddress.setTypeface(Functions.getNormalFont(this));
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
        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);

        txtCustomTitle.setText("Schedule Booking");
        txtCustomTitle.setTypeface(Functions.getBoldFont(this));

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

        carPojo = PrefUtils.getCurrentCarSelected(this);
        userProfileOutput = PrefUtils.getUserFullProfileDetails(this);
        if (userProfileOutput != null) {

            txtUsername.setText(String.format("%s", userProfileOutput.Name));
        }

        if (carPojo != null) {
            txtCarName.setText(String.format("%s %s", carPojo.Make, carPojo.Model));
            txtCarNo.setText(String.format("%s", carPojo.VehicleNo));
            // Functions.LoadImage(imgCar, carPojo.CarImage, this);
        }
        Intent intent = getIntent();
        centreDetailPojo = (FetchServiceCentreDetailPojo) intent.getSerializableExtra(IntentConstant.FetchServiceCentreDetailPojo);
        if (centreDetailPojo != null) {

            txtTitle.setText(centreDetailPojo.ServiceCentreName);
            txtAddress.setText(centreDetailPojo.Address1);

        }

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
                pickAddressSet();
                break;

            case R.id.btnDropUpAddressAdd:
                dropAddressSet();
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
                bookRequest();
                break;
            case R.id.btnPickUpAddressRemove:
                removePickAddress();
                break;
            case R.id.btnDropAddressRemove:
                removeDropAddress();
                break;


        }
    }

    private void pickAddressSet() {


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
                btnPickUpAddressAdd.setText(getString(R.string.item_booking_edit));
            }


        });
        addressDialog.show();
        addressDialog.setAddressDetails(bookingRequest, true);
    }

    private void dropAddressSet() {
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
                btnDropAddressAdd.setText(getString(R.string.item_booking_edit));

            }

        });
        addressDialog1.show();
        addressDialog1.setAddressDetails(bookingRequest, false);
    }

    private void removePickAddress() {
        btnPickUpAddressAdd.setText(getString(R.string.item_booking_add));
        txtPickupAddress.setText("");
        bookingRequest.PickAddress = "";
        bookingRequest.PickArea = "";
        bookingRequest.PickCity = "";
        bookingRequest.PickZipCode = "";
    }

    private void removeDropAddress() {
        btnDropAddressAdd.setText(getString(R.string.item_booking_add));
        txtDropoffAddress.setText("");

        bookingRequest.DropAddress = "";
        bookingRequest.DropArea = "";
        bookingRequest.DropCity = "";
        bookingRequest.DropZipCode = "";

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


    public void bookRequest() {

        try {

            bookingRequest.Description = Functions.toStr(edtDescription);

            if (!TextUtils.isEmpty(bookingRequest.DropAddress) && !TextUtils.isEmpty(bookingRequest.DropArea)
                    && !TextUtils.isEmpty(bookingRequest.DropCity) && !TextUtils.isEmpty(bookingRequest.DropZipCode)) {

                bookingRequest.IsDrop = true;
            } else {

                bookingRequest.IsDrop = false;
            }

            bookingRequest.IsBodyShop = checkBodyWash.isChecked();

            if (!checkBodyWash.isChecked() && !checkService.isChecked()) {


                Toast.makeText(this, "Please, Select Service Type.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!TextUtils.isEmpty(bookingRequest.PickAddress) && !TextUtils.isEmpty(bookingRequest.PickArea)
                    && !TextUtils.isEmpty(bookingRequest.PickCity) && !TextUtils.isEmpty(bookingRequest.PickZipCode)) {

                bookingRequest.IsPickup = true;
            } else {
                bookingRequest.IsPickup = false;
            }

            bookingRequest.IsService = checkService.isChecked();


            String bookingDateAndTime = txtDate.getText() + " " + txtTime.getText();
            boolean isValidDateTime = checkValidBookDateAndTime(bookingDateAndTime);

            if (!isValidDateTime) {
                Toast.makeText(this, "Please, Select Date and Time.", Toast.LENGTH_SHORT).show();
                return;
            } else {


                bookingRequest.PreferredDateTime = Functions.parseDate(bookingDateAndTime, bookingDateTimeFormate, Functions.ServerDateTimeFormat);

            }

            bookingRequest.ServiceCentreID = centreDetailPojo.ServiceCentreID;

            bookingRequest.UserID = userProfileOutput.UserID;

            bookingRequest.VehicleID = carPojo.VehicleID;

            presenter.book(this, bookingRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean checkValidBookDateAndTime(String dateTime) {
        boolean validDateAndTime;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(bookingDateTimeFormate);
            simpleDateFormat.parse(dateTime);
            validDateAndTime = true;
        } catch (Exception e) {
            validDateAndTime = false;
        }
        return validDateAndTime;
    }
}
