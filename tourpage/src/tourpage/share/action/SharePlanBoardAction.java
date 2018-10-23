package tourpage.share.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import tourpage.makePlan.db.PlanDAO;
import tourpage.makePlan.db.PlanDTO;
import tourpage.share.db.SharePlanBean;
import tourpage.share.db.SharePlanDAO;

public class SharePlanBoardAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("SharePlanBoardAction_execute()");
	      request.setCharacterEncoding("utf-8");
	      HttpSession session = request.getSession();
	      String id = (String)session.getAttribute("id");
	      
	      SharePlanDAO spdao = new SharePlanDAO();
	      List<SharePlanBean> list = spdao.getBoardList();
	    
	      Gson gson = new Gson();
	      String json = gson.toJson(list);
	      response.setCharacterEncoding("utf8");
	      response.getWriter().write(json);
	      return null;
	}
}
