package com.rovertech.utomo.app.widget;

import android.app.Dialog;
import android.content.Context;

import com.rovertech.utomo.app.R;

/**
 * Created by sagartahelyani on 16-05-2016.
 */
public class LoadingDialog extends Dialog {

    public LoadingDialog(Context context) {
        super(context);
        init();
    }

    private void init() {
        setContentView(R.layout.progress_layout);

    }

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }


}
