package igeorge.xtreme.ergotechnicaltest.presenters;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;

import java.util.ArrayList;

import igeorge.xtreme.ergotechnicaltest.R;
import igeorge.xtreme.ergotechnicaltest.model.Item;
import igeorge.xtreme.ergotechnicaltest.server.APIInteractor;
import igeorge.xtreme.ergotechnicaltest.server.APIInteractorImpl;
import igeorge.xtreme.ergotechnicaltest.services.DistanceStatementIntentService;
import igeorge.xtreme.ergotechnicaltest.views.ListActivityView;

/**
 * Created by iGeorge on 26/11/17.
 */

public class ListActivityPresenterImpl implements ListActivityPresenter {
    private Context context;
    private ListActivityView listActivityView;
    private APIInteractor apiInteractor;

    private static final String BASE_URL = "http://hatil.ergov.com/exam/";

    public ListActivityPresenterImpl(Context context){
        this.context = context;
        listActivityView = (ListActivityView) context;
        apiInteractor = new APIInteractorImpl(context, BASE_URL);
    }

    @Override
    public void initializePresenter() {
        listActivityView.initUI();
        listActivityView.showProgress(context.getString(R.string.loading_items));
        apiInteractor.getItemResponse(this);
    }

    @Override
    public void onItemLoaded(ArrayList<Item> items) {
        listActivityView.hideProgress();
        listActivityView.setListAdapter(items);
        Intent intent = new Intent(context, DistanceStatementIntentService.class);
        intent.putExtra(DistanceStatementIntentService.EXTRA_ITEM_ARRAY, new Gson().toJson(items));
        context.startService(intent);
    }

    @Override
    public void onFailed(String message) {
        listActivityView.hideProgress();
        listActivityView.showMessage(message);
    }
}
