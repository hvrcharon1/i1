package com.getinfocia.infocia;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Created by evgenii on 3/8/18.
 */

public class LocalSettings {

    private static final String KEY_LOAD_IMAGES = "load_images";
    private SharedPreferences sharedPreferences;

    public LocalSettings(@NonNull Context context) {
        sharedPreferences = context.getSharedPreferences("infocia_settings", Context.MODE_PRIVATE);
    }

    public void saveImagesLoadingOption(boolean loadImages) {
        sharedPreferences.edit().putBoolean(KEY_LOAD_IMAGES, loadImages).apply();
    }

    public boolean isImagesLoadingEnabled() {
        return sharedPreferences.getBoolean(KEY_LOAD_IMAGES, true);
    }
}
