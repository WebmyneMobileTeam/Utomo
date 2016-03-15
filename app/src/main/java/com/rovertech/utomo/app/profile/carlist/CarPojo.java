package com.rovertech.utomo.app.profile.carlist;

import java.io.Serializable;

/**
 * Created by sagartahelyani on 14-03-2016.
 */
public class CarPojo implements Serializable {

    public String carName;

    public int carSpeed;

    public String carImage;

    public CarPojo() {
        this.carName = "";
        this.carSpeed = 0;
        this.carImage = "";
    }
}
