package com.rovertech.utomo.app.profile.carlist;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 14-03-2016.
 */
public class CarFragmentPresenterImpl implements CarFragmentPresenter {

    CarFragmentView carFragmentView;

    public CarFragmentPresenterImpl(CarFragmentView carFragmentView) {
        this.carFragmentView = carFragmentView;

    }

    @Override
    public ArrayList<CarPojo> fetchMyCars() {

        // Call WS for fetching car list

        // Static

        ArrayList<CarPojo> carPojoList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {

            CarPojo car = new CarPojo();
            car.carName = "Car " + i;
            car.carSpeed = 37;
            carPojoList.add(car);

        }
        return carPojoList;
    }
}
