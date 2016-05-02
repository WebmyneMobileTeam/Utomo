package com.rovertech.utomo.app.widget.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.flyco.animation.SlideEnter.SlideLeftEnter;
import com.flyco.animation.SlideExit.SlideRightExit;
import com.flyco.dialog.widget.base.BaseDialog;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.offers.adpter.*;
import com.rovertech.utomo.app.offers.model.OfferCategory;
import com.rovertech.utomo.app.profile.carlist.CarPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sagartahelyani on 17-03-2016.
 */
public class AdminOfferInfoDialog extends BaseDialog implements View.OnClickListener {

    View parentView;
    Context context;

    private TextView txtTitle, txtTitle1, emptyTextView;
    private ImageView imgClose;
    private ListView carListView;
    ArrayList<OfferCategory> offerList;

    onSubmitListener onSubmitListener;
    String type, title,desc;

    public void setOnSubmitListener(AdminOfferInfoDialog.onSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    public AdminOfferInfoDialog(Context context, ArrayList<OfferCategory> offerlist, String type, String title,String desc) {
        super(context);
        this.offerList = offerlist;
        this.context = context;
        this.type = type;
        this.title = title;
        this.desc=desc;
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new SlideLeftEnter());
        dismissAnim(new SlideRightExit());

        parentView = View.inflate(context, R.layout.layout_car_list_dialog, null);

        emptyTextView = (TextView) parentView.findViewById(R.id.emptyTextView);

        carListView = (ListView) parentView.findViewById(R.id.carListView);
        emptyTextView.setText("You don't have any detail. Please cancel this procedure.");
        emptyTextView.setPadding(16, 16, 16, 16);
        emptyTextView.setTypeface(Functions.getRegularFont(context));
        carListView.setEmptyView(emptyTextView);

        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        txtTitle1 = (TextView) parentView.findViewById(R.id.txtTitle1);
        imgClose = (ImageView) parentView.findViewById(R.id.imgClose);

        txtTitle1.setVisibility(View.VISIBLE);
        txtTitle1.setText(desc);
        setTypeface();



        //   if (carList.size() > 0) {

        new MyAsyncTask().execute();

        carListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onSubmitListener != null)
                    onSubmitListener.onSubmit(offerList.get(position));
            }
        });

        //   }else{

        //   }

        return parentView;
    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
        txtTitle1.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
    }

    @Override
    public void setUiBeforShow() {
        txtTitle.setText(title);

        setCancelable(false);

        imgClose.setOnClickListener(this);
        parentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgClose:
                dismiss();
                break;
        }
    }

    public interface onSubmitListener {
        void onSubmit(OfferCategory offerCategory);
    }


    class MyAsyncTask extends AsyncTask<String, Integer, ArrayList<OfferCategory>> {

        private final ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("please wait...");
            dialog.show();
        }


        @Override
        protected ArrayList<OfferCategory> doInBackground(String... params) {
            ArrayList<OfferCategory> result = new ArrayList<OfferCategory>();
            result=offerList;
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<OfferCategory> offerCategories) {
            super.onPostExecute(offerCategories);
            dialog.dismiss();

            AdminOfferInfoAdapter adapter = new AdminOfferInfoAdapter(context, offerCategories);
            adapter.setItemList(offerCategories);
            adapter.notifyDataSetChanged();
            carListView.setAdapter(adapter);
        }
    }
}
