package wang.ismy.seeaw4.terminal;

/**
 * 逻辑终端
 * @author my
 */
public interface Terminal {

    /**
     * 输入命令到终端
     * @param cmd 命令文本
     */
    void input(String cmd);

    void output();


}
