package wang.ismy.seeaw4.terminal.camera;

import wang.ismy.seeaw4.terminal.Resolution;
import wang.ismy.seeaw4.terminal.enums.ImgType;

/**
 * 相机接口，所有相机都应有这些行为
 * @author MY
 * @date 2019/12/14 15:20
 */
public interface Camera {

    /**
     * 调用系统摄像机拍摄一张照片
     * @param type 图片格式
     * @param resolution 分辨率
     * @return 图片数据
     */
    byte[] getCameraSnapshot(ImgType type, Resolution resolution);
}
