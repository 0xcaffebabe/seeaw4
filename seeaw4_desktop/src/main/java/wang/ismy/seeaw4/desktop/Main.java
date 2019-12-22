package wang.ismy.seeaw4.desktop;

import com.jfoenix.controls.JFXListView;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Main.fxml"));
        Controller controller = new Controller();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> controller.shutdown());
        stage.show();

    }
}
