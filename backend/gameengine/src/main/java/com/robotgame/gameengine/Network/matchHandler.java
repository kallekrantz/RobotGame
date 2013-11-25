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
/**
 * Handler for network-communication during a match. 
 * @author Rickard Gräntzelius
 *
 */
public class MatchHandler implements IMatchHandler {
	private boolean _isRunning = false;
	/**
	 * List of usernames of the clients associated to this match 
	 */
	private Vector<String> _expectedClients = new Vector<String>();
	private Vector<RobotBlueprint> _robots = new Vector<RobotBlueprint>();
	/**
	 * List of websockets connected to the match.
	 */
	private Vector<MatchSocket> _connectedClients = new Vector<MatchSocket>();
	/**
	 * To be implemented, will determine the setup of a match on the frontend side.
	 */
	private String unpackingRoutine;
	/**
	 * if the number of ready clients are equal to the number of expected clients, the match should try to initiate.
	 */
	private int clientsReady=0;
	private int id;
	/**
	 * Sets the id of the match, so the match can be discarded once finished
	 * @param matchId
	 */
	public MatchHandler(int matchId){
		id=matchId;
	}
	/**
	 * Used to connect each client to the handler. Will turn away any user not in the list of expected clients
	 * @param socket
	 * @return Will return true if the user is accepted
	 */
	public boolean join(MatchSocket socket){
		for(String expected: _expectedClients ){
			if(socket.getUser().equals(expected)){
				_connectedClients.add(socket);
				return true;
			}
		}
		socket._session.getRemote().sendStringByFuture("NetworkError:: Unexpected user");
		return false;
	}
	/**
	 * Called by the matchmaker before the user switches websocket, so only the intended participants can join
	 * @param match
	 */
	public void setExpectedParticipants(Vector<MatchMakerSocket> match) {
		for(MatchMakerSocket client : match){
			_expectedClients.add(client._user);
			_robots.add(client._robot);
		}
		
	}
	/**
	 * Not yet implemented
	 */
	public void generateUnpackingRoutine() {
		unpackingRoutine="dostuff";
		
	}
	/**
	 * Initiates a game. Should concider separating initation and running of the match. 
	 */
	public void startMatch(){
		sendToAll("Game on!");
		Match match = new Match(this,id);
		match.BuildRobots(_robots);
		match.run();
	}
	/**
	 * Sends a message to all connected clients. This should be the primary channel of communication during a match.
	 * @param message
	 */
	public void sendToAll(String message){
		for(MatchSocket s:_connectedClients){
			s._session.getRemote().sendStringByFuture(message);
		}
	}
	/**
	 * Called if a client is ready. If the expected number of clients are ready, a check will be made on each websocket before starting the match.
	 */
	public synchronized void setReady(){
		if(_isRunning==false){
			clientsReady++;
			if(clientsReady==_expectedClients.size()){
				boolean go=true;
				for(MatchSocket m:_connectedClients){
					if(!m.ready){
						go=false;
					}
				}
				if(go){
					_isRunning=true;
					startMatch();
				}else{
					clientsReady--;
				}
			}
		}
		
	}
	/**
	 * This function sends an update of the match state to the clients every update cycle.
	 */
	public void SendMatchState(MatchState _matchState) {
		Gson gson= new Gson();
		sendToAll("UpdateState::"+gson.toJson(_matchState));
		
	}
	/**
	 * Tells the clients that the match is over, and calls for the handler to be closed. The closing part is still unstable.
	 */
	public void MatchEnded(MatchResult _matchResult) {
		Gson gson= new Gson();
		sendToAll("MatchEnded::"+gson.toJson(_matchResult));
		MatchMaker.getInstance().closeGameServer(id);
	}
	/**
	 * Temporary solution to initating the robots states. Should be changed to work with the initation of the match instead
	 * @param socket
	 */
	public void requestStartState(MatchSocket socket) {
		MatchState state  = new MatchState(0,_robots.size());
		for(int robots = 0; robots<_robots.size(); robots++){
			state.robotStates[robots] = new RobotState();
		}
		Gson gson = new Gson();
		socket._session.getRemote().sendStringByFuture("StartingState::"+gson.toJson(state));
	}
	
}
