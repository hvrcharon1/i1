package com.getinfocia.infocia;

import android.app.Application;

import com.getinfocia.infocia.dagger.DaggerMainComponent;
import com.getinfocia.infocia.dagger.MainComponent;
import com.getinfocia.infocia.dagger.MainModule;
import com.getinfocia.infocia.push.ParseUtils;

/**
 * Created by evgenii on 3/8/18.
 */

public class InfociaApp extends Application {

    private static InfociaApp instance;

    private MainComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // initialize dependencies module
        component =
                DaggerMainComponent
                        .builder()
                        .mainModule(new MainModule(this))
                        .build();
        // register with parse
        ParseUtils.registerParse(this);
    }

    public static InfociaApp getInstance() {
        return instance;
    }

    public MainComponent getMainComponent() {
        return component;
    }
}
