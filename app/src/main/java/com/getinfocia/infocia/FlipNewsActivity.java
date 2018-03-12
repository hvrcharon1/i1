package com.getinfocia.infocia;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getinfocia.infocia.adapter.NewsBinder;
import com.getinfocia.infocia.db.DatabaseHelper;
import com.getinfocia.infocia.item.ItemCategory;
import com.getinfocia.infocia.item.Travels;
import com.getinfocia.infocia.util.Constant;
import com.getinfocia.infocia.util.JsonUtils;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

public class FlipNewsActivity extends Activity {

    DatabaseHelper dbHelper;
    private CardStackView flipView;
    MyBaseAdapter newsadapter;
    StringBuilder sb;
    ImageView mainGoToFirst, mainRefresh, mainCategory;
    ArrayList<ItemCategory> arrayOfAllcategory;
    //GridView lsv_category;
    RecyclerView recyclerView;
    //CategoryAdapter adapter;
    DataAdapter adapter;
    ItemCategory objBean;
    String selected_category;
    private AdView mAdView;
    InterstitialAd interstitial;
    String imageUrl;
    Typeface font;
    RelativeLayout relativeLayoutHeader, header;
    TextView textView1;
    boolean isDrawerOpened = false;
    @Inject
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flipview_hindi_layout);
        InfociaApp.getInstance().getMainComponent().inject(this);
        //mAdView = (AdView) findViewById(R.id.adView);
        //mAdView.loadAd(new AdRequest.Builder().build());
        sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().toString()).append(File.separator).append(getString(R.string.app_name));
        dbHelper = new DatabaseHelper(getApplicationContext());
        //lsv_category=(GridView)findViewById(R.id.lsv);
        relativeLayoutHeader = (RelativeLayout) findViewById(R.id.relativeLayoutHeader);
        header = (RelativeLayout) findViewById(R.id.header);
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        textView1 = (TextView) findViewById(R.id.textView1);

        flipView = (CardStackView) findViewById(R.id.flipView);
        mainGoToFirst = (ImageView) findViewById(R.id.main_GoToFirst);
        mainRefresh = (ImageView) findViewById(R.id.main_Refresh);
        mainCategory = (ImageView) findViewById(R.id.language_change);
        Travels.IMG_DESCRIPTIONS.clear();
        arrayOfAllcategory = new ArrayList<ItemCategory>();
        final Bundle i = getIntent().getExtras();
        if (i == null) {
            showData("all");
            selected_category = "0";
        } else {
            showData(i.getString("selected_category"));
            selected_category = i.getString("selected_category");
        }
        font = Typeface.createFromAsset(getAssets(), "RobotoLight.ttf");
        newsadapter = new MyBaseAdapter(this);
        flipView.setAdapter(newsadapter);

        textView1.setTypeface(font);

        final RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(500);

        //		final int guid = dbHelper.getAllNewsTableInfo().getCount();

        flipView.setCardEventListener(new CardStackView.CardEventListener() {
            @Override
            public void onCardDragging(float percentX, float percentY) {

            }

            @Override
            public void onCardSwiped(SwipeDirection direction) {
                recyclerView.setVisibility(View.GONE);
                relativeLayoutHeader.setVisibility(View.GONE);
                //InterstitialAdsDisplay(position);

				/*if(position == 10 || position == 20 && JsonUtils.isNetworkAvailable(FlipNewsActivity.this))
                {
					int guid = dbHelper.getNews_1stDataGuid();
					new GetOldNewsTask().execute(Constant.GET_OLD_NEWS+guid);
				}*/

                if (JsonUtils.isNetworkAvailable(FlipNewsActivity.this)) {
                    int guid = dbHelper.getNews_1stDataGuid();
                    Log.e("guid", "" + guid);
                    new GetOldNewsTask().execute(Constant.GET_OLD_NEWS + guid);
                }
            }

            @Override
            public void onCardReversed() {

            }

            @Override
            public void onCardMovedToOrigin() {

            }

            @Override
            public void onCardClicked(int index) {
                openNewsFull(Travels.IMG_DESCRIPTIONS.get(index));
            }
        });

		/*flipView.setOnViewFlipListener(new ViewFlipListener() {

			@Override
			public void onViewFlipped(View view, int position) {
				// TODO Auto-generated method stub
				recyclerView.setVisibility(View.GONE);
				relativeLayoutHeader.setVisibility(View.GONE);
				//InterstitialAdsDisplay(position);

				/*if(position == 10 || position == 20 && JsonUtils.isNetworkAvailable(FlipNewsActivity.this))
				{
					int guid = dbHelper.getNews_1stDataGuid();
					new GetOldNewsTask().execute(Constant.GET_OLD_NEWS+guid);
				}

				if (JsonUtils.isNetworkAvailable(FlipNewsActivity.this))
				{
					int guid = dbHelper.getNews_1stDataGuid();
					Log.e("guid",""+guid);
					new GetOldNewsTask().execute(Constant.GET_OLD_NEWS+guid);
				}

			}
		});

*/

        mainGoToFirst.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                startActivity(getIntent());
                finish();
            }
        });


        mainRefresh.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if (JsonUtils.isNetworkAvailable(FlipNewsActivity.this)) {
                    mainRefresh.startAnimation(anim);
                    int i = dbHelper.getNewsMaxColumnData();
                    new GetNewsTask().execute(Constant.GET_LATEST_NEWS_NEW + i);
                } else {
                    mainRefresh.clearAnimation();
                    Toast.makeText(FlipNewsActivity.this, getResources().getString(R.string.news_2), Toast.LENGTH_SHORT).show();
                }

            }
        });


        mainCategory.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                recyclerView.setVisibility(View.VISIBLE);
                relativeLayoutHeader.setVisibility(View.VISIBLE);
                //mAdView.setVisibility(View.INVISIBLE);
                isDrawerOpened = true;

                recyclerView.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        recyclerView.setVisibility(View.GONE);
                        relativeLayoutHeader.setVisibility(View.GONE);
                        //mAdView.setVisibility(View.VISIBLE);
                        isDrawerOpened = false;
                    }
                }, 13000);
            }
        });

        header.findViewById(R.id.img_setting).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intsetting = new Intent(FlipNewsActivity.this, OtherActivty.class);
                startActivity(intsetting);
            }
        });





		/*lsv_category.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub


				if(arg2-lsv_category.getHeaderViewsCount()==0)
				{
					Intent i=new Intent(FlipNewsActivity.this,FlipNewsActivity.class);
					startActivity(i);
					finish();
				}
				else
				{
					objBean=arrayOfAllcategory.get(arg2-lsv_category.getHeaderViewsCount());
					startActivity(getIntent().putExtra("selected_category", ""+objBean.getCategoryId()));
					finish();
				}


			}
		});*/


        if (JsonUtils.isNetworkAvailable(FlipNewsActivity.this)) {
            ItemCategory recentAlbum = new ItemCategory(0, "All News", "");//mkmkmkmk
            arrayOfAllcategory.add(0, recentAlbum);
            new GetCategory().execute(Constant.GET_CATEGORY);
        } else {
            if (dbHelper.getCategoryTableInfo().getCount() > 0) {
                getCategoryWhenOffline();
                adapter = new DataAdapter(FlipNewsActivity.this, arrayOfAllcategory);
                recyclerView.setAdapter(adapter);
                adapter.setCurrentPage(Integer.parseInt(selected_category));
                /*adapter=new CategoryAdapter(FlipNewsActivity.this, R.layout.category_lsv_item, arrayOfAllcategory);
                lsv_category.setAdapter(adapter);
				adapter.setCurrentPage(Integer.parseInt(selected_category));*/

            } else {
                Toast.makeText(FlipNewsActivity.this, getResources().getString(R.string.news_2), Toast.LENGTH_SHORT).show();
            }

        }


    }

    private void openNewsFull(Travels travels) {
        startActivity(NewsFullActivity.buildIntent(this, travels));
    }


    private void showData(String Name) {
        Cursor cursor = dbHelper.getAllNewsByCategory(Name);
        do {
            if (!cursor.moveToNext()) {
                cursor.close();
                return;
            }

            Travels.IMG_DESCRIPTIONS.add(new Travels(cursor.getInt(0), cursor.getInt(7), cursor.getString(1), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(8), cursor.getString(9), cursor.getString(6)));

        } while (true);
    }

    private class MyBaseAdapter extends BaseAdapter {

        private Context context;
        private LayoutInflater inflater;
        RelativeLayout homeLayout;
        private NewsBinder newsBinder;

        private MyBaseAdapter(Context context) {
            inflater = LayoutInflater.from(context);
            this.context = context;
            newsBinder = new NewsBinder(context, font, imageLoader);
        }

        @Override
        public int getCount() {
            return Travels.IMG_DESCRIPTIONS.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View layout = convertView;
            if (convertView == null) {
                layout = inflater.inflate(R.layout.view_news_common, parent, false);
                ViewGroup descriptionContainer = layout.findViewById(R.id.container_description);
                descriptionContainer.addView(LayoutInflater.from(context)
                        .inflate(R.layout.view_card_news_descriptions, descriptionContainer, false));
            }
            //added ellipsize in the end
            final TextView tvDesc = layout.findViewById(R.id.description);
            tvDesc.getViewTreeObserver()
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            tvDesc.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            int noOfLinesVisible = tvDesc.getHeight() / tvDesc.getLineHeight();
                            tvDesc.setMaxLines(noOfLinesVisible);
                            tvDesc.setEllipsize(TextUtils.TruncateAt.END);

                        }
                    });
            final Travels data = Travels.IMG_DESCRIPTIONS.get(position);
            newsBinder.bindNews(data, layout);
            return layout;
        }
    }

    public String SaveBackground(RelativeLayout panelResult1) {
        View panelResult = panelResult1.getRootView();
        Bitmap bitmap;
        panelResult.invalidate();
        panelResult.setDrawingCacheEnabled(true);
        panelResult.buildDrawingCache();
        bitmap = Bitmap.createBitmap(panelResult.getDrawingCache());
        panelResult.setDrawingCacheEnabled(false);
        String s = null;
        File file;
        file = new File(sb.toString());
        s = null;
        file.mkdir();
        FileOutputStream fileoutputstream1 = null;
        s = (new StringBuilder(String.valueOf("short"))).append("_news_").append(System.currentTimeMillis()).append(".png").toString();
        try {
            fileoutputstream1 = new FileOutputStream(new File(file, s));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        FileOutputStream fileoutputstream = fileoutputstream1;

        StringBuilder stringbuilder1;
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileoutputstream);
        stringbuilder1 = new StringBuilder();
        stringbuilder1.append(sb.toString()).append(File.separator).append(s);

        try {
            fileoutputstream.flush();
            fileoutputstream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "" + stringbuilder1;

    }

    private class GetNewsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            return JsonUtils.getJSONString(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


            try {
                JSONObject mainJson = new JSONObject(result);
                String status = mainJson.getString("success");
                if (status.equals("1")) {
                    JSONArray jsonArray = mainJson.getJSONArray(Constant.KEY_ARRAY);
                    JSONObject objJson = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);
                        ContentValues contentvalues = new ContentValues();
                        contentvalues.put(DatabaseHelper.KEY_GUIDE, Integer.valueOf(objJson.getInt(Constant.KEY_GUIDE)));
                        contentvalues.put(DatabaseHelper.KEY_TITLE, Html.fromHtml(objJson.getString(Constant.KEY_TITLE)).toString().replaceAll("'", "''"));
                        contentvalues.put(DatabaseHelper.KEY_CATEGORY, Html.fromHtml(objJson.getString(Constant.KEY_CATEGORY)).toString().replaceAll("'", "''"));
                        contentvalues.put(DatabaseHelper.KEY_DESCRIPTION, Html.fromHtml(objJson.getString(Constant.KEY_DESCRIPTION)).toString().replaceAll("'", "''"));
                        contentvalues.put(DatabaseHelper.KEY_POSTDATE, Html.fromHtml(objJson.getString(Constant.KEY_POSTDATE)).toString().replaceAll("'", "''"));
                        contentvalues.put(DatabaseHelper.KEY_IMAGEURL, Html.fromHtml(objJson.getString(Constant.KEY_IMAGEURL)).toString().replaceAll("'", "''"));
                        contentvalues.put(DatabaseHelper.KEY_VIDEOURL, Html.fromHtml(objJson.getString(Constant.KEY_VIDEOURL)).toString().replaceAll("'", "''"));
                        contentvalues.put(DatabaseHelper.KEY_READED, Integer.valueOf(0));
                        contentvalues.put(DatabaseHelper.KEY_SOURCETITLE, Html.fromHtml(objJson.getString(Constant.KEY_SOURCETITLE)).toString().replaceAll("'", "''"));
                        contentvalues.put(DatabaseHelper.KEY_SOURCELINK, Html.fromHtml(objJson.getString(Constant.KEY_SOURCELINK)).toString().replaceAll("'", "''"));
                        dbHelper.InsertData(DatabaseHelper.TABLE_NAME, contentvalues, null);
                    }
                    SetActivity();

                } else {
                    mainRefresh.clearAnimation();
                    //Toast.makeText(FlipNewsActivity.this, getResources().getString(R.string.news_1),Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void SetActivity() {
        // TODO Auto-generated method stub
        mainRefresh.clearAnimation();
        startActivity(getIntent());
        finish();
    }

    private class GetCategory extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            return JsonUtils.getJSONString(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            Log.e("CAT_Response", "" + result);
            if (null == result || result.length() == 0) {


            } else {

                try {
                    JSONObject mainJson = new JSONObject(result);
                    JSONArray jsonArray = mainJson.getJSONArray(Constant.CATEGORY_ARRAY_NAME);
                    JSONObject objJson = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);

                        ItemCategory objItem = new ItemCategory();
                        objItem.setCategoryName(objJson.getString(Constant.CATEGORY_NAME));
                        objItem.setCategoryId(objJson.getInt(Constant.CATEGORY_CID));
                        String image = objJson.getString(Constant.CATEGORY_CIMAGE);
                        objItem.setCategoryImageurl(imageUrl + image);

                        Log.e("CAT_NAME", "" + objJson.getString(Constant.CATEGORY_NAME));
                        Log.e("CAT_ID", "" + objJson.getString(Constant.CATEGORY_CID));
                        Log.e("CAT_IMAGE", "" + objJson.getString(Constant.CATEGORY_CIMAGE));
                        arrayOfAllcategory.add(objItem);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setAdapterListview();
            }
        }
    }

    public void setAdapterListview() {
        // TODO Auto-generated method stub
        /*adapter=new CategoryAdapter(FlipNewsActivity.this, R.layout.category_lsv_item, arrayOfAllcategory);
        lsv_category.setAdapter(adapter);
		adapter.setCurrentPage(Integer.parseInt(selected_category));*/
        adapter = new DataAdapter(getApplicationContext(), arrayOfAllcategory);
        recyclerView.setAdapter(adapter);
        adapter.setCurrentPage(Integer.parseInt(selected_category));
        EnterCategory();
    }

    public void EnterCategory() {
        Log.e("CAT_DELETED1", "" + dbHelper.getCategoryTableInfo().getCount());
        if (dbHelper.getCategoryTableInfo().getCount() > 0) {
            dbHelper.deleteCategory();
            Log.e("CAT_DELETED", "" + dbHelper.getCategoryTableInfo().getCount());
            Log.e("CAT_DELETED", "" + DatabaseHelper.TABLE_CATEGORY_NAME);
        }
        for (int i = 0; i < arrayOfAllcategory.size(); i++) {
            ContentValues contentvalues = new ContentValues();
            contentvalues.put(DatabaseHelper.KEY_CATEGORY_ID, arrayOfAllcategory.get(i).getCategoryId());
            contentvalues.put(DatabaseHelper.KEY_CATEGORY_NAME, Html.fromHtml(arrayOfAllcategory.get(i).getCategoryName()).toString().replaceAll("'", "''"));
            contentvalues.put(DatabaseHelper.KEY_CATEGORY_IMAGE, Html.fromHtml(arrayOfAllcategory.get(i).getCategoryImageurl()).toString().replaceAll("'", "''"));
            dbHelper.InsertData(DatabaseHelper.TABLE_CATEGORY_NAME, contentvalues, null);
            Log.e("CAT_INSERTED", "" + DatabaseHelper.TABLE_CATEGORY_NAME);
        }
    }

    public void getCategoryWhenOffline() {
        Cursor cursor = dbHelper.getAllCategory();
        do {
            if (!cursor.moveToNext()) {
                cursor.close();
                return;
            }
            arrayOfAllcategory.add(new ItemCategory(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));//hhbkhbkj
            Log.e("CAT_GET", "" + new ItemCategory(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
        } while (true);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        //flipView.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //flipView.onResume();
    }

    public void InterstitialAdsDisplay(int news) {
        if (news % Integer.parseInt(getString(R.string.number_of_news_ad)) == 0) {
            interstitial = new InterstitialAd(this);
            interstitial.setAdUnitId(getString(R.string.admob_publisher_interstitial_id));
            interstitial.loadAd(new AdRequest.Builder().build());
            interstitial.show();
            if (!interstitial.isLoaded()) {
                AdRequest adRequest1 = new AdRequest.Builder()
                        .build();
                // Begin loading your interstitial.
                interstitial.loadAd(adRequest1);
            }
            interstitial.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    interstitial.show();
                }
            });
        }
    }

    private class GetOldNewsTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            return JsonUtils.getJSONString(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);


            try {
                JSONObject mainJson = new JSONObject(result);
                String status = mainJson.getString("success");
                if (status.equals("1")) {
                    JSONArray jsonArray = mainJson.getJSONArray(Constant.KEY_ARRAY);
                    JSONObject objJson = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);
                        ContentValues contentvalues = new ContentValues();
                        contentvalues.put(DatabaseHelper.KEY_GUIDE, Integer.valueOf(objJson.getInt(Constant.KEY_GUIDE)));
                        contentvalues.put(DatabaseHelper.KEY_TITLE, Html.fromHtml(objJson.getString(Constant.KEY_TITLE)).toString().replaceAll("'", "''"));
                        contentvalues.put(DatabaseHelper.KEY_CATEGORY, Html.fromHtml(objJson.getString(Constant.KEY_CATEGORY)).toString().replaceAll("'", "''"));
                        contentvalues.put(DatabaseHelper.KEY_DESCRIPTION, Html.fromHtml(objJson.getString(Constant.KEY_DESCRIPTION)).toString().replaceAll("'", "''"));
                        contentvalues.put(DatabaseHelper.KEY_POSTDATE, Html.fromHtml(objJson.getString(Constant.KEY_POSTDATE)).toString().replaceAll("'", "''"));
                        contentvalues.put(DatabaseHelper.KEY_IMAGEURL, Html.fromHtml(objJson.getString(Constant.KEY_IMAGEURL)).toString().replaceAll("'", "''"));
                        contentvalues.put(DatabaseHelper.KEY_VIDEOURL, Html.fromHtml(objJson.getString(Constant.KEY_VIDEOURL)).toString().replaceAll("'", "''"));
                        contentvalues.put(DatabaseHelper.KEY_READED, Integer.valueOf(0));
                        contentvalues.put(DatabaseHelper.KEY_SOURCETITLE, Html.fromHtml(objJson.getString(Constant.KEY_SOURCETITLE)).toString().replaceAll("'", "''"));
                        contentvalues.put(DatabaseHelper.KEY_SOURCELINK, Html.fromHtml(objJson.getString(Constant.KEY_SOURCELINK)).toString().replaceAll("'", "''"));
                        dbHelper.InsertData(DatabaseHelper.TABLE_NAME, contentvalues, null);
                    }
                } else {
                    //Toast.makeText(FlipNewsActivity.this, getResources().getString(R.string.news_3),Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();

            }
        }
    }

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

            if (android.get(position).getCategoryName().toString().equalsIgnoreCase("All News")) {
                Picasso.with(context).load(R.drawable.topic_all_news).into(viewHolder.img);
            } else {
                if (android.get(position).getCategoryImageurl().contains("null")) {
                    String str = android.get(position).getCategoryImageurl();
                    imageUrl = android.get(position).getCategoryImageurl().substring(4, str.length()).replace(" ", "%20");
                    Log.e("CAT_IMAGE_ADAPTER True", "" + imageUrl);
                } else {
                    imageUrl = android.get(position).getCategoryImageurl().replace(" ", "%20");
                    Log.e("CAT_IMAGE_ADAPTER false", "" + imageUrl);
                }
                imageLoader.loadImageGuaranteed(imageUrl, viewHolder.img);
            }
            final int pos = position;
            viewHolder.layoutItems.setBackgroundColor((currentPage == position) ? highlightColor : Color.TRANSPARENT);

            viewHolder.layoutItems.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (pos == 0) {
                        Intent i = new Intent(FlipNewsActivity.this, FlipNewsActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        //objBean=arrayOfAllcategory.get(pos-lsv_category.getHeaderViewsCount());
                        startActivity(getIntent().putExtra("selected_category", "" + android.get(pos).getCategoryId()));
                        finish();
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return android.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            public TextView txt;
            ImageView img;
            RelativeLayout layoutItems;

            public ViewHolder(View view) {
                super(view);

                txt = (TextView) itemView.findViewById(R.id.txt_allvideos_categty);
                img = (ImageView) itemView.findViewById(R.id.itemsCatImage);
                layoutItems = (RelativeLayout) itemView.findViewById(R.id.layoutItems);

                font = Typeface.createFromAsset(context.getAssets(), "RobotoLight.ttf");
                txt.setTypeface(font);
            }
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
            notifyDataSetChanged();
        }


    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        // super.onBackPressed();

        if (isDrawerOpened) {
            recyclerView.setVisibility(View.GONE);
            relativeLayoutHeader.setVisibility(View.GONE);
            //mAdView.setVisibility(View.VISIBLE);
            isDrawerOpened = false;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    FlipNewsActivity.this);
            builder.setTitle("Exit infocia ?");
            builder.setMessage("Do you want to exit press exit. Review our application by pressing review.\nThanks.");
            builder.setPositiveButton("Review",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                            arg0.dismiss();

                            final Uri uri = Uri.parse("market://details?id="
                                    + getApplicationContext().getPackageName());
                            final Intent rateAppIntent = new Intent(
                                    Intent.ACTION_VIEW, uri);

                            if (getPackageManager().queryIntentActivities(
                                    rateAppIntent, 0).size() > 0) {
                                startActivity(rateAppIntent);
                            } else {
                            /*
                             * handle your error case: the device has no way to
							 * handle market urls
							 */
                            }
                        }
                    });
            builder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub
                            arg0.dismiss();

                        }
                    });
            builder.setNeutralButton("Exit", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // TODO Auto-generated method stub
                    arg0.dismiss();
                    finish();
                }
            });

            builder.create().show();
        }
    }


}
