package com.unicorn.qingkee.mycode;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;


public class UploadImageView extends ImageView {

    public UploadImageView(Context context) {
        super(context);
    }

    public UploadImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UploadImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public UploadImageView(Context context, String compressedPhotoPath) {
        super(context);
        this.compressedPhotoPath = compressedPhotoPath;
    }

    private String compressedPhotoPath;

    public String getCompressedPhotoPath() {
        return compressedPhotoPath;
    }

    public void setCompressedPhotoPath(String compressedPhotoPath) {
        this.compressedPhotoPath = compressedPhotoPath;
    }


    private boolean uploading = true;

    public boolean isUploading() {
        return uploading;
    }

    public void setUploading(boolean uploading) {
        this.uploading = uploading;
    }
}
