package com.example.seeaw4.seeaw4.android.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.ListView;

import com.example.seeaw4.seeaw4.android.Controller;
import com.example.seeaw4.seeaw4.android.MainActivity;
import com.example.seeaw4.seeaw4.android.R;
import com.example.seeaw4.seeaw4.android.view.ClientView;

import java.util.ArrayList;
import java.util.List;

import wang.ismy.seeaw4.common.connection.ConnectionState;

public class OnLineFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_on_line, container, false);
        final ListView listView = view.findViewById(R.id.online_listview);
        final List<ClientView> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ClientView clientView = new ClientView(container.getContext(), null);
            list.add(clientView);
        }
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Object getItem(int position) {
                return list.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return list.get(position);
            }
        });

        Controller controller = new Controller(listView,(conn, state) -> {
            listView.post(()->{

                if (state.equals(ConnectionState.LIVE)){
                    MainActivity.instance.getSupportActionBar().setTitle("Seeaw4 android 连接服务器正常");
                }else {
                    MainActivity.instance.getSupportActionBar().setTitle("Seeaw4 android 连接服务器超时");
                }
            });
        });

        return view;
    }
}
