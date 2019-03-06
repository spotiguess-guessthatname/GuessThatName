package com.example.guessthatname;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class ImageUtil {

    public static Void displayImageFromLink(Context context, ImageView imageView, String url){
        Glide.with(context)
                .load(url)
                .into(imageView);
        return null;
    }

}
