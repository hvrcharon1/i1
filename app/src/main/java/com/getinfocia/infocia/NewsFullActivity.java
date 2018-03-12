package com.getinfocia.infocia;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.getinfocia.infocia.adapter.NewsBinder;
import com.getinfocia.infocia.item.Travels;

import javax.inject.Inject;

/**
 * Created by evgenii on 3/12/18.
 */

/**
 * activity displays full content of the news with scroll
 */
public class NewsFullActivity extends AppCompatActivity {

    private static final String EXTRA_TRAVELS = "extra_travels";

    @Inject
    ImageLoader imageLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_news_common);
        InfociaApp.getInstance().getMainComponent().inject(this);
        ViewGroup descriptionContainer = findViewById(R.id.container_description);
        descriptionContainer.addView(LayoutInflater.from(this)
                .inflate(R.layout.view_full_news_descriptions, descriptionContainer, false));
        NewsBinder newsBinder = new NewsBinder(this,
                Typeface.createFromAsset(getAssets(), "RobotoLight.ttf"), imageLoader);
        newsBinder.bindNews((Travels) getIntent().getSerializableExtra(EXTRA_TRAVELS),
                getWindow().getDecorView());
    }

    public static Intent buildIntent(Context context, Travels travels) {
        Intent intent = new Intent(context, NewsFullActivity.class);
        intent.putExtra(EXTRA_TRAVELS, travels);
        return intent;
    }
}
