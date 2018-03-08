package com.getinfocia.infocia.dagger;

import android.content.Context;

import com.getinfocia.infocia.ImageLoader;
import com.getinfocia.infocia.LocalSettings;
import com.getinfocia.infocia.NoImagesLoader;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by evgenii on 3/8/18.
 */
@Module
public class MainModule {

    private Context context;

    public MainModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    LocalSettings provideLocalSettings() {
        return new LocalSettings(context);
    }

    @Provides
    @Singleton
    Picasso providePicasso() {
        return Picasso.with(context);
    }

    @Provides
    @Singleton
    ImageLoader provideImageLoader(Picasso picasso, LocalSettings localSettings) {
        return new NoImagesLoader(picasso, localSettings);
    }
}
