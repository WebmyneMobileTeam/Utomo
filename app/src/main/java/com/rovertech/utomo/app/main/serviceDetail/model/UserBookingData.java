package com.rovertech.utomo.app.main.serviceDetail.model;

import com.rovertech.utomo.app.invoice.model.PaymentJobCardDetailsModel;

import java.util.List;

/**
 * Created by sagartahelyani on 15-04-2016.
 */
public class UserBookingData {

    public String BookingCode;

    public int BookingID;

    public int BookingStatusID;

    public String ContactNo;

    public String PreferredDateTime;

    public String DeliveredDate;

    public String Description;

    public boolean IsCarDelivered;

    public String RescheduledDateTime;

    public double Lattitude;

    public double Longitude;

    public int NotificationID;

    public String Rating;

    public String ReviewCount;

    public String SCImageName;

    public int ServiceCentreID;

    public String ServiceCentreName;

    public String Status;

    public String VehicleNo;

    public List<PaymentJobCardDetailsModel> lstJobCardDeatils;
}
