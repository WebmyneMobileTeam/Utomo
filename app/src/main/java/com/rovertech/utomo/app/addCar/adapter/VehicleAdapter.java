package com.rovertech.utomo.app.addCar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.addCar.model.Vehicle;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 25-03-2016.
 */
public class VehicleAdapter extends ArrayAdapter<Vehicle> {

    private Context context;
    private int resource;
    private ArrayList<Vehicle> modelList;
    private LayoutInflater inflater;

    public VehicleAdapter(Context context, int resource, ArrayList<Vehicle> modelList) {
        super(context, resource, modelList);
        this.context = context;
        this.resource = resource;
        this.modelList = modelList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return getCustomView(position, convertView, parent);

    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View convertView, ViewGroup parent) {

        View row = inflater.inflate(resource, parent, false);
        TextView textView = (TextView) row.findViewById(R.id.textView);
        textView.setText(modelList.get(position).Model);

        return row;
    }

    @Override
    public int getCount() {
        return modelList.size();
    }
}
