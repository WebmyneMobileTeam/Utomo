package com.rovertech.utomo.app.main.notification;

import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.widget.familiarrecyclerview.FamiliarRecyclerView;

public class NotificationActivity extends AppCompatActivity implements NotificationView {

    private Toolbar toolbar;
    private TextView txtCustomTitle;
    private View parentView;

    private NotificationPresenter mNotificationPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mNotificationPresenter = new NotificationPresenterImpl(this);
        mNotificationPresenter.init();
    }


    @Override
    public void init() {
        parentView = findViewById(android.R.id.content);
        mNotificationPresenter.setUpRecyclerView();

    }

    @Override
    public void initToolBar() {
        initToolbar();
    }

    @Override
    public void setUpRecyclerView(NotificationAdapter notificationAdapter) {

        FamiliarRecyclerView notificationsFamiliarRecyclerView = (FamiliarRecyclerView) findViewById(R.id.notificationsRecyclerView);
        notificationsFamiliarRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        notificationsFamiliarRecyclerView.setLayoutManager(linearLayoutManager);
        notificationsFamiliarRecyclerView.setAdapter(notificationAdapter);
        notificationsFamiliarRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(8));

    }

    private void initToolbar() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);

        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        txtCustomTitle.setText("Notifications");
        txtCustomTitle.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mNotificationPresenter.destroy();
    }

    private class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int mVerticalSpaceHeight;

        public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
            this.mVerticalSpaceHeight = mVerticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            outRect.bottom = mVerticalSpaceHeight;
        }
    }
}
