package com.getinfocia.infocia;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

/**
 * Created by evgenii on 3/8/18.
 */

public interface ImageLoader {

    void loadImageGuaranteed(String imageUrl, ImageView imageView);

    void loadImage(String imageUrl, ImageView imageView);

    void loadImage(String imageUrl, ImageView imageView, @DrawableRes int placeHolder);
}
