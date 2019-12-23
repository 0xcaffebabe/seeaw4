package com.example.seeaw4.seeaw4.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.seeaw4.seeaw4.android.R;

import java.io.ByteArrayInputStream;

public class ImgActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        ImageView imageView = findViewById(R.id.img_imgview);

        Intent intent = getIntent();
        if (intent != null) {

            byte[] imgs = intent.getByteArrayExtra("img");
            ByteArrayInputStream bis = new ByteArrayInputStream(imgs);
            imageView.setImageBitmap(BitmapFactory.decodeStream(bis));
        }

    }
}
