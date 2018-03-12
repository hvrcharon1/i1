package com.getinfocia.infocia.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.aphidmobile.utils.UI;
import com.getinfocia.infocia.ImageLoader;
import com.getinfocia.infocia.R;
import com.getinfocia.infocia.WebNews;
import com.getinfocia.infocia.item.Travels;
import com.getinfocia.infocia.util.JsonUtils;
import com.getinfocia.infocia.yt.YoutubePlay;

/**
 * Created by evgenii on 3/12/18.
 */

/**
 * common binder for news
 */
public class NewsBinder {

    private Context context;

    private Typeface font;

    private ImageLoader imageLoader;

    public NewsBinder(@NonNull Context context, @NonNull Typeface font, @NonNull ImageLoader imageLoader) {
        this.context = context;
        this.font = font;
        this.imageLoader = imageLoader;
    }

    public void bindNews(final Travels data, View layout) {
        UI
                .<TextView>findViewById(layout, R.id.barrel_postedTime)
                .setText(String.format(context.getString(R.string.posted), Html.fromHtml(data.postdate)));

        UI
                .<TextView>findViewById(layout, R.id.barrel_writtenByActual)
                .setText(Html.fromHtml(data.source_title));

        ImageView photoView = UI.findViewById(layout, R.id.photo);
        ImageView youtube = UI.findViewById(layout, R.id.video_link);
        ImageView share = UI.findViewById(layout, R.id.goto_link);
        TextView more = UI.findViewById(layout, R.id.view_more);
        TextView title = (TextView) UI.findViewById(layout, R.id.title);
        TextView description = (TextView) UI.findViewById(layout, R.id.description);
        description.setTypeface(font);
        description.setText(Html.fromHtml(data.description));
        title.setTypeface(font);
        title.setText(data.title);

        photoView.setScaleType(ImageView.ScaleType.FIT_XY);

        imageLoader.loadImage(data.imgurl.replace(" ", "%20"), photoView,
                R.drawable.screen_loading);


        if (data.video_id.equals("") || data.video_id.equals("null")) {
            youtube.setVisibility(View.GONE);
        } else {
            youtube.setVisibility(View.VISIBLE);
        }

        youtube.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent yt = new Intent(context, YoutubePlay.class);
                yt.putExtra("id", JsonUtils.getVideoId(data.video_id));
                context.startActivity(yt);
            }
        });


        share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String shareText = (new StringBuilder(String.valueOf("-For more news download short news App https://play.google.com/store/apps/details?id=" + context.getPackageName()))).append("\n\n").append(data.title.toString()).append("\n").append(data.source_link.toString()).toString();
                    /*String path=SaveBackground(homeLayout);
                    File imagepath=new File(path);
					Intent share = new Intent(Intent.ACTION_SEND);
					share.setType("image/png");
					share.putExtra(Intent.EXTRA_TEXT, shareText);
					share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imagepath));
					startActivity(Intent.createChooser(share, "Share Image"));*/

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                //sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareText);
                context.startActivity(Intent.createChooser(sharingIntent, "Share"));
            }
        });


        more.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intmore = new Intent(context, WebNews.class);
                intmore.putExtra("link", data.source_link);
                context.startActivity(intmore);
            }
        });
    }
}
