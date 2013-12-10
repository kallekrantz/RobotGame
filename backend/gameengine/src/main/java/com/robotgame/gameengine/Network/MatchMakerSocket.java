package com.robotgame.gameengine.Network;

import java.io.IOException;

import com.robotgame.gameengine.Robot.testRobot;
import com.robotgame.gameengine.Robot.Builder.RobotBlueprint;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import com.google.gson.Gson;
/**
 * Websocket for the lobby system. 
 * @author Rickard Grantzelius
 *
 */
@WebSocket
public class MatchMakerSocket implements WebSocketListener{
	Session _session;
	String _user;
	RobotBlueprint _robot;

    public MatchMakerSocket(){}


    
    /**
     * Method to fetch data from the database about the chosen robot, and from that construct a robot blueprint. The current implementation creates a dummy robot.
     * @param robotId
     * @return
     */
    private RobotBlueprint constructRobot(int robotId) {
		// koppla till databas och tolka dess JSON
    	testRobot test= new testRobot();
    	Gson g=new Gson();
    	
    	RobotBlueprint robot = g.fromJson(test.json, RobotBlueprint.class);
		return robot;
	}

    @Override
    public void onWebSocketBinary(byte[] bytes, int i, int i2) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        System.out.println("Close: statusCode=" + statusCode + ", reason=" + reason);
    }

    @Override
    public void onWebSocketConnect(Session session) {
        System.out.println("WOOHOOOO CONNECTION");
        _session=session;
        try {
            session.getRemote().sendString("Connected");
        } catch (IOException e) {
            e.printStackTrace();
        }    }

    @Override
    public void onWebSocketError(Throwable throwable) {
        System.out.println("Error: " + throwable.getMessage());
        throwable.printStackTrace();
    }

    /**
     * Handles incoming messages from the client. As it is now, the client sends only one type of message. Could be extended to implement a more dynamic lobby
     * @param message
     */
    @Override
    public void onWebSocketText(String message) {
        Gson g=new Gson();
        System.out.println("Message: "+message);
        joinRequest req=g.fromJson(message, joinRequest.class);
        _robot = constructRobot(req.robotId);
        _user = req.user;
        MatchMaker.getInstance().queue(this, req.type);
    }

    /**
     * Used for the interpretation of the clients json object.
     * @author Rickard Grantzelius
     *
     */
	private class joinRequest{
    	String user;
    	int type;
    	int robotId;
    	
    }

}
