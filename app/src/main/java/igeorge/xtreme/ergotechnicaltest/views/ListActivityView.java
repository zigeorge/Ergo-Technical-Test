package igeorge.xtreme.ergotechnicaltest.views;

import java.util.ArrayList;

import igeorge.xtreme.ergotechnicaltest.model.Item;

/**
 * Created by iGeorge on 26/11/17.
 */

public interface ListActivityView {
    void initUI();

    void setListAdapter(ArrayList<Item> items);

    void showProgress(String message);

    void hideProgress();

    void showMessage(String message);
}
