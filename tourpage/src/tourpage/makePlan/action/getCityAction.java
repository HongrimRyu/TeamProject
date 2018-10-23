package tourpage.makePlan.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tourpage.makePlan.db.PlaceDAO;

public class getCityAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("getCityAction execute!");
        PlaceDAO dao = new PlaceDAO();
     	int level=15;
    	String east = request.getParameter("east");
    	String west = request.getParameter("west");
    	String south = request.getParameter("south");
    	String north = request.getParameter("north");
    	String zoom = request.getParameter("zoom");
    	System.out.println(level+","+zoom);
    	List list = dao.getCityList(south,north,east,west,level);
    	Gson gson = new Gson();
    	String json = gson.toJson(list);
    	response.setContentType("text/html; charset=UTF-8");
    	PrintWriter out = response.getWriter();
    	out.println(json);
      	out.close();
		
		return null;
	}

}
