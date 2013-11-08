package com.robotgame.network;

import java.util.Vector;


public class matchHandler {
	private static final int STANDBY = 0;
	private static final int RUNNING = 1;
	private int _matchState = STANDBY;
	private Vector<String> _expectedClients = new Vector<String>();
	private Vector<Robot> _robots = new Vector<Robot>();
	private Vector<matchSocket> _connectedClients = new Vector<matchSocket>();
	private String unpackingRoutine;
	private int clientsReady=0;
	public boolean join(matchSocket socket){
		for(String expected: _expectedClients ){
			if(socket.getUser().equals(expected)){
				_connectedClients.add(socket);
				return true;
			}
		}
		return false;
	}
	public void setExpectedParticipants(Vector<matchMakerHandler> match) {
		for(matchMakerHandler client : match){
			_expectedClients.add(client._user);
			_robots.add(client._robot);
		}
		
	}

	public void generateUnpackingRoutine() {
		// TODO Auto-generated method stub
		unpackingRoutine="dostuff";
		
	}
	public void startMatch(){
		sendToAll("Game on!");
	}
	public void sendToAll(String message){
		for(matchSocket s:_connectedClients){
			s._session.getRemote().sendStringByFuture(message);
		}
	}
	public synchronized void setReady(){
		if(_matchState==STANDBY){
			clientsReady++;
			if(clientsReady==_expectedClients.size()){
				boolean go=true;
				for(matchSocket m:_connectedClients){
					if(!m.ready){
						go=false;
					}
				}
				if(go){
					_matchState=RUNNING;
					startMatch();
				}else{
					clientsReady--;
				}
			}
		}
		
	}

}
