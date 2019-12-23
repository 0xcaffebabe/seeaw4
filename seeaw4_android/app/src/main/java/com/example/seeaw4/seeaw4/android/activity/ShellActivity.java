package com.example.seeaw4.seeaw4.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.seeaw4.seeaw4.android.R;

import java.io.IOException;

import wang.ismy.seeaw4.client.client.LocalPer;
import wang.ismy.seeaw4.common.ExecuteService;
import wang.ismy.seeaw4.terminal.observer.impl.LazyTerminalObserver;

public class ShellActivity extends AppCompatActivity {

    public static LocalPer localPer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);
        TextView textView = findViewById(R.id.shell_text);
        EditText editText = findViewById(R.id.shell_edit);
        localPer.getTerminalProxy().registerObserver(new LazyTerminalObserver() {
            @Override
            public void onMessage(String s) {
                runOnUiThread(()->{

                    textView.append(s);
                });
            }
        });
        ExecuteService.excutes(()->{
            String str = localPer.getTerminalProxy().getTerminalBuffer();
            runOnUiThread(()->{
                textView.setText(str);
            });
            try {
                localPer.getTerminalProxy().input("uptime");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    ExecuteService.excutes(()->{
                        try {
                            localPer.getTerminalProxy().input(editText.getText().toString());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    });

                    return true;
                }
                return false;
            }
        });


    }
}
