package wang.ismy.seeaw4.desktop;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import wang.ismy.seeaw4.client.client.LocalPer;
import wang.ismy.seeaw4.terminal.observer.impl.LazyTerminalObserver;

import java.io.IOException;

/**
 * @author MY
 * @date 2019/12/21 23:22
 */
public class ShellFx extends VBox {

    @FXML
    private JFXTextArea label;
    private LocalPer localPer;
    @FXML
    private JFXTextField textField;

    public ShellFx(LocalPer localPer) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/shell.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            label.setText(localPer.getTerminalProxy().getTerminalBuffer());
            localPer.getTerminalProxy().registerObserver(new LazyTerminalObserver() {
                @Override
                public void onMessage(String msg) {
                    System.out.println("receive"+msg);
                    label.appendText(msg);
                    label.setScrollTop(Double.MAX_VALUE);
                }
            });

            textField.setOnKeyPressed(k->{
                if (k.getCode().equals(KeyCode.ENTER)){
                    String cmd = textField.getText();
                    textField.clear();
                    try {
                        localPer.getTerminalProxy().input(cmd);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
