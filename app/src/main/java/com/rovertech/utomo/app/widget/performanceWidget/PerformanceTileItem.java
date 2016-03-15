package com.rovertech.utomo.app.widget.performanceWidget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.rovertech.utomo.app.R;

/**
 * Created by sagartahelyani on 09-03-2016.
 */
public class PerformanceTileItem extends LinearLayout {

    Context context;
    View parentView;
    private LayoutInflater inflater;


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

    }

}
