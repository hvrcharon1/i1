package com.getinfocia.infocia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.getinfocia.infocia.db.DatabaseHelper;
import com.getinfocia.infocia.util.Constant;
import com.getinfocia.infocia.util.JsonUtils;


public class MainActivity extends Activity {

	DatabaseHelper dbHelper;
	boolean is_new=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dbHelper=new DatabaseHelper(getApplicationContext());
		
		if (dbHelper.getAllNewsTableInfo().getCount() > 0)
		{
			if(JsonUtils.isNetworkAvailable(MainActivity.this))
			{
				is_new=true;
				int i = dbHelper.getNewsMaxColumnData();
				new GetNewsTask().execute(Constant.GET_LATEST_NEWS_NEW+i);
			}
			else
			{
				SetActivity();
			}
			 
		}
		else
		{
			if(JsonUtils.isNetworkAvailable(MainActivity.this))
			{
				new GetNewsTask().execute(Constant.GET_LATEST_NEWS);
			}
			else
			{
				Toast.makeText(MainActivity.this, getResources().getString(R.string.news_2),Toast.LENGTH_SHORT).show();
			}
		}


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	private class GetNewsTask extends AsyncTask<String, Void, String>
	{

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
					if(status.equals("1"))
					{
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
							Log.e("NEWS_IMAGE",""+objJson.getString(Constant.KEY_IMAGEURL));
							contentvalues.put(DatabaseHelper.KEY_SOURCETITLE, Html.fromHtml(objJson.getString(Constant.KEY_SOURCETITLE)).toString().replaceAll("'", "''"));
							contentvalues.put(DatabaseHelper.KEY_SOURCELINK, Html.fromHtml(objJson.getString(Constant.KEY_SOURCELINK)).toString().replaceAll("'", "''"));
							dbHelper.InsertData(DatabaseHelper.TABLE_NAME, contentvalues, null);
						}
						
						SetActivity();
					}
					else
					{
						if(is_new)
						{
							SetActivity();
						}
						else
						{
							Toast.makeText(MainActivity.this, getResources().getString(R.string.news_1),Toast.LENGTH_SHORT).show();	
						}
					}
				}
				catch (JSONException e) {
					e.printStackTrace();
				}
		}
	}


	public void SetActivity() {
		// TODO Auto-generated method stub

		Intent intmain=new Intent(MainActivity.this,FlipNewsActivity.class);
		startActivity(intmain);
		finish();
		 
	}

}
