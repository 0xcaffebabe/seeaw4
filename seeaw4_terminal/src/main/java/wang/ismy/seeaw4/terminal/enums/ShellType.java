package wang.ismy.seeaw4.terminal.enums;

/**
 * @author MY
 * @date 2019/12/13 16:49
 */
public enum ShellType {

    /**
     * windows的ps
     */
    POWER_SHELL(0,"powershell"),

    /**
     * windows普通控制台
     */
    CMD(1,"cmd"),

    /**
     * unix下的sh
     */
    SHELL(2,"sh"),

    /**
     * unix下的bash
     */
    BASH(3,"bash");

    private int code;
    private String shellName;

    ShellType(int code, String shellName) {
        this.code = code;
        this.shellName = shellName;
    }

    public String getShellName() {
        return shellName;
    }
}
