package wang.ismy.seeaw4.server;

import org.junit.Test;
import wang.ismy.seeaw4.common.ExecuteService;

import static org.junit.Assert.*;

public class ExecuteServiceTest {

    @Test
    public void excute() {
        ExecuteService service = ExecuteService.getInstance();
        service.excute(() -> {

        });
    }

    @Test
    public void getInstance() {
        ExecuteService service = ExecuteService.getInstance();
        assertNotNull(service);
    }

    @Test
    public void testSchedule(){

    }
}