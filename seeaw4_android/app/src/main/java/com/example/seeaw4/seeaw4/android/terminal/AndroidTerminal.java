package com.example.seeaw4.seeaw4.android.terminal;

import java.io.IOException;

import wang.ismy.seeaw4.terminal.Resolution;
import wang.ismy.seeaw4.terminal.camera.Camera;
import wang.ismy.seeaw4.terminal.desktop.Desktop;
import wang.ismy.seeaw4.terminal.enums.ImgType;
import wang.ismy.seeaw4.terminal.enums.ShellType;
import wang.ismy.seeaw4.terminal.impl.CommonTerminal;

public class AndroidTerminal extends CommonTerminal {

    public AndroidTerminal() throws IOException {
        super(ShellType.ANDROID_SHELL);
    }

    @Override
    public Camera getCamera() {
        class AndroidCamera implements Camera{

            @Override
            public byte[] getCameraSnapshot(ImgType imgType, Resolution resolution) {
                return new byte[0];
            }
        }
        return new AndroidCamera();
    }

    @Override
    public Desktop getDesktop() {
        class AndroidDesktop implements  Desktop{

            @Override
            public byte[] getScreen(ImgType imgType, Resolution resolution) {
                return new byte[0];
            }
        }
        return new AndroidDesktop();
    }
}
