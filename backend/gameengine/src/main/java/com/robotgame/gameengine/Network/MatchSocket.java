package com.robotgame.gameengine.Network;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.google.gson.Gson;
/**
 * Websocket for matches. 
 * @author Rickard Grantzelius
 *
 */
@WebSocket
public class MatchSocket {
	/**
	 * Constant signifying a clients message as a join-request
	 */
	private static final String JOIN="0";
	/**
	 * Constant signifying a clients message as a ready-statement
	 */
	private static final String READY="1";
	/**
	 * Constant signifying a clients message as a keypress
	 */
	private static final String KEYPRESS="2";
	Session _session;
	String _user;
	MatchHandler _handler;
	int _playerIndex;
	public boolean ready;
	@OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
    }

    @OnWebSocketError
    public void onError(Throwable t) {
        System.out.println("Error: " + t.getMessage());
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        _session=session;
        ready=false;
        try {
            session.getRemote().sendString("Connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handler for client messages. The first token of every message is used to determine what kind of message has been sent, according to the defined constants
     * @param message
     */
    @OnWebSocketMessage
    public void onMessage(String message) {
    	System.out.println(message.substring(0,1));
    	Gson g=new Gson();
        String s = message.substring(0, 1);
        if (s.equals(JOIN)) {
            joinRequest req = g.fromJson(message.substring(1), joinRequest.class);
            _user = req.user;
            _handler = MatchMaker.getInstance().getHandler(req.port);
            _playerIndex=_handler.join(this);
            if (_playerIndex>=0) {
            	_handler.requestStartState(this);
            } else {
                System.out.println("halp " + _user);
            }


        } else if (s.equals(READY)) {
            ready = true;
            _handler.setReady();

        } else if (s.equals(KEYPRESS)) {
            _handler.sendToAll(_user + " pressed " + message.substring(1));
            if(message.substring(1).equals('A')){
            	_handler.setA(_playerIndex);
            }else if(message.substring(1).equals('B')){
            	_handler.setB(_playerIndex);
            }
        } else {
            System.out.println("nope");

        }
      	 
    }

	public String getUser() {
		return _user;
	}
	/**
	 * Class used to interpret the clients json-object
	 * @author Rickard Grantzelius
	 *
	 */
	private class joinRequest{
		String user;
		int port;
	}
}
