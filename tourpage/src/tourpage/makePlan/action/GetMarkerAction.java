package tourpage.makePlan.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import tourpage.makePlan.db.PlaceDAO;

public class GetMarkerAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("getMarkerAction() execute.");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		PlaceDAO dao = new PlaceDAO();
	 	int level=15;
		String east = request.getParameter("east");
		String west = request.getParameter("west");
		String south = request.getParameter("south");
		String north = request.getParameter("north");
		int zoom = Integer.parseInt(request.getParameter("zoom"));
		if(zoom>10 && zoom < 14) level=70;
		List list= new ArrayList();
		if(id!=null){
			list = dao.getMarkerList(south,north,east,west,level,id);
		}else{
			list = dao.getMarkerList(south,north,east,west,level);
		}
		Gson gson = new Gson();
		String json = gson.toJson(list);
    	response.setContentType("text/html; charset=UTF-8");
    	PrintWriter out = response.getWriter();
    	out.println(json);
      	out.close();
		
		
		return null;
	}

}
