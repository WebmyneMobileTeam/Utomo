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
        this.DeviceID = "";
        this.EmailID = "";
        this.FName = "";
        this.GCMToken = "";
        this.Gender = "";
        this.LName = "";
        this.Lattitude = 0.0;
        this.LoginBy = "";
        this.Longitude = 0.0;
        this.MobileNo = "";
        this.ProfileImg = "";
        this.SocialID = "";
    }
}
