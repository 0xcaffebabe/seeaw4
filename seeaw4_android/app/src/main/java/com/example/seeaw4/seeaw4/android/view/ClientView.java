package com.example.seeaw4.seeaw4.android.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.seeaw4.seeaw4.android.R;
import com.example.seeaw4.seeaw4.android.activity.ImgActivity;
import com.example.seeaw4.seeaw4.android.activity.ShellActivity;

import java.io.ByteArrayInputStream;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TimeZone;

import wang.ismy.seeaw4.client.client.LocalPer;
import wang.ismy.seeaw4.common.ExecuteService;

public class ClientView extends LinearLayout {

    private ImageView screen, camera;
    private TextView text;
    private LocalPer per;

    public ClientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View view = inflate(context, R.layout.view_client, this);
        screen = view.findViewById(R.id.client_screen);
        camera = view.findViewById(R.id.client_camera);
        view.setOnClickListener(e->{
            Intent intent = new Intent();
            ShellActivity.localPer = per;
            intent.setClass(context, ShellActivity.class);
            context.startActivity(intent);
        });
        screen.setOnClickListener(v -> {
            new Thread(()->{

                byte[] bytes= per.getTerminalProxy().getDesktop().getScreen(null, null);
                screen.post(()->{
                    Intent intent = new Intent();
                    intent.putExtra("img",bytes);
                    intent.setClass(screen.getContext(), ImgActivity.class);
                    screen.getContext().startActivity(intent);
                });
            }).start();

        });
        camera.setOnClickListener(v -> {
            new Thread(()->{

                byte[] bytes= per.getTerminalProxy().getCamera().getCameraSnapshot(null, null);
                camera.post(()->{
                    Intent intent = new Intent();
                    intent.putExtra("img",bytes);
                    intent.setClass(screen.getContext(), ImgActivity.class);
                    camera.getContext().startActivity(intent);
                });
            }).start();
        });

        text = view.findViewById(R.id.client_text);
    }

    public void setScreen(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        screen.setImageBitmap(BitmapFactory.decodeStream(bis));
    }

    public void setCamera(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        camera.setImageBitmap(BitmapFactory.decodeStream(bis));
    }

    public void setPer(LocalPer per) {
        this.per = per;
        String id = per.getPer().getId();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(per.getPer().getConnectTime()), TimeZone.getDefault().toZoneId());
        text.setText("ID:" + id + "\n连接时间:" + localDateTime.toString());
        ExecuteService.excutes(()->{
            Map<String, Object> info = per.getTerminalProxy().getSystemInfo();
            String str = "\n";
            for (String s : info.keySet()) {
                str+=s+":"+info.get(s)+"\n";
            }
            String finalStr = str;
            text.post(()->{

                text.append(finalStr);
            });
        });

    }
}
