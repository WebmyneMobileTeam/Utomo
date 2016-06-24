package com.rovertech.utomo.app.main.review;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener, ReviewView {

    private Toolbar toolbar;
    private TextView txtCustomTitle, txtRecommend;
    private View parentView;
    private ReviewPresenter presenter;
    private EditText edtComment;
    private RadioGroup radioGroup;
    private Button btnSubmit;
    private RadioButton radioNo;
    private AppCompatRadioButton radioYes;
    private AppCompatRatingBar ratingBar;

    private int serviceCenterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        serviceCenterId = getIntent().getIntExtra("serviceCenterId", 0);

        presenter = new ReviewPresenterImpl(this, serviceCenterId);

        init();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void init() {

        initToolbar();

        parentView = findViewById(android.R.id.content);
        txtRecommend = (TextView) findViewById(R.id.txtRecommend);
        edtComment = (EditText) findViewById(R.id.edtComment);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        radioYes = (AppCompatRadioButton) findViewById(R.id.radioYes);
        radioNo = (RadioButton) findViewById(R.id.radioNo);
        ratingBar = (AppCompatRatingBar) findViewById(R.id.ratingBar);

        Drawable progress = ratingBar.getProgressDrawable();
        DrawableCompat.setTint(progress, ContextCompat.getColor(this, R.color.white));

        setTypeface();

        clickListener();
    }

    private void setTypeface() {
        txtRecommend.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        edtComment.setTypeface(Functions.getRegularFont(this));
        btnSubmit.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        radioYes.setTypeface(Functions.getRegularFont(this));
        radioNo.setTypeface(Functions.getRegularFont(this));
    }

    private void clickListener() {
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:
                presenter.submitRating(this, Functions.toStr(edtComment), ratingBar.getRating());
                break;
        }
    }

    private void initToolbar() {
        txtCustomTitle = (TextView) findViewById(R.id.txtCustomTitle);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow);
        setSupportActionBar(toolbar);

        txtCustomTitle.setText("Add Review");
        txtCustomTitle.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });
    }
}
