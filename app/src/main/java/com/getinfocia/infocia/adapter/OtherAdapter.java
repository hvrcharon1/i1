package com.getinfocia.infocia.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.getinfocia.infocia.R;
import com.getinfocia.infocia.item.ItemOther;


public class OtherAdapter  extends ArrayAdapter<ItemOther> {
	
	private Activity activity;
	private List<ItemOther> itemsAllphotos;
	private ItemOther objAllBean;
	private int row;
	 
	
	public OtherAdapter(Activity act, int resource, List<ItemOther> arrayList) {
		super(act, resource, arrayList);
		this.activity = act;
		this.row = resource;
		this.itemsAllphotos = arrayList;
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

		objAllBean = itemsAllphotos.get(position);
		holder.txt=(TextView)view.findViewById(R.id.txt_other);
		holder.img=(ImageView)view.findViewById(R.id.img_other);
		
		holder.txt.setText(objAllBean.getName().toString());
		holder.img.setImageResource(objAllBean.getImgRes());
 
		return view;
		
	}

	public class ViewHolder {
		public TextView txt;
		public ImageView img;
	}
}
