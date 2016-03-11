package com.rovertech.utomo.app.temp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nshmura.recyclertablayout.RecyclerTabLayout;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.profile.CustomAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DemoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtCustomTitle;
    private View parentView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CustomAdapter adapter;
    private RecyclerTabLayout recyclerTabLayout;
    private DemoImagePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        init();
    }

    private void init() {
        initToolbar();

        parentView = findViewById(android.R.id.content);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.pager);
        recyclerTabLayout = (RecyclerTabLayout) findViewById(R.id.recyclerTabLayout);

        tabLayout.addTab(tabLayout.newTab().setText("Personal"));
        tabLayout.addTab(tabLayout.newTab().setText("Car"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //adapter = new CustomAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        pagerAdapter = new DemoImagePagerAdapter();
        pagerAdapter.addAll(loadImageResourceList());

        viewPager.setAdapter(pagerAdapter);
        recyclerTabLayout.setUpWithAdapter(new DemoCustomView02Adapter(viewPager));
        recyclerTabLayout.setPositionThreshold(0.5f);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private List<Integer> loadImageResourceList() {
        return new ArrayList<>(Arrays.asList(R.drawable.ic_wallet, R.drawable.ic_settings));
    }

    private void initToolbar() {

        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);

        txtCustomTitle.setText("My Profile");

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

    private class DemoImagePagerAdapter extends PagerAdapter {

        private List<Integer> mItems = new ArrayList<>();

        public DemoImagePagerAdapter() {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext())
                    .inflate(R.layout.layout_page, container, false);

            TextView textView = (TextView) view.findViewById(R.id.title);
            textView.setText("Page: " + position);
            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public int getImageResourceId(int position) {
            return mItems.get(position);
        }

        public void addAll(List<Integer> items) {
            mItems = new ArrayList<>(items);
        }
    }

    private class DemoCustomView02Adapter extends RecyclerTabLayout.Adapter<DemoCustomView02Adapter.ViewHolder> {

        private DemoImagePagerAdapter mAdapater;

        public DemoCustomView02Adapter(ViewPager viewPager) {
            super(viewPager);
            mAdapater = (DemoImagePagerAdapter) mViewPager.getAdapter();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_custom_view02_tab, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DemoCustomView02Adapter.ViewHolder holder, int position) {
            Drawable drawable = loadIconWithTint(holder.imageView.getContext(),
                    mAdapater.getImageResourceId(position));

            holder.imageView.setImageDrawable(drawable);
            holder.imageView.setSelected(position == getCurrentIndicatorPosition());
        }

        private Drawable loadIconWithTint(Context context, @IdRes int resourceId) {
            Drawable icon = ContextCompat.getDrawable(context, resourceId);
            ColorStateList colorStateList = ContextCompat
                    .getColorStateList(context, R.color.brown);
            icon = DrawableCompat.wrap(icon);
            DrawableCompat.setTintList(icon, colorStateList);
            return icon;
        }

        @Override
        public int getItemCount() {
            return mAdapater.getCount();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ImageView imageView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.image);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getViewPager().setCurrentItem(getAdapterPosition());
                    }
                });
            }
        }

    }

}
