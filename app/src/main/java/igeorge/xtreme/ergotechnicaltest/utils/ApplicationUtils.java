package igeorge.xtreme.ergotechnicaltest.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import igeorge.xtreme.ergotechnicaltest.R;

/**
 * Created by Zahidul_Islam on 02-August-2016.
 */
public class ApplicationUtils {

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
