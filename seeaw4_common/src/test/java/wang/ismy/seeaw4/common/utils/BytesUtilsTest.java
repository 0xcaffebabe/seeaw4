package wang.ismy.seeaw4.common.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class BytesUtilsTest {

    @Test
    public void concat() {
        byte[] bytes1 = new byte[]{1,1,1,1};
        byte[] bytes2 = new byte[]{2,2,2,2};
        byte[] expected = new byte[]{1,1,1,1,2,2,2,2};
        assertArrayEquals(expected,BytesUtils.concat(bytes1,bytes2));
    }

    @Test
    public void subBytes() {
        byte[] bytes = new byte[]{1,2,3,4,5};
        byte[] ret = BytesUtils.subBytes(bytes, 1, 3);
        assertArrayEquals(new byte[]{2,3,4},ret);
    }
}