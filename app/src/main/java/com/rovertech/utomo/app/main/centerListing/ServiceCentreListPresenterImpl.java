package com.rovertech.utomo.app.main.centerListing;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gun0912.tedpermission.PermissionListener;
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
import com.rovertech.utomo.app.helper.RetrofitErrorHelper;
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
    private boolean isDrag = false;
    private GoogleMap googleMap;

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
    public void fetchCentreList(int centreId, final Context context, int type, boolean isBodyShop, boolean isPickup) {

        Log.e("tag", "userLatitude:-" + userLatitude + "  userLongitude:-" + userLongitude);

        centreView.showProgress();
        // Call WS
        CentreListRequest request = new CentreListRequest();

        if (type == AppConstant.BY_CITY) {
            request.CityId = cityId;

        } else {
            request.CityId = 0;
            request.Lattitude = userLatitude;
            request.Longitude = userLongitude;
        }

        if (PrefUtils.isUserLoggedIn(context))
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
                    Functions.showToast(context, "Error occurred");

                } else {
                    Log.e("response", Functions.jsonString(response.body()));
                    CentreListResponse centreListResponse = response.body();

                    centerArrayList = new ArrayList<ServiceCenterPojo>();

                    if (centreListResponse.FetchServiceCentreList.ResponseCode == 0) {
                        onSubmit(new ArrayList<ServiceCenterPojo>(), lastCentreId);

                        if (googleMap != null) {
                            clearAndSetCurrentMarker(googleMap, new LatLng(userLatitude, userLongitude), new ArrayList<ServiceCenterPojo>());
                        }

                    } else if (centreListResponse.FetchServiceCentreList.Data.size() > 0) {

                        centerArrayList.addAll(centreListResponse.FetchServiceCentreList.Data);

                        ServiceCenterPojo center = centreListResponse.FetchServiceCentreList.Data.get(centreListResponse.FetchServiceCentreList.Data.size() - 1);
                        if (center != null)
                            lastCentreId = center.ServiceCentreID;

                        onSubmit(centerArrayList, lastCentreId);

                    } else {
                        onSubmit(new ArrayList<ServiceCenterPojo>(), lastCentreId);

                        if (googleMap != null) {
                            clearAndSetCurrentMarker(googleMap, new LatLng(userLatitude, userLongitude), new ArrayList<ServiceCenterPojo>());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CentreListResponse> call, Throwable t) {
                RetrofitErrorHelper.showErrorMsg(t, context);
                onSubmit(new ArrayList<ServiceCenterPojo>(), 0);
            }
        });

    }

    public void onSubmit(List<ServiceCenterPojo> list, int lastCentreId) {
        centreView.hideProgress();
        centreView.setListing(list, lastCentreId);
    }


    @Override
    public void fetchCity(final Context context, String string) {
        CityRequest request = new CityRequest();
        request.CityName = string;

        FetchCityService service = UtomoApplication.retrofit.create(FetchCityService.class);
        Call<CityOutput> call = service.doFetchCity(request);
        call.enqueue(new Callback<CityOutput>() {
            @Override
            public void onResponse(Call<CityOutput> call, Response<CityOutput> response) {
                if (response.body() == null) {
                    Functions.showToast(context, "Error");
                } else {
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
    public void showMapView(final GoogleMap googleMap, final List<ServiceCenterPojo> centerList, final Context context) {

        try {

            centreView.hideListLayout();
            centreView.showMapContainer();

            if (googleMap == null || centerList == null) {
                return;
            }


//            googleMap.clear();

            /* 02-12-2016 : get current location,
            show marker, drag & drop marker, call WS */
            Functions.setPermission(context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, new PermissionListener() {
                @Override
                public void onPermissionGranted() {

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }

                    googleMap.setMyLocationEnabled(true);

                    LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                    Criteria criteria = new Criteria();

                    Location currentLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                    LatLng currentLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    Log.e("currentLatLng", String.valueOf(currentLatLng));

                    if (isDrag) {
                        currentLatLng = new LatLng(userLatitude, userLongitude);
                        setCurrentMarkerAndServiceCentreMarkers(context, currentLatLng, googleMap, centerList);
                    } else {
                        setCurrentMarkerAndServiceCentreMarkers(context, currentLatLng, googleMap, centerList);
                    }

                }

                @Override
                public void onPermissionDenied(ArrayList<String> arrayList) {

                }
            });

            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    if (marker.getTitle().equals("My Location")) {
                        marker.hideInfoWindow();
                    } else {
                        marker.showInfoWindow();
                    }

                    return true;
                }
            });

            googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker arg0) {
                    // TODO Auto-generated method stub
                    Log.e("tag", "onMarkerDragStart..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);
                }

                @Override
                public void onMarkerDragEnd(Marker arg0) {
                    // TODO Auto-generated method stub
                    Log.e("tag", "onMarkerDragEnd..." + arg0.getPosition().latitude + "..." + arg0.getPosition().longitude);

                    isDrag = true;

                    centreView.setDrag(isDrag);

                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(arg0.getPosition()));

                    setLocation(arg0.getPosition().latitude, arg0.getPosition().longitude, 0);

                    centerArrayList = new ArrayList<ServiceCenterPojo>();
                    centerArrayList.clear();

                    fetchCentreList(0, context, AppConstant.BY_LAT_LNG, true, true);


                }

                @Override
                public void onMarkerDrag(Marker arg0) {
                    // TODO Auto-generated method stub
                }
            });


        } catch (Exception e) {

        }

        this.googleMap = googleMap;
    }

    private void setCurrentMarkerAndServiceCentreMarkers(final Context context, LatLng newLatLng, GoogleMap googleMap, List<ServiceCenterPojo> centerList) {

        Log.e("tag", "centerList.size():-" + centerList.size());
        for (int i = 0; i < centerList.size(); i++) {
            Log.e("centerLatLng", centerList.get(i).Lattitude + " - " + centerList.get(i).Longitude);
        }

        clearAndSetCurrentMarker(googleMap, newLatLng, centerList);

        final HashMap<String, ServiceCenterPojo> integerServiceCenterPojoHashMap = new HashMap<>();

        int lastItem = centerList.size();
        for (ServiceCenterPojo serviceCenterPojo : centerList) {


            LatLng latLng = new LatLng(serviceCenterPojo.Lattitude, serviceCenterPojo.Longitude);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng).title(serviceCenterPojo.ServiceCentreName);

            Marker marker = googleMap.addMarker(markerOptions);
            integerServiceCenterPojoHashMap.put(marker.getId(), serviceCenterPojo);
//            if (--lastItem == 0) {
//                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
//            }
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
                if (serviceCenterPojo != null) {
                    Intent intent = new Intent(context, CentreDetailsActivity.class);
                    intent.putExtra("centreId", serviceCenterPojo.ServiceCentreID);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Error, Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void clearAndSetCurrentMarker(GoogleMap googleMap, LatLng newLatLng, List<ServiceCenterPojo> serviceCenterPojos) {

        googleMap.clear();

        googleMap.addMarker(new MarkerOptions().position(newLatLng).title("My Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_location2)).draggable(true)).hideInfoWindow();

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 7));

    }

    @Override
    public void destory() {
        if (centreView != null) {
            centreView = null;

        }
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
