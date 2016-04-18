package com.rovertech.utomo.app.main.centreDetail.service;

import com.rovertech.utomo.app.main.centreDetail.model.FetchServiceCentreDetailPojo;

/**
 * Created by raghavthakkar on 30-03-2016.
 */
public interface CentreDetailsView {

    void initToolbar();

    void setDetails(FetchServiceCentreDetailPojo centreDetailPojo);

    void showProgressBar();

    void hideProgressBar();

    void showMainLayoutHolder();

    void hideMainLayoutHolder();
}
