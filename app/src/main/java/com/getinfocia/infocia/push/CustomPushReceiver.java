package com.getinfocia.infocia.push;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

/**
 * Created by Ravi on 01/06/15.
 */
public class CustomPushReceiver extends ParsePushBroadcastReceiver {
	private final String TAG = CustomPushReceiver.class.getSimpleName();

	private NotificationUtils notificationUtils;
	String title,postdate,source_title,source_link,imgurl,desc,videoid;
	public CustomPushReceiver() {
		super();
	}

	@Override
	protected void onPushReceive(Context context, Intent intent) {
		super.onPushReceive(context, intent);

		if (intent == null)
			return;

		try {
			JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

			parsePushJson(context, json);

		} catch (JSONException e) {
			Log.e(TAG, "Push message json exception: " + e.getMessage());
		}
	}

	@Override
	protected void onPushDismiss(Context context, Intent intent) {
		super.onPushDismiss(context, intent);
	}

	@Override
	protected void onPushOpen(Context context, Intent intent) {
		super.onPushOpen(context, intent);
	}

	/**
	 * Parses the push notification json
	 *
	 * @param context
	 * @param json
	 */
	private void parsePushJson(Context context, JSONObject json) {
		try {
			JSONArray jsonArray = json.getJSONArray("data");
			JSONObject data = null;
			
			for (int i = 0; i < jsonArray.length(); i++) {
				data = jsonArray.getJSONObject(i);
				title = data.getString("title");
				postdate = data.getString("postdate");
				source_title = data.getString("source_title");
				source_link = data.getString("source_link");
				imgurl = data.getString("imgurl");
				desc = data.getString("description");
				videoid = data.getString("video_id");
			}
			showNotificationMessage(context, title, postdate,source_title,source_link,imgurl,desc,videoid);


		} catch (JSONException e) {
			Log.e(TAG, "Push message json exception: " + e.getMessage());
		}
	}


	/**
	 * Shows the notification message in the notification bar
	 * If the app is in background, launches the app
	 *
	 * @param context
	 * @param title
	 * @param message
	 * @param videoid 
	 * @param desc 
	 * @param imgurl 
	 * @param intent
	 */
	private void showNotificationMessage(Context context, String title, String postdate,String source_title,String source_link, String imgurl, String desc, String videoid) {

		notificationUtils = new NotificationUtils(context);
		notificationUtils.showNotificationMessage(title, postdate,source_title,source_link,imgurl,desc,videoid);
	}
}