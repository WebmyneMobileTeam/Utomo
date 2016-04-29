package com.rovertech.utomo.app.addCar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sagartahelyani on 28-04-2016.
 */
public class CustomSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

    LayoutInflater layoutInflater;
    public List<String> values = new ArrayList<String>();
    Context context;
    int mainView, dropdownView;

    public CustomSpinnerAdapter(Context context, int mainView, int dropdownView, ArrayList<String> values) {
        this.context = context;
        this.mainView = mainView;
        this.dropdownView = dropdownView;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public String getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;

        view = layoutInflater.inflate(dropdownView, parent, false);

        TextView txt = (TextView) view.findViewById(R.id.textView);
        txt.setTypeface(Functions.getRegularFont(context));
        txt.setText(values.get(position));
        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "position " + ((TextView) v).getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;

        view = layoutInflater.inflate(mainView, parent, false);

        TextView txt = (TextView) view.findViewById(R.id.textView);
        txt.setTypeface(Functions.getRegularFont(context));
        txt.setText(values.get(position));

        return view;
    }

}
