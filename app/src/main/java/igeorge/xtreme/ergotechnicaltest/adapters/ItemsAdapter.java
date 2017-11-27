package igeorge.xtreme.ergotechnicaltest.adapters;

/**
 * Created by iGeorge on 8/9/2016.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import igeorge.xtreme.ergotechnicaltest.R;
import igeorge.xtreme.ergotechnicaltest.model.Item;
import igeorge.xtreme.ergotechnicaltest.utils.ApplicationUtils;
import igeorge.xtreme.ergotechnicaltest.utils.CircleTransform;

/**
 * Created by iGeorge on 8/6/2016.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder> {
    private ArrayList<Item> items;
    private Context context;

    public ItemsAdapter(Context context, ArrayList<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new ItemsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ItemsViewHolder itemsViewHolder, final int position) {
        final Item item = items.get(position);
        if (items != null && items.size() > 0) {
            itemsViewHolder.tv_name.setText(item.getName());
            if (item.getStatement() != null) {
                itemsViewHolder.tv_right_statement.setText(item.getStatement());
            }
            if (item.getImage_url() != null && item.getImage_url().length() > 0) {
                ApplicationUtils.loadImage(itemsViewHolder.iv_image, item.getImage_url(), context, ImageView.ScaleType.CENTER_INSIDE, R.mipmap.ic_launcher, new CircleTransform());
            } else {
                itemsViewHolder.iv_image.setImageResource(R.mipmap.ic_launcher);
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class ItemsViewHolder extends RecyclerView.ViewHolder {
        protected LinearLayout ll_info;
        protected CardView cv_container;
        protected TextView tv_name, tv_right_statement;
        protected ImageView iv_image;

        public ItemsViewHolder(View view) {
            super(view);
            ll_info = (LinearLayout) view.findViewById(R.id.ll_info);
            cv_container = (CardView) view.findViewById(R.id.cv_container);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_right_statement = (TextView) view.findViewById(R.id.tv_right_statement);
            iv_image = (ImageView) view.findViewById(R.id.iv_image);
        }
    }
}

