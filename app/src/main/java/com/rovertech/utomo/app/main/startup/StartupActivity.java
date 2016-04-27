package com.rovertech.utomo.app.main.startup;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.account.LoginActivity;
import com.rovertech.utomo.app.account.SignUpActivity;
import com.rovertech.utomo.app.helper.AppConstant;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.IntentConstant;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.widget.linkedViewPager.MyPagerAdapter;
import com.rovertech.utomo.app.widget.linkedViewPager.ViewPager;

import java.util.ArrayList;

public class StartupActivity extends AppCompatActivity implements StartupView, View.OnClickListener {

    private TextView txtSkip;
    private TextSwitcher textView;
    private Button btnLogin, btnSignUp;

    private StartupPresenter presenter;
    private View parentView;

    private ViewPager mPager;
    private ArrayList<View> mPageViews;
    private MyPagerAdapter mPageAdapter;

    private ViewPager mFramePager;
    private ArrayList<View> mFramePageViews;
    private MyPagerAdapter mFramePageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        if (!Functions.isConnected(this)) {
            Functions.showErrorAlert(this, AppConstant.NO_INTERNET_CONNECTION, true);
        }

        init();

        presenter = new StartupPresenterImpl(this);

    }

    private void init() {
        textView = (TextSwitcher) findViewById(R.id.textView);
        textView.setInAnimation(this, R.anim.slide_in_right);
        textView.setOutAnimation(this, R.anim.slide_out_left);

        textView.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView myText = new TextView(StartupActivity.this);
                myText.setGravity(Gravity.CENTER);
                myText.setTextSize(Functions.convertPixelsToDp(getResources().getDimension(R.dimen.M_TEXT), StartupActivity.this));
                myText.setPadding(2, 2, 2, 2);
                myText.setEllipsize(TextUtils.TruncateAt.END);
                myText.setLines(2);
                myText.setText(AppConstant.startupTexts[0]);
                myText.setTypeface(Functions.getRegularFont(StartupActivity.this));
                myText.setTextColor(Color.WHITE);
                return myText;
            }
        });

        parentView = findViewById(android.R.id.content);
        txtSkip = (TextView) findViewById(R.id.txtSkip);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        mPager = (ViewPager) findViewById(R.id.viewPager);
        mFramePager = (ViewPager) findViewById(R.id.main_scrolllayout);

        initPager();

        txtSkip.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);

        setTypeface();
    }

    private void setTypeface() {
        txtSkip.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        btnLogin.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
        btnSignUp.setTypeface(Functions.getBoldFont(this), Typeface.BOLD);
    }

    private void initPager() {
        mPageViews = new ArrayList<View>();
        mFramePageViews = new ArrayList<View>();

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View frameView1 = inflater.inflate(R.layout.transparent_layer_image, null);
        initFramePagerView(frameView1, R.drawable.slider1);
        View frameView2 = inflater.inflate(R.layout.transparent_layer_image, null);
        initFramePagerView(frameView2, R.drawable.slider2);
        View frameView3 = inflater.inflate(R.layout.transparent_layer_image, null);
        initFramePagerView(frameView3, R.drawable.slider3);
        View frameView4 = inflater.inflate(R.layout.transparent_layer_image, null);
        initFramePagerView(frameView4, R.drawable.slider1);
        View frameView5 = inflater.inflate(R.layout.transparent_layer_image, null);
        initFramePagerView(frameView5, R.drawable.slider2);
        mFramePageViews.add(frameView1);
        mFramePageViews.add(frameView2);
        mFramePageViews.add(frameView3);
        mFramePageViews.add(frameView4);
        mFramePageViews.add(frameView5);

        mFramePageAdapter = new MyPagerAdapter(mFramePageViews);
        mFramePager.setAdapter(mFramePageAdapter);

        View view1 = inflater.inflate(R.layout.transparent_layer, null);
        initPagerView(view1, "Text 1");
        View view2 = inflater.inflate(R.layout.transparent_layer, null);
        initPagerView(view2, "Text 2");
        View view3 = inflater.inflate(R.layout.transparent_layer, null);
        initPagerView(view3, "Text 3");
        View view4 = inflater.inflate(R.layout.transparent_layer, null);
        initPagerView(view4, "Text 4");
        View view5 = inflater.inflate(R.layout.transparent_layer, null);
        initPagerView(view5, "Text 5");

        mPageViews.add(view1);
        mPageViews.add(view2);
        mPageViews.add(view3);
        mPageViews.add(view4);
        mPageViews.add(view5);
        mPageAdapter = new MyPagerAdapter(mPageViews);
        mPager.setAdapter(mPageAdapter);
        mPager.setFollowViewPager(mFramePager);

        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mFramePager.getCurrentItem() > position) {
                    textView.setInAnimation(StartupActivity.this, R.anim.slide_in_left);
                    textView.setOutAnimation(StartupActivity.this, R.anim.slide_out_right);

                } else {
                    textView.setInAnimation(StartupActivity.this, R.anim.slide_in_right);
                    textView.setOutAnimation(StartupActivity.this, R.anim.slide_out_left);

                }
                textView.setText(AppConstant.startupTexts[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initPagerView(View view, String text) {
        TextView textView1 = (TextView) view.findViewById(R.id.text);
        textView1.setText(text);
    }

    public void initFramePagerView(View frameView, int drawable) {
        ImageView image = (ImageView) frameView.findViewById(R.id.image);
        image.setImageResource(drawable);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtSkip:
                presenter.skip(this);
                break;

            case R.id.imgFb:
                presenter.loginFb();
                break;

            case R.id.imgGoogle:
                presenter.loginGplus();
                break;

            case R.id.btnLogin:
                presenter.login();
                break;

            case R.id.btnSignUp:
                presenter.signUp();
                break;
        }
    }

    @Override
    public void normalLogin() {
        PrefUtils.setRedirectLogin(this, AppConstant.FROM_START);
        Intent loginIntent = new Intent(StartupActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    @Override
    public void signUp() {
        Functions.fireIntent(StartupActivity.this, SignUpActivity.class);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    @Override
    public void fbLogin() {
        Functions.showSnack(parentView, "fb login");
    }

    @Override
    public void gplusLogin() {
        Functions.showSnack(parentView, "gplus login");
    }
}
