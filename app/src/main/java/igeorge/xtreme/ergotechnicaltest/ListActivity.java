package igeorge.xtreme.ergotechnicaltest;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

import igeorge.xtreme.ergotechnicaltest.adapters.ItemsAdapter;
import igeorge.xtreme.ergotechnicaltest.model.Item;
import igeorge.xtreme.ergotechnicaltest.presenters.ListActivityPresenter;
import igeorge.xtreme.ergotechnicaltest.presenters.ListActivityPresenterImpl;
import igeorge.xtreme.ergotechnicaltest.services.DistanceStatementIntentService;
import igeorge.xtreme.ergotechnicaltest.utils.ApplicationUtils;
import igeorge.xtreme.ergotechnicaltest.views.ListActivityView;

public class ListActivity extends AppCompatActivity implements ListActivityView {

    private Context context;
    private ListActivityPresenter listActivityPresenter;
    private ItemsAdapter itemsAdapter;

    private RecyclerView rv_items;
    private ProgressDialog progressDialog;

    private ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        context = this;
        listActivityPresenter = new ListActivityPresenterImpl(context);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ApplicationUtils.checkInternet(context)) {
            listActivityPresenter.initializePresenter();
        } else {
            showMessage(getString(R.string.no_internet));
        }
        registerReceiver(receiver, new IntentFilter(
                DistanceStatementIntentService.DISTANCE_NOTIFICATION));
    }

    @Override
    public void initUI() {
        rv_items = (RecyclerView) findViewById(R.id.rv_items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_items.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void setListAdapter(ArrayList<Item> items) {
        this.items = items;
        itemsAdapter = new ItemsAdapter(context, items);
        rv_items.setAdapter(itemsAdapter);
    }

    @Override
    public void showProgress(String message) {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(DistanceStatementIntentService.EXTRA_ITEM);
                Item changedItem = new Gson().fromJson(string, Item.class);
                int resultCode = bundle.getInt(DistanceStatementIntentService.RESULT);
                if (resultCode == RESULT_OK) {
                    for (int i = 0; i < items.size(); i++) {
                        Item item = items.get(i);
                        if (item.getStatement() == null) {
                            items.set(i, changedItem);
                            itemsAdapter.notifyItemChanged(i);
                            break;
                        }
                    }
                }
            }
        }
    };
}
