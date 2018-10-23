package tourpage.makePlan.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.makePlan.db.PlaceDAO;
import tourpage.makePlan.db.PlaceDTO;
import tourpage.makePlan.db.PlanDAO;
import tourpage.makePlan.db.PlanDTO;

public class ModifyPlan implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ModifyPlan exceute()");
		int plan_idx = Integer.parseInt(request.getParameter("plan_idx"));
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		System.out.println(id);
		String plan_detail="";
		PlanDAO pdao = new PlanDAO();
		List<PlanDTO> list = pdao.getPlan(id, plan_idx);
		Iterator<PlanDTO> iter = list.iterator();
		while(iter.hasNext()){
			PlanDTO ptdo=iter.next();
			plan_detail += ptdo.getPlan_detail() + ",";
			
		}
		plan_detail=plan_detail.substring(0, plan_detail.length()-1);
		System.out.println(plan_detail);
		PlaceDAO pldao= new PlaceDAO();
		Map<Integer, PlaceDTO> map = pldao.getPlaceList(plan_detail);
		request.setAttribute("placeinfo",map);
		request.setAttribute("planlist", list);
		ActionForward forward = new ActionForward();
		forward.setPath("./makePlan/modifyPlan.jsp");
		forward.setRedirect(false);	
		return forward;
	}

}
