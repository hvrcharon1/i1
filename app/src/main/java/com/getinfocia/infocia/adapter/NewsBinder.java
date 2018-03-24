package com.getinfocia.infocia.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.getinfocia.infocia.util.ShareHelper;
import com.getinfocia.infocia.yt.YoutubePlay;

/**
 * Created by evgenii on 3/12/18.
 */

/**
 * common binder for news
 */
public class NewsBinder {

    private Context context;

    private ImageLoader imageLoader;

    private ShareHelper shareHelper;

    public NewsBinder(@NonNull Context context, @NonNull ImageLoader imageLoader,
                      @NonNull ShareHelper shareHelper) {
        this.context = context;
        this.imageLoader = imageLoader;
        this.shareHelper = shareHelper;
    }

    public void bindNews(final Travels data, final View layout) {
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
        description.setText(Html.fromHtml(data.description));
        title.setText(data.title.replaceAll("’", "’’"));

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
                final String shareText = new StringBuilder(context.getString(R.string.sharing_text))
                        .append(context.getPackageName())
                        .append("\n\n")
                        .append(data.title.toString())
                        .append("\n")
                        .append(data.source_link.toString())
                        .toString();
                final Bitmap shareBitmap = shareHelper.getBitmapFromView(
                        ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        shareHelper.saveBitmapToCacheFile(shareBitmap);
                        Intent intent = shareHelper.buildShareImageIntent(shareHelper.getCacheFileUri(),
                                null, shareText, context.getString(R.string.shate_title));
                        context.startActivity(intent);
                    }
                }).start();
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
