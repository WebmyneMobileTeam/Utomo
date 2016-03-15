package com.rovertech.utomo.app.main.centerListing;

import com.rovertech.utomo.app.profile.carlist.CarPojo;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 15-03-2016.
 */
public class ServiceCentrePresenterImpl implements ServiceCentrePresenter {

    ServiceCentreView centreView;

    public ServiceCentrePresenterImpl(ServiceCentreView centreView) {
        this.centreView = centreView;

    }

    @Override
    public ArrayList<ServiceCenterPojo> fetchCentreList() {

        // Call WS

        // Static Data

        ArrayList<ServiceCenterPojo> centerArrayList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            ServiceCenterPojo centre = new ServiceCenterPojo();
            centre.centreName = "Maruti Service Centre";
            centre.centreInfo = "Centre Info";
            centre.centreRating = 2.3;
            centre.centreReviewCount = 3;
            centre.isOffer = true;
            centerArrayList.add(centre);

        }
        return centerArrayList;

    }
}
