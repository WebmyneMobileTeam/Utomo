package com.rovertech.utomo.app.main.centerListing;

import com.rovertech.utomo.app.account.adapter.CityAdapter;
import com.rovertech.utomo.app.account.model.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sagartahelyani on 15-03-2016.
 */
public interface ServiceCentreView {

    void setListing(List<ServiceCenterPojo> centerArrayList, int lastCentreId);

    void hideScroll();

    void setCityAdapter(CityAdapter adapter, ArrayList<City> data);

    void showMapContainer();

    void hideMapContainer();

    void showFab();

    void hideFab();

    void showListLayout();

    void hideListLayout();







}
