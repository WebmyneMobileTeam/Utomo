package com.rovertech.utomo.app.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.flyco.animation.SlideEnter.SlideLeftEnter;
import com.flyco.animation.SlideExit.SlideRightExit;
import com.flyco.dialog.widget.base.BaseDialog;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.widget.IconEditText;

/**
 * Created by sagartahelyani on 17-03-2016.
 */
public class ChangePasswordDialog extends BaseDialog implements View.OnClickListener {

    View parentView;
    Context context;

    private IconEditText edtPassword, edtRePassword;
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
        edtPassword = (IconEditText) parentView.findViewById(R.id.edtPassword);
        edtRePassword = (IconEditText) parentView.findViewById(R.id.edtRePassword);
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
        if (edtPassword.getText().toString().trim().length() == 0 || edtRePassword.getText().toString().trim().length() == 0) {
            Functions.showToast(context, "Password can't be empty");
        } else {
            if (edtPassword.getText().toString().trim().equals(edtRePassword.getText().toString().trim()) && onSubmitListener != null) {
                onSubmitListener.onSubmit(edtPassword.getText().toString().trim());
            } else {
                Functions.showToast(context, "Password mismatch");
            }
        }
    }

    public interface onSubmitListener {
        void onSubmit(String password);
    }
}
