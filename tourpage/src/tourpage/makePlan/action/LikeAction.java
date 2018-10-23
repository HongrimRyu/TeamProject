package tourpage.makePlan.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.makePlan.db.PlaceDAO;

public class LikeAction implements Action {

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
		if(pdao.likePlace(id,prod)){
			pdao.updateLike(prod);
        	response.setContentType("text/html; charset=UTF-8");
        	PrintWriter out = response.getWriter();
        	out.println("{\"chk\": true}");
          	out.close();
	    	return null;
		}
		
		
		return null;
	}

}
