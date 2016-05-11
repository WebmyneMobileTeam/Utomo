package com.rovertech.utomo.app.main.booking;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.IntentConstant;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.booking.model.AddressItem;
import com.rovertech.utomo.app.main.booking.model.DropPojo;
import com.rovertech.utomo.app.main.booking.model.PickupPojo;
import com.rovertech.utomo.app.main.centreDetail.model.FetchServiceCentreDetailPojo;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;
import com.rovertech.utomo.app.main.drawer.DrawerActivityRevised;
import com.rovertech.utomo.app.profile.carlist.CarPojo;
import com.rovertech.utomo.app.widget.dialog.AddressDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BookingActivity extends AppCompatActivity implements BookingView, View.OnClickListener {

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
    private TextView txtDate, txtTime;
    private CheckBox checkService, checkBodyWash;

    // Address Section
    private TextView txtDropoffAddress, txtPickupAddress, txtAddress, txtSelectCar,
            txtPickupTitle, txtDropoffTitle;

    // Promo Section
    private CardView promoCardView, edtPromoCard;
    private RadioGroup radioGroup;
    // private RadioButton radioDefault, radioPromo;

    private int ADDRESS_PICK_UP = 1;
    private int ADDRESS_DROP_OFF = 2;
    private BookingRequest bookingRequest;
    private UserProfileOutput userProfileOutput;
    private FetchServiceCentreDetailPojo centreDetailPojo;
    private CarPojo carPojo;
    private LinearLayout addressHolder;
    private static final String bookingDateTimeFormate = "dd MMM, yyyy KK:mm a";

    private String dealerShip = "", date, time;
    private int redirectFrom = 0;
    private boolean isCarSelected = false;

    private ArrayList<CarPojo> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        redirectFrom = getIntent().getIntExtra(IntentConstant.BOOKING_PAGE, 0);

        init();

        presenter = new BookingPresenterImpl(this, this);

        presenter.fetchDetails();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_requestbook, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_home_requestbook:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        this);
                alertDialogBuilder.setTitle("Alert !!")
                        .setMessage("Are you sure want to navigate to Dashboard?")
                        .setCancelable(false)
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // do your stuff
                                dialog.dismiss();

                                Intent iDashboard = new Intent(BookingActivity.this, DrawerActivityRevised.class);
                                iDashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(iDashboard);
                                finish();


                            }
                        })
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                break;
        }


        return super.onOptionsItemSelected(item);
    }

    private void init() {

        initToolbar();

        txtSelectCar = (TextView) findViewById(R.id.txtSelectCar);
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
        checkService = (CheckBox) findViewById(R.id.checkService);
        checkBodyWash = (CheckBox) findViewById(R.id.checkBodyWash);


        //checkIsPickUp = (CheckBox) findViewById(R.id.checkIsPickUp);
        //checkIsDrop = (CheckBox) findViewById(R.id.checkIsDrop);
        txtPickupAddress = (TextView) findViewById(R.id.txtPickupAddress);
        txtDropoffAddress = (TextView) findViewById(R.id.txtDropoffAddress);


        btnPickUpAddressRemove = (TextView) findViewById(R.id.btnPickUpAddressRemove);

        btnDropAddressRemove = (TextView) findViewById(R.id.btnDropAddressRemove);

        promoCardView = (CardView) findViewById(R.id.promoCardView);
        edtPromoCard = (CardView) findViewById(R.id.edtPromoCard);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        // radioDefault = (RadioButton) findViewById(R.id.radioDefault);
        // radioPromo = (RadioButton) findViewById(R.id.radioPromo);
        //txtApply = (TextView) findViewById(R.id.txtApply);
        txtPickupTitle = (TextView) findViewById(R.id.txtPickupTitle);
        txtDropoffTitle = (TextView) findViewById(R.id.txtDropoffTitle);

        txtDate.setOnClickListener(this);
        txtTime.setOnClickListener(this);
        //txtApply.setOnClickListener(this);
        btnBook.setOnClickListener(this);
        txtSelectCar.setOnClickListener(this);

        btnPickUpAddressAdd.setOnClickListener(this);
        btnDropAddressAdd.setOnClickListener(this);

        btnPickUpAddressRemove.setOnClickListener(this);

        btnDropAddressRemove.setOnClickListener(this);

        /*checkIsPickUp.setOnCheckedChangeListener(this);
        checkIsDrop.setOnCheckedChangeListener(this);*/

        // radioDefault.setOnClickListener(this);
        // radioPromo.setOnClickListener(this);

        setTypeface();

        bookingRequest = new BookingRequest();

    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        txtAddress.setTypeface(Functions.getRegularFont(this));

        txtCarName.setTypeface(Functions.getRegularFont(this), Typeface.BOLD);
        txtCarNo.setTypeface(Functions.getRegularFont(this));

        txtDate.setTypeface(Functions.getRegularFont(this), Typeface.BOLD);
        txtTime.setTypeface(Functions.getRegularFont(this), Typeface.BOLD);

        checkService.setTypeface(Functions.getRegularFont(this), Typeface.BOLD);
        checkBodyWash.setTypeface(Functions.getRegularFont(this), Typeface.BOLD);

        txtPickupTitle.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        txtDropoffTitle.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        btnPickUpAddressAdd.setTypeface(Functions.getBoldFont(this));
        btnDropAddressAdd.setTypeface(Functions.getBoldFont(this));
        btnPickUpAddressRemove.setTypeface(Functions.getBoldFont(this));
        btnDropAddressRemove.setTypeface(Functions.getBoldFont(this));

        txtPickupAddress.setTypeface(Functions.getRegularFont(this));
        txtDropoffAddress.setTypeface(Functions.getRegularFont(this));

        btnBook.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);

        txtSelectCar.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);

        edtDescription.setTypeface(Functions.getRegularFont(this));

        txtUsername.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);

    }

    private void initToolbar() {
        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);

        txtCustomTitle.setText("Booking");
        txtCustomTitle.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);

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

        centreDetailPojo = PrefUtils.getCurrentCenter(this);

        dealerShip = centreDetailPojo.Dealership;

        if (redirectFrom == AppConstant.FROM_SC_LIST) {

            isCarSelected = true;
            carPojo = PrefUtils.getCurrentCarSelected(this);
            userProfileOutput = PrefUtils.getUserFullProfileDetails(this);

            if (userProfileOutput != null) {
                txtUsername.setText(String.format("%s", userProfileOutput.Name));
                // txtUsername.setVisibility(View.VISIBLE);
            }

            if (carPojo != null) {
                txtCarName.setText(String.format("%s %s", carPojo.Make, carPojo.Model));
                txtCarNo.setText(String.format("%s", carPojo.VehicleNo));
            }

        } else {
            carList = new ArrayList<>();
            presenter.fetchCarList(this, centreDetailPojo.Dealership);
        }

        txtTitle.setText(centreDetailPojo.ServiceCentreName);
        txtAddress.setText(centreDetailPojo.Address1);

    }

    @Override
    public void setDate(String convertedDate) {
        date = convertedDate;
        txtDate.setText(convertedDate);
        if (time != null) {
            SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM, yyyy hh:mm aa");
            String eDate = date + " " + time;
            Date CurrentDate = new Date();
            Date eDDte;
            try {
                eDDte = df2.parse(eDate);
                if (eDDte.compareTo(CurrentDate) <= 0) {
                    //Log.d("Date is",eDate + " < "+CurrentDate.toString()  );
                    txtDate.setText("Select Date");
                    Functions.showErrorAlert(BookingActivity.this, "Select valid Date And Time", AppConstant.INVALID_TIME);
                } else {
                    //Log.d("Date is",eDate + " >  "+CurrentDate.toString());
                    //txtTime.setText(strTime);
                    txtDate.setText(convertedDate);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            txtDate.setText(convertedDate);
        }

    }

    @Override
    public void setTime(String strTime) {
        time = strTime;
        ///// check //////
        //Log.d("Date is",date + " || "+time);
        if (date != null) {
            SimpleDateFormat df2 = new SimpleDateFormat("dd MMMM, yyyy hh:mm aa");
            String eDate = date + " " + time;
            Date CurrentDate = new Date();
            Date eDDte;
            try {
                eDDte = df2.parse(eDate);

                if (eDDte.compareTo(CurrentDate) <= 0) {
                    //Log.d("Date is",eDate + " < "+CurrentDate.toString()  );
                    txtTime.setText("Select Time");
                    Functions.showErrorAlert(BookingActivity.this, "Select valid Date And Time", AppConstant.INVALID_TIME);
                } else {
                    //Log.d("Date is",eDate + " >  "+CurrentDate.toString());
                    txtTime.setText(strTime);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            txtTime.setText(strTime);
        }

    }

    @Override
    public void setSelectedCar(CarPojo carPojo) {
        this.carPojo = carPojo;
        txtCarName.setVisibility(View.VISIBLE);
        txtCarNo.setVisibility(View.VISIBLE);
        txtCarName.setText(String.format("%s %s", carPojo.Make, carPojo.Model));
        txtCarNo.setText(String.format("%s", carPojo.VehicleNo));
        isCarSelected = true;
    }

    @Override
    public void setCarList(ArrayList<CarPojo> carList) {
        this.carList = carList;

        if (carList.size() > 0) {
            carPojo = carList.get(0);

            Log.e("car", Functions.jsonString(carList.get(0)));

            isCarSelected = true;
            txtUsername.setVisibility(View.GONE);
            txtCarNo.setVisibility(View.VISIBLE);
            txtCarName.setVisibility(View.VISIBLE);
            txtSelectCar.setVisibility(View.VISIBLE);

            txtCarName.setText(String.format("%s %s", carPojo.Make, carPojo.Model));
            txtCarNo.setText(String.format("%s", carPojo.VehicleNo));

        } else {
            isCarSelected = false;
            txtUsername.setVisibility(View.GONE);
            txtCarNo.setVisibility(View.GONE);
            txtCarName.setVisibility(View.GONE);
            txtSelectCar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setAddress(ArrayList<AddressItem> data) {
        DropPojo dropAddress = data.get(0).DropDetails.get(0);
        if (dropAddress != null) {
            bookingRequest.DropAddress = dropAddress.DropAddress;
            bookingRequest.DropCity = dropAddress.DropCity;
            bookingRequest.DropArea = dropAddress.DropArea;
            bookingRequest.DropZipCode = dropAddress.DropZipCode;

            txtDropoffAddress.setVisibility(View.VISIBLE);
            String addressString = String.format("%s, %s, %s, %s", dropAddress.DropAddress, dropAddress.DropArea, dropAddress.DropCity, dropAddress.DropZipCode);
            txtDropoffAddress.setText(addressString);
            btnDropAddressAdd.setText(getString(R.string.item_booking_edit));

        }

        PickupPojo pickupAddress = data.get(0).PickUpDetails.get(0);

        if (pickupAddress != null) {
            bookingRequest.PickAddress = pickupAddress.PickAddress;
            bookingRequest.PickArea = pickupAddress.PickArea;
            bookingRequest.PickCity = pickupAddress.PickCity;
            bookingRequest.PickZipCode = pickupAddress.PickZipCode;

            txtPickupAddress.setVisibility(View.VISIBLE);
            String addressString = String.format("%s, %s, %s, %s", pickupAddress.PickAddress, pickupAddress.PickArea, pickupAddress.PickCity, pickupAddress.PickZipCode);
            txtPickupAddress.setText(addressString);
            btnPickUpAddressAdd.setText(getString(R.string.item_booking_edit));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtDate:
                txtDate.setText("Select Date");
                presenter.selectDate(this);
                break;

            case R.id.txtTime:
                txtTime.setText("Select Time");
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
                if (isCarSelected)
                    if (carPojo.CurrentBooking) {
                        Functions.showErrorAlert(BookingActivity.this, "Can't Book", AppConstant.ALREADY_BOOK);
                    } else {
                        bookRequest();
                    }
                else
                    Functions.showToast(this, "Select car");
                break;

            case R.id.btnPickUpAddressRemove:
                removePickAddress();
                break;

            case R.id.btnDropAddressRemove:
                removeDropAddress();
                break;

            case R.id.txtSelectCar:
                presenter.openCarList(this, centreDetailPojo.Dealership);
                break;

        }
    }

    private void pickAddressSet() {

        AddressDialog addressDialog = new AddressDialog(this, ADDRESS_PICK_UP);
        addressDialog.setOnSubmitListener(new AddressDialog.onSubmitListener() {
            @Override
            public void onSubmit(String address, String area, String city, String zipCode, boolean isSame) {
                txtPickupAddress.setVisibility(View.VISIBLE);
                String addressString = String.format("%s, %s, %s, %s", address, area, city, zipCode);

                txtPickupAddress.setText(addressString);
                bookingRequest.PickAddress = address;
                bookingRequest.PickArea = area;
                bookingRequest.PickCity = city;
                bookingRequest.PickZipCode = zipCode;
                btnPickUpAddressAdd.setText(getString(R.string.item_booking_edit));

                if (isSame) {
                    txtDropoffAddress.setVisibility(View.VISIBLE);
                    txtDropoffAddress.setText(addressString);
                    bookingRequest.DropAddress = address;
                    bookingRequest.DropArea = area;
                    bookingRequest.DropCity = city;
                    bookingRequest.DropZipCode = zipCode;
                    btnDropAddressAdd.setText(getString(R.string.item_booking_edit));

                } else {
                    txtDropoffAddress.setVisibility(View.INVISIBLE);
                }
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
                    String addressString = String.format("%s, %s, %s, %s", address, area, city, zipCode);
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

            bookingRequest.UserID = PrefUtils.getUserID(this);

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
