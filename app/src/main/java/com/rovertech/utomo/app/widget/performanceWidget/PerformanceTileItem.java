package com.rovertech.utomo.app.widget.performanceWidget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liulishuo.magicprogresswidget.MagicProgressBar;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.home.car.model.Performance;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by sagartahelyani on 09-03-2016.
 */
public class PerformanceTileItem extends LinearLayout {

    Context context;
    View parentView;
    private LayoutInflater inflater;
    private TextView txtItemName, txtItemValue, txtReset, txtProgressStatus;

    private MagicProgressBar progressBar;

    private onResetListener onResetListener;

    private Performance performance;

    public void setOnResetListener(PerformanceTileItem.onResetListener onResetListener) {
        this.onResetListener = onResetListener;
    }

    public PerformanceTileItem(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PerformanceTileItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;


        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_performance_item, this, true);

        progressBar = (MagicProgressBar) parentView.findViewById(R.id.progressBar);
        txtProgressStatus = (TextView) parentView.findViewById(R.id.txtProgressStatus);
        txtItemName = (TextView) parentView.findViewById(R.id.txtItemName);
        txtItemValue = (TextView) parentView.findViewById(R.id.txtItemValue);
        txtReset = (TextView) parentView.findViewById(R.id.txtReset);

        setTypeface();
    }

    private void setTypeface() {
        txtProgressStatus.setTypeface(Functions.getRegularFont(context));
        txtItemName.setTypeface(Functions.getRegularFont(context));
        txtItemValue.setTypeface(Functions.getRegularFont(context));
        txtReset.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
    }

    public void setValue(final Performance performance) {
        this.performance = performance;

        txtItemName.setText(performance.CriteriaName);

        float f = Float.parseFloat(performance.PerformancePercentage);

        progressBar.setPercent(f / 100);
        progressBar.setFillColor(Functions.getColor(context, (int) f));

        txtItemValue.setText(String.format("%d %s", (int) f, "%"));

        txtProgressStatus.setText(Functions.getProgressStatus(context, f, performance.Type));
        txtProgressStatus.setTextColor(Functions.getColor(context, f));

        txtReset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (performance.Type == 0) {
                    if (onResetListener != null)
                        onResetListener.onReset(performance.CarPerformanceMatrixID, "", performance.CriteriaName);
                } else {
                    selectDate();
                }
            }
        });
    }

    private void selectDate() {

        Calendar now = Calendar.getInstance();

        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String date = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        if (onResetListener != null)
                            onResetListener.onReset(performance.CarPerformanceMatrixID, date, performance.CriteriaName);
                    }
                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.setMaxDate(now);
        datePickerDialog.show(((Activity) context).getFragmentManager(), "Select Date");
    }

    public interface onResetListener {
        void onReset(int matricesId, String date, String matricesName);
    }


}
