package com.unicorn.qingkee.custom;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;


import com.unicorn.qingkee.util.ToastUtils;

import java.io.File;
import java.util.UUID;


public class ImageUtil {

    public static Uri getRandomPhotoUri() {

        File mediaStorageDir = null;
        try {
            mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Qingke");
        } catch (Exception e) {
            ToastUtils.show("手机没有SD卡");
        }
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }

        String filePath = mediaStorageDir.getAbsolutePath() + File.separator + UUID.randomUUID().toString() + ".jpg";
        return Uri.fromFile(new File(filePath));
    }

    public static BitmapFactory.Options getFactoryOptions(final int width, final int height, final String imagePath) {

        BitmapFactory.Options factoryOptions = new BitmapFactory.Options();

        factoryOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, factoryOptions);

        final int imageWidth = factoryOptions.outWidth;
        final int imageHeight = factoryOptions.outHeight;

        int scaleFactor = Math.min(imageWidth / width, imageHeight / height);

        factoryOptions.inJustDecodeBounds = false;
        factoryOptions.inSampleSize = scaleFactor;

        return factoryOptions;
    }

}
