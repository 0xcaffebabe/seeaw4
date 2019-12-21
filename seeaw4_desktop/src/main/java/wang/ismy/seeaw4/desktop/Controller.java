package wang.ismy.seeaw4.desktop;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import wang.ismy.seeaw4.client.Client;
import wang.ismy.seeaw4.common.ExecuteService;
import wang.ismy.seeaw4.common.client.Per;
import wang.ismy.seeaw4.terminal.Resolution;
import wang.ismy.seeaw4.terminal.enums.ImgType;
import wang.ismy.seeaw4.terminal.observer.impl.LazyTerminalObserver;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;


/**
 * @author MY
 * @date 2019/12/21 14:27
 */
public class Controller {

    @FXML
    private JFXListView<ClientView> listView;

    private Client client;
    private ExecuteService executeService = ExecuteService.getInstance();

    public Controller() {
        client = new Client();
        client.getTerminalProxy().registerObserver(new LazyTerminalObserver() {
            @Override
            public void onMessage(String msg) {
                System.err.print(msg);
            }
        });
        client.setClientListChangeListener(this::renderClientList);
        client.init();
    }

    public void init(){

    }

    private void renderClientList(List<Per> list){
        System.out.println("client list render");
        Platform.runLater(()->{
            listView.getItems().clear();
            for (Per per : list) {
                ClientView clientView = new ClientView();
                clientView.setClient(per);
                if (!per.isSelf()){
                    executeService.excute(()->{
                        client.getTerminalProxy().setRemoteClientId(per.getId());
                        client.getTerminalProxy().setBindSuccessListener(()->{
                            byte[] bytes = client.getTerminalProxy().getCamera().getCameraSnapshot(ImgType.PNG, Resolution.getDefault());
                            Platform.runLater(()->{
                                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                                clientView.setImg(new Image(bis));
                            });
                        });
                        try {
                            client.getTerminalProxy().bind();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    });
                }
                listView.getItems().add(clientView);
            }
        });

    }
}
