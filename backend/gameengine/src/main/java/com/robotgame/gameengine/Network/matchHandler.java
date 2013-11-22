package com.robotgame.gameengine.Network;

import java.util.Vector;

import com.google.gson.Gson;
import com.robotgame.gameengine.Match.Match;
import com.google.gson.Gson;
import com.robotgame.gameengine.Match.Match;
import com.robotgame.gameengine.Match.IMatchHandler;
import com.robotgame.gameengine.Match.MatchResult;
import com.robotgame.gameengine.Robot.RobotState;
import com.robotgame.gameengine.Robot.Builder.*;


public class matchHandler implements IMatchHandler {
	private static final int STANDBY = 0;
	private static final int RUNNING = 1;
	private int _matchState = STANDBY;
	private Vector<String> _expectedClients = new Vector<String>();
	private Vector<RobotBlueprint> _robots = new Vector<RobotBlueprint>();
	private Vector<matchSocket> _connectedClients = new Vector<matchSocket>();
	private String unpackingRoutine;
	private int clientsReady=0;
	private int id;
	public boolean join(matchSocket socket){
		for(String expected: _expectedClients ){
			if(socket.getUser().equals(expected)){
				_connectedClients.add(socket);
				return true;
			}
		}
		socket._session.getRemote().sendStringByFuture("NetworkError:: Unexpected user");
		return false;
	}
	public matchHandler(int matchId){
		id=matchId;
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
		Match match = new Match(this,1);
		match.BuildRobots(_robots);
		match.run();
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
	public void SendMatchState(MatchState _matchState) {
		Gson gson= new Gson();
		sendToAll("UpdateState::"+gson.toJson(_matchState));
		
	}
	public void MatchEnded(MatchResult _matchResult) {
		// TODO Auto-generated method stub
		Gson gson= new Gson();
		sendToAll("MatchEnded::"+gson.toJson(_matchResult));
		MatchMaker.getInstance().closeGameServer(id);
	}
	public void requestStartState(matchSocket socket) {
		MatchState state  = new MatchState(0,_robots.size());
		for(int robots = 0; robots<_robots.size(); robots++){
			state.robotStates[robots] = new RobotState();
		}
		Gson gson = new Gson();
		socket._session.getRemote().sendStringByFuture("StartingState::"+gson.toJson(state));
	}
	
}
