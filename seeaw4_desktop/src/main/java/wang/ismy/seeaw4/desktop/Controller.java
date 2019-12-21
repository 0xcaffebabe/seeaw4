package wang.ismy.seeaw4.desktop;

import com.jfoenix.controls.JFXListView;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import wang.ismy.seeaw4.client.Client;
import wang.ismy.seeaw4.client.client.LocalPer;
import wang.ismy.seeaw4.client.terminal.TerminalProxy;
import wang.ismy.seeaw4.common.ExecuteService;
import wang.ismy.seeaw4.common.client.Per;
import wang.ismy.seeaw4.terminal.Resolution;
import wang.ismy.seeaw4.terminal.camera.Camera;
import wang.ismy.seeaw4.terminal.desktop.Desktop;
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

    public void init() {

    }

    private void renderClientList(List<Per> list) {
        System.out.println("client list render");
        Platform.runLater(() -> {
            listView.getItems().clear();
            List<LocalPer> localPerList = client.getLocalPerList();
            for (LocalPer per : localPerList) {
                ClientView clientView = new ClientView();
                clientView.setClient(per.getPer());
                // 显示画面
                executeService.excute(()->{
                    TerminalProxy terminalProxy = per.getTerminalProxy();
                    if (terminalProxy != null) {
                        Desktop desktop = terminalProxy.getDesktop();
                        byte[] bytes = desktop.getScreen(null, null);
                        Platform.runLater(()->{
                            clientView.setImg(new Image(new ByteArrayInputStream(bytes)));
                        });
                    }
                });
                listView.getItems().add(clientView);
            }
        });

    }
}
