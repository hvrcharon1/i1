package com.getinfocia.infocia.push;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.TextUtils;

import com.getinfocia.infocia.R;


/**
 *  
 */
public class NotificationUtils {

    

    private Context mContext;

    public NotificationUtils() {
    }

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

    public void showNotificationMessage(String title, String postdate,String source_title,String source_link, String imgurl, String desc, String videoid) {

        // Check for empty push message
        if (TextUtils.isEmpty(desc))
            return;

        
            // notification icon
            int icon = R.drawable.ic_notification;

            int smallIcon = R.drawable.ic_notification;

            int mNotificationId = AppConfig.NOTIFICATION_ID;

            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            mContext,
                            0,
                            new Intent(mContext,PushNews.class).putExtra("title", title)
                            .putExtra("date", postdate)
                            .putExtra("stitle", source_title)
                            .putExtra("slink", source_link)
                            .putExtra("image", imgurl)
                            .putExtra("desc", desc)
                            .putExtra("videoid", videoid),
                            PendingIntent.FLAG_CANCEL_CURRENT
                    );
            Bitmap bitmap = null;
			try {
				bitmap = new MyTask().execute(imgurl).get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    //        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
            NotificationCompat.BigPictureStyle style=new NotificationCompat.BigPictureStyle();
            style.bigPicture(bitmap);
            style.setBigContentTitle(title);
            style.setSummaryText(Html.fromHtml(desc).toString().replaceAll("'", "''"));
            
            
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    mContext);
            Notification notification = mBuilder.setSmallIcon(smallIcon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setStyle(style)
                    .setContentIntent(resultPendingIntent)
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                    .setContentText(Html.fromHtml(desc).toString().replaceAll("'", "''"))
                    .build();

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(mNotificationId, notification);

       
    }
    
    public Bitmap getBitmapFromURL(String strURL) {
	    try {
	        URL url = new URL(strURL);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.connect();
	        InputStream input = connection.getInputStream();
	        Bitmap myBitmap = BitmapFactory.decodeStream(input);
	        return myBitmap;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
    
    public class MyTask extends AsyncTask<String, Void, Bitmap>
    {
    	@Override
		protected Bitmap doInBackground(String... params) {
			// TODO Auto-generated method stub
			return getBitmapFromURL(params[0]);
		}
    }
   
}
