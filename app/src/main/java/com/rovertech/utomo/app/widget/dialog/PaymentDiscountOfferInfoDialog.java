package com.rovertech.utomo.app.widget.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.flyco.animation.SlideEnter.SlideLeftEnter;
import com.flyco.animation.SlideExit.SlideRightExit;
import com.flyco.dialog.widget.base.BaseDialog;
import com.rovertech.utomo.app.R;
import com.rovertech.utomo.app.helper.Functions;
import com.rovertech.utomo.app.invoice.model.PaymentDistinctDiscountModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sagartahelyani on 17-03-2016.
 */
public class PaymentDiscountOfferInfoDialog extends BaseDialog implements View.OnClickListener {

    View parentView;
    Context context;

    private TextView txtTitle, emptyTextView;
    private ImageView imgClose;
    private ListView carListView;
    private List<PaymentDistinctDiscountModel> offerList;
    private String title;

    public PaymentDiscountOfferInfoDialog(Context context, List<PaymentDistinctDiscountModel> offerlist, String title) {
        super(context);
        this.offerList = offerlist;
        this.context = context;
        this.title = title;
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new SlideLeftEnter());
        dismissAnim(new SlideRightExit());

        parentView = View.inflate(context, R.layout.layout_payment_discount_offers_list_dialog, null);

        emptyTextView = (TextView) parentView.findViewById(R.id.emptyTextView);

        carListView = (ListView) parentView.findViewById(R.id.carListView);
        emptyTextView.setText("No Offers Available");
        emptyTextView.setPadding(16, 16, 16, 16);
        emptyTextView.setTypeface(Functions.getRegularFont(context));
        carListView.setEmptyView(emptyTextView);

        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        imgClose = (ImageView) parentView.findViewById(R.id.imgClose);
        txtTitle.setText(title);
        setTypeface();

        new MyAsyncTask().execute();

        return parentView;
    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(context), Typeface.BOLD);
    }

    @Override
    public void setUiBeforShow() {
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

    class MyAsyncTask extends AsyncTask<String, Integer, List<PaymentDistinctDiscountModel>> {

        private final ProgressDialog dialog = new ProgressDialog(context);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Please Wait...");
            dialog.show();
        }


        @Override
        protected List<PaymentDistinctDiscountModel> doInBackground(String... params) {
            List<PaymentDistinctDiscountModel> result = new ArrayList<PaymentDistinctDiscountModel>();
            result = offerList;
            return result;
        }

        @Override
        protected void onPostExecute(List<PaymentDistinctDiscountModel> offerCategories) {
            super.onPostExecute(offerCategories);
            dialog.dismiss();

            PaymentDiscountOfferInfoAdapter adapter = new PaymentDiscountOfferInfoAdapter(context, offerCategories);
            adapter.setItemList(offerCategories);
            adapter.notifyDataSetChanged();
            carListView.setAdapter(adapter);
        }
    }
}
