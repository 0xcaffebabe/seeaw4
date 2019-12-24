package com.example.seeaw4.seeaw4.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.seeaw4.seeaw4.android.R;

import java.io.IOException;

import wang.ismy.seeaw4.client.client.LocalPer;
import wang.ismy.seeaw4.common.ExecuteService;
import wang.ismy.seeaw4.terminal.observer.impl.LazyTerminalObserver;

public class ShellActivity extends AppCompatActivity {

    public static LocalPer localPer;
    private ScrollView scrollView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);
        textView = findViewById(R.id.shell_text);
        EditText editText = findViewById(R.id.shell_edit);
        Button button= findViewById(R.id.shell_send);
        scrollView = findViewById(R.id.shell_scroll);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        localPer.getTerminalProxy().registerObserver(new LazyTerminalObserver() {
            @Override
            public void onMessage(String s) {
                // 增量缓冲区
                runOnUiThread(()->{
                    textView.append(s);
                });
                ExecuteService.excutes(()->{
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(ShellActivity.this::scrollToBottom);

                });
            }
        });
        ExecuteService.excutes(()->{
            // 输出缓冲区所有内容到屏幕
            String str = localPer.getTerminalProxy().getTerminalBuffer();
            runOnUiThread(()->{
                textView.setText(str);
            });
            ExecuteService.excutes(()->{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(this::scrollToBottom);

            });
        });
        button.setOnClickListener(e->{
            // 发送一条命令
            ExecuteService.excutes(()->{
                try {
                    localPer.getTerminalProxy().input(editText.getText().toString());
                    editText.setText("");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            });
        });

    }

    private void scrollToBottom(){
        int offset = textView.getMeasuredHeight() - scrollView.getHeight();
        if (offset < 0) {
            offset = 0;
        }

        scrollView.scrollTo(0, textView.getMeasuredHeight());
    }
}
