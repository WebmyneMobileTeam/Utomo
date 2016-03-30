package com.rovertech.utomo.app.account.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.account.model.City;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 29-03-2016.
 */
public class CityAdapter extends ArrayAdapter<City> {

    Context context;
    private ArrayList<City> cityItems;
    private ArrayList<City> cityAll;
    private ArrayList<City> suggestions;
    LayoutInflater li;
    private int viewResourceId;


    public CityAdapter(Context context, int viewResourceId, ArrayList<City> data) {
        super(context, viewResourceId, data);
        this.cityItems = data;
        this.cityAll = (ArrayList<City>) data.clone();
        this.suggestions = new ArrayList<City>();
        this.viewResourceId = viewResourceId;
        li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = li.inflate(viewResourceId, null);
        }

        City city = cityItems.get(position);
        if (city != null) {
            TextView txtArea = (TextView) convertView.findViewById(R.id.textView);
            if (txtArea != null) {
                txtArea.setText(city.CityName);
            }
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((City) (resultValue)).CityName;
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            if (constraint != null) {
                suggestions.clear();
                for (City area : cityAll) {
                    if (area.CityName.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(area);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<City> filteredList = (ArrayList<City>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (City a : filteredList) {
                    add(a);
                }
                notifyDataSetChanged();
            }

        }
    };
}
