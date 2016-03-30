package com.rovertech.utomo.app.widget.serviceTypeChip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

/**
 * Created by sagartahelyani on 17-03-2016.
 */
public class ServiceChip extends LinearLayout {

    private View parentView;
    private ImageView imgServiceIcon;
    private TextView txtServiceType;
    private Context context;
    private LayoutInflater inflater;

    public ServiceChip(Context context) {
        super(context);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_service_chip, this);

        imgServiceIcon = (ImageView) parentView.findViewById(R.id.imgServiceIcon);
        txtServiceType = (TextView) parentView.findViewById(R.id.txtServiceType);

        txtServiceType.setTypeface(Functions.getNormalFont(context));

    }
}
