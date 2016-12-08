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
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.RetrofitErrorHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 17-03-2016.
 */
public class AddressDialog extends BaseDialog implements View.OnClickListener {

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

    public void setOnSubmitListener(AddressDialog.onSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    public AddressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public AddressDialog(Context context, int addressType, boolean isSameAddress) {
        super(context);
        this.context = context;
        this.addressType = addressType;
        this.isSameAddress = isSameAddress;
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
                isSame = isChecked;
                if (isSame) {
                    setAddressDetails(bookingRequest, true);
                } else {
                    setAddressDetails(bookingRequest, false);
                }

            }
        });

        edtZipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.toString().length() == 6) {
                    // call api
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

        if (addressType == 1) {
            txtTitle.setText("Pick-up Address");
            checkSame.setVisibility(View.VISIBLE);
        } else {
            txtTitle.setText("Drop-off Address");
            checkSame.setVisibility(View.GONE);
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
                switch (addressType) {
                    case 1:
                        selectPickAddress();
                        break;

                    case 2:
                        selectDropOffAddress();
                        break;
                }
                break;

            case R.id.imgClose:
                dismiss();
                break;

        }
    }

    private void selectDropOffAddress() {
        if (!isSame) {
            validCheckAddress();
        } else {
            if (onSubmitListener != null) {
                onSubmitListener.onSubmit(
                        Functions.toStr(edtAddress)
                        , Functions.toStr(edtArea)
                        , Functions.toStr(edtCity)
                        , Functions.toStr(edtZipCode), false);
            }
            dismiss();
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
            Functions.hideKeyPad(context, btnOk);
            if (onSubmitListener != null) {
                onSubmitListener.onSubmit(
                        Functions.toStr(edtAddress)
                        , Functions.toStr(edtArea)
                        , Functions.toStr(edtCity)
                        , Functions.toStr(edtZipCode), isSame);
            }
            dismiss();
        }
    }

    private void selectPickAddress() {
        validCheckAddress();
    }

    public interface onSubmitListener {
        void onSubmit(String address, String area, String city, String zipCode, boolean isSame);


    }

    public void setAddressDetails(BookingRequest bookingRequest, boolean isPickUpAddress) {
        this.bookingRequest = bookingRequest;
        if (isPickUpAddress) {

            if (!TextUtils.isEmpty(bookingRequest.PickZipCode)) {
                edtAddress.setText(bookingRequest.PickAddress);
                edtArea.setText(bookingRequest.PickArea);
                edtCity.setText(bookingRequest.PickCity);
                edtZipCode.setText(bookingRequest.PickZipCode);
            }
        } else {
            if (!TextUtils.isEmpty(bookingRequest.DropZipCode)) {
                edtAddress.setText(bookingRequest.DropAddress);
                edtArea.setText(bookingRequest.DropArea);
                edtCity.setText(bookingRequest.DropCity);
                edtZipCode.setText(bookingRequest.DropZipCode);
            }
        }


    }

}
