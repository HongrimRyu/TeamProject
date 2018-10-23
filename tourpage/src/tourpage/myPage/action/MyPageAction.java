package tourpage.myPage.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import tourpage.makePlan.db.PlanDAO;
import tourpage.makePlan.db.PlanDTO;

public class MyPageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Mypage_execute()");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		System.out.println(id);
		if(id == null){
			ActionForward forward = new ActionForward();
			forward.setPath("./Login.me");
			forward.setRedirect(true);
			return forward;
		}
		
		
		PlanDAO pdao = new PlanDAO();
		List<PlanDTO> list = pdao.getPlan(id);
		for(int i=0; i<list.size(); i++){
			PlanDTO pdto = list.get(i);
			String plan_detail = pdto.getPlan_detail();
			String place = pdao.getPlanPlace(plan_detail);
			pdto.setPlan_detail(place);
		}
		
		
		Gson gson = new Gson();
		String json = gson.toJson(list);
		response.setCharacterEncoding("utf8");
		response.getWriter().write(json);
		return null;
	}
	
}//MyPage
