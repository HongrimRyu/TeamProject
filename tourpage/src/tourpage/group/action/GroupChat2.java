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


@ServerEndpoint("/GroupChat2.gw/{groupid}/{nick}")
public class GroupChat2 {
	    private static Map<Session,String> clients = Collections.synchronizedMap(new HashMap<Session,String>());
	    private static Map<String,String> groupUsers = Collections.synchronizedMap(new HashMap<String,String>());
	    private volatile String groupid;
	    private volatile String nick; 
	    @OnMessage
	    public void onMessage(String message, Session session) throws IOException, EncodeException {
	    	System.out.println("두번째 소켓");
	        System.out.println("메세지"+ message);
	        String[] action = message.split("/");
	        synchronized(clients) {
	        	String users="@#users : ";
    	        for(String n: groupUsers.keySet()){
    	        	if(groupUsers.get(n).equals(groupid)){
    	        		users += n+",";
    	        	}
            	}
    	        if(action[0].equals("sort")||action[0].equals("action")){
	    	    	System.out.println("일정변경");
	    	    	String plan_detail[]=action[1].substring(0, action[1].length()-1).split("@");
	    	    	GroupDAO gdao= new GroupDAO();
	    	    	gdao.updateDetail(groupid,action[1].substring(0, action[1].length()-1));
	    	    	String p_detail="";
	    	    	for(int i=0,max=plan_detail.length ; i<max ; i+=1 ){
	    	    		p_detail += plan_detail[i]+",";
	    	    	}
	    	    	p_detail=p_detail.substring(0, p_detail.length()-1);
	    	    	System.out.println(p_detail);
	    	        PlaceDAO pdao= new PlaceDAO();
	    	        Map<Integer, PlaceDTO>  pdto= pdao.getPlaceList(p_detail);
	    	        Gson gson = new Gson();
	    	        String json = gson.toJson(pdto);
	    	        System.out.println(json);
	    	        Iterator<Session> iter = clients.keySet().iterator();
	    	        while(iter.hasNext()){
	    	        	Session clients = iter.next();
	    	        	if(!clients.equals(session)) clients.getBasicRemote().sendText("sort/"+json+"/"+action[1].substring(0, action[1].length()-1));
	    	        }
	    	        return;
	    	    }else if(action[0].equals("delete")){
	    	        Iterator<Session> iter = clients.keySet().iterator();
	    	        while(iter.hasNext()){
	    	        	Session clients = iter.next();
	    	        	if(!clients.equals(session)) clients.getBasicRemote().sendText("delete/"+action[1]+"/"+action[2]);
	    	        }
	    	        return;
	    	    }else if(action[1].equals("open")){
	    	    	
	    	    	GroupDAO gdao = new GroupDAO();
	    	    	GroupBean gb = gdao.getGroupPlan(groupid);
	    	    	String plan_detail[]=gb.getPlan_detail().split("@");
	    	    	String p_detail="";
	    	    	for(int i=0,max=plan_detail.length ; i<max ; i+=1 ){
	    	    		p_detail += plan_detail[i]+",";
	    	    	}
	    	    	p_detail=p_detail.substring(0, p_detail.length()-1);
	    	    	System.out.println(p_detail);
	    	        PlaceDAO pdao= new PlaceDAO();
	    	        Map<Integer, PlaceDTO>  pdto= pdao.getPlaceList(p_detail);
	    	        Gson gson = new Gson();
	    	        String json = gson.toJson(gb);
	    	        String json1 = gson.toJson(pdto);
	    	        Iterator<Session> iter = clients.keySet().iterator();
	    	        int i=0;
	    	        while(iter.hasNext()){
	    	        	
	    	        	Session clients = iter.next();
	    	        	if(clients.equals(session)) clients.getBasicRemote().sendText("open/"+json+"/"+json1+"/"+i);
	    	        	i++;
	    	        }
	    	    	System.out.println("들어왔다");
	    	    }else if(action[0].equals("rmday")){
	    	    	GroupDAO gdao = new GroupDAO();
	    	    	GroupBean gb = gdao.getGroupPlan(groupid);
	    	    	String plan_detail[]=gb.getPlan_detail().split("@");
	    	    	String plans="";
	    	    	for(int i=0,max=plan_detail.length;i<max;i+=1){
	    	    		if(i!=Integer.parseInt(action[1])){
	    	    			plans+=plan_detail+"@";
	    	    		}
	    	    	}
	    	    	plans=plans.substring(0, plans.length()-1);
	    	    	gdao.updateDetail(groupid,plans,1);
	    	        Iterator<Session> iter = clients.keySet().iterator();
	    	        while(iter.hasNext()){
	    	        	
	    	        	Session clients = iter.next();
	    	        	if(clients.equals(session)) clients.getBasicRemote().sendText("rmday/"+action[1]);
	    	        	
	    	        }
	    	    }
   
	        }
	    }
	    
	    @OnOpen
	    public void onOpen(@PathParam("groupid") String groupid,@PathParam("nick") String nick,Session session) throws IOException{
	    	System.out.println("두번째 소켓");
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
	    	System.out.println("session2:close");
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
