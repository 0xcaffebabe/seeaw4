package wang.ismy.seeaw4.server;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ServerSocketServiceTest {

    @Test
    public void init(){
        ServerSocketService service = new ServerSocketService();
        service.init();
    }

    @Test(expected = IllegalStateException.class)
    public void acceptShouldFail(){
        ServerSocketService service = new ServerSocketService();
        try {
            service.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}