package com.rovertech.utomo.app.account.model;

import java.io.Serializable;

/**
 * Created by sagartahelyani on 23-03-2016.
 */
public class UserProfileOutput implements Serializable {

    public int UserID;

    public String Name;

    public String Email;

    public String ProfileImg;

    public String DOB;

    public String Address;

    public double WalletBalance;

    public String MyReferCode;

    public String OTP;

    public int CityID;

    @Override
    public String toString() {
        return "UserProfileOutput{" +
                "UserID=" + UserID +
                ", Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", ProfileImg='" + ProfileImg + '\'' +
                ", DOB='" + DOB + '\'' +
                ", Address='" + Address + '\'' +
                ", WalletBalance=" + WalletBalance +
                ", MyReferCode='" + MyReferCode + '\'' +
                ", OTP='" + OTP + '\'' +
                ", CityID=" + CityID +
                '}';
    }
}
