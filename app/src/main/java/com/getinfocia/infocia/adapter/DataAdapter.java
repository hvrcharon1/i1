package com.getinfocia.infocia.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getinfocia.infocia.R;
import com.getinfocia.infocia.item.ItemCategory;
import com.getinfocia.infocia.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by Pravin on 23-01-2018.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<ItemCategory> android;
    private Context context;
    Typeface font;
    private int currentPage;
    private int highlightColor;

    public DataAdapter(Context context, ArrayList<ItemCategory> android) {
        this.android = android;
        this.context = context;
        this.currentPage = 0;
        this.highlightColor = context.getResources().getColor(R.color.yellow);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_lsv_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        String imageUrl = "";
        //objAllBean = itemsAllphotos.get(position);

        viewHolder.txt.setText(android.get(position).getCategoryName().toString());

        if (android.get(position).getCategoryName().toString().equalsIgnoreCase("All Post")) {
            Picasso.with(context).load(R.drawable.topic_all_news).into(viewHolder.img);
        } else {
            if (android.get(position).getCategoryImageurl().contains("null")) {
                String str = android.get(position).getCategoryImageurl();
                imageUrl = android.get(position).getCategoryImageurl().substring(4, str.length());
                Log.e("CAT_IMAGE_ADAPTER True", "" + imageUrl);
                Picasso.with(context).load(Constant.IMAGE_PATH + imageUrl).into(viewHolder.img);
            } else {
                imageUrl = android.get(position).getCategoryImageurl().replace(" ", "%20");
                Log.e("CAT_IMAGE_ADAPTER false", "" + imageUrl);
                Picasso.with(context).load(Constant.IMAGE_PATH + imageUrl).into(viewHolder.img);
            }
        }

        //view.setBackgroundColor((currentPage == position) ? highlightColor : Color.TRANSPARENT);
        viewHolder.layoutItems.setBackgroundColor((currentPage == position) ? highlightColor : Color.TRANSPARENT);

    }

        @Override
        public int getItemCount () {
            return android.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView txt;
            ImageView img;
            RelativeLayout layoutItems;

            public ViewHolder(View view) {
                super(view);

                txt=(TextView)itemView.findViewById(R.id.txt_allvideos_categty);
                img = (ImageView)itemView.findViewById(R.id.itemsCatImage);
                layoutItems = (RelativeLayout)itemView.findViewById(R.id.layoutItems);

                font = Typeface.createFromAsset(context.getAssets(), "RobotoLight.ttf");
                txt.setTypeface(font);
            }
        }
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        notifyDataSetChanged();
    }


}
