package com.robotgame.gameengine.Network;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.google.gson.Gson;

@WebSocket
public class matchSocket {
	private static final String JOIN="0";
	private static final String READY="1";
	private static final String KEYPRESS="2";
	Session _session;
	String _user;
	matchHandler _handler;
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

    @OnWebSocketMessage
    public void onMessage(String message) {
    	System.out.println(message.substring(0,1));
    	Gson g=new Gson();
        String s = message.substring(0, 1);
        if (s.equals(JOIN)) {
            joinRequest req = g.fromJson(message.substring(1), joinRequest.class);
            _user = req.user;
            _handler = MatchMaker.getInstance().getHandler(req.port);
            if (_handler.join(this)) {
            	_handler.requestStartState(this);
            } else {
                System.out.println("halp " + _user);
            }


        } else if (s.equals(READY)) {
            ready = true;
            _handler.setReady();

        } else if (s.equals(KEYPRESS)) {
            System.out.println("nu h�nder det");
            _handler.sendToAll(_user + " pressed " + message.substring(1));

        } else {
            System.out.println("nope");

        }
      	 
    }

	public String getUser() {
		// TODO Auto-generated method stub
		return _user;
	}
	private class joinRequest{
		String user;
		int port;
	}
}
