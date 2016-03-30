package com.rovertech.utomo.app.main.drawer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.rovertech.utomo.app.profile.ProfileActivity;
import com.rovertech.utomo.app.settings.SettingsFragment;
import com.rovertech.utomo.app.wallet.WalletFragment;
import com.rovertech.utomo.app.widget.LocationFinder;

public class DrawerActivity extends AppCompatActivity implements DrawerView {

    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Drawer drawer;
    private TextView txtCustomTitle;
    private DrawerPresenter presenter;
    private View parentView;
    private FloatingActionButton fab;
    private BadgeHelper badgeHelper;
    private MenuItem notificationMenuItem;
    private String fragmentValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_acrtivity);

        fragmentValue = getIntent().getStringExtra(AppConstant.FRAGMENT_VALUE);
        presenter = new DrawerPresenterImpl(this);

        init();

        UserProfileOutput profile = PrefUtils.getUserFullProfileDetails(this);
        Log.e("profile", Functions.jsonString(profile));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drawer_menu, menu);
        badgeHelper = new BadgeHelper(this, menu.findItem(R.id.action_notification), ActionItemBadge.BadgeStyles.YELLOW);
        presenter.setNotificationBadge(badgeHelper);

        return super.onCreateOptionsMenu(menu);
    }

    private void init() {
        parentView = findViewById(android.R.id.content);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        initToolbar();

        initDrawer();

        if (fragmentValue.equals(AppConstant.HOME_FRAGMENT)) {
            presenter.openDashboard();

        } else if (fragmentValue.equals(AppConstant.MY_BOOKING_FRAGMENT)) {
            presenter.openMyBookings();
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.openCenterListing();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_notification) {
            presenter.openNotification(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //presenter.checkGPS(DrawerActivity.this);
    }

    public void setHeaderTitle(String title) {
        txtCustomTitle.setText(title);
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        txtCustomTitle.setTypeface(Functions.getBoldFont(this));
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        setHeaderTitle("Dashboard");
    }

    private void initDrawer() {

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(DrawerActivity.this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(new ProfileDrawerItem().withName(PrefUtils.getUserFullProfileDetails(DrawerActivity.this).Name).withIcon(R.drawable.ic_account))
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

    public void hideFab(boolean isHide) {
        if (isHide)
            fab.setVisibility(View.GONE);
        else
            fab.setVisibility(View.VISIBLE);
    }
}
