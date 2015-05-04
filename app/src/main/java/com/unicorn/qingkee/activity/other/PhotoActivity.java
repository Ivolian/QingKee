package com.unicorn.qingkee.activity.other;

import butterknife.ButterKnife;
import butterknife.InjectView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.unicorn.qingkee.R;
import com.unicorn.qingkee.mycode.ImageUtil;


public class PhotoActivity extends Activity {

    @InjectView(R.id.imageView)
    ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.inject(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        String photoPath = getIntent().getStringExtra("photoPath");
        BitmapFactory.Options factoryOptions = ImageUtil.getFactoryOptions(width, height, photoPath);
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, factoryOptions);
        imageView.setImageBitmap(bitmap);
    }

}
