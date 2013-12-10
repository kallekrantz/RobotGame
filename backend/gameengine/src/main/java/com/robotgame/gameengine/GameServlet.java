package com.robotgame.gameengine;
import com.robotgame.gameengine.Network.MatchMakerSocket;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.http.HttpServletRequest;


/**
 * Created with IntelliJ IDEA.
 * User: kallekrantz
 * Date: 10/12/13
 * Time: 16:02
 * To change this template use File | Settings | File Templates.
 */

public class GameServlet  extends WebSocketServlet {

    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.register(MatchMakerSocket.class);
        factory.getPolicy().setIdleTimeout(10000);

    }

}
