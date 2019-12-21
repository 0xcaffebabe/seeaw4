package wang.ismy.seeaw4.desktop;

import com.jfoenix.controls.JFXListView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import wang.ismy.seeaw4.client.Client;
import wang.ismy.seeaw4.terminal.observer.impl.LazyTerminalObserver;

/**
 * @author MY
 * @date 2019/12/20 23:49
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Main.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }
}
