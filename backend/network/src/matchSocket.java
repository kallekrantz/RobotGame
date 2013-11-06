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
    	switch(message.substring(0,1)){
    	case JOIN:
    		joinRequest req = g.fromJson(message.substring(1), joinRequest.class);
    		_user = req.user;
    		_handler = MatchMaker.getInstance().getHandler(req.port);
    		if(_handler.join(this)){
    			
    		}else{
    			System.out.println("halp "+_user);
    		}
    		
    		break;
    	case READY:
    		ready=true;
    		_handler.setReady();
    		break;
    	case KEYPRESS:
    		System.out.println("nu händer det");
    		_handler.sendToAll(_user+" pressed "+message.substring(1));
    		break;
    	default:
    		System.out.println("nope");
    		break;
    	
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
