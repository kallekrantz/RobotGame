package com.robotgame.gameengine.Network;

import java.util.Vector;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;


public class MatchMaker {

	private static final MatchMaker INSTANCE = new MatchMaker();
	
	private static final int _INITIALPORT = 49500;
	private int _serverIndex = 0;
	private int _numberOfServers=0;
	private Vector<Server> _servers = new Vector<Server>();
	private Vector<matchHandler> _matchHandlers = new Vector<matchHandler>();
	private Vector<matchMakerHandler> _1v1 = new Vector<matchMakerHandler>();
	private Vector<matchMakerHandler> _2v2 = new Vector<matchMakerHandler>();
	private Vector<matchMakerHandler> _2v1 = new Vector<matchMakerHandler>();
	private Vector<matchMakerHandler> _battleRoyale = new Vector<matchMakerHandler>();
	
	
	private static final int _1V1MODE = 0;
	private static final int _2V2MODE = 1;
	private static final int _2V1MODE = 2;
	private static final int _BATTLEROYALEMODE = 3;
	
	private int[] participants=new int[]{2,4,3,4};
	
	 public static MatchMaker getInstance()
	    {
	        return INSTANCE;
	    }
	 public synchronized void que(matchMakerHandler socket, int mode){
		 switch(mode){
		
		 case _1V1MODE:	
			_1v1.add(socket);
			System.out.println("one added");
			if(_1v1.size()==participants[mode]){
				launch1v1();
				_1v1.clear();
			}
			break;
		 case _2V2MODE:	
			_2v2.add(socket);
			if(_2v2.size()==participants[mode]){
				launch2v2();
				_2v2.clear();
			}
			break;
		 case _2V1MODE:
			_2v1.add(socket);
			if(_2v1.size()==participants[mode]){
				launch2v1();
				_2v1.clear();
			}
			break;
		 case _BATTLEROYALEMODE:
			_battleRoyale.add(socket);
			if(_battleRoyale.size()==participants[mode]){
				launchBattleRoyale();
				_battleRoyale.clear();
			}
			break;
		 }
		 
	 }
	 private void launchBattleRoyale() {
		 GameServer game=new GameServer(_serverIndex);
		Thread t=new Thread(game);
		t.start();
		String message = "port:"+(_INITIALPORT+_serverIndex);
		matchHandler match=new matchHandler(_serverIndex);
		match.setExpectedParticipants(_battleRoyale);
		_matchHandlers.add(_serverIndex,match);
		writeAllMembers(message,_BATTLEROYALEMODE);
		_matchHandlers.get(_serverIndex).generateUnpackingRoutine();
		_serverIndex++;
		
	}
	private void launch2v1() {
		GameServer game=new GameServer(_serverIndex);
		Thread t=new Thread(game);
		t.start();
		String message = "port:"+(_INITIALPORT+_serverIndex);
		matchHandler match=new matchHandler(_serverIndex);
		match.setExpectedParticipants(_2v1);
		_matchHandlers.add(_serverIndex,match);
		writeAllMembers(message,_2V1MODE);
		_matchHandlers.get(_serverIndex).generateUnpackingRoutine();
		_serverIndex++;
		
	}
	private void launch2v2() {
		GameServer game=new GameServer(_serverIndex);
		Thread t=new Thread(game);
		t.start();
		String message = "port:"+(_INITIALPORT+_serverIndex);
		matchHandler match=new matchHandler(_serverIndex);
		match.setExpectedParticipants(_2v2);
		_matchHandlers.add(_serverIndex,match);
		writeAllMembers(message,_2V2MODE);
		_matchHandlers.get(_serverIndex).generateUnpackingRoutine();
		_serverIndex++;
	}
	private void launch1v1() {
		GameServer game=new GameServer(_serverIndex);
		Thread t=new Thread(game);
		t.start();
		String message = "port:"+(_INITIALPORT+_serverIndex);
		matchHandler match=new matchHandler(_serverIndex);
		match.setExpectedParticipants(_1v1);
		_matchHandlers.add(_serverIndex,match);
		writeAllMembers(message,_1V1MODE);
		_matchHandlers.get(_serverIndex).generateUnpackingRoutine();
		_serverIndex++;
		_numberOfServers++;
		
	}
	public synchronized void leave(matchMakerHandler socket, int mode){
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
	 public void writeAllMembers(String message, int group) 
	    {
		 switch(group){
			
		 case _1V1MODE:	
			 for(matchMakerHandler client: _1v1){
		            client._session.getRemote().sendStringByFuture(message);
		        }
			break;
		 case _2V2MODE:	
			 for(matchMakerHandler client: _2v2){
		        client._session.getRemote().sendStringByFuture(message);
		     }
			break;
		 case _2V1MODE:
			 for(matchMakerHandler client: _2v1){
	            client._session.getRemote().sendStringByFuture(message);
	         }
			break;
		 case _BATTLEROYALEMODE:
			 for(matchMakerHandler client: _battleRoyale){
		         client._session.getRemote().sendStringByFuture(message);
		     }
			break;
		 }
	    }
	 public void makeGameServer(int sIndex){
		 Server server = new Server(_INITIALPORT+sIndex);
		 
		 WebSocketHandler wsHandler = new WebSocketHandler() {
                @Override
                public void configure(WebSocketServletFactory factory) {
                    factory.register(matchSocket.class);
                }
            };
           server.setHandler(wsHandler);
           _servers.add(sIndex,server);
   	     try {
				server.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   	     try {
				server.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println("end");
	 }
	 public class GameServer implements Runnable{
		 private int serverIndex;
		 public GameServer(int index){
			 serverIndex=index;
		 }
		 public void run(){
			 makeGameServer(serverIndex);
		 }
	 }
	 public void closeGameServer(int serverIndex){
		 try {
			 Thread.sleep(300);
			_servers.get(serverIndex).stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 _servers.remove(serverIndex);
		 _matchHandlers.remove(serverIndex);
		 _numberOfServers--;
		 if(_numberOfServers==0){
			 _serverIndex=0;
		 }
		 System.out.println("Skiten funkar iallafall");
	 }
	public matchHandler getHandler(int port) {
		return _matchHandlers.get(port-49500);
		
	}
}
