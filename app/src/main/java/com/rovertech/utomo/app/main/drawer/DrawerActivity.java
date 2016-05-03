package com.rovertech.utomo.app.main.drawer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
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
import com.rovertech.utomo.app.offers.AdminOfferRequestAPI;
import com.rovertech.utomo.app.offers.model.AdminOfferResp;
import com.rovertech.utomo.app.profile.ProfileActivity;
import com.rovertech.utomo.app.settings.SettingsFragment;
import com.rovertech.utomo.app.wallet.WalletFragment;
import com.rovertech.utomo.app.widget.LocationFinder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrawerActivity extends AppCompatActivity implements DrawerView {

    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Drawer drawer;
    private TextView txtCustomTitle;
    private DrawerPresenter presenter;
    private View parentView;
    private BadgeHelper badgeHelper;
    private MenuItem offerItem;
    private String fragmentValue;
    private Menu mainMenu;
    private static int OfferSize = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_acrtivity);

        PrefUtils.setCurrentPosition(this, 0);

        fragmentValue = getIntent().getStringExtra(AppConstant.FRAGMENT_VALUE);
        presenter = new DrawerPresenterImpl(this);

        init();

        UserProfileOutput profile = PrefUtils.getUserFullProfileDetails(this);
        Log.e("profile", Functions.jsonString(profile));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);

        mainMenu = menu;
        offerItem = mainMenu.findItem(R.id.action_offers);

        if (OfferSize >= 1) {
            showOfferIcon();
        }

        badgeHelper = new BadgeHelper(this, menu.findItem(R.id.action_notification), ActionItemBadge.BadgeStyles.GREY);
        presenter.setNotificationBadge(badgeHelper, 6);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ImageView iv = (ImageView) inflater.inflate(R.layout.iv_offer, null);
        iv.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Log.d("m click?","action_offers click");
                presenter.openOffersPage(DrawerActivity.this);
                return false;
            }
        });
        menu.findItem(R.id.action_offers).setActionView(iv);

        return super.onCreateOptionsMenu(menu);
    }

    private void init() {
        parentView = findViewById(android.R.id.content);
        initToolbar();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_notification) {
            //Log.d("m click?","action_notification click");
            presenter.openNotification(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (drawer != null && drawer.isDrawerOpen()) {
            drawer.closeDrawer();
        }

        if (!Functions.isConnected(this)) {
            Functions.showErrorAlert(this, AppConstant.NO_INTERNET_CONNECTION, true);

        } else {
            initDrawer();

            callOfferApi();

            if (fragmentValue.equals(AppConstant.HOME_FRAGMENT)) {
                presenter.openDashboard();

            } else if (fragmentValue.equals(AppConstant.MY_BOOKING_FRAGMENT)) {
                presenter.openMyBookings();
            }
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

    private void initDrawer() {

        ProfileDrawerItem profile = new ProfileDrawerItem().
                withName(PrefUtils.getUserFullProfileDetails(DrawerActivity.this).Name);

        if (PrefUtils.getUserFullProfileDetails(this).ProfileImg != null || PrefUtils.getUserFullProfileDetails(this).ProfileImg.length() > 0) {
            profile.withIcon(PrefUtils.getUserFullProfileDetails(this).ProfileImg);
        } else {
            profile.withIcon(ContextCompat.getDrawable(this, R.drawable.ic_person));
        }

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(DrawerActivity.this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(profile)
                .withAlternativeProfileHeaderSwitching(false)
                .withSelectionListEnabled(false)
                .withOnAccountHeaderSelectionViewClickListener(new AccountHeader.OnAccountHeaderSelectionViewClickListener() {
                    @Override
                    public boolean onClick(View view, IProfile profile) {
                        Functions.fireIntent(DrawerActivity.this, ProfileActivity.class);
                        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                        return true;
                    }
                })
                .withOnAccountHeaderProfileImageListener(new AccountHeader.OnAccountHeaderProfileImageListener() {
                    @Override
                    public boolean onProfileImageClick(View view, IProfile profile, boolean current) {
                        Functions.fireIntent(DrawerActivity.this, ProfileActivity.class);
                        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                        return true;
                    }

                    @Override
                    public boolean onProfileImageLongClick(View view, IProfile profile, boolean current) {
                        return false;
                    }
                })
                .build();

        PrimaryDrawerItem homeItem = new PrimaryDrawerItem().withName("Dashboard").withIcon(R.drawable.ic_dashboard).withIdentifier(0);
        PrimaryDrawerItem walletItem = new PrimaryDrawerItem().withName("Wallet").withIcon(R.drawable.ic_wallet).withIdentifier(1);
        PrimaryDrawerItem settingsItem = new PrimaryDrawerItem().withName("Settings").withIcon(R.drawable.ic_settings).withIdentifier(2);
        PrimaryDrawerItem bookingItem = new PrimaryDrawerItem().withName("My Bookings").withIcon(R.drawable.ic_booking).withIdentifier(3);
        PrimaryDrawerItem inviteItem = new PrimaryDrawerItem().withName("Invite & Earn").withIcon(R.drawable.ic_money).withIdentifier(4);
        PrimaryDrawerItem aboutItem = new PrimaryDrawerItem().withName("About Us").withIcon(R.drawable.ic_about).withIdentifier(5);
        PrimaryDrawerItem rateItem = new PrimaryDrawerItem().withName("Rate Us").withIcon(R.drawable.ic_rate_us).withIdentifier(6);
        PrimaryDrawerItem shareItem = new PrimaryDrawerItem().withName("Share App").withIcon(R.drawable.ic_share).withIdentifier(7);
        PrimaryDrawerItem logoutItem = new PrimaryDrawerItem().withName("Log Out").withIcon(R.drawable.ic_logout).withIdentifier(8);

        drawer = new DrawerBuilder()
                .withAccountHeader(accountHeader)
                .withActivity(this)
                .withToolbar(toolbar)
                .withShowDrawerOnFirstLaunch(false)
                .withTranslucentStatusBar(false)
                .addDrawerItems(homeItem, walletItem, settingsItem, bookingItem, inviteItem, aboutItem, rateItem, shareItem, logoutItem)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        long identifier = drawerItem.getIdentifier();

                        if (identifier == 0) {
                            presenter.openDashboard();
                            PrefUtils.setCurrentPosition(DrawerActivity.this, 0);

                        } else if (identifier == 1) {
                            presenter.openWallet();

                        } else if (identifier == 2) {
                            presenter.openSettings();

                        } else if (identifier == 3) {
                            presenter.openMyBookings();

                        } else if (identifier == 4) {
                            presenter.openInvite();

                        } else if (identifier == 5) {
                            presenter.openAboutUs();

                        } else if (identifier == 6) {
                            presenter.rateUs(DrawerActivity.this);

                        } else if (identifier == 7) {
                            presenter.share(DrawerActivity.this);

                        } else if (identifier == 8) {
                            presenter.logOut(DrawerActivity.this);
                        }
                        return false;
                    }
                })
                .build();
    }

    @Override
    public void actionHome() {
        initFragment(DashboardFragment.newInstance(), "Dashboard");
    }

    @Override
    public void actionWallet() {
        initFragment(WalletFragment.newInstance(), "Wallet");
    }

    @Override
    public void actionSettings() {
        initFragment(SettingsFragment.newInstance(), "Settings");
    }

    @Override
    public void actionBookings() {
        initFragment(MyBookingFragment.newInstance(), "My Bookings");
    }

    private void initFragment(Fragment fragment, String title) {
        setHeaderTitle(title);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void actionInvite() {
        initFragment(InviteFragment.newInstance(), "Invite & Earn");
    }

    @Override
    public void actionAbout() {
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
            Intent centreIntent = new Intent(DrawerActivity.this, ServiceCenterListActivity.class);
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
                Intent centreIntent = new Intent(DrawerActivity.this, ServiceCenterListActivity.class);
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
