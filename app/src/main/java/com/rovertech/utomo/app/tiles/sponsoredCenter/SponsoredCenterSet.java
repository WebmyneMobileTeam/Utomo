package com.rovertech.utomo.app.tiles.sponsoredCenter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableLayout;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.centerListing.ServiceCenterPojo;
import com.rovertech.utomo.app.main.centerListing.ServiceCentreListAdapter;
import com.rovertech.utomo.app.widget.familiarrecyclerview.FamiliarRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sagartahelyani on 10-03-2016.
 */
public class SponsoredCenterSet extends LinearLayout {

    Context context;
    private View parentView;
    private LayoutInflater inflater;

    private TextView txtTitle;
    private ImageView imgArrow;
    private ExpandableLayout expandLayout;
    private LinearLayout expandRecommendedClick;

    private List<ServiceCenterPojo> centerList;
    private ServiceCentreListAdapter adapter;
    private FamiliarRecyclerView recyclerView;

    public SponsoredCenterSet(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public SponsoredCenterSet(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentView = inflater.inflate(R.layout.layout_tile_sponsored_set, this, true);

        findViewById();

        setTypeface();

        initRecycler();

    }

    private void initRecycler() {

        recyclerView = (FamiliarRecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        centerList = new ArrayList<>();
        adapter = new ServiceCentreListAdapter(context, centerList, true);
        recyclerView.setAdapter(adapter);

    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
    }

    private void findViewById() {

        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        imgArrow = (ImageView) parentView.findViewById(R.id.imgArrow);
        expandRecommendedClick = (LinearLayout) findViewById(R.id.expandRecommendedClick);
        expandLayout = (ExpandableLayout) findViewById(R.id.expandLayout);

        expandLayout.setExpanded(true);

        expandRecommendedClick.setOnClickListener(new OnClickListener() {
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

    public void setCenterList(ArrayList<ServiceCenterPojo> lstReferTile) {
        centerList.addAll(lstReferTile);
        adapter.setCentreList(centerList);

    }
}
