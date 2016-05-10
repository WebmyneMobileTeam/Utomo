package com.rovertech.utomo.app.widget.dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
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
public class OTPDialog extends BaseDialog implements View.OnClickListener {

    View parentView;
    Context context;

    private TextView txtTitle, txtNote;
    private ImageView imgClose;
    private EditText edtOTP;
    private TextView btnVerify;
    private String otp;

    onSubmitListener onSubmitListener;

    public void setOnSubmitListener(OTPDialog.onSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    public OTPDialog(Context context, String otp) {
        super(context);
        this.otp = otp;
        this.context = context;
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new SlideLeftEnter());
        dismissAnim(new SlideRightExit());

        parentView = View.inflate(context, R.layout.layout_otp_dialog, null);
        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        txtNote = (TextView) parentView.findViewById(R.id.txtNote);
        imgClose = (ImageView) parentView.findViewById(R.id.imgClose);
        edtOTP = (EditText) parentView.findViewById(R.id.edtOTP);
        btnVerify = (TextView) parentView.findViewById(R.id.btnVerify);

        edtOTP.setText(otp);

        setTypeface();

        return parentView;
    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
        txtNote.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
        btnVerify.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
        edtOTP.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
    }

    @Override
    public void setUiBeforShow() {
        txtTitle.setText("Verification");

        setCancelable(false);
        setCanceledOnTouchOutside(false);

        imgClose.setOnClickListener(this);
        btnVerify.setOnClickListener(this);
        parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVerify:
                if (onSubmitListener != null) {
                    onSubmitListener.onSubmit(Functions.toStr(edtOTP));
                }
                break;

            case R.id.imgClose:
                dismiss();
                break;
        }
    }

    public interface onSubmitListener {
        void onSubmit(String otp);
    }
}
