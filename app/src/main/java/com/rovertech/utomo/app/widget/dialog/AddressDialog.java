package com.rovertech.utomo.app.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.animation.SlideEnter.SlideLeftEnter;
import com.flyco.animation.SlideExit.SlideRightExit;
import com.flyco.dialog.widget.base.BaseDialog;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

/**
 * Created by sagartahelyani on 17-03-2016.
 */
public class AddressDialog extends BaseDialog implements View.OnClickListener {

    View parentView;
    Context context;

    private TextView txtTitle;
    private ImageView imgClose;
    private EditText edtAddress;
    private CheckBox checkSame;
    private Button btnOk;
    private int addressType = 0;
    private boolean isSame;

    onSubmitListener onSubmitListener;

    public void setOnSubmitListener(AddressDialog.onSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    public AddressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public AddressDialog(Context context, int addressType) {
        super(context);
        this.context = context;
        this.addressType = addressType;
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new SlideLeftEnter());
        dismissAnim(new SlideRightExit());

        parentView = View.inflate(context, R.layout.layout_address_dialog, null);
        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        imgClose = (ImageView) parentView.findViewById(R.id.imgClose);
        edtAddress = (EditText) parentView.findViewById(R.id.edtAddress);
        btnOk = (Button) parentView.findViewById(R.id.btnOk);
        checkSame = (CheckBox) parentView.findViewById(R.id.checkSame);

        setTypeface();

        checkSame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isSame = isChecked;
            }
        });

        return parentView;
    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(context));
        edtAddress.setTypeface(Functions.getNormalFont(context));
        btnOk.setTypeface(Functions.getBoldFont(context));
        checkSame.setTypeface(Functions.getNormalFont(context));
    }

    @Override
    public void setUiBeforShow() {

        if (addressType == 1) {
            txtTitle.setText("Pick-up Address");
            checkSame.setVisibility(View.GONE);
        } else {
            txtTitle.setText("Drop-off Address");
            checkSame.setVisibility(View.VISIBLE);
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
            if (Functions.toStr(edtAddress).length() == 0) {
                Functions.showToast(context, "Enter Address");
            } else {
                if (onSubmitListener != null) {
                    onSubmitListener.onSubmit(Functions.toStr(edtAddress), isSame);
                }
                dismiss();
            }
        } else {
            if (onSubmitListener != null) {
                onSubmitListener.onSubmit(Functions.toStr(edtAddress), isSame);
            }
            dismiss();
        }
    }

    private void selectPickAddress() {
        if (Functions.toStr(edtAddress).length() == 0) {
            Functions.showToast(context, "Enter Address");
        } else {
            if (onSubmitListener != null) {
                onSubmitListener.onSubmit(Functions.toStr(edtAddress), false);
            }
            dismiss();
        }

    }

    public interface onSubmitListener {
        void onSubmit(String address, boolean isSame);
    }
}
