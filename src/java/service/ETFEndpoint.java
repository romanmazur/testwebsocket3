package service;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author romanm
 */
@ServerEndpoint("/dukeetf")
public class ETFEndpoint {

    private static final Logger logger = Logger.getLogger("ETFEndpoint");
    /* Queue for all open WebSocket sessions */
    static Queue<Session> queue = new ConcurrentLinkedQueue<>();

    /* PriceVolumeBean calls this method to send updates */
    public static void send(double price, int volume) {
        String msg = String.format("%.2f, %d", price, volume);
        try {
            /* Send updates to all open WebSocket sessions */
            for (Session session : queue) {
                session.getBasicRemote().sendText(msg);
                logger.log(Level.INFO, "Sent: {0}", msg);
            }
        } catch (IOException e) {
            logger.log(Level.INFO, e.toString());
        }
    }
    
    
   @OnOpen
   public void openConnection(Session session) {
      /* Register this connection in the queue */
      queue.add(session);
      logger.log(Level.INFO, "Connection opened.");
   }

   @OnClose
   public void closedConnection(Session session) {
      /* Remove this connection from the queue */
      queue.remove(session);
      logger.log(Level.INFO, "Connection closed.");
   }

   @OnError
   public void error(Session session, Throwable t) {
      /* Remove this connection from the queue */
      queue.remove(session);
      logger.log(Level.INFO, t.toString());
      logger.log(Level.INFO, "Connection error.");
   }
    
    
    
}
