package com.example.seeaw4.seeaw4.android;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.core.util.Consumer;

import com.example.seeaw4.seeaw4.android.terminal.AndroidTerminal;
import com.example.seeaw4.seeaw4.android.view.ClientView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import wang.ismy.seeaw4.client.Client;
import wang.ismy.seeaw4.client.client.LocalPer;
import wang.ismy.seeaw4.common.ExecuteService;
import wang.ismy.seeaw4.common.client.Per;
import wang.ismy.seeaw4.common.encrypt.PasswordService;
import wang.ismy.seeaw4.common.message.MessageService;
import wang.ismy.seeaw4.terminal.observer.impl.LazyTerminalObserver;

public class Controller {

    private ListView listView;

    protected Client client;

    public Controller(ListView listView) {
        // 指定密码
        PasswordService.update("123321");
        this.listView = listView;
        client = new Client("100.64.137.37", 1999, false);
        try {
            client.setTerminal(new AndroidTerminal());
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.init();
        client.setClientListChangeListener(this::renderList);
    }

    public Controller() {
        client = new Client("192.168.43.132", 1999, false);
        try {
            client.setTerminal(new AndroidTerminal());
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.init();
        client.setClientListChangeListener(this::renderList);
    }

    private void renderList(List<Per> list) {
        List<LocalPer> localPerList = client.getLocalPerList();

        System.out.println("本地客户列表:" + localPerList);
        List<ClientView> clientViewList = new ArrayList<>();
        for (LocalPer localPer : localPerList) {


            ClientView clientView = new ClientView(listView.getContext(), null);
            ExecuteService.excutes(()->{

                byte[] screen = localPer.getTerminalProxy().getDesktop().getScreen(null, null);
                clientView.post(()->{

                    clientView.setScreen(screen);
                });
                byte[] camera = localPer.getTerminalProxy().getCamera().getCameraSnapshot(null, null);
                clientView.post(()->{

                    clientView.setCamera(camera);
                });
            });
            clientView.setPer(localPer);
            clientViewList.add(clientView);

        }
        listView.post(() -> {
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
