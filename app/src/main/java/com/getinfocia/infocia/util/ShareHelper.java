package com.getinfocia.infocia.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by evgenii on 3/22/18.
 */

public class ShareHelper {

    private static final String SCREENSHOT = "screenshot.jpg";
    private static final String TEMP_DIR = "/temp";
    private static final String FILE_PROVIDER_AUTHORITY = "com.getinfocia.infocia.fileprovider";
    private static final int IMAGE_MAX_SIZE = 1000;
    private Context context;

    public ShareHelper(Context context) {
        this.context = context;
    }

    private int getScaleFactor(int reqWidth, int reqHeight, int width, int height) {
        // Determine how much to scale down the image
        int inSampleSize = 1;
        while ((width / inSampleSize) >= reqWidth
                || (height / inSampleSize) >= reqHeight) {
            inSampleSize *= 2;
        }
        return inSampleSize;
    }

    public Bitmap getBitmapFromView(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public void saveBitmapToCacheFile(Bitmap bitmap) {
        final File file = new File(context.getCacheDir().getPath() + TEMP_DIR, SCREENSHOT);
        if (file.exists()) {
            file.delete();
        }
        file.getParentFile().mkdirs();
        FileOutputStream outputStream = null;
        try {
            file.createNewFile();
            int scaleFactor = getScaleFactor(IMAGE_MAX_SIZE, IMAGE_MAX_SIZE, bitmap.getWidth(),
                    bitmap.getHeight());
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,
                    bitmap.getWidth() / scaleFactor, bitmap.getHeight() /
                            scaleFactor,
                    true);
            if (bitmap != scaledBitmap) {
                bitmap.recycle();
            }
            outputStream = new FileOutputStream(file);
            scaledBitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    Log.e("IOException", "closing stream", e);
                }
            }
        }
    }

    public Uri getCacheFileUri() {
        final File file = new File(context.getCacheDir().getPath() + TEMP_DIR, SCREENSHOT);
        return FileProvider.getUriForFile(context, FILE_PROVIDER_AUTHORITY, file);
    }

    public Intent buildShareImageIntent(final Uri uri, final String message,
                                        final String text, final String shareTitle) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_SUBJECT, message);
        share.putExtra(Intent.EXTRA_TEXT, text);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        return Intent.createChooser(share, shareTitle);
    }
}
