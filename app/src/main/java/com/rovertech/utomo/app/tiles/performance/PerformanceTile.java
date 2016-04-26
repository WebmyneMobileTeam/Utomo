package com.rovertech.utomo.app.tiles.performance;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.home.car.model.Performance;
import com.rovertech.utomo.app.widget.performanceWidget.PerformanceTileItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class PerformanceTile extends LinearLayout implements View.OnClickListener, PerformanceTileItem.onResetListener {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private TextView txtTitle;
    private ExpandableLayout expandLayout;
    private LinearLayout expandClickLayout, initialLayout, remainsLayout;
    private ImageView imgArrow;
    private TextView txtMore;

    private onPerformanceResetListener onPerformanceResetListener;

    public void setOnPerformanceResetListener(PerformanceTile.onPerformanceResetListener onPerformanceResetListener) {
        this.onPerformanceResetListener = onPerformanceResetListener;
    }


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
        txtMore.setTypeface(Functions.getBoldFont(context));
    }

    private void findViewById() {
        remainsLayout = (LinearLayout) parentView.findViewById(R.id.remainsLayout);
        initialLayout = (LinearLayout) parentView.findViewById(R.id.initialLayout);
        txtMore = (TextView) parentView.findViewById(R.id.txtMore);
        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        expandLayout = (ExpandableLayout) parentView.findViewById(R.id.expandLayout);
        imgArrow = (ImageView) parentView.findViewById(R.id.imgArrow);
        expandClickLayout = (LinearLayout) parentView.findViewById(R.id.expandClickLayout);

        expandClickLayout.setOnClickListener(this);
        txtMore.setOnClickListener(this);
    }

    private void doExpandCollapse() {

        if (expandLayout.isExpanded()) {
            Functions.antirotateViewClockwise(imgArrow);
            txtMore.setText("More");
        } else {
            Functions.rotateViewClockwise(imgArrow);
            txtMore.setText("Less");
        }

        expandLayout.toggle();
    }

    public void setPerformance(ArrayList<Performance> lstPerformance) {

        initialLayout.removeAllViews();
        initialLayout.invalidate();
        remainsLayout.removeAllViews();
        remainsLayout.invalidate();

        Collections.sort(lstPerformance, new Comparator<Performance>() {
            @Override
            public int compare(Performance lhs, Performance rhs) {
                return Float.compare(Float.parseFloat(lhs.PerformancePercentage), Float.parseFloat(rhs.PerformancePercentage));
            }
        });

        for (int i = 0; i < 3; i++) {
            PerformanceTileItem item = new PerformanceTileItem(context);
            item.setOnResetListener(this);
            item.setValue(lstPerformance.get(i));
            initialLayout.addView(item);
        }

        for (int j = 3; j < lstPerformance.size(); j++) {
            PerformanceTileItem item = new PerformanceTileItem(context);
            item.setOnResetListener(this);
            item.setValue(lstPerformance.get(j));
            remainsLayout.addView(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtMore:
                doExpandCollapse();
                break;

            case R.id.expandClickLayout:
                doExpandCollapse();
                break;
        }
    }

    @Override
    public void onReset(int matricesId, String date) {
        if (onPerformanceResetListener != null)
            onPerformanceResetListener.onReset(matricesId, date);
    }

    public interface onPerformanceResetListener {
        public void onReset(int matricesId, String date);
    }
}
