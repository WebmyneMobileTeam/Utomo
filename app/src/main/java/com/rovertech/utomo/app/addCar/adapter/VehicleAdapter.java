package com.rovertech.utomo.app.addCar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.addCar.model.Vehicle;
import com.rovertech.utomo.app.helper.Functions;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 25-03-2016.
 */
public class VehicleAdapter extends ArrayAdapter<Vehicle> {

    LayoutInflater layoutInflater;
    int mainView, dropdownView;
    private ArrayList<Vehicle> modelList;
    private Context context;

    public VehicleAdapter(Context context, int mainView, int dropdownView, ArrayList<Vehicle> modelList) {
        super(context, mainView, mainView, modelList);
        this.context = context;
        this.mainView = mainView;
        this.dropdownView = dropdownView;
        this.modelList = modelList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(mainView, parent, false);

        TextView txt = (TextView) convertView.findViewById(R.id.textView);
        txt.setTypeface(Functions.getRegularFont(context));
        txt.setText(modelList.get(position).Model);

        return convertView;

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(dropdownView, parent, false);

        TextView txt = (TextView) convertView.findViewById(R.id.textView);
        txt.setTypeface(Functions.getRegularFont(context));
        txt.setText(modelList.get(position).Model);

        return convertView;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }

    @Override
    public Vehicle getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }
}
