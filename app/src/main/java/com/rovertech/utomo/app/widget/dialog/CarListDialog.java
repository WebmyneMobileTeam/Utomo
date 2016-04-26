package com.rovertech.utomo.app.widget.dialog;

import android.content.Context;
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
import com.rovertech.utomo.app.helper.PrefUtils;
import com.rovertech.utomo.app.profile.carlist.CarPojo;

import java.util.ArrayList;

/**
 * Created by sagartahelyani on 17-03-2016.
 */
public class CarListDialog extends BaseDialog implements View.OnClickListener {

    View parentView;
    Context context;

    private TextView txtTitle, emptyTextView;
    private ImageView imgClose;
    private ListView carListView;
    ArrayList<CarPojo> carList;

    onSubmitListener onSubmitListener;
    String dealership;

    public void setOnSubmitListener(CarListDialog.onSubmitListener onSubmitListener) {
        this.onSubmitListener = onSubmitListener;
    }

    public CarListDialog(Context context, ArrayList<CarPojo> carList, String dealership) {
        super(context);
        this.carList = carList;
        this.context = context;
        this.dealership = dealership;
    }

    @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new SlideLeftEnter());
        dismissAnim(new SlideRightExit());

        parentView = View.inflate(context, R.layout.layout_car_list_dialog, null);

        emptyTextView = (TextView) parentView.findViewById(R.id.emptyTextView);

        carListView = (ListView) parentView.findViewById(R.id.carListView);
        emptyTextView.setText(String.format("You don't have any %s car. Please cancel this procedure.", dealership));
        emptyTextView.setPadding(16, 16, 16, 16);
        emptyTextView.setTypeface(Functions.getRegularFont(context));
        carListView.setEmptyView(emptyTextView);

        txtTitle = (TextView) parentView.findViewById(R.id.txtTitle);
        imgClose = (ImageView) parentView.findViewById(R.id.imgClose);

        setTypeface();

        CarListAdapter adapter = new CarListAdapter(context, carList);

     //   if (carList.size() > 0) {

            carListView.setAdapter(adapter);
            carListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (onSubmitListener != null)
                        onSubmitListener.onSubmit(carList.get(position));
                }
            });

     //   }else{

     //   }

        return parentView;
    }

    private void setTypeface() {
        txtTitle.setTypeface(Functions.getBoldFont(context));
    }

    @Override
    public void setUiBeforShow() {
        txtTitle.setText("My Cars");

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
        void onSubmit(CarPojo carPojo);
    }
}
