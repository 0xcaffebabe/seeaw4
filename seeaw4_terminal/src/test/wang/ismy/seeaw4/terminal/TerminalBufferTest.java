package wang.ismy.seeaw4.terminal;

import org.junit.Test;
import org.junit.runners.Parameterized;

import java.nio.Buffer;
import java.util.Arrays;

import static org.junit.Assert.*;

public class TerminalBufferTest {

    @Test
    public void test(){
        TerminalBuffer buffer = new TerminalBuffer(15);
        buffer.append("1111")
                .append("2222")
                .append("3333");
        char[] chars = buffer.getBuffer();

        assertArrayEquals("111122223333".toCharArray(),chars);
    }

    @Test
    public void test2(){
        TerminalBuffer buffer = new TerminalBuffer(10);
        buffer.append("1111")
                .append("2222")
                .append("3333");
        char[] chars = buffer.getBuffer();

        assertArrayEquals("1122223333".toCharArray(),chars);
    }

}