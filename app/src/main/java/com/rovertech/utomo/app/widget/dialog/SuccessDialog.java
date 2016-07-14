package com.rovertech.utomo.app.widget.dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.flyco.animation.SlideEnter.SlideLeftEnter;
import com.flyco.animation.SlideExit.SlideRightExit;
import com.flyco.dialog.widget.base.BaseDialog;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

/**
 * Created by sagartahelyani on 18-03-2016.
 */
public class SuccessDialog extends BaseDialog implements View.OnClickListener {

    private Context context;
    View parentView;
    String string;
    private TextView txtTitle, txtView;
    private TextView btnOk;
    private onSubmitListener onSubmitListener;

    public void setOnSubmitListener(SuccessDialog.onSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    public SuccessDialog(Context context, String string) {
        super(context);
        this.context = context;
        this.string = string;
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new SlideLeftEnter());
        dismissAnim(new SlideRightExit());

        parentView = View.inflate(context, R.layout.layout_success_dialog, null);

        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        txtView = (TextView) parentView.findViewById(R.id.txtView);
        btnOk = (TextView) parentView.findViewById(R.id.btnOk);

        txtView.setText(string);

        setTypeface();

        return parentView;
    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
        txtView.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
        btnOk.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
    }

    @Override
    public void setUiBeforShow() {
        setCancelable(false);

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
                dismiss();
                new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        if (onSubmitListener != null) {
                            onSubmitListener.onSubmit();
                        }
                    }
                }.start();
                break;
        }
    }

    public interface onSubmitListener {
        void onSubmit();
    }
}
