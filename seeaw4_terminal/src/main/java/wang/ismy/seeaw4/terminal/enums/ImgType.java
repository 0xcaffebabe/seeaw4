package wang.ismy.seeaw4.terminal.enums;

/**
 * @author MY
 * @date 2019/12/14 14:36
 */
public enum ImgType {

    PNG(0,"PNG"),
    JPEG(1,"JPEG");
    private int code;
    private String format;

    ImgType(int code, String format) {
        this.code = code;

        this.format = format;
    }

    public String getFormat() {
        return format;
    }
}
