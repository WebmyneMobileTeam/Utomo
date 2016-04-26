package com.rovertech.utomo.app.about;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.main.drawer.DrawerActivity;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private View parentView;
    private DrawerActivity activity;

    //  private TextView txtAppName, txtVersion, txt1, txt2, txt3, txt4, txt5;

    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance() {

        Bundle args = new Bundle();

        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.fragment_about, container, false);
        init();
        return parentView;
    }

    private void init() {
        activity = (DrawerActivity) getActivity();

      /*  txtAppName = (TextView) parentView.findViewById(R.id.txtAppName);
        txt1 = (TextView) parentView.findViewById(R.id.txt1);
        txt2 = (TextView) parentView.findViewById(R.id.txt2);
        txt3 = (TextView) parentView.findViewById(R.id.txt3);
        txt4 = (TextView) parentView.findViewById(R.id.txt4);
        txt5 = (TextView) parentView.findViewById(R.id.txt5);
        txtVersion = (TextView) parentView.findViewById(R.id.txtVersion);*/

        Element versionElement = new Element();
        versionElement.setTitle("Version 1.0");

        parentView = new AboutPage(getActivity())
                .isRTL(false)
                .setImage(R.drawable.ic_launcher)
                .addItem(versionElement)
                .setDescription(getString(R.string.dummy1) + "\n" + getString(R.string.dummy2) + "\n" + getString(R.string.dummy3) + "\n" + getString(R.string.dummy4) + "\n" + getString(R.string.dummy5))
                .addGroup("Connect with us")
                .addEmail("elmehdi.sakout@gmail.com")
                .addFacebook("the.medy")
                .addTwitter("medyo80")
                .addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
                .addPlayStore("com.ideashower.readitlater.pro")
                .addGitHub("medyo")
                .addInstagram("medyo80")
                .create();

        setTypeface();

    }

    private void setTypeface() {
       /* txtAppName.setTypeface(Functions.getBoldFont(getActivity()));
        txt1.setTypeface(Functions.getThinFont(getActivity()));
        txt2.setTypeface(Functions.getThinFont(getActivity()));
        txt3.setTypeface(Functions.getThinFont(getActivity()));
        txt4.setTypeface(Functions.getThinFont(getActivity()));
        txt5.setTypeface(Functions.getThinFont(getActivity()));
        txtVersion.setTypeface(Functions.getRegularFont(getActivity()));*/
    }

}
