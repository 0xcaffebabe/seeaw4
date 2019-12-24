package wang.ismy.seeaw4.desktop;

import com.jfoenix.controls.JFXListView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import wang.ismy.seeaw4.client.client.LocalPer;
import wang.ismy.seeaw4.common.client.Per;
import wang.ismy.seeaw4.common.utils.SwingUtils;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**
 * @author MY
 * @date 2019/12/21 14:48
 */
public class ClientView extends HBox {

    @FXML
    private Label label;
    @FXML
    private ImageView screen;
    @FXML
    private ImageView camera;
    @FXML
    private JFXListView<Label> listview;

    private LocalPer per;

    public ClientView() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/my_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        screen.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                byte[] screen = per.getTerminalProxy().getDesktop().getScreen(null, null);
                SwingUtils.showImg(screen);
            }
        });
        camera.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                byte[] camera = per.getTerminalProxy().getCamera().getCameraSnapshot(null, null);
                SwingUtils.showImg(camera);
            }
        });
        this.setOnMouseClicked(event -> {
            new ShellFx(per);
        });
    }

    public void setScreen(Image pic) {
        screen.setImage(pic);
        screen.setPreserveRatio(true);
    }

    public void setCamera(Image pic) {
        camera.setImage(pic);
        camera.setPreserveRatio(true);
    }

    public void setClient(LocalPer per) {
        this.per = per;
        String str = "ID:" + per.getId() + "\n" +
                "连接时间:" + LocalDateTime.ofInstant(Instant.ofEpochMilli(per.getPer().getConnectTime()), TimeZone.getDefault().toZoneId());
        if (per.isSelf()) {
            label.setStyle("-fx-background-color: red");
        }
        Map<String,Object> map = per.getTerminalProxy().getSystemInfo();
        Platform.runLater(()->{
            listview.getItems().clear();
            map.forEach((s,o)->{
                listview.getItems().add(new Label(s+":"+o));
            });
        });
        label.setText(str);
    }

}
