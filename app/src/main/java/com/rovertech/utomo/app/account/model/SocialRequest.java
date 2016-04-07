package com.rovertech.utomo.app.account.model;

import java.io.Serializable;

/**
 * Created by sagartahelyani on 22-03-2016.
 */
public class SocialRequest implements Serializable {

    public String DOB;

    public String DeviceID;

    public String EmailID;

    public String FName;

    public String GCMToken;

    public String Gender;

    public String LName;

    public double Lattitude;

    public String LoginBy;

    public double Longitude;

    public String MobileNo;

    public String ProfileImg;

    public String SocialID;

    public SocialRequest() {
        this.DOB = "";
        DeviceID = "";
        this.EmailID = "";
        this.FName = "";
        this.GCMToken = "";
        Gender = "";
        this.LName = "";
        Lattitude = 0.0;
        LoginBy = "";
        Longitude = 0.0;
        MobileNo = "";
        ProfileImg = "";
        SocialID = "";
    }
}
