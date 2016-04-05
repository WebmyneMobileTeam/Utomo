package com.rovertech.utomo.app.profile.personal;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.account.adapter.CityAdapter;
import com.rovertech.utomo.app.account.model.City;
import com.rovertech.utomo.app.account.model.UserProfileOutput;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.profile.ProfileActivity;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalProfileFragment extends Fragment implements PersonalProfileView, View.OnClickListener {

    private View parentView;
    private ProfileActivity activity;
    private EditText edtEmail, edtMobile, edtName, edtDOB, edtAddress;
    private AutoCompleteTextView edtCity;
    private Button txtUpdate, txtChangePassword;
    private ImageView imagePerson;
    private RelativeLayout imageSelectLayout;
    private PersonalProfilePresenter personalProfilePresenter;
    private int cityId = 0;
    private File file = null;

    public PersonalProfileFragment() {
        // Required empty public constructor
    }

    public static PersonalProfileFragment newInstance() {
        PersonalProfileFragment fragment = new PersonalProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_personal, container, false);

        personalProfilePresenter = new PersonalProfilePresenterImpl(this);

        init();

        setTypeface();

        loadProfile();

        return parentView;
    }

    private void loadProfile() {
        UserProfileOutput profile = PrefUtils.getUserFullProfileDetails(getActivity());
        Log.e("profile", Functions.jsonString(profile));

        cityId = profile.CityID;
        edtMobile.setText(profile.MobileNo);
        edtEmail.setText(profile.Email);
        edtCity.setText(profile.CityName);
        edtName.setText(profile.Name);
        edtDOB.setText(profile.DOB);
        edtAddress.setText(profile.Address);
    }

    private void setTypeface() {
        edtEmail.setTypeface(Functions.getNormalFont(getActivity()));
        edtMobile.setTypeface(Functions.getNormalFont(getActivity()));
        edtName.setTypeface(Functions.getNormalFont(getActivity()));
        edtDOB.setTypeface(Functions.getNormalFont(getActivity()));
        edtAddress.setTypeface(Functions.getNormalFont(getActivity()));
        edtCity.setTypeface(Functions.getNormalFont(getActivity()));
        txtChangePassword.setTypeface(Functions.getBoldFont(getActivity()));
        txtUpdate.setTypeface(Functions.getBoldFont(getActivity()));
    }

    private void init() {
        activity = (ProfileActivity) getActivity();
        imagePerson = (ImageView) parentView.findViewById(R.id.imagePerson);
        imageSelectLayout = (RelativeLayout) parentView.findViewById(R.id.imageSelectLayout);
        edtDOB = (EditText) parentView.findViewById(R.id.edtDOB);
        edtCity = (AutoCompleteTextView) parentView.findViewById(R.id.edtCity);
        edtAddress = (EditText) parentView.findViewById(R.id.edtAddress);
        edtEmail = (EditText) parentView.findViewById(R.id.edtEmail);
        edtMobile = (EditText) parentView.findViewById(R.id.edtMobile);
        edtName = (EditText) parentView.findViewById(R.id.edtName);
        txtChangePassword = (Button) parentView.findViewById(R.id.txtChangePassword);
        txtUpdate = (Button) parentView.findViewById(R.id.txtUpdate);

        listenerActions();

    }

    private void listenerActions() {
        txtChangePassword.setOnClickListener(this);
        txtUpdate.setOnClickListener(this);
        edtDOB.setOnClickListener(this);
        imageSelectLayout.setOnClickListener(this);

        edtCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0)
                    personalProfilePresenter.fetchCity(getActivity(), s.toString());
            }
        });
    }

    @Override
    public void onUpdateSuccess() {

    }

    @Override
    public void setDOB(String convertedDate) {
        edtDOB.setText(convertedDate);
    }

    @Override
    public void setCityAdapter(CityAdapter adapter, final ArrayList<City> cityArrayList) {
        edtCity.setAdapter(adapter);
        edtCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edtCity.getWindowToken(), 0);
                City city = cityArrayList.get(position);
                cityId = city.CityID;
            }
        });
    }

    @Override
    public void setImage(Bitmap thumbnail, File finalFile) {
        imagePerson.setImageBitmap(thumbnail);
        file = finalFile;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtUpdate:
                personalProfilePresenter.doUpdate(getActivity());
                break;

            case R.id.edtDOB:
                personalProfilePresenter.selectPUCDate(getActivity());
                break;

            case R.id.imageSelectLayout:
                personalProfilePresenter.selectImage(getActivity());
                break;

            case R.id.txtChangePassword:
                personalProfilePresenter.changePwd(getActivity());
                break;
        }
    }

    public void setSrc(Intent data, Context context, int requestCode) {
        personalProfilePresenter.setImage(data, context, requestCode);
    }
}
