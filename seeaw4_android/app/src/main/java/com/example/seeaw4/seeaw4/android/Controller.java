package com.example.seeaw4.seeaw4.android;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.core.util.Consumer;

import com.example.seeaw4.seeaw4.android.view.ClientView;

import java.util.ArrayList;
import java.util.List;

import wang.ismy.seeaw4.client.Client;
import wang.ismy.seeaw4.client.client.LocalPer;
import wang.ismy.seeaw4.common.client.Per;
import wang.ismy.seeaw4.terminal.observer.impl.LazyTerminalObserver;

public class Controller {

    private ListView listView;

    protected Client client;

    public Controller(ListView listView) {
        this.listView = listView;
        client = new Client("192.168.43.242",1999,false);
        client.init();
        client.setClientListChangeListener(this::renderList);
    }

    public Controller() {
        client = new Client("192.168.43.242",1999,false);
        client.init();
        client.setClientListChangeListener(this::renderList);
    }

    private void renderList(List<Per> list){
        List<LocalPer> localPerList = client.getLocalPerList();

        System.out.println("本地客户列表:"+localPerList);
        List<ClientView> clientViewList = new ArrayList<>();
        for (LocalPer localPer : localPerList) {
            ClientView clientView = new ClientView(listView.getContext(),null);
            byte[] screen = localPer.getTerminalProxy().getDesktop().getScreen(null, null);
            byte[] camera = localPer.getTerminalProxy().getCamera().getCameraSnapshot(null, null);
            clientView.setScreen(screen);
            clientView.setCamera(camera);
            clientViewList.add(clientView);
        }
        listView.post(()->{
            listView.setAdapter(new BaseAdapter() {
                @Override
                public int getCount() {
                    return clientViewList.size();
                }

                @Override
                public Object getItem(int position) {
                    return clientViewList.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    return clientViewList.get(position);
                }
            });
        });

    }
}
