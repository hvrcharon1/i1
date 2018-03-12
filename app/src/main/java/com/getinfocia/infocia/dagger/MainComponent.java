package com.getinfocia.infocia.dagger;

import com.getinfocia.infocia.FlipNewsActivity;
import com.getinfocia.infocia.NewsFullActivity;
import com.getinfocia.infocia.push.PushNews;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by evgenii on 3/8/18.
 */

@Singleton
@Component(modules = {MainModule.class})
public interface MainComponent {
    void inject(FlipNewsActivity activity);

    void inject(PushNews activity);

    void inject(NewsFullActivity activity);
}
