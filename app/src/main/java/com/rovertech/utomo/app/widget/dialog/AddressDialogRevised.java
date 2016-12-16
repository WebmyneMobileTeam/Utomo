package com.rovertech.utomo.app.widget.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.animation.SlideEnter.SlideLeftEnter;
import com.flyco.animation.SlideExit.SlideRightExit;
import com.flyco.dialog.widget.base.BaseDialog;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.bookings.GetLocationAPI;
import com.rovertech.utomo.app.bookings.model.BookingRequest;
import com.rovertech.utomo.app.bookings.model.LocationResponse;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.RetrofitErrorHelper;
import com.rovertech.utomo.app.main.booking.model.DropPojo;
import com.rovertech.utomo.app.main.booking.model.PickupPojo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 17-03-2016.
 */
public class AddressDialogRevised extends BaseDialog implements View.OnClickListener {

    View parentView;
    Context context;
    private TextView txtTitle;
    private ImageView imgClose;
    private MaterialEditText edtAddress, edtArea, edtCity, edtZipCode;
    private CheckBox checkSame;
    private TextView btnOk;
    private int addressType = 0;
    private boolean isSame;
    private BookingRequest bookingRequest;

    onSubmitListener onSubmitListener;

    private boolean isSameAddress;

    private DropPojo dropPojo;
    private PickupPojo pickupPojo;

    private DropPojo finalDropOffPojo;
    private PickupPojo finalPickupPojo;

    public void setOnSubmitListener(AddressDialogRevised.onSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    public AddressDialogRevised(Context context, int addressType, boolean isSameAddress, PickupPojo pickupPojo, DropPojo dropPojo) {
        super(context);
        this.context = context;
        this.addressType = addressType;
        this.isSameAddress = isSameAddress;
        this.dropPojo = dropPojo;
        this.finalDropOffPojo = dropPojo;
        this.pickupPojo = pickupPojo;
        this.finalPickupPojo = pickupPojo;
        isSame = isSameAddress;
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new SlideLeftEnter());
        dismissAnim(new SlideRightExit());

        parentView = View.inflate(context, R.layout.layout_address_dialog, null);
        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        imgClose = (ImageView) parentView.findViewById(R.id.imgClose);
        edtAddress = (MaterialEditText) parentView.findViewById(R.id.edtAddress);
        edtArea = (MaterialEditText) parentView.findViewById(R.id.edtArea);
        edtCity = (MaterialEditText) parentView.findViewById(R.id.edtCity);
        edtZipCode = (MaterialEditText) parentView.findViewById(R.id.edtZipCode);
        btnOk = (TextView) parentView.findViewById(R.id.btnOk);
        checkSame = (CheckBox) parentView.findViewById(R.id.checkSame);

        setTypeface();

        edtArea.setEnabled(false);
        edtCity.setEnabled(false);

        if (isSameAddress) {
            checkSame.setChecked(true);
        }

        checkSame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSameAddress = isChecked;
            }
        });

        edtZipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().length() == 6) {
                    Functions.hideKeyPad(context, edtZipCode);

                    final ProgressDialog progressDialog = ProgressDialog.show(context, "Loading", "Please wait..", false, false);

                    GetLocationAPI api = UtomoApplication.retrofit.create(GetLocationAPI.class);
                    Call<LocationResponse> call = api.getLocationFromZip(charSequence.toString());
                    call.enqueue(new Callback<LocationResponse>() {
                        @Override
                        public void onResponse(Call<LocationResponse> call, Response<LocationResponse> response) {
                            progressDialog.dismiss();
                            if (response.body() != null) {
                                LocationResponse mainResponse = response.body();

                                if (mainResponse.GetLocationFromPIN.ResponseCode == 1) {
                                    Log.e("response", Functions.jsonString(mainResponse));
                                    edtArea.setText(mainResponse.GetLocationFromPIN.Data.get(0).Area);
                                    edtCity.setText(mainResponse.GetLocationFromPIN.Data.get(0).City);
                                } else {
                                    Functions.showErrorAlert(context, "Error", mainResponse.GetLocationFromPIN.ResponseMessage);
                                }
                            } else {
                                Functions.showErrorAlert(context, "Error", "Something went wrong. Please try again.");
                            }
                        }

                        @Override
                        public void onFailure(Call<LocationResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            RetrofitErrorHelper.showErrorMsg(t, context);
                        }
                    });
                } else {
                    edtArea.setText("");
                    edtCity.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setAddressDetails();

        return parentView;
    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
        edtAddress.setTypeface(Functions.getRegularFont(context));
        edtArea.setTypeface(Functions.getRegularFont(context));
        edtCity.setTypeface(Functions.getRegularFont(context));
        edtZipCode.setTypeface(Functions.getRegularFont(context));
        btnOk.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
        checkSame.setTypeface(Functions.getRegularFont(context));
    }

    @Override
    public void setUiBeforShow() {

        if (addressType == AppConstant.TYPE_PICK_UP) {
            txtTitle.setText("Pick-up Address");
            checkSame.setText("Is your Drop-off Address same as above?");

        } else {
            txtTitle.setText("Drop-off Address");
            checkSame.setText("Is your Pick-up Address same as above?");
        }

        setCancelable(false);

        imgClose.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Functions.hideKeyPad(context, v);

        switch (v.getId()) {
            case R.id.btnOk:
                sendAddress();
                break;

            case R.id.imgClose:
                dismiss();
                break;

        }
    }

    private void validCheckAddress() {
        if (TextUtils.isEmpty(Functions.toStr(edtAddress))) {
            edtAddress.setError("Enter Address");
        } else if (TextUtils.isEmpty(Functions.toStr(edtArea))) {
            edtArea.setError("Enter Area");
        } else if (TextUtils.isEmpty(Functions.toStr(edtCity))) {
            edtCity.setError("Enter City");
        } else if (TextUtils.isEmpty(Functions.toStr(edtZipCode)) || Functions.toStr(edtZipCode).length() < 6) {
            edtZipCode.setError("Enter ZipCode");
        } else {
            setAddressPojo();
            Functions.hideKeyPad(context, btnOk);
            if (onSubmitListener != null) {
                onSubmitListener.onSubmit(
                        addressType,
                        finalPickupPojo,
                        finalDropOffPojo,
                        isSame);
            }
            dismiss();
        }
    }

    private void setAddressPojo() {

        if (addressType == AppConstant.TYPE_PICK_UP) {
            finalPickupPojo = new PickupPojo();
            finalPickupPojo.PickArea = Functions.toStr(edtArea);
            finalPickupPojo.PickAddress = Functions.toStr(edtAddress);
            finalPickupPojo.PickCity = Functions.toStr(edtCity);
            finalPickupPojo.PickZipCode = Functions.toStr(edtZipCode);
        } else {
            finalDropOffPojo = new DropPojo();
            finalDropOffPojo.DropAddress = Functions.toStr(edtAddress);
            finalDropOffPojo.DropArea = Functions.toStr(edtArea);
            finalDropOffPojo.DropCity = Functions.toStr(edtCity);
            finalDropOffPojo.DropZipCode = Functions.toStr(edtZipCode);
        }

        if (isSameAddress) {
            finalPickupPojo = new PickupPojo();
            finalPickupPojo.PickArea = Functions.toStr(edtArea);
            finalPickupPojo.PickAddress = Functions.toStr(edtAddress);
            finalPickupPojo.PickCity = Functions.toStr(edtCity);
            finalPickupPojo.PickZipCode = Functions.toStr(edtZipCode);

            finalDropOffPojo = new DropPojo();
            finalDropOffPojo.DropAddress = Functions.toStr(edtAddress);
            finalDropOffPojo.DropArea = Functions.toStr(edtArea);
            finalDropOffPojo.DropCity = Functions.toStr(edtCity);
            finalDropOffPojo.DropZipCode = Functions.toStr(edtZipCode);
        }
    }

    private void sendAddress() {

        validCheckAddress();
    }

    public interface onSubmitListener {
        void onSubmit(int addressType, PickupPojo finalPickup, DropPojo finalDropOff, boolean isSame);
    }

    private void setAddressDetails() {

        if (pickupPojo != null & dropPojo != null) {
            if (addressType == AppConstant.TYPE_PICK_UP) {
                edtAddress.setText(pickupPojo.PickAddress);
                edtArea.setText(pickupPojo.PickArea);
                edtCity.setText(pickupPojo.PickCity);
                edtZipCode.setText(pickupPojo.PickZipCode);

            } else {
                edtAddress.setText(dropPojo.DropAddress);
                edtArea.setText(dropPojo.DropArea);
                edtCity.setText(dropPojo.DropCity);
                edtZipCode.setText(dropPojo.DropZipCode);
            }
        }
    }

}
