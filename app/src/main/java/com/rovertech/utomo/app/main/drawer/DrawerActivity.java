package com.rovertech.utomo.app.main.drawer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import com.rovertech.utomo.app.bookings.MyBookingFragment;
import com.rovertech.utomo.app.helper.Functions;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_acrtivity);

        presenter = new DrawerPresenterImpl(this);

        init();
    }

    private void init() {
        parentView = findViewById(android.R.id.content);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        initToolbar();

        initDrawer();

        presenter.openDashboard();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.openCenterListing();
            }
        });
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
                .addProfiles(new ProfileDrawerItem().withName("Dhruvil Patel").withIcon(R.drawable.slider2))
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
        PrimaryDrawerItem logoutItem = new PrimaryDrawerItem().withName("Log Out").withIcon(R.drawable.ic_logout).withIdentifier(7);

        drawer = new DrawerBuilder()
                .withAccountHeader(accountHeader)
                .withActivity(this)
                .withToolbar(toolbar)
                .withShowDrawerOnFirstLaunch(false)
                .withTranslucentStatusBar(false)
                .addDrawerItems(homeItem, walletItem, settingsItem, bookingItem, inviteItem, aboutItem, rateItem, logoutItem)
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
                            presenter.logOut();
                        }
                        return false;
                    }
                })
                .build();
    }

    @Override
    public void actionHome() {
        setHeaderTitle("Dashboard");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, new DashboardFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void actionWallet() {
        setHeaderTitle("Wallet");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, new WalletFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void actionSettings() {
        setHeaderTitle("Settings");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, new SettingsFragment());
        fragmentTransaction.commit();
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
        setHeaderTitle("Invite & Earn");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, new InviteFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void actionAbout() {
        setHeaderTitle("About Us");
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, new AboutFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void actionLogout() {
        Functions.showSnack(parentView, "Logout");
    }

    @Override
    public void getLocation(LocationFinder finder) {
        Toast.makeText(DrawerActivity.this, "Location " + finder.getLatitude() + " : " + finder.getLongitude(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void navigateCenterListActivity() {
        Functions.fireIntent(DrawerActivity.this, ServiceCenterListActivity.class);
    }

}
