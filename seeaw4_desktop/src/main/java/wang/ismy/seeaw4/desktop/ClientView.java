package wang.ismy.seeaw4.desktop;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import wang.ismy.seeaw4.common.client.Per;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
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
    private ImageView img;
    private Per per;

    public ClientView() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/my_view.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImg(Image pic){
        img.setImage(pic);
    }

    public void setClient(Per per){
        this.per = per;
        String str = "ID:"+per.getId()+"\n"+
                "连接时间:"+ LocalDateTime.ofInstant(Instant.ofEpochMilli(per.getConnectTime()), TimeZone.getDefault().toZoneId());
        if (per.isSelf()){
            label.setStyle("-fx-background-color: red");
        }
        label.setText(str);
    }


}
