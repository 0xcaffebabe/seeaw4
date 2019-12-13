package wang.ismy.seeaw4.terminal;

import java.util.Arrays;

/**
 * 终端缓冲区，用来缓冲终端程序的输出字符
 *
 * @author MY
 * @date 2019/12/13 14:24
 */
public class TerminalBuffer {

    private final char[] buffer;
    private final int size;
    private int writePos = 1;

    public TerminalBuffer(int size) {
        buffer = new char[size + 1];
        this.size = size;
    }

    public char[] getBuffer() {
        return Arrays.copyOfRange(buffer, 1, writePos);

    }

    public TerminalBuffer append(CharSequence sequence) {
        int length = sequence.length();
        int i = size - writePos;
        if (i < 0) {
            bufferPreMove(length);
        } else if (i < length) {
            bufferPreMove(length - i);
        }

        for (int j = 0; j < length; j++) {
            buffer[writePos++] = sequence.charAt(j);
        }
        return this;

    }


    /**
     * 将buffer向前移动n个位置，空出n个位置
     */
    private void bufferPreMove(int size) {
        for (int i = size; i <= this.size; i++) {
            buffer[i - size] = buffer[i-1];
        }
        // 将write指针回退到size-1个位置前
        writePos -= size - 1;
    }


}
