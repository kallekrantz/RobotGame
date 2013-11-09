package com.robotgame.network;

import java.io.IOException;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.google.gson.Gson;

@WebSocket
public class matchMakerHandler {
	Session _session;
	String _user;
	Robot _robot;
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
        try {
            session.getRemote().sendString("Connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
    	Gson g=new Gson();
    	joinRequest req=g.fromJson(message, joinRequest.class);
    	_robot = constructRobot(req.robotId);
    	_user = req.user;
      	MatchMaker.getInstance().que(this, req.type);
    }
    
    private Robot constructRobot(int robotId) {
		// koppla till databas och tolka dess JSON
		return new Robot(15);
	}

	private class joinRequest{
    	String user;
    	int type;
    	int robotId;
    	
    }

}
