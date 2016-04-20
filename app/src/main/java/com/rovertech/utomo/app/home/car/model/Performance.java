package com.rovertech.utomo.app.home.car.model;

import java.io.Serializable;

/**
 * Created by sagartahelyani on 12-04-2016.
 */
public class Performance implements Serializable {

    public int CarPerformanceMatrixID;

    public String CriteriaName;

    public String PerformancePercentage;

    public int group = 0;


    public int getGroup() {
        int temp = 0;
        if (this.CriteriaName.equalsIgnoreCase("PUC") || this.CriteriaName.equalsIgnoreCase("Permits") || this.CriteriaName.equalsIgnoreCase("Insurance")) {
            temp = 1;
        }
        return temp;
    }

}
