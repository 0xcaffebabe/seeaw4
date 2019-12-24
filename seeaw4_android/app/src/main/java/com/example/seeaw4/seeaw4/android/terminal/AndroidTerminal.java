package com.example.seeaw4.seeaw4.android.terminal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.provider.Settings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import wang.ismy.seeaw4.terminal.Resolution;
import wang.ismy.seeaw4.terminal.camera.Camera;
import wang.ismy.seeaw4.terminal.desktop.Desktop;
import wang.ismy.seeaw4.terminal.enums.ImgType;
import wang.ismy.seeaw4.terminal.enums.ShellType;
import wang.ismy.seeaw4.terminal.impl.CommonTerminal;

public class AndroidTerminal extends CommonTerminal {

    public AndroidTerminal() throws IOException {
        super(ShellType.ANDROID_SHELL);
    }

    @Override
    public Camera getCamera() {
        class AndroidCamera implements Camera {

            @Override
            public byte[] getCameraSnapshot(ImgType imgType, Resolution resolution) {
                return appendTextToPicture("","nothing");
            }


        }
        return new AndroidCamera();
    }

    @Override
    public Desktop getDesktop() {
        class AndroidDesktop implements Desktop {

            @Override
            public byte[] getScreen(ImgType imgType, Resolution resolution) {
                return appendTextToPicture("","nothing");
            }
        }
        return new AndroidDesktop();
    }

    @Override
    public Map<String, Object> getSystemInfo() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("系统", "android");
        return map;
    }

    private byte[] appendTextToPicture(final String picPath, final String msg) {
        //返回具有指定宽度和高度可变的位图,它的初始密度可以调用getDensity()
        final int TXT_SIZE = 24;

        Bitmap bmp = Bitmap.createBitmap(300,200, Bitmap.Config.RGB_565);

        final int y_offset = 5;
        int heigth = bmp.getHeight() + y_offset + TXT_SIZE;
        final int max_width = bmp.getWidth();
        List<String> buf = new ArrayList<String>();
        String lineStr = "";

        Paint p = new Paint();
        Typeface font = Typeface.create("宋体", Typeface.BOLD);
        p.setColor(Color.BLACK);
        p.setTypeface(font);
        p.setTextSize(TXT_SIZE);

        for (int i = 0; i < msg.length(); ) {

            if (Character.getType(msg.charAt(i)) == Character.OTHER_LETTER) {
                // 如果这个字符是一个汉字
                if ((i + 1) < msg.length()) {
                    lineStr += msg.substring(i, i + 2);
                }

                i = i + 2;
            } else {
                lineStr += msg.substring(i, i + 1);
                i++;
            }

            float[] ws = new float[lineStr.length()];
            int wid = p.getTextWidths(lineStr, ws);

            if (wid > max_width) {
                buf.add(lineStr);
                lineStr = "";
                heigth += (TXT_SIZE + y_offset);
            }

            if (i >= msg.length()) {
                heigth += (TXT_SIZE + y_offset);
                break;
            }
        }


        Bitmap canvasBmp = Bitmap.createBitmap(max_width, heigth + TXT_SIZE, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(canvasBmp);
        canvas.drawColor(Color.WHITE);

        float y = y_offset + TXT_SIZE;
        for (String str : buf) {
            canvas.drawText(str, 0, y, p);
            y += (TXT_SIZE + y_offset);
        }

        canvas.drawBitmap(bmp, 0, y, p);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,100,bos);
        return bos.toByteArray();
    }
}
