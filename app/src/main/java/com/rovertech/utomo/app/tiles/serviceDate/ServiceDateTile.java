package com.rovertech.utomo.app.tiles.serviceDate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class ServiceDateTile extends LinearLayout implements View.OnClickListener, ServiceDateView {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private TextView txtTitle, txtServiceDate, txtChangeDate;
    private ServiceDatePresenter presenter;

    public ServiceDateTile(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public ServiceDateTile(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_tile_service_date, this, true);

        findViewById();

        setTypeface();

        presenter = new ServiceDatePresenterImpl(this);

        txtChangeDate.setOnClickListener(this);

    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(context));
        txtServiceDate.setTypeface(Functions.getNormalFont(context));
        txtChangeDate.setTypeface(Functions.getBoldFont(context));
    }

    private void findViewById() {
        txtChangeDate = (TextView) parentView.findViewById(R.id.txtChangeDate);
        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        txtServiceDate = (TextView) parentView.findViewById(R.id.txtServiceDate);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtChangeDate:
                presenter.setDate(context);
                break;
        }
    }

    @Override
    public void setDate(String convertedDate) {
        txtServiceDate.setText(convertedDate);
    }
}
