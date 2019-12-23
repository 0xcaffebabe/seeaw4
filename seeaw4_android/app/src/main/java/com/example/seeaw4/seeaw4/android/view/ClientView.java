package com.example.seeaw4.seeaw4.android.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.seeaw4.seeaw4.android.R;

import java.io.ByteArrayInputStream;

public class ClientView extends LinearLayout {

    private ImageView screen,camera;
    private TextView text;

    public ClientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.view_client,this);
        screen = view.findViewById(R.id.client_screen);
        camera= view.findViewById(R.id.client_camera);
        screen.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("screen");
                setScreen();
            }
        });
        camera.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCamera();
            }
        });
        text= view.findViewById(R.id.client_text);
    }

    public void setScreen(){
        screen.setImageResource(R.drawable.ic_launcher_background);
    }

    public void setCamera(){
        camera.setImageResource(R.drawable.ic_launcher_background);
    }

    public void setScreen(byte[] bytes){
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        screen.setImageBitmap(BitmapFactory.decodeStream(bis));
    }
    public void setCamera(byte[] bytes){
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        camera.setImageBitmap(BitmapFactory.decodeStream(bis));
    }
}
