package service;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author romanm
 */
@ServerEndpoint("/echo")
public class MyEndpoint {

    @OnMessage
    public void onMessage(String message) {
        System.out.println("endpiont");
    }
    
}
