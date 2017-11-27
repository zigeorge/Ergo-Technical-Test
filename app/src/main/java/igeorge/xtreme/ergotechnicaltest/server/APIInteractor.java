package igeorge.xtreme.ergotechnicaltest.server;

import java.util.ArrayList;

import igeorge.xtreme.ergotechnicaltest.model.Item;

/**
 * Created by Zahidul_Islam_George on 08-November-2016.
 */
public interface APIInteractor {

    void getItemResponse(OnItemLoadedListener onItemLoadedListener);

    void loadUrl(OnUrlLoadedListener onUrlLoadedListener, String origin, String destination, boolean alternatives);

    interface OnItemLoadedListener extends FailedListener {
        void onItemLoaded(ArrayList<Item> items);
    }

    interface OnUrlLoadedListener extends FailedListener {
        void onUrlLoaded(String response);
    }

    interface FailedListener {
        void onFailed(String message);
    }
}
