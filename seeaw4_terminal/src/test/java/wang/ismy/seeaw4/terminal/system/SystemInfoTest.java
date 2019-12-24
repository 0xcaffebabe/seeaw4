package wang.ismy.seeaw4.terminal.system;

import org.junit.Test;
import oshi.SystemInfo;

import static org.junit.Assert.*;

public class SystemInfoTest {

    @Test
    public void test(){
        SystemInfo info = new SystemInfo();

        System.out.println(info.getHardware().getProcessor().getLogicalProcessorCount());
    }

}