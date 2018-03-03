package com.getinfocia.infocia.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getinfocia.infocia.OtherActivty;
import com.getinfocia.infocia.R;
import com.getinfocia.infocia.item.ItemCategory;
import com.getinfocia.infocia.util.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.font;
import static android.R.attr.resource;

/**
 * Created by Pravin on 20-01-2018.
 */

public class HeaderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private Activity activity;
    private List<ItemCategory> itemsAllphotos;
    private ItemCategory objAllBean;
    private int row;
    private int currentPage;
    private int highlightColor;
    Typeface font;

    public HeaderAdapter(Activity act,List<ItemCategory> arrayList) {
        this.activity = act;
        this.itemsAllphotos = arrayList;
        this.currentPage = 0;
        this.highlightColor = act.getResources().getColor(R.color.yellow);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_lsv_item, parent, false);
            return new VHItem(view);
        } else if (viewType == TYPE_HEADER) {
            View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lsv_header, parent, false);
            return new VHHeader(headerView);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof VHItem) {
            //String dataItem = getItem(position);
            //cast holder to VHItem and set data
            String imageUrl = "";
            //objAllBean = itemsAllphotos.get(position);

            ((VHItem) holder).txt.setText(itemsAllphotos.get(position).getCategoryName().toString());

            if (itemsAllphotos.get(position).getCategoryName().toString().equalsIgnoreCase("All Post"))
            {
                Picasso.with(activity).load(R.drawable.topic_all_news).into(((VHItem) holder).img);
            }else {
                if (itemsAllphotos.get(position).getCategoryImageurl().contains("null"))
                {
                    String str = itemsAllphotos.get(position).getCategoryImageurl();
                    imageUrl = itemsAllphotos.get(position).getCategoryImageurl().substring(4,str.length());
                    Log.e("CAT_IMAGE_ADAPTER True",""+imageUrl);
                    Picasso.with(activity).load(Constant.IMAGE_PATH+imageUrl).into(((VHItem) holder).img);
                }else {
                    imageUrl = itemsAllphotos.get(position).getCategoryImageurl().replace(" ", "%20");
                    Log.e("CAT_IMAGE_ADAPTER false",""+imageUrl);
                    Picasso.with(activity).load(Constant.IMAGE_PATH+imageUrl).into(((VHItem) holder).img);
                }
            }
            ((VHItem)holder).layoutItems.setBackgroundColor((currentPage == position) ? highlightColor : Color.TRANSPARENT);

            /*((VHItem)holder).layoutItems.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    objBean=arrayOfAllcategory.get(position-lsv_category.getHeaderViewsCount());
                    startActivity(getIntent().putExtra("selected_category", ""+objBean.getCategoryId()));
                    finish();
                }
            });
*/
        } else if (holder instanceof VHHeader) {
            //cast holder to VHHeader and set data for header.
            ((VHHeader)holder).layoutHeader.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intsetting=new Intent(activity,OtherActivty.class);
                    activity.startActivity(intsetting);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemsAllphotos.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private ItemCategory getItem(int position) {
        return itemsAllphotos.get(position);
    }

    class VHItem extends RecyclerView.ViewHolder {
        public TextView txt;
        ImageView img;
        RelativeLayout layoutItems;

        public VHItem(View itemView) {
            super(itemView);
            txt=(TextView)itemView.findViewById(R.id.txt_allvideos_categty);
            img = (ImageView)itemView.findViewById(R.id.itemsCatImage);
            layoutItems = (RelativeLayout)itemView.findViewById(R.id.layoutItems);

            font = Typeface.createFromAsset(activity.getAssets(), "RobotoLight.ttf");
            txt.setTypeface(font);
        }
    }

    class VHHeader extends RecyclerView.ViewHolder {
        public TextView headertxt;
        ImageView headerImg;
        RelativeLayout layoutHeader;

        public VHHeader(View itemView) {
            super(itemView);
            layoutHeader = (RelativeLayout)itemView.findViewById(R.id.header);
        }
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        notifyDataSetChanged();
    }
}



