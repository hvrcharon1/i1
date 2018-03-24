package com.getinfocia.infocia.push;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.getinfocia.infocia.ImageLoader;
import com.getinfocia.infocia.InfociaApp;
import com.getinfocia.infocia.R;
import com.getinfocia.infocia.WebNews;
import com.getinfocia.infocia.yt.YoutubePlay;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;


public class PushNews extends Activity {


    ImageView imgPhotoview, imgshare, imgYt;
    TextView txtTitle, txtDesc, txtPostdate, txtsource, txtmore;
    String Title, Desc, Image, Date, SourceTitle, SLink, VideoId;
    StringBuilder sb;
    View homeLayout;
    @Inject
    ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        InfociaApp.getInstance().getMainComponent().inject(this);
        setContentView(R.layout.view_news_common);
        ViewGroup descriptionContainer = findViewById(R.id.container_description);
        descriptionContainer.addView(LayoutInflater.from(this)
                .inflate(R.layout.view_full_news_descriptions, descriptionContainer, false));
        sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().toString()).append(File.separator).append(getString(R.string.app_name));
        imgPhotoview = (ImageView) findViewById(R.id.photo);
        imgshare = (ImageView) findViewById(R.id.goto_link);
        imgYt = (ImageView) findViewById(R.id.video_link);
        homeLayout = findViewById(R.id.barrel_layout);
        txtTitle = (TextView) findViewById(R.id.title);
        txtDesc = (TextView) findViewById(R.id.description);
        txtPostdate = (TextView) findViewById(R.id.barrel_postedTime);
        txtsource = (TextView) findViewById(R.id.barrel_writtenByActual);
        txtmore = (TextView) findViewById(R.id.view_more);

        Intent I = getIntent();

        Title = I.getStringExtra("title");
        Desc = I.getStringExtra("desc");
        Image = I.getStringExtra("image");
        Date = I.getStringExtra("date");
        SourceTitle = I.getStringExtra("stitle");
        SLink = I.getStringExtra("slink");
        VideoId = I.getStringExtra("videoid");


        txtTitle.setText(Title.replaceAll("’", "’’"));
        txtDesc.setText(Html.fromHtml(Desc).toString().replaceAll("'", "''"));
        txtPostdate.setText(String.format(getString(R.string.posted), Date));
        txtsource.setText(SourceTitle);

        imageLoader.loadImage(Image, imgPhotoview);

        if (VideoId.equals("") || VideoId.equals("null")) {
            imgYt.setVisibility(View.GONE);
        } else {
            imgYt.setVisibility(View.VISIBLE);
        }

        imgYt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent yt = new Intent(PushNews.this, YoutubePlay.class);
                yt.putExtra("id", VideoId);
                startActivity(yt);
            }
        });

        txtmore.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intmore = new Intent(PushNews.this, WebNews.class);
                intmore.putExtra("link", SLink);
                startActivity(intmore);
            }
        });

        imgshare.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String shareText = (new StringBuilder(String.valueOf("-For more news download news App https://play.google.com/store/apps/details?id=in.co.newso.hindi".toString()))).append("\n\n").append(Title).append("\n").append(SLink).toString();
                String path = SaveBackground(homeLayout);
                File imagepath = new File(path);
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/png");
                share.putExtra(Intent.EXTRA_TEXT, shareText);
                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imagepath));
                startActivity(Intent.createChooser(share, "Share Image"));
            }
        });
    }

    public String SaveBackground(View panelResult1) {
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
        s = (new StringBuilder(String.valueOf("sort"))).append("_news_").append(System.currentTimeMillis()).append(".png").toString();
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

}
