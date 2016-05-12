package com.rovertech.utomo.app.profile.personal;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
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
    private MaterialEditText edtEmail, edtMobile, edtName, edtDOB, edtAddress;
    private MaterialAutoCompleteTextView edtCity;
    private Button txtUpdate, txtChangePassword;
    private ImageView imagePerson;
    private RelativeLayout imageSelectLayout;
    private PersonalProfilePresenter personalProfilePresenter;
    private int cityId = 0;
    private File file = null;
    ProgressDialog progressDialog;

    private String birthDate = "";

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

        personalProfilePresenter = new PersonalProfilePresenterImpl(this, getActivity());

        init();

        setTypeface();

        loadProfile();

        return parentView;
    }

    private void loadProfile() {
        UserProfileOutput profile = PrefUtils.getUserFullProfileDetails(getActivity());
        Log.e("profile", Functions.jsonString(profile));

        cityId = profile.CityID;
       /* if(profile.MobileNo!=null && !profile.MobileNo.equals("0"))
        {
        edtMobile.setText(profile.MobileNo);}
       *//* else
        {
            edtMobile.setText("");
        }*/
        edtMobile.setText(profile.MobileNo);
        edtEmail.setText(profile.EmailID);
        //edtCity.setText(profile.CityName);
        if (profile.CityName != null && !profile.CityName.equals("0")) {
            edtCity.setText(profile.CityName);
        }
        edtName.setText(profile.Name);
        // edtDOB.setText(profile.DOB);
        if (profile.DOB != null && !profile.DOB.equals("01-01-1900")) {
            edtDOB.setText(profile.DOB);
        }
        edtAddress.setText(profile.Address.trim());
        if (profile.ProfileImg != null && profile.ProfileImg.length() > 0)
            Glide.with(getActivity()).load(profile.ProfileImg).asBitmap().override(480, 320).into(imagePerson);
    }

    private void setTypeface() {
        edtEmail.setTypeface(Functions.getRegularFont(getActivity()));
        edtMobile.setTypeface(Functions.getRegularFont(getActivity()));
        edtName.setTypeface(Functions.getRegularFont(getActivity()));
        edtDOB.setTypeface(Functions.getRegularFont(getActivity()));
        edtAddress.setTypeface(Functions.getRegularFont(getActivity()));
        edtCity.setTypeface(Functions.getRegularFont(getActivity()));
        txtChangePassword.setTypeface(Functions.getBoldFont(getActivity()), Typeface.BOLD);
        txtUpdate.setTypeface(Functions.getBoldFont(getActivity()), Typeface.BOLD);
    }

    private void init() {
        activity = (ProfileActivity) getActivity();
        imagePerson = (ImageView) parentView.findViewById(R.id.imagePerson);
        imageSelectLayout = (RelativeLayout) parentView.findViewById(R.id.imageSelectLayout);
        edtDOB = (MaterialEditText) parentView.findViewById(R.id.edtDOB);
        edtCity = (MaterialAutoCompleteTextView) parentView.findViewById(R.id.edtCity);
        edtAddress = (MaterialEditText) parentView.findViewById(R.id.edtAddress);
        edtEmail = (MaterialEditText) parentView.findViewById(R.id.edtEmail);
        edtMobile = (MaterialEditText) parentView.findViewById(R.id.edtMobile);
        edtName = (MaterialEditText) parentView.findViewById(R.id.edtName);
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
        PrefUtils.setRefreshDashboard(getActivity(), true);
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
    public void success() {
        Functions.showToast(getActivity(), "Profile updated successfully.");
    }

    @Override
    public void fail(String responseMessage) {
        Functions.showToast(getActivity(), responseMessage);
    }

    @Override
    public void showProgress() {
        progressDialog = ProgressDialog.show(getActivity(), "Loading", "Please wait..", false);
    }

    @Override
    public void hideProgress() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public void setRxImage(File finalFile) {
        file = finalFile;
        Bitmap bitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
        Bitmap resizedBitmap = Functions.getResizedBitmap(bitmap, 640, 640);
        imagePerson.setImageBitmap(resizedBitmap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtUpdate:
                personalProfilePresenter.doUpdate(getActivity(), edtName.getText().toString().trim(), edtDOB.getText().toString().trim(),
                        edtAddress.getText().toString().trim(), cityId, file, edtEmail.getText().toString().trim());
                break;

            case R.id.edtDOB:
                personalProfilePresenter.selectDOB(getActivity());
                break;

            case R.id.imageSelectLayout:
                Functions.setPermission(getActivity(),
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        new PermissionListener() {
                            @Override
                            public void onPermissionGranted() {
                                personalProfilePresenter.selectImage();
                            }

                            @Override
                            public void onPermissionDenied(ArrayList<String> arrayList) {

                                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT);
                            }
                        });

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
