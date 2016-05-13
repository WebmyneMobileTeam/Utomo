package com.rovertech.utomo.app.main.drawer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.UtomoApplication;
import com.rovertech.utomo.app.about.AboutFragment;
import com.rovertech.utomo.app.account.model.UserProfileOutput;
import com.rovertech.utomo.app.bookings.MyBookingFragment;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.BadgeHelper;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.home.DashboardFragment;
import com.rovertech.utomo.app.invite.InviteFragment;
import com.rovertech.utomo.app.main.centerListing.ServiceCenterListActivity;
import com.rovertech.utomo.app.main.notification.model.NotificationItem;
import com.rovertech.utomo.app.main.notification.model.NotificationResp;
import com.rovertech.utomo.app.main.notification.service.NotificationRequestAPI;
import com.rovertech.utomo.app.offers.AdminOfferRequestAPI;
import com.rovertech.utomo.app.offers.model.AdminOfferResp;
import com.rovertech.utomo.app.profile.ProfileActivity;
import com.rovertech.utomo.app.settings.SettingsFragment;
import com.rovertech.utomo.app.wallet.WalletFragment;
import com.rovertech.utomo.app.widget.LocationFinder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawerActivityRevised extends AppCompatActivity implements DrawerView {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private NavigationView navigationView;

    private Toolbar toolbar;
    private TextView txtCustomTitle, txtName;
    private DrawerPresenter presenter;
    private BadgeHelper badgeHelper;
    private MenuItem offerItem;
    private int OfferSize = 0, notificationSize = 0;

    private UserProfileOutput profile;
    ImageView profilePic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_activity_revised);

        PrefUtils.setCurrentPosition(this, 0);

        profile = PrefUtils.getUserFullProfileDetails(this);
        Log.e("profile", Functions.jsonString(profile));

        String fragmentValue = getIntent().getStringExtra(AppConstant.FRAGMENT_VALUE);
        presenter = new DrawerPresenterImpl(this);

        init();

        presenter.openDashboard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);

        offerItem = menu.findItem(R.id.action_offers);

        if (OfferSize >= 1) {
            showOfferIcon();
        }

        badgeHelper = new BadgeHelper(this, menu.findItem(R.id.action_notification), ActionItemBadge.BadgeStyles.GREY);
        presenter.setNotificationBadge(badgeHelper, notificationSize);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView iv = (ImageView) inflater.inflate(R.layout.iv_offer, null);
        iv.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Log.d("m click?","action_offers click");
                presenter.openOffersPage(DrawerActivityRevised.this);
                return false;
            }
        });
        menu.findItem(R.id.action_offers).setActionView(iv);

        return super.onCreateOptionsMenu(menu);
    }

    private void init() {

        View parentView = findViewById(android.R.id.content);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        initToolbar();

        initDrawer();

    }

    private void initDrawer() {

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        View headerLayout = navigationView.getHeaderView(0);

        txtName = (TextView) headerLayout.findViewById(R.id.txtName);

        txtName.setTypeface(Functions.getRegularFont(this));

        TextView txtLogout = (TextView) headerLayout.findViewById(R.id.txtLogout);
        txtLogout.setTypeface(Functions.getRegularFont(this));
        txtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.logOut(DrawerActivityRevised.this);
            }
        });

        TextView txtProfile = (TextView) headerLayout.findViewById(R.id.txtProfile);
        txtProfile.setTypeface(Functions.getRegularFont(this));
        txtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntent(DrawerActivityRevised.this, ProfileActivity.class);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        });

        profilePic = (ImageView) headerLayout.findViewById(R.id.profilePic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Functions.fireIntent(DrawerActivityRevised.this, ProfileActivity.class);
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                setDrawerClick(menuItem.getItemId());

                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setDrawerClick(int itemId) {
        switch (itemId) {
            case R.id.drawer_dashboard:
                presenter.openDashboard();
                PrefUtils.setCurrentPosition(DrawerActivityRevised.this, 0);
                break;

            case R.id.drawer_wallet:
                presenter.openWallet();
                break;

            case R.id.drawer_settings:
                presenter.openSettings();
                break;

            case R.id.drawer_booking:
                presenter.openMyBookings();
                break;

            case R.id.drawer_invite:
                presenter.openInvite();
                break;

            case R.id.drawer_about:
                presenter.openAboutUs();
                break;

            case R.id.drawer_rate:
                presenter.rateUs(DrawerActivityRevised.this);
                break;

            case R.id.drawer_share:
                presenter.share(DrawerActivityRevised.this);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.action_notification:
                presenter.openNotification(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        profile = PrefUtils.getUserFullProfileDetails(this);
        txtName.setText(String.format("%s", profile.Name));
        Functions.loadRoundImage(profilePic, profile.ProfileImg, this);

        if (!Functions.isConnected(this)) {
            Functions.showErrorAlert(this, AppConstant.NO_INTERNET_CONNECTION, true);

        } else {

            callOfferApi();

            callNotificationApi(PrefUtils.getUserID(this));
        }

        Log.e("isRefresh", PrefUtils.isRefreshDashboard(this) + "");
        if (PrefUtils.isRefreshDashboard(this)) {
            PrefUtils.setRefreshDashboard(this, false);
            presenter.openDashboard();
        }

    }

    private void callOfferApi() {

        // Log.d("Resp","Inside Call");

        AdminOfferRequestAPI api = UtomoApplication.retrofit.create(AdminOfferRequestAPI.class);
        Call<AdminOfferResp> call = api.adminOfferApi();

        call.enqueue(new Callback<AdminOfferResp>() {
            @Override
            public void onResponse(Call<AdminOfferResp> call, Response<AdminOfferResp> response) {
                try {

                    if (response.body().FetchAdminOffer.ResponseCode == 1) {
                        OfferSize = response.body().FetchAdminOffer.Data.size();
                        if (offerItem != null && OfferSize >= 1) {
                            showOfferIcon();
                        }
                    } else {
                        hideOfferIcon();
                    }
                } catch (Exception e) {
                    Log.e("excaption", e.toString());
                }
            }

            @Override
            public void onFailure(Call<AdminOfferResp> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

    public void callNotificationApi(int userId) {

        NotificationRequestAPI api = UtomoApplication.retrofit.create(NotificationRequestAPI.class);
        Call<NotificationResp> call = api.notificationApi(userId);

        call.enqueue(new Callback<NotificationResp>() {
            @Override
            public void onResponse(Call<NotificationResp> call, Response<NotificationResp> response) {
                try {
                    Log.e("onResponse", Functions.jsonString(response.body()));
                    if (response.body().FetchNotification.ResponseCode == 1) {

                        int badgeCount = 0;
                        ArrayList<NotificationItem> notificationItems = response.body().FetchNotification.Data;

                        for (int i = 0; i < notificationItems.size(); i++) {
                            if (!notificationItems.get(i).IsReadMobile) {
                                badgeCount++;
                            }
                        }
                        presenter.setNotificationBadge(badgeHelper, badgeCount);

                    }
                } catch (Exception e) {
                    Log.e("exception", e.toString());
                }
            }

            @Override
            public void onFailure(Call<NotificationResp> call, Throwable t) {
                Log.e("error", t.toString());
            }
        });
    }

    public void setHeaderTitle(String title) {
        txtCustomTitle.setText(title);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        txtCustomTitle.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        setHeaderTitle("Dashboard");
    }

    @Override
    public void actionHome() {
        navigationView.setCheckedItem(R.id.drawer_dashboard);
        initFragment(DashboardFragment.newInstance(), "Dashboard");
    }

    @Override
    public void actionWallet() {
        navigationView.setCheckedItem(R.id.drawer_wallet);
        initFragment(WalletFragment.newInstance(), "Wallet");
    }

    @Override
    public void actionSettings() {
        navigationView.setCheckedItem(R.id.drawer_settings);
        initFragment(SettingsFragment.newInstance(), "Settings");
    }

    @Override
    public void actionBookings() {
        navigationView.setCheckedItem(R.id.drawer_booking);
        initFragment(MyBookingFragment.newInstance(), "My Bookings");
    }

    private void initFragment(Fragment fragment, String title) {
        setHeaderTitle(title);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void actionInvite() {
        navigationView.setCheckedItem(R.id.drawer_invite);
        initFragment(InviteFragment.newInstance(), "Invite & Earn");
    }

    @Override
    public void actionAbout() {
        navigationView.setCheckedItem(R.id.drawer_about);
        initFragment(AboutFragment.newInstance(), "About Us");
    }

    @Override
    public void getLocation(LocationFinder finder) {
        Log.e("location", finder.getLatitude() + " : " + finder.getLongitude());
    }

    @Override
    public void navigateCenterListActivity() {

        LocationFinder finder = new LocationFinder(this);

        if (!finder.canGetLocation()) {
            accurateAlert();

        } else {
            getLocation(finder);
            Intent centreIntent = new Intent(DrawerActivityRevised.this, ServiceCenterListActivity.class);
            centreIntent.putExtra("lat", finder.getLatitude());
            centreIntent.putExtra("lng", finder.getLongitude());
            startActivity(centreIntent);
        }
    }

    @Override
    public void showOfferIcon() {
        offerItem.setVisible(true);
    }

    @Override
    public void hideOfferIcon() {
        offerItem.setVisible(false);
    }

    private void accurateAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Note");
        alert.setMessage("Do you want to getting service centres nearby your location? Turn on your GPS from Settings.");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        alert.setNegativeButton("No, Thanks", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent centreIntent = new Intent(DrawerActivityRevised.this, ServiceCenterListActivity.class);
                centreIntent.putExtra("lat", 0.0);
                centreIntent.putExtra("lng", 0.0);
                startActivity(centreIntent);
            }
        });
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PrefUtils.setCurrentPosition(this, 0);
    }
}
