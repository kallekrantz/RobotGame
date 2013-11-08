package com.robotgame.network;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

public class WebSocketText {
	
    public static void main(String[] args) throws Exception {
        Matcher m= new Matcher();
        m.start();
        
    }
    public static class Matcher extends Thread{
    	Server server;
    	WebSocketHandler wsHandler; 
        public Matcher(){
        	server = new Server(61989);
        	wsHandler = new WebSocketHandler() {
                @Override
                public void configure(WebSocketServletFactory factory) {
                    factory.register(matchMakerHandler.class);
                }
            };
        }
    	@Override
    	public void start(){
    		 server.setHandler(wsHandler);
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
    		
    	}
    }
   
}