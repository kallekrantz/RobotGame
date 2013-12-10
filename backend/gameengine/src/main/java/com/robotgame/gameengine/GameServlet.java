package com.robotgame.gameengine;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

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
        factory.register(com.robotgame.gameengine.Network.MatchMakerSocket.class);
    }
}
