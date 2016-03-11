package com.rovertech.utomo.app.widget.tiles.odometer;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by sagartahelyani on 11-03-2016.
 */
public class OdometerPresenterImpl implements OdometerPresenter {

    OdometerView odometerView;

    public OdometerPresenterImpl(OdometerView odometerView) {
        this.odometerView = odometerView;
    }

    @Override
    public void reset(final Context context) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Reset Odometer");
        alert.setMessage("Are you sure want reset odometer?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                odometerView.resetOdometer();
                dialog.dismiss();

            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();

    }

    @Override
    public void done(final Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Change Odometer Reading");
        alert.setMessage("Are you sure want set this odometer reading?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                odometerView.setReading();
                dialog.dismiss();

            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}
