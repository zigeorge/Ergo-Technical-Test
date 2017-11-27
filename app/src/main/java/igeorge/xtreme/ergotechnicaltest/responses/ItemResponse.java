package igeorge.xtreme.ergotechnicaltest.responses;

import java.util.ArrayList;

import igeorge.xtreme.ergotechnicaltest.model.Item;

/**
 * Created by iGeorge on 26/11/17.
 */

public class ItemResponse {
    private ArrayList<Item> array;
    private String status;

    public ArrayList<Item> getArray() {
        return array;
    }

    public void setArray(ArrayList<Item> array) {
        this.array = array;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
