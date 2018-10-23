package tourpage.share.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tourpage.share.db.SharePlanDAO;
import tourpage.share.db.SharePlanLikeDAO;


public class SharePlanLikeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Like Action excute");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		int plan_idx = Integer.parseInt(request.getParameter("plan_idx")); //?
		SharePlanLikeDAO spldao = new SharePlanLikeDAO();
		SharePlanDAO spdao = new SharePlanDAO();
		
		boolean check = spldao.check_insert_delete(plan_idx, id); // id와 plan_idx에 해당하는 값이 있으면 true 없으면 값을 집어넣고 false반환
		
		// true면 -1해주고 false면 +1해준다.
		// spldao에서 id로 체크를 하니깐 굳이 여기서는 id를 인자값으로 안넣었다.
		String count = String.valueOf(spdao.updateBestCount(plan_idx, check));
		System.out.println("count : " + count);
		
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(count);
		return null;
		
	}
}
