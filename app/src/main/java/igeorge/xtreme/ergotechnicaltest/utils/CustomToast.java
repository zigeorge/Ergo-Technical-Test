package igeorge.xtreme.ergotechnicaltest.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import igeorge.xtreme.ergotechnicaltest.R;


/**
 * Created by pop on 3/18/2017.
 * this class
 */

public class CustomToast {



    public static void show(Context mContext, String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }


}
