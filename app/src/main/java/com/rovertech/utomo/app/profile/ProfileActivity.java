package com.rovertech.utomo.app.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.profile.personal.PersonalProfileFragment;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtCustomTitle;
    private View parentView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CustomAdapter adapter;
    private String tabTitles[] = new String[]{"Personal", "My Car"};
    private int tabIcons[] = new int[]{R.drawable.ic_account, R.drawable.ic_car};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_revised);

        init();
    }

    private void init() {
        initToolbar();

        parentView = findViewById(android.R.id.content);

        viewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        setupViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {

        adapter = new CustomAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Functions.hideKeyPad(ProfileActivity.this,parentView);
            }

            @Override
            public void onPageSelected(int position) {
                Functions.hideKeyPad(ProfileActivity.this,parentView);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null)
                tab.setCustomView(adapter.getTabView(i));
        }

    }

    private void initToolbar() {

        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);

        txtCustomTitle.setText("My Profile");
        txtCustomTitle.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            int index = viewPager.getCurrentItem();
            CustomAdapter adapter = (CustomAdapter) viewPager.getAdapter();
            Fragment fragment = adapter.getFragment(index);

            if (viewPager.getCurrentItem() == 0 || fragment != null) {
                ((PersonalProfileFragment) fragment).setSrc(data, this, requestCode);
            }
        }
    }
}
