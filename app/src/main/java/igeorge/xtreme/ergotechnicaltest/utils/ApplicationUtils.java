package igeorge.xtreme.ergotechnicaltest.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import igeorge.xtreme.ergotechnicaltest.R;

/**
 * Created by Zahidul_Islam on 02-August-2016.
 */
public class ApplicationUtils {
    public static final int PERMISSION_MEDIA_REQUEST_CODE = 11;

    public static boolean checkMediaPermissions(Context context) {
        int resultWritePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (resultWritePermission == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    public static void requestMediaPermission(Activity activity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(activity, activity.getResources().getString(R.string.app_name) + " needs to access your external storage and camera to show images to select.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_MEDIA_REQUEST_CODE);
        }
    }
    public static boolean checkInternet(Context context) {
        ConnectivityManager check = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = check.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static void loadImage(final ImageView imageView, String imgUrl, Context context, final ImageView.ScaleType scaleType, final int imgRes, final Transformation transformation) {
        if (!TextUtils.isEmpty(imgUrl) && imgUrl != null) {
            Picasso.with(context).load(imgUrl)
                    .placeholder(R.drawable.progress_animation)
                    .transform(transformation)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            imageView.setScaleType(scaleType);
                        }

                        @Override
                        public void onError() {
                            imageView.setScaleType(ImageView.ScaleType.CENTER);
                            imageView.setImageResource(imgRes);
                        }
                    });
        } else {
            imageView.setVisibility(View.GONE);
        }
    }


}
