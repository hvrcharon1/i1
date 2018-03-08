package com.getinfocia.infocia;

import android.widget.ImageView;

import com.getinfocia.infocia.util.Constant;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

/**
 * Created by evgenii on 3/8/18.
 */

public class NoImagesLoader implements ImageLoader {

    private Picasso picasso;

    private LocalSettings localSettings;

    public NoImagesLoader(Picasso picasso, LocalSettings localSettings) {
        this.picasso = picasso;
        this.localSettings = localSettings;
    }

    @Override
    public void loadImage(String imageUrl, ImageView imageView) {
        loadImage(imageUrl, imageView, -1);
    }

    @Override
    public void loadImage(String imageUrl, ImageView imageView, int placeHolder) {
        if (localSettings.isImagesLoadingEnabled()) {
            RequestCreator requestCreator = picasso.load(Constant.IMAGE_PATH + imageUrl);
            if (placeHolder > 0) {
                requestCreator.placeholder(placeHolder);
            }
            requestCreator.into(imageView);
        } else {
            picasso.load(R.drawable.image_off).fit().into(imageView);
        }
    }
}
