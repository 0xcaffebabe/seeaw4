package wang.ismy.seeaw4.terminal;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 终端缓冲区，用来缓冲终端程序的输出字符
 *
 * @author MY
 * @date 2019/12/13 14:24
 */
public class TerminalBuffer {

    private final StringBuffer buffer = new StringBuffer();
    private final int size;

    public TerminalBuffer(int size) {
        this.size = size;
    }

    public synchronized String getBuffer() {
        return buffer.toString();
    }

    public TerminalBuffer append(CharSequence sequence) {
        int length = sequence.length();
        synchronized (buffer) {
            if (buffer.length() > size) {
                trim();
            }
            buffer.append(sequence);
            // 添加后检查是否还要修剪
            if (buffer.length() > size) {
                trim();
            }
        }
        return this;
    }

    private void trim() {
        // 修剪的数量等于缓冲区现在的尺寸与规定的尺寸之差
        int count = buffer.length() - size;
        if (count <= 0) {
            return;
        }
        buffer.delete(0,count);
    }

    public TerminalBuffer append(char c) {
        append(String.valueOf(c));
        return this;
    }


}
