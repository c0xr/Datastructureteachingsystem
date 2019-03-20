package com.csti.datastructureteachingsystem.handler;

import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class ImageLoader extends Handler {
    private ImageView mImageView;
    private String mUrl;

    public ImageLoader(ImageView imageView, String url) {
        mImageView = imageView;
        mUrl = url;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }
}
