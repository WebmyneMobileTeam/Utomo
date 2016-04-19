package com.rovertech.utomo.app.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.flyco.animation.SlideEnter.SlideLeftEnter;
import com.flyco.animation.SlideExit.SlideRightExit;
import com.flyco.dialog.widget.base.BaseDialog;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

/**
 * Created by sagartahelyani on 17-03-2016.
 */
public class ChangePasswordDialog extends BaseDialog implements View.OnClickListener {

    View parentView;
    Context context;

    private EditText edtPassword, edtRePassword;
    private Button btnSubmit;

    onSubmitListener onSubmitListener;

    public void setOnSubmitListener(ChangePasswordDialog.onSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    public ChangePasswordDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new SlideLeftEnter());
        dismissAnim(new SlideRightExit());

        parentView = View.inflate(context, R.layout.layout_change_pwd, null);
        edtPassword = (EditText) parentView.findViewById(R.id.edtPassword);
        edtRePassword = (EditText) parentView.findViewById(R.id.edtRePassword);
        btnSubmit = (Button) parentView.findViewById(R.id.btnSubmit);

        setTypeface();

        return parentView;
    }

    private void setTypeface() {
        edtPassword.setTypeface(Functions.getRegularFont(context));
        edtRePassword.setTypeface(Functions.getRegularFont(context));
        btnSubmit.setTypeface(Functions.getBoldFont(context));
    }

    @Override
    public void setUiBeforShow() {

        setCancelable(false);

        btnSubmit.setOnClickListener(this);
        parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                checkValidation();
                break;
        }
    }

    private void checkValidation() {
        if (Functions.toStr(edtPassword).length() == 0 || Functions.toStr(edtRePassword).length() == 0) {
            Functions.showToast(context, "Password can't be empty");
        } else {
            if (Functions.toStr(edtPassword).equals(Functions.toStr(edtRePassword)) && onSubmitListener != null) {
                onSubmitListener.onSubmit(Functions.toStr(edtPassword));
            } else {
                Functions.showToast(context, "Password mismatch");
            }
        }
    }

    public interface onSubmitListener {
        void onSubmit(String password);
    }
}
