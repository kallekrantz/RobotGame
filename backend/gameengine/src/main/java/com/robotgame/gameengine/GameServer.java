package com.robotgame.gameengine;

import com.robotgame.gameengine.Network.MatchMakerSocket;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.websocket.server.WebSocketHandler;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * Created with IntelliJ IDEA.
 * User: Oskar
 * Date: 2013-11-08
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
public class GameServer
{
       public static void main(String[] args){
           Server server =new Server(61989);
           WebSocketHandler wsHandler = new WebSocketHandler(){
               @Override
                public void configure(WebSocketServletFactory factory){
                   factory.register(MatchMakerSocket.class);
               }
           };
            server.setHandler(wsHandler);
           try{
               server.start();
           }catch(Exception e){

           } try{
               server.join();
           }catch(Exception e){

           }



       }

}
