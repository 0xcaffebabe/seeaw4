package com.example.seeaw4.seeaw4.android.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

import com.example.seeaw4.seeaw4.android.Controller;
import com.example.seeaw4.seeaw4.android.MainActivity;
import com.example.seeaw4.seeaw4.android.R;
import com.example.seeaw4.seeaw4.android.view.ClientView;

import java.util.ArrayList;
import java.util.List;

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

        Controller controller = new Controller(listView);

        return view;
    }
}
