package igeorge.xtreme.ergotechnicaltest;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import igeorge.xtreme.ergotechnicaltest.receivers.VersionStateChangeReceiver;
import igeorge.xtreme.ergotechnicaltest.utils.AppController;
import igeorge.xtreme.ergotechnicaltest.utils.ApplicationUtils;

public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        if (!ApplicationUtils.checkMediaPermissions(context)) {
            ApplicationUtils.requestMediaPermission(this);
        }else {
            callBroadCastReceiverToCheck();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(ApplicationUtils.checkMediaPermissions(context)){
            callBroadCastReceiverToCheck();
        }
    }

    private void callBroadCastReceiverToCheck() {
        Intent registerBroadcast = new Intent(this, VersionStateChangeReceiver.class);
        sendBroadcast(registerBroadcast);
    }
}
