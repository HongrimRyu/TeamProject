package tourpage.share.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import tourpage.makePlan.db.PlanDAO;
import tourpage.member.db.MemberBean;

public class SharePlanDAO {

   // SharePlanBean저장된 정보를 받아서
      // 데이터 처리(디비 저장)

      // 공통으로 사용되는 레퍼런스를 미리 생성
      // 인스턴스변수 (전역변수)
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = "";

      private Connection getConnection() throws Exception {
         Context init = new InitialContext();
         DataSource ds =(DataSource) init.lookup("java:comp/env/jdbc/jsptest");
         con = ds.getConnection();
         return con;         
      }

      // DB에 데이터 자원을 해제 시키는 메서드
      // closeDB() 메서드
      public void closeDB() {
         if (rs != null) {
            try {
               rs.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
         if (pstmt != null) {
            try {
               pstmt.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
         if (con != null) {
            try {
               con.close();
            } catch (SQLException e) {
               e.printStackTrace();
            }
         }
      }
      
      
      // 게시판 기입 기능(insertShareplan)
      public boolean insertShareplan(SharePlanBean spb) {
    	  boolean check = false;
         try {
            // 1단계 드라이버로드(프로그램 설치)
            // 2단계 디비연결
            con = getConnection();
            // 3단계 sql 객체 생성
            sql = "select max(idx) from shareplan";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            int max = 0;
            if(rs.next()){
               max = rs.getInt(1)+1;
            }
            sql = "insert into shareplan"
            		+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?,now(), ?, ?, ?, ?)";
 
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, max);
            pstmt.setString(2, spb.getPlan_name());
            pstmt.setInt(3, spb.getPlan_idx());
            pstmt.setInt(4, 0);
            pstmt.setInt(5, 0);
            pstmt.setString(6, spb.getTravel_period());
            pstmt.setInt(7, spb.getPlan_days());
            pstmt.setString(8, spb.getComment());
            pstmt.setString(9, spb.getPlan_detail());//이걸 바꿔야한다.        
            pstmt.setString(10, spb.getWriter_ip());
            pstmt.setString(11, spb.getId());
            pstmt.setString(12, spb.getNick());
            pstmt.setString(13, spb.getImage());
            
            // 4단계 sql 객체 실행
            pstmt.executeUpdate();
            System.out.println("일정공유 성공(insert)");
            check = true;
            // pstmt.close();
            // con.close();
         } catch (SQLException e) {
            System.out.println("일정공유 실패(insert)");
            e.printStackTrace();
         } catch (Exception e) {
            e.printStackTrace();
         } finally {
            // 예외발생 여부와 상관없이
            // 반드시 실행해야하는 코드 작성
            closeDB();
         }
         return check;
      }// 일정공유 삽입 기능(insertShareplan)
      
      
      public String getImage(String plan_detail){
    	  String result = "";
    	  try {
			con = getConnection();
			String[] cutter = plan_detail.split(",");
			int firstplace = Integer.parseInt(cutter[0].toString());
			sql = "select image from placeinfo where prod = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, firstplace);
			rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getString(1);
			}
		} catch (Exception e) {
			System.out.println("placeinfo image 가져오기 실패");
			e.printStackTrace();
		} finally {
			closeDB();
		}
    	return result;
      }
      
      public List<SharePlanBean> getBoardList(){ // 공유완료된 shareplan에서 값 다 가져오기
    	  List<SharePlanBean> planList = new ArrayList<SharePlanBean>();
    	  try {
			con = getConnection();
			sql = "select * from shareplan order by idx desc ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				SharePlanBean spb = new SharePlanBean();
				spb.setIdx(rs.getInt("idx"));
				spb.setPlan_name(rs.getString("plan_name"));
				spb.setPlan_idx(rs.getInt("plan_idx"));
				spb.setBest(rs.getInt("best"));
				spb.setReadcount(rs.getInt("readcount"));
				spb.setTravel_period(rs.getString("travel_period"));
				spb.setPlan_days(rs.getInt("plan_days"));
				spb.setComment(rs.getString("comment"));
				spb.setPlan_detail(rs.getString("plan_detail"));
				spb.setRegdate(rs.getDate("regdate"));
				spb.setWriter_ip(rs.getString("writer_ip"));
				spb.setId(rs.getString("id"));
				spb.setNick(rs.getString("nick"));
				spb.setImage(rs.getString("image"));
				planList.add(spb);
			}
			System.out.println("getBoardList 불러오기 성공");
		} catch (Exception e) {
			System.out.println("getBoardList 실패");
			e.printStackTrace();
		} finally {
			closeDB();
		}
    	  
    	return planList;  
      }
      
      public List<SharePlanBean> getBoardList(int plan_idx){ // 오버로딩.해당 plan_ipx에 관련된 SharePlan테이블에서 가져오기
    	  List<SharePlanBean> planList = new ArrayList<SharePlanBean>();
    	  try {
			con = getConnection();
			sql = "select * from shareplan where plan_idx = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, plan_idx);
			rs = pstmt.executeQuery();
			while(rs.next()){
				SharePlanBean spb = new SharePlanBean();
				spb.setIdx(rs.getInt("idx"));
				spb.setPlan_name(rs.getString("plan_name"));
				spb.setPlan_idx(rs.getInt("plan_idx"));
				spb.setBest(rs.getInt("best"));
				spb.setReadcount(rs.getInt("readcount"));
				spb.setTravel_period(rs.getString("travel_period"));
				spb.setPlan_days(rs.getInt("plan_days"));
				spb.setComment(rs.getString("comment"));
				spb.setPlan_detail(rs.getString("plan_detail"));
				spb.setRegdate(rs.getDate("regdate"));
				spb.setWriter_ip(rs.getString("writer_ip"));
				spb.setId(rs.getString("id"));
				spb.setNick(rs.getString("nick"));
				spb.setImage(rs.getString("image"));
				planList.add(spb);
			}
			System.out.println("getBoardList(int plan_idx) 불러오기 성공");
		} catch (Exception e) {
			System.out.println("getBoardList(int plan_idx) 실패");
			e.printStackTrace();
		} finally {
			closeDB();
		}
    	  
    	return planList;  
      }
      //공유한 일정 중복 확인하는 메소드
      public boolean getduplicationcheck(int plan_idx){
          boolean check = false; // false=중복이 없다
          
          try {
            con = getConnection();
            sql = "select * from shareplan where plan_idx = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, plan_idx);
            rs = pstmt.executeQuery();
            if(rs.next()){
               check = true;
            }
            System.out.println("getduplicationcheck(int plan_idx) 성공");
         } catch (Exception e) {
            System.out.println("getduplicationcheck(int plan_idx) 실패");
            e.printStackTrace();
         } finally {
            closeDB();
         }
          return check;
       }
       //조회수 업데이트 메소드
       public void updateReadCount(int idx){
    	   try {
			con = getConnection();
			sql = "update shareplan set readcount = readcount+1 where plan_idx = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
			System.out.println("조회수 증가 성공");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            closeDB();
         }
       }//updateReadCount
       //공유일정 삭제하는 메소드
       public boolean deleteSharePlan(int plan_idx){
    	   boolean check = false;
    	   try {
			con = getConnection();
			sql = "delete from shareplan where plan_idx = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, plan_idx);
			pstmt.executeUpdate();
			sql = "delete from sharecomment where board_idx = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, plan_idx);
			pstmt.executeUpdate();
			check = true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeDB();
		}
    	   return check;
       }//deleteSharePlan
       //공유일정 수정하는 메소드(단순 코멘트 업데이트)
       public boolean updateSharePlan(SharePlanBean spb){
    	   boolean check = false;
    	   try {
			con = getConnection();
			sql = "update shareplan set comment = ?, regdate = now() where plan_idx = ? and plan_days = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, spb.getComment());
			pstmt.setInt(2, spb.getPlan_idx());
			pstmt.setInt(3, spb.getPlan_days());
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	   return check;
       }
     //좋아요 업데이트 메소드
       public int updateBestCount(int idx, boolean check){
    	   int count = 0;
    	   try {
			con = getConnection();
			if(check) {
				sql = "update shareplan set best = best-1 where plan_idx = ?";
				System.out.println("감소시켜준다");
			}
			else {
				sql = "update shareplan set best = best+1 where plan_idx = ?";
				System.out.println("증가시켜준다");
			}
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			pstmt.executeUpdate();
			
			sql = "select best from shareplan where plan_idx = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
			
			System.out.println("좋아요 증가or감소 성공");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("좋아요 증가 실패");
		} finally {
            closeDB();
        }
    	return count;   
    	   
       }//updateBestCount
      
}
