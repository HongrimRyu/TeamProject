package tourpage.group.action;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

import tourpage.group.db.GroupBean;
import tourpage.group.db.GroupDAO;
import tourpage.makePlan.db.PlaceDAO;
import tourpage.makePlan.db.PlaceDTO;


@ServerEndpoint("/GroupChat.gw/{groupid}/{nick}")
public class GroupChat {
	    private static Map<Session,String> clients = Collections.synchronizedMap(new HashMap<Session,String>());
	    private static Map<String,String> groupUsers = Collections.synchronizedMap(new HashMap<String,String>());
	    private volatile String groupid;
	    private volatile String nick; 
	    @OnMessage
	    public void onMessage(String message, Session session) throws IOException, EncodeException {
	    	System.out.println("보냄");
	        System.out.println(message);
	        synchronized(clients) {
	        	String users="@#users : ";
    	        for(String n: groupUsers.keySet()){
    	        	if(groupUsers.get(n).equals(groupid)){
    	        		users += n+",";
    	        	}
            	}

    	        for(Session client : clients.keySet()) {
	                if(clients.get(client).equals(groupid)){
	                	if(!client.equals(session)) {
	                	  	if(message.equals("action")){
	    	            		client.getBasicRemote().sendText("action");
	    	            	}else{
			                    client.getBasicRemote().sendText(message);
			                    client.getBasicRemote().sendText(users);}
		                }
	                	
	                }
	            }
	        }
	    }
	    
	    @OnOpen
	    public void onOpen(@PathParam("groupid") String groupid,@PathParam("nick") String nick,Session session) throws IOException{
	    	System.out.println("첫번째 소켓");
	        System.out.println(session);
	        this.groupid = groupid;
	        this.nick = nick;
	        clients.put(session,groupid);
	        groupUsers.put(nick,groupid);
	        
	        String users="@#users : ";
	        
	        for(String n: groupUsers.keySet()){
	        	if(groupUsers.get(n).equals(groupid)){
	        		users += n+",";
	        	}
        	}
	        synchronized(clients) {
	            for(Session client : clients.keySet()) {
	            	if(clients.get(client).equals(groupid)){
	                	client.getBasicRemote().sendText(users);
	                }
	            }
	        }
	        System.out.println(groupid+users);
	    }
	    
	    @OnClose
	    public void onClose(Session session) throws IOException {
	    	System.out.println("session1:close");
	        clients.remove(session);
	        groupUsers.remove(nick);
	        
	        String users="@#users : ";
	        
	        for(String n: groupUsers.keySet()){
	        	if(groupUsers.get(n).equals(groupid)){
	        		users += n+",";
	        	}
        	}
	        synchronized(clients) {
	            for(Session client : clients.keySet()) {
	            	if(clients.get(client).equals(groupid)){
	                	client.getBasicRemote().sendText(users);
	                }
	            }
	        }
	        System.out.println(groupid+users);
	    }
}
