package com.getinfocia.infocia.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.getinfocia.infocia.R;
import com.getinfocia.infocia.item.ItemCategory;
import com.getinfocia.infocia.util.Constant;
import com.squareup.picasso.Picasso;

import static android.R.attr.data;
import static android.R.attr.font;


public class CategoryAdapter  extends ArrayAdapter<ItemCategory> {
	
	private Activity activity;
	private List<ItemCategory> itemsAllphotos;
	private ItemCategory objAllBean;
	private int row;
	private int currentPage;
	private int highlightColor;
	Typeface font;
	
	public CategoryAdapter(Activity act, int resource, List<ItemCategory> arrayList) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.itemsAllphotos = arrayList;
		this.currentPage = 0;
		this.highlightColor = act.getResources().getColor(R.color.yellow);
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(row, null);

			holder = new ViewHolder();
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		if ((itemsAllphotos == null) || ((position + 1) > itemsAllphotos.size()))
			return view;

		String imageUrl = "";
		objAllBean = itemsAllphotos.get(position);
		holder.txt=(TextView)view.findViewById(R.id.txt_allvideos_categty);
		holder.img = (ImageView)view.findViewById(R.id.itemsCatImage);
		font = Typeface.createFromAsset(activity.getAssets(), "RobotoLight.ttf");
		holder.txt.setTypeface(font);


		holder.txt.setText(objAllBean.getCategoryName().toString());
		//Log.e("CAT_IMAGE_ADAPTER 1",""+objAllBean.getCategoryImageurl());
		//Log.e("CAT_IMAGE_ADAPTER 2",""+itemsAllphotos.get(position).getCategoryImageurl());
		//Log.e("CAT_IMAGE_ADAPTER 3",""+Constant.IMAGE_PATH+objAllBean.getCategoryImageurl().replace(" ", "%20"));

		if (objAllBean.getCategoryName().toString().equalsIgnoreCase("All News"))
		{
			Picasso.with(activity).load(R.drawable.topic_all_news).into(holder.img);
		}else {
			if (itemsAllphotos.get(position).getCategoryImageurl().contains("null"))
			{
				String str = itemsAllphotos.get(position).getCategoryImageurl();
				imageUrl = itemsAllphotos.get(position).getCategoryImageurl().substring(4,str.length());
				Log.e("CAT_IMAGE_ADAPTER True",""+imageUrl);
				Picasso.with(activity).load(Constant.IMAGE_PATH+imageUrl).into(holder.img);
			}else {
				imageUrl = objAllBean.getCategoryImageurl().replace(" ", "%20");
				Log.e("CAT_IMAGE_ADAPTER false",""+imageUrl);
				Picasso.with(activity).load(Constant.IMAGE_PATH+imageUrl).into(holder.img);
			}
		}








		/*if (objAllBean.getCategoryName().toString().equalsIgnoreCase("All Post"))
		{
			holder.img.setImageResource(R.drawable.topic_all_news);
			holder.txt.setText(objAllBean.getCategoryName().toString());
		}else {
			if (objAllBean.getCategoryName().toString().equalsIgnoreCase("Business"))
			{
				holder.img.setImageResource(R.drawable.topic_business);
				holder.txt.setText(objAllBean.getCategoryName().toString());
			}else {
				if (objAllBean.getCategoryName().toString().equalsIgnoreCase("Technology"))
				{
					holder.img.setImageResource(R.drawable.topic_science);
					holder.txt.setText(objAllBean.getCategoryName().toString());
				}else {
					if (objAllBean.getCategoryName().toString().equalsIgnoreCase("Sport"))
					{
						holder.img.setImageResource(R.drawable.topic_sports);
						holder.txt.setText(objAllBean.getCategoryName().toString());
					}else {
						if (objAllBean.getCategoryName().toString().equalsIgnoreCase("Gadget"))
						{
							holder.img.setImageResource(R.drawable.topic_all_news);
							holder.txt.setText(objAllBean.getCategoryName().toString());
						}else {
							if (objAllBean.getCategoryName().toString().equalsIgnoreCase("Trailer"))
							{
								holder.img.setImageResource(R.drawable.topic_all_news);
								holder.txt.setText(objAllBean.getCategoryName().toString());
							}else {
								if (objAllBean.getCategoryName().toString().equalsIgnoreCase("Entertainment"))
								{
									holder.img.setImageResource(R.drawable.topic_entertainment);
									holder.txt.setText(objAllBean.getCategoryName().toString());
								}else {
									holder.img.setImageResource(R.drawable.topic_governments);
									holder.txt.setText(objAllBean.getCategoryName().toString());
								}
							}
						}
					}
				}
			}
		}*/

		view.setBackgroundColor((currentPage == position) ? highlightColor : Color.TRANSPARENT);

//		if(Constant.POSITION==position)
//		{
//			holder.txt.setTextColor(Color.parseColor("#f59618"));
//		}
//		else
//		{
//			holder.txt.setTextColor(Color.parseColor("#646769"));
//		}
		return view;
		
	}

	public class ViewHolder {
		public TextView txt;
		ImageView img;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
		notifyDataSetChanged();
	}
}
