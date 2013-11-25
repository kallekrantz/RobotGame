package com.robotgame.gameengine.Network;

import java.util.Vector;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * The main handler for network communication. 
 * @author Rickard Gräntzelius
 *
 */
public class MatchMaker {

	/**
	 * Matchmaker should be globally available, and is therefore a singleton.
	 */
	private static final MatchMaker INSTANCE = new MatchMaker();
	
	private static final int _INITIALPORT = 49500;
	private int _serverIndex = 0;
	private int _numberOfServers=0;
	/**
	 * A list of active matchservers, mainly so they can be terminated after use.
	 */
	private Vector<Server> _servers = new Vector<Server>();
	/**
	 * A list of active matchhandlers, for the clients to be able to connect with them, and for termination after use
	 */
	private Vector<MatchHandler> _matchHandlers = new Vector<MatchHandler>();
	/**
	 * List of users in queue for a 1v1 match. 
	 */
	private Vector<MatchMakerSocket> _1v1 = new Vector<MatchMakerSocket>();
	/**
	 * List of users in queue for a 2v2 match
	 */
	private Vector<MatchMakerSocket> _2v2 = new Vector<MatchMakerSocket>();
	/**
	 * List of users in queue for a 2v1 match
	 */
	private Vector<MatchMakerSocket> _2v1 = new Vector<MatchMakerSocket>();
	/**
	 * List of users in queue for a battle royale match
	 */
	private Vector<MatchMakerSocket> _battleRoyale = new Vector<MatchMakerSocket>();
	
	
	private static final int _1V1MODE = 0;
	private static final int _2V2MODE = 1;
	private static final int _2V1MODE = 2;
	private static final int _BATTLEROYALEMODE = 3;
	/**
	 * The required number of participants for each type of match
	 */
	private int[] participants=new int[]{2,4,3,4};
	
	 public static MatchMaker getInstance()
	    {
	        return INSTANCE;
	    }
	 /**
	  * Puts a client in a lobby for the desired match-type. 
	  * @param socket
	  * @param type 
	  */
	 public synchronized void queue(MatchMakerSocket socket, int type){
		 switch(type){
		
		 case _1V1MODE:	
			_1v1.add(socket);
			if(_1v1.size()==participants[type]){
				launch1v1();
				_1v1.clear();
			}
			break;
		 case _2V2MODE:	
			_2v2.add(socket);
			if(_2v2.size()==participants[type]){
				launch2v2();
				_2v2.clear();
			}
			break;
		 case _2V1MODE:
			_2v1.add(socket);
			if(_2v1.size()==participants[type]){
				launch2v1();
				_2v1.clear();
			}
			break;
		 case _BATTLEROYALEMODE:
			_battleRoyale.add(socket);
			if(_battleRoyale.size()==participants[type]){
				launchBattleRoyale();
				_battleRoyale.clear();
			}
			break;
		 }
		 
	 }
	 /**
	  * Launches a matchserver and sends connection information to the clients in the match
	  */
	 private void launchBattleRoyale() {
		 GameServer game=new GameServer(_serverIndex);
		Thread t=new Thread(game);
		t.start();
		String message = "port:"+(_INITIALPORT+_serverIndex);
		MatchHandler match=new MatchHandler(_serverIndex);
		match.setExpectedParticipants(_battleRoyale);
		_matchHandlers.add(_serverIndex,match);
		writeAllMembers(message,_BATTLEROYALEMODE);
		_matchHandlers.get(_serverIndex).generateUnpackingRoutine();
		_serverIndex++;
		
	}
	 /**
	  * Launches a matchserver and sends connection information to the clients in the match
	  */
	private void launch2v1() {
		GameServer game=new GameServer(_serverIndex);
		Thread t=new Thread(game);
		t.start();
		String message = "port:"+(_INITIALPORT+_serverIndex);
		MatchHandler match=new MatchHandler(_serverIndex);
		match.setExpectedParticipants(_2v1);
		_matchHandlers.add(_serverIndex,match);
		writeAllMembers(message,_2V1MODE);
		_matchHandlers.get(_serverIndex).generateUnpackingRoutine();
		_serverIndex++;
		
	}
	/**
	  * Launches a matchserver and sends connection information to the clients in the match
	  */
	private void launch2v2() {
		GameServer game=new GameServer(_serverIndex);
		Thread t=new Thread(game);
		t.start();
		String message = "port:"+(_INITIALPORT+_serverIndex);
		MatchHandler match=new MatchHandler(_serverIndex);
		match.setExpectedParticipants(_2v2);
		_matchHandlers.add(_serverIndex,match);
		writeAllMembers(message,_2V2MODE);
		_matchHandlers.get(_serverIndex).generateUnpackingRoutine();
		_serverIndex++;
	}
	/**
	  * Launches a matchserver and sends connection information to the clients in the match
	  */
	private void launch1v1() {
		GameServer game=new GameServer(_serverIndex);
		Thread t=new Thread(game);
		t.start();
		String message = "port:"+(_INITIALPORT+_serverIndex);
		MatchHandler match=new MatchHandler(_serverIndex);
		match.setExpectedParticipants(_1v1);
		_matchHandlers.add(_serverIndex,match);
		writeAllMembers(message,_1V1MODE);
		_matchHandlers.get(_serverIndex).generateUnpackingRoutine();
		_serverIndex++;
		_numberOfServers++;
		
	}
	/**
	 * Leave a client from a match queue.
	 * @param socket
	 * @param mode
	 */
	public synchronized void leave(MatchMakerSocket socket, int mode){
		 switch(mode){
			
		 case _1V1MODE:	
			_1v1.remove(socket);
			break;
		 case _2V2MODE:	
			_2v2.remove(socket);
			break;
		 case _2V1MODE:
			_2v1.remove(socket);
			break;
		 case _BATTLEROYALEMODE:
			_battleRoyale.remove(socket);
			break;
		 }
	 }
	/**
	 * Writes a message to all members of a certain lobbygroup.
	 * @param message
	 * @param group
	 */
	 public void writeAllMembers(String message, int group) 
	    {
		 switch(group){
			
		 case _1V1MODE:	
			 for(MatchMakerSocket client: _1v1){
		            client._session.getRemote().sendStringByFuture(message);
		        }
			break;
		 case _2V2MODE:	
			 for(MatchMakerSocket client: _2v2){
		        client._session.getRemote().sendStringByFuture(message);
		     }
			break;
		 case _2V1MODE:
			 for(MatchMakerSocket client: _2v1){
	            client._session.getRemote().sendStringByFuture(message);
	         }
			break;
		 case _BATTLEROYALEMODE:
			 for(MatchMakerSocket client: _battleRoyale){
		         client._session.getRemote().sendStringByFuture(message);
		     }
			break;
		 }
	    }
	 /**
	  * Starts up a new matchserver
	  * @param sIndex
	  */
	 public void makeGameServer(int sIndex){
		 Server server = new Server(_INITIALPORT+sIndex);
		 
		 WebSocketHandler wsHandler = new WebSocketHandler() {
                @Override
                public void configure(WebSocketServletFactory factory) {
                    factory.register(MatchSocket.class);
                }
            };
           server.setHandler(wsHandler);
           _servers.add(sIndex,server);
   	     try {
				server.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
   	     try {
				server.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        System.out.println("end");
	 }
	 /**
	  * A runnable class to create a separate thread for each match
	  *
	  */
	 public class GameServer implements Runnable{
		 private int serverIndex;
		 public GameServer(int index){
			 serverIndex=index;
		 }
		 public void run(){
			 makeGameServer(serverIndex);
		 }
	 }
	 /**
	  * Method for closing a server when the match is finished. Still unstable
	  * @param serverIndex
	  */
	 public void closeGameServer(int serverIndex){
		 try {
			 Thread.sleep(300);
			_servers.get(serverIndex).stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 _servers.remove(serverIndex);
		 _matchHandlers.remove(serverIndex);
		 _numberOfServers--;
		 if(_numberOfServers==0){
			 _serverIndex=0;
		 }
	 }
	 /**
	  * Method for a client to get the right matchhandler based on the port of the matchsocket.
	  * @param port
	  * @return
	  */
	public MatchHandler getHandler(int port) {
		return _matchHandlers.get(port-49500);
		
	}
}
