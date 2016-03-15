package com.rovertech.utomo.app.tiles.performance;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class PerformanceTile extends LinearLayout {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private TextView txtTitle;
    private ExpandableLayout expandLayout;
    private LinearLayout expandClickLayout;
    private ImageView imgArrow;

    public PerformanceTile(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public PerformanceTile(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_tile_performance, this, true);

        findViewById();

        setTypeface();

    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(context));

    }

    private void findViewById() {
        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        expandLayout = (ExpandableLayout) parentView.findViewById(R.id.expandLayout);
        imgArrow = (ImageView) parentView.findViewById(R.id.imgArrow);
        expandClickLayout = (LinearLayout) parentView.findViewById(R.id.expandClickLayout);

        expandClickLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expandLayout.isExpanded()) {
                    Functions.antirotateViewClockwise(imgArrow);
                } else {
                    Functions.rotateViewClockwise(imgArrow);
                }
                expandLayout.toggle();
            }
        });

    }
}
