package com.rovertech.utomo.app.profile;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

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
        setContentView(R.layout.activity_my_profile);

        init();
    }

    private void init() {
        initToolbar();

        parentView = findViewById(android.R.id.content);

        viewPager = (ViewPager) parentView.findViewById(R.id.pager);
        tabLayout = (TabLayout) parentView.findViewById(R.id.tab_layout);
        setupViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {

        adapter = new CustomAdapter(getSupportFragmentManager(), this, tabTitles);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
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
        txtCustomTitle.setTypeface(Functions.getBoldFont(this));

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
}
