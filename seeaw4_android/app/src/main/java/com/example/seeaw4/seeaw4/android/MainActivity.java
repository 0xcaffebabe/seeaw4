package com.example.seeaw4.seeaw4.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.seeaw4.seeaw4.android.fragment.OnLineFragment;
import com.example.seeaw4.seeaw4.android.fragment.SystemLogFragment;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import wang.ismy.seeaw4.terminal.enums.ShellType;
import wang.ismy.seeaw4.terminal.impl.CommonTerminal;
import wang.ismy.seeaw4.terminal.observer.impl.LazyTerminalObserver;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        final List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new OnLineFragment());
        fragmentList.add(new SystemLogFragment());

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("在线用户");
        tabLayout.getTabAt(1).setText("系统日志");

//        try {
//            CommonTerminal commonTerminal = new CommonTerminal(ShellType.ANDROID_SHELL);
//            commonTerminal.registerObserver(new LazyTerminalObserver() {
//                @Override
//                public void onMessage(String s) {
//                    runOnUiThread(() -> {
//                        System.out.print(s);
//                        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
//                    });
//                }
//            });
//            commonTerminal.input("ls");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
