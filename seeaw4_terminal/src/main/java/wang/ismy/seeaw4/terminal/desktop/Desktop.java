package wang.ismy.seeaw4.terminal.desktop;

import wang.ismy.seeaw4.terminal.Resolution;
import wang.ismy.seeaw4.terminal.enums.ImgType;

/**
 * @author MY
 * @date 2019/12/14 15:26
 */
public interface Desktop {

    /**
     * 获取屏幕截图
     * @param imgType 图片格式
     * @param resolution 分辨率
     * @return 图片数据
     */
    byte[] getScreen(ImgType imgType, Resolution resolution);
}
