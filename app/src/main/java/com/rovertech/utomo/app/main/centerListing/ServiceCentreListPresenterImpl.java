package com.rovertech.utomo.app.main.centerListing;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.account.adapter.CityAdapter;
import com.rovertech.utomo.app.account.model.CityOutput;
import com.rovertech.utomo.app.account.model.CityRequest;
import com.rovertech.utomo.app.account.service.FetchCityService;
import com.rovertech.utomo.app.helper.AdvancedSpannableString;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.main.centerListing.model.CentreListRequest;
import com.rovertech.utomo.app.main.centerListing.model.CentreListResponse;
import com.rovertech.utomo.app.main.centerListing.service.FetchServiceCentreListService;
import com.rovertech.utomo.app.main.centreDetail.CentreDetailsActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sagartahelyani on 15-03-2016.
 */
public class ServiceCentreListPresenterImpl implements ServiceCentreLisPresenter {

    ServiceCentreView centreView;
    List<ServiceCenterPojo> centerArrayList = new ArrayList<>();
    int lastCentreId;
    private Double userLatitude = 0.0, userLongitude = 0.0;
    private int cityId = 0;

    public ServiceCentreListPresenterImpl(ServiceCentreView centreView) {
        this.centreView = centreView;
    }

    @Override
    public void setLocation(Double userLatitude, Double userLongitude, int cityID) {
        this.userLatitude = userLatitude;
        this.userLongitude = userLongitude;
        this.cityId = cityID;
    }

    @Override
    public void fetchCentreList(int centreId, Context context, int type, boolean isBodyShop, boolean isPickup) {

        // Call WS
        CentreListRequest request = new CentreListRequest();

        if (type == AppConstant.BY_CITY) {
            request.CityId = cityId;

        } else {
            request.CityId = 0;
            request.Lattitude = userLatitude;
            request.Longitude = userLongitude;
        }

        request.DealerShipName = PrefUtils.getCurrentCarSelected(context).Make;
        request.LastServiceCentreID = centreId;
        request.IsBodyWash = isBodyShop;
        request.IsPickupDrop = isPickup;

        Log.e("CentreListRequest", Functions.jsonString(request));

        FetchServiceCentreListService service = UtomoApplication.retrofit.create(FetchServiceCentreListService.class);
        Call<CentreListResponse> call = service.doFetchCentreList(request);

        call.enqueue(new Callback<CentreListResponse>() {
            @Override
            public void onResponse(Call<CentreListResponse> call, Response<CentreListResponse> response) {

                if (response.body() == null) {
                    Log.e("Error", "Error");

                } else {
                    Log.e("response", Functions.jsonString(response.body()));
                    CentreListResponse centreListResponse = response.body();

                    centerArrayList = new ArrayList<ServiceCenterPojo>();

                    if (centreListResponse.FetchServiceCentreList.Data.size() > 0) {
                        centerArrayList.addAll(centreListResponse.FetchServiceCentreList.Data);

                        ServiceCenterPojo center = centreListResponse.FetchServiceCentreList.Data.get(centreListResponse.FetchServiceCentreList.Data.size() - 1);
                        if (center != null)
                            lastCentreId = center.ServiceCentreID;

                        onSubmit(centerArrayList, lastCentreId);

                    } else {
                        onSubmit(new ArrayList<ServiceCenterPojo>(), lastCentreId);
                    }
                }
            }

            @Override
            public void onFailure(Call<CentreListResponse> call, Throwable t) {
                onSubmit(new ArrayList<ServiceCenterPojo>(), 0);
            }
        });

    }

    @Override
    public void fetchCity(final Context context, String string) {
        CityRequest request = new CityRequest();
        request.CityName = string;

        Log.e("city_req", Functions.jsonString(request));

        FetchCityService service = UtomoApplication.retrofit.create(FetchCityService.class);
        Call<CityOutput> call = service.doFetchCity(request);
        call.enqueue(new Callback<CityOutput>() {
            @Override
            public void onResponse(Call<CityOutput> call, Response<CityOutput> response) {
                if (response.body() == null) {
                    Functions.showToast(context, "Error");
                } else {
                    Log.e("json_res", Functions.jsonString(response.body()));
                    CityAdapter adapter = new CityAdapter(context, R.layout.layout_adapter_item, response.body().FetchCity.Data);
                    centreView.setCityAdapter(adapter, response.body().FetchCity.Data);
                }
            }

            @Override
            public void onFailure(Call<CityOutput> call, Throwable t) {

            }
        });
    }


    @Override
    public void showListView() {

        centreView.showListLayout();
        centreView.hideMapContainer();
    }

    @Override
    public void showMapView(final GoogleMap googleMap, List<ServiceCenterPojo> centerList, final Context context) {

        try {


            centreView.hideListLayout();
            centreView.showMapContainer();

            if (googleMap == null || centerList == null) {
                return;
            }

            final HashMap<String, ServiceCenterPojo> integerServiceCenterPojoHashMap = new HashMap<>();

            int lastItem = centerList.size();
            for (ServiceCenterPojo serviceCenterPojo : centerList) {


                LatLng latLng = new LatLng(serviceCenterPojo.Lattitude, serviceCenterPojo.Longitude);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng).title(serviceCenterPojo.ServiceCentreName);

                Marker marker = googleMap.addMarker(markerOptions);
                integerServiceCenterPojoHashMap.put(marker.getId(), serviceCenterPojo);
                if (--lastItem == 0) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
                }
            }

            googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    View v = LayoutInflater.from(context).inflate(R.layout.marker_window_layout, null);
                    try {


                        final ServiceCenterPojo serviceCenterPojo = integerServiceCenterPojoHashMap.get(marker.getId());
                        TextView markerTextView = (TextView) v.findViewById(R.id.markerTextView);
                        //TextView txtViewDetails = (TextView) v.findViewById(R.id.txtViewDetails);
                        TextView txtRating = (TextView) v.findViewById(R.id.txtRating);
                        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
                        markerTextView.setText(serviceCenterPojo.ServiceCentreName);

                        if (serviceCenterPojo != null) {
                            Glide.clear(imageView);
                            Glide.with(context).load(serviceCenterPojo.ServiceCentreImage).thumbnail(0.01f).into(imageView);


                        }
                        txtRating.setText(String.format("Rating : %.1f", serviceCenterPojo.Rating));


                    } catch (Exception e) {

                    }
                    return v;
                }
            });


            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    final ServiceCenterPojo serviceCenterPojo = integerServiceCenterPojoHashMap.get(marker.getId());
                    Intent intent = new Intent(context, CentreDetailsActivity.class);
                    intent.putExtra("centreId", serviceCenterPojo.ServiceCentreID);
                    context.startActivity(intent);
                }
            });
        } catch (Exception e) {

        }


    }

    @Override
    public void destory() {
        if (centreView != null) {
            centreView = null;

        }
    }

    public void onSubmit(List<ServiceCenterPojo> list, int lastCentreId) {
        centreView.setListing(list, lastCentreId);
    }


    public static AdvancedSpannableString getSpannableString(String text, final Context context, TextView tv) {

        String fullName = String.format("%s", text);
        AdvancedSpannableString advFullName = new AdvancedSpannableString(fullName);
        advFullName.setClickableSpanTo(fullName);
        advFullName.setBold(fullName);
        advFullName.setColor(Color.BLACK, fullName);
        advFullName.setSpanClickListener(new AdvancedSpannableString.OnClickableSpanListner() {
            @Override
            public void onSpanClick() {

                Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();

            }
        });
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        return advFullName;
    }

}
