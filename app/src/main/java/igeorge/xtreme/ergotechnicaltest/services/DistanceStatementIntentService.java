package igeorge.xtreme.ergotechnicaltest.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import igeorge.xtreme.ergotechnicaltest.R;
import igeorge.xtreme.ergotechnicaltest.model.Item;
import igeorge.xtreme.ergotechnicaltest.model.MapUrl;
import igeorge.xtreme.ergotechnicaltest.server.APIInteractor;
import igeorge.xtreme.ergotechnicaltest.server.APIInteractorImpl;

public class DistanceStatementIntentService extends IntentService implements APIInteractor.OnUrlLoadedListener {

    // TODO: Rename parameters
    public static final String EXTRA_ITEM_ARRAY = "igeorge.xtreme.ergotechnicaltest.services.extra.ITEM_ARRAY";
    public static final String EXTRA_ITEM = "EXTRA_ITEM";
    public static final String DISTANCE_NOTIFICATION = "igeorge.xtreme.ergotechnicaltest.services.receiver";
    public static final String RESULT = "result";

    private Context context;

    private ArrayList<Item> items;
    private Item itemInProcess;

    private int counter = 1;
    private int length;

    private APIInteractor apiInteractor;

    public DistanceStatementIntentService() {
        super("DistanceStatementIntentService");
    }

    private static Map<String, String> getQueryMap(String query) {
        String[] params = query.replace("?", "").split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            context = this;
            final String itemJson = intent.getStringExtra(EXTRA_ITEM_ARRAY);
            final Item[] item_array = new Gson().fromJson(itemJson, Item[].class);
            this.items = new ArrayList<>(Arrays.asList(item_array));
            length = items.size();
            handleAction();
        }
    }

    private void handleAction() {
        itemInProcess = items.get(counter - 1);
        requestDistance(itemInProcess.getUrl1());
    }

    private void requestDistance(String url) {
        MapUrl mapUrl = prepareMapUrl(url);
        apiInteractor = new APIInteractorImpl(context, mapUrl.getBase());
        apiInteractor.loadUrl(this, mapUrl.getOrigin(), mapUrl.getDestination(), mapUrl.isAlternatives());
    }

    @Override
    public void onUrlLoaded(String response) {
        if (itemInProcess.getDistance1() == null) {
            itemInProcess.setDistance1(parseResponseToGetDistance(response));
            requestDistance(itemInProcess.getUrl2());
        } else if (itemInProcess.getDistance2() == null) {
            itemInProcess.setDistance2(parseResponseToGetDistance(response));
            itemInProcess.setStatement(compareDistances());
            publishChange();
            counter++;
            if (counter <= length) {
                handleAction();
            }
        }
    }

    private MapUrl prepareMapUrl(String url) {
        MapUrl mapUrl = new MapUrl();
        String[] urlInArray = url.split("json");
        mapUrl.setBase(urlInArray[0]);
        Map<String, String> query = getQueryMap(urlInArray[1]);
        mapUrl.setOrigin(query.get("origin"));
        mapUrl.setDestination(query.get("destination"));
        mapUrl.setAlternatives(query.get("alternatives").equalsIgnoreCase("true"));
        return mapUrl;
    }

    private String parseResponseToGetDistance(String response) {
        try {
            JSONObject responseObject = new JSONObject(response);
            String distance = responseObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getString("value");
            return distance;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String compareDistances() {
        if (getDistanceInInteger(itemInProcess.getDistance1()) > getDistanceInInteger(itemInProcess.getDistance2())) {
            return context.getString(R.string.distance_true);
        } else {
            return context.getString(R.string.distance_false);
        }
    }

    private int getDistanceInInteger(String distance) {
        return Integer.parseInt(distance);
    }

    private void publishChange() {
        Intent intent = new Intent(DISTANCE_NOTIFICATION);
        intent.putExtra(EXTRA_ITEM, new Gson().toJson(itemInProcess));
        intent.putExtra(RESULT, Activity.RESULT_OK);
        sendBroadcast(intent);
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
