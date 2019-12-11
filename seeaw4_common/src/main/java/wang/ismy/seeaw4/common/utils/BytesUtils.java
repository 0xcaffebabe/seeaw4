package wang.ismy.seeaw4.common.utils;

/**
 * @author my
 */
public class BytesUtils {

    public static byte[] concat(byte[] a,byte[] b){
        byte[] ret = new byte[a.length+b.length];
        System.arraycopy(a,0,ret,0,a.length);
        System.arraycopy(b,0,ret,a.length,b.length);
        return ret;
    }

    public static byte[] subBytes(byte[] bytes,int startPos,int offset){
        byte[] ret = new byte[offset];
        System.arraycopy(bytes,startPos,ret,0,offset);
        return ret;
    }
}
