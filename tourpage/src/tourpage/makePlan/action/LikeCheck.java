package tourpage.makePlan.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.makePlan.db.PlaceDAO;

public class LikeCheck implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Like Action excute");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		int prod = Integer.parseInt(request.getParameter("prod")); 
		if(id==null){
        	response.setContentType("text/html; charset=UTF-8");
        	PrintWriter out = response.getWriter();
        	out.println("{\"chk\": false}");
          	out.close();
	    	return null;
		}
		PlaceDAO pdao = new PlaceDAO();
		if(pdao.getLikePlace(id,prod)){
			response.setContentType("text/html; charset=UTF-8");
        	PrintWriter out = response.getWriter();
        	out.println("{\"chk\": true,\"like\": \"like\"}");
          	out.close();
	    	return null;
		}
			response.setContentType("text/html; charset=UTF-8");
	    	PrintWriter out = response.getWriter();
	    	out.println("{\"chk\": true,\"like\": \"unlike\"}");
	      	out.close();
	    	return null;
		
	}

}
