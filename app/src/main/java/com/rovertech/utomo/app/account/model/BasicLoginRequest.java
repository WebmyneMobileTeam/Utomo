package com.rovertech.utomo.app.account.model;

import java.io.Serializable;

/**
 * Created by sagartahelyani on 23-03-2016.
 */
public class BasicLoginRequest implements Serializable {

    public String DeviceId, EmailID, GCMId, Mobile, Name, Password, ReferralCode;

    public int CityID;

    public boolean isSignUp;

    @Override
    public String toString() {
        return "BasicLoginRequest{" +
                "DeviceId='" + DeviceId + '\'' +
                ", EmailID='" + EmailID + '\'' +
                ", GCMId='" + GCMId + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Name='" + Name + '\'' +
                ", Password='" + Password + '\'' +
                ", ReferralCode='" + ReferralCode + '\'' +
                ", CityID=" + CityID +
                ", isSignUp=" + isSignUp +
                '}';
    }

    public BasicLoginRequest() {
        this.CityID = 0;
        this.DeviceId = "";
        this.EmailID = "";
        this.GCMId = "";
        this.Mobile = "";
        this.Name = "";
        this.Password = "";
        this.ReferralCode = "";
        this.isSignUp = false;
    }
}
