package com.rovertech.utomo.app.account.model;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 23-03-2016.
 */
public class BasicLoginSignUp {

    public ArrayList<UserProfileOutput> Data;
    public String ResponseMessage;
    public int ResponseCode;

    @Override
    public String toString() {
        return "BasicLoginSignUp{" +
                "Data=" + Data +
                ", ResponseCode='" + ResponseCode + '\'' +
                ", ResponseMessage='" + ResponseMessage + '\'' +
                '}';
    }
}
