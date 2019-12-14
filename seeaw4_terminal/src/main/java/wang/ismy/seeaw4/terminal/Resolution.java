package wang.ismy.seeaw4.terminal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

/**
 * @author MY
 * @date 2019/12/14 15:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resolution {

    private int width;
    private int height;

    public static Resolution getDefault() {
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension size = t.getScreenSize();
        return new Resolution((int) size.getWidth(), (int) size.getHeight());
    }
}
