package wang.ismy.seeaw4.terminal.desktop.impl;

import wang.ismy.seeaw4.terminal.Resolution;
import wang.ismy.seeaw4.terminal.desktop.Desktop;
import wang.ismy.seeaw4.terminal.enums.ImgType;

/**
 * @author MY
 * @date 2019/12/14 15:44
 */
public class OSXDesktop implements Desktop {
    @Override
    public byte[] getScreen(ImgType imgType, Resolution resolution) {
        return new byte[0];
    }
}
