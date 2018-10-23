package tourpage.makePlan.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class PlanDAO {

	// 디비 연결 
		// 드라이버로드/디비연결/sql쿼리 /stmt,pstmt/실행/결과값(rs)/자원해제 
		Connection con= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql ="";
		
		
		private Connection getCon() throws Exception{
			Context init = new InitialContext();
			DataSource ds =(DataSource)init.lookup("java:comp/env/jdbc/jsptest");
			con = ds.getConnection();
			return con;
		}
		public void CloseDB(){
			if(rs !=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt !=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con !=null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		// pdao.savePlan(plan_idx,date,planInfos,id);
		public void savePlan(String date, String[] planInfos,String subject,String id){
			int plan_idx=0;
			try {
				con=getCon();
				sql="select max(plan_idx) from planlist";
				pstmt=con.prepareStatement(sql);
				rs=pstmt.executeQuery();
				if(rs.next()){
					if(rs.getInt(1)<1){
						plan_idx=1;
					}else{
						plan_idx += rs.getInt(1)+1;
					}
					for(int i=0,max=planInfos.length; i < max;i++){
						sql="insert into planlist (plan_idx, plan_name,plan_startdate,plan_days,plan_writer,plan_time,id,plan_detail) values (?,?,?,?,?,now(),?,?)";
						pstmt=con.prepareStatement(sql);
						pstmt.setInt(1, plan_idx);
						pstmt.setString(2, subject);
						pstmt.setString(3, date);
						pstmt.setInt(4, (i+1));
						pstmt.setString(5, id);
						pstmt.setString(6, id);
						pstmt.setString(7, planInfos[i]);
						pstmt.executeUpdate();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				CloseDB();
			}
			
		}
		
		// pdao.getPlan(id)
		public List<PlanDTO> getPlan(String id){
			List<PlanDTO> planList = new ArrayList();
			PlanDTO pdto = null;
			Map<Integer, Integer> map = new HashMap<Integer, Integer>();
			try {
				con = getCon();
				sql = "select plan_idx, max(plan_days) 'maxDay' from planlist group by plan_idx, id having id = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				while(rs.next()){
					map.put(rs.getInt("plan_idx"), rs.getInt("maxDay"));
					}
				
					sql = "select * from planlist where id = ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, id);
					rs = pstmt.executeQuery();
					while(rs.next()){
						pdto = new PlanDTO();
						pdto.setId(rs.getString("id"));
						pdto.setPlan_idx(rs.getInt("plan_idx"));
						pdto.setPlan_name(rs.getString("plan_name"));
						pdto.setPlan_startdate(rs.getString("plan_startdate"));
						pdto.setPlan_detail(rs.getString("plan_detail"));
						pdto.setPlan_days(rs.getInt("plan_days"));
						pdto.setMaxDay(map.get(rs.getInt("plan_idx")));
						planList.add(pdto);
					}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("getPlan 오류");
			}finally{
				CloseDB();
			}
			return planList;
		}
		//pdao.getPlan(id, plan_idx)
		public List<PlanDTO> getPlan(String id, int plan_idx){
			List<PlanDTO> planList = new ArrayList<PlanDTO>();
			PlanDTO pdto = null;
			try {
				con = getCon();
				sql = "select * from planlist where id = ? and plan_idx = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setInt(2, plan_idx);
				rs = pstmt.executeQuery();
				while(rs.next()){
					pdto = new PlanDTO();
					pdto.setId(rs.getString("id"));
					pdto.setPlan_days(rs.getInt("plan_days"));
					pdto.setPlan_detail(rs.getString("plan_detail"));
					pdto.setPlan_idx(rs.getInt("plan_idx"));
					pdto.setPlan_name(rs.getString("plan_name"));
					pdto.setPlan_startdate(rs.getString("plan_startdate"));
					planList.add(pdto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				CloseDB();
			}
			return planList;
			
		}
				// pdao.getPlanPlace(plan_detail)
		public String getPlanPlace(String plan_detail){
			String place = "";			
			String detail[] = plan_detail.split(",");
			int prod = 0;
			try {
				con = getCon();
				sql = "select name from placeinfo where prod = ?";
				pstmt = con.prepareStatement(sql);
				for(int i=0; i<detail.length; i++){
					 prod = Integer.parseInt(detail[i].toString());
				pstmt.setInt(1, prod);
				rs = pstmt.executeQuery();
				while(rs.next()){
					place += rs.getString("name")+ ", ";
				}
			  }
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("getPlanPlace 오류");
			} finally {
				CloseDB();
			}
			return place.substring(0, place.length()-2);
		}
		
		public boolean deletePlan(int plan_idx){ // 마이페이지에서 일정삭제하기 눌렀을 때
			boolean check = false;
			try{
				con = getCon();
				sql = "delete from planlist where plan_idx = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, plan_idx);
			    pstmt.executeUpdate();
			    check = true;
			    System.out.println("Mypage에서 plan_idx에 해당하는 Plan삭제");
			} catch(Exception e){
				e.printStackTrace();
				System.out.println("Mypage에서 Plan삭제실패");
			} finally{
				CloseDB();
			}
			return check;
		}
		public void modifyPlan(int plan_idx, String date, String[] planInfos, String subject, String id) {
			
			try {
				con=getCon();
				sql="select max(plan_days) from planlist where plan_idx= ? ";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, plan_idx);
				rs=pstmt.executeQuery();
				if(rs.next()){
					int days = rs.getInt(1);
					for(int i=0, max=planInfos.length ; i < max ; i++){
						if((i+1)<=days){//udate 기존일정수정
							sql="update planlist set plan_startdate=?,plan_detail=?, plan_name=? where plan_idx=? && plan_days=?";
							pstmt = con.prepareStatement(sql);
							pstmt.setString(1, date);
							pstmt.setString(2, planInfos[i]);
							pstmt.setInt(4, plan_idx);
							pstmt.setString(3, subject);
							pstmt.setInt(5, i+1);
							pstmt.executeUpdate();
						}else if((i+1)>days){//insert 추가된일정
							sql="insert into planlist (plan_idx, plan_name,plan_startdate,plan_days,plan_writer,plan_time,id,plan_detail) values (?,?,?,?,?,now(),?,?)";
							pstmt=con.prepareStatement(sql);
							pstmt.setInt(1, plan_idx);
							pstmt.setString(2, subject);
							pstmt.setString(3, date);
							pstmt.setInt(4, (i+1));
							pstmt.setString(5, id);
							pstmt.setString(6, id);
							pstmt.setString(7, planInfos[i]);
							pstmt.executeUpdate();
						}
						
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				CloseDB();
			}
			
		}
		
		
		
}
