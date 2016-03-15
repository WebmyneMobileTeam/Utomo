package com.rovertech.utomo.app.tiles.centreServiceType;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

/**
 * Created by sagartahelyani on 09-03-2016.
 */
public class CentreServiceTypeTile extends LinearLayout {

    Context context;
    View parentView;
    private LayoutInflater inflater;

    private ImageView imgServiceIcon;
    private TextView txtServiceName;

    public CentreServiceTypeTile(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public CentreServiceTypeTile(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_service_type, this, true);

        imgServiceIcon = (ImageView) parentView.findViewById(R.id.imgServiceIcon);
        txtServiceName = (TextView) parentView.findViewById(R.id.txtServiceName);

        txtServiceName.setTypeface(Functions.getNormalFont(context));

    }

}
