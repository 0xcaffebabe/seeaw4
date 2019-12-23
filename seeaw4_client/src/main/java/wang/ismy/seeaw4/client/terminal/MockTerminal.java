package wang.ismy.seeaw4.client.terminal;

import wang.ismy.seeaw4.terminal.Resolution;
import wang.ismy.seeaw4.terminal.Terminal;
import wang.ismy.seeaw4.terminal.camera.Camera;
import wang.ismy.seeaw4.terminal.desktop.Desktop;
import wang.ismy.seeaw4.terminal.enums.ImgType;

import java.io.IOException;

public class MockTerminal extends Terminal {
    @Override
    public void input(String cmd) throws IOException {

    }

    @Override
    public Camera getCamera() {
        return new Camera() {
            @Override
            public byte[] getCameraSnapshot(ImgType type, Resolution resolution) {
                return new byte[0];
            }
        };
    }

    @Override
    public Desktop getDesktop() {
        return new Desktop() {
            @Override
            public byte[] getScreen(ImgType imgType, Resolution resolution) {
                return new byte[0];
            }
        };
    }
}
