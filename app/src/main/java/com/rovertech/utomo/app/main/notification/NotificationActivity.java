package com.rovertech.utomo.app.main.notification;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.helper.VerticalSpaceItemDecoration;
import com.rovertech.utomo.app.main.notification.adapter.NotificationAdapter;
import com.rovertech.utomo.app.main.notification.presenter.NotificationPresenter;
import com.rovertech.utomo.app.main.notification.presenter.NotificationPresenterImpl;
import com.rovertech.utomo.app.main.notification.presenter.NotificationView;

public class NotificationActivity extends AppCompatActivity implements NotificationView {

    private Toolbar toolbar;
    private TextView txtCustomTitle;
    private View parentView;
    private ProgressDialog dialog;
    private NotificationPresenter mNotificationPresenter;
    private int userID;
    private RecyclerView notificationsFamiliarRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        userID = PrefUtils.getUserID(this);
        mNotificationPresenter = new NotificationPresenterImpl(this.getApplicationContext(), this);
        mNotificationPresenter.init();
        mNotificationPresenter.setUpRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNotificationPresenter.callNotificationApi(userID, 0);
    }

    @Override
    public void init() {
        parentView = findViewById(android.R.id.content);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait");
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });

        // mNotificationPresenter.setUpRecyclerView();
    }

    @Override
    public void initToolBar() {
        initToolbar();
    }

    @Override
    public void setUpRecyclerView(NotificationAdapter notificationAdapter) {
        notificationsFamiliarRecyclerView = (RecyclerView) findViewById(R.id.notificationsRecyclerView);
        notificationsFamiliarRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        notificationsFamiliarRecyclerView.setLayoutManager(linearLayoutManager);
        notificationsFamiliarRecyclerView.setAdapter(notificationAdapter);
        notificationsFamiliarRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(8));
        notificationsFamiliarRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                mSwipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @Override
    public void onAcceptRejectCallback(String message) {
        Log.e("onAcceptRejectCallback", message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        refreshItems();
    }

    @Override
    public void showProgreessDialog() {
        dialog.show();
    }

    @Override
    public void hideProgreessDialog() {
        dialog.hide();
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

    void refreshItems() {
        // Load items
        // ...
        mNotificationPresenter.callNotificationApi(userID, 1);
        mSwipeRefreshLayout.setRefreshing(false);

    }


}
