package com.sample.ui.view;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sample.ui.R;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageview);

        ImageView imageDrawable = findViewById(R.id.image_drawable);
        ImageView imageLoading = findViewById(R.id.image_loading);
        imageDrawable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageDrawable.startAnimation(AnimationUtils.loadAnimation(ImageViewActivity.this, R.anim.anim_rotate));
            }
        });

        Glide.with(this).load("https://tspimg.tstartel.com/upload/material/109/43245/mie_202201111333220.png").placeholder(R.drawable.sample).into(imageLoading);

    }
}