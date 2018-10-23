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

public class ShareCommentDAO {
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = "";
	//커넥션 풀
	private Connection getConnection() throws Exception{
		Context init = new InitialContext();
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/jsptest");
		con = ds.getConnection();
		return con;
	}//getConnection()
	//자원해제 메소드 
	public void DBClose(){
		if(rs != null){
			try{
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(pstmt != null){
			try{
			pstmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(con != null){
			try{	
			con.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
}//DBClose
	//댓글을 저장하는 메소드
	public boolean insertComment(ShareCommentBean scb){
		boolean check = false;
		int num = 0;
		try {
			con = getConnection();
			sql = "select max(idx) from sharecomment";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				num = rs.getInt(1)+1;
			}
			sql = "insert into sharecomment values(?,?,?,?,now(),?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setInt(2, scb.getBoard_idx());
			pstmt.setString(3, scb.getNick());
			pstmt.setString(4, scb.getProfile());
			pstmt.setInt(5, num); //re_ref
			pstmt.setInt(6, 0); //re_lev
			pstmt.setInt(7, 0); //re_seq
			pstmt.setString(8, scb.getContent());
			pstmt.setString(9, scb.getIp());
			pstmt.setString(10, scb.getId());
			pstmt.setString(11, scb.getRef_nick());
			pstmt.executeUpdate();
			check = true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBClose();
		}
		return check;
	}//insertComment
	//댓글 리스트를 반환하는 메소드
	public List<ShareCommentBean> getCommentList(int board_idx){
		List<ShareCommentBean> commentList = new ArrayList<ShareCommentBean>();
		ShareCommentBean scb = null;
		try {
			con = getConnection();
			sql = "select * from sharecomment where board_idx=? order by re_ref desc, re_lev asc, re_seq asc";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, board_idx);
			rs = pstmt.executeQuery();
			while(rs.next()){
				scb = new ShareCommentBean();
				scb.setIdx(rs.getInt(1));
				scb.setBoard_idx(rs.getInt(2));
				scb.setNick(rs.getString(3));
				scb.setProfile(rs.getString(4));
				scb.setDate(rs.getDate(5));
				scb.setRe_ref(rs.getInt(6));
				scb.setRe_lev(rs.getInt(7));
				scb.setRe_seq(rs.getInt(8));
				scb.setContent(rs.getString(9));
				scb.setIp(rs.getString(10));
				scb.setId(rs.getString(11));
				scb.setRef_nick(rs.getString(12));
				commentList.add(scb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBClose();
		}
		return commentList;
	}//getCommentList
	//대댓글 작성하는 메소드
	public boolean replyComment(ShareCommentBean scb){
		boolean check = false;
		int num = 0;
		try{
			con = getConnection();
			sql = "select max(idx) from sharecomment";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()){
				num = rs.getInt(1)+1;
			}
			sql = "insert into sharecomment values(?,?,?,?,now(),?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setInt(2, scb.getBoard_idx());
			pstmt.setString(3, scb.getNick());
			pstmt.setString(4, scb.getProfile());
			pstmt.setInt(5, scb.getRe_ref()); //re_ref
			pstmt.setInt(6, scb.getRe_lev()); //re_lev
			pstmt.setInt(7, scb.getRe_seq()+1); //re_seq
			pstmt.setString(8, scb.getContent());
			pstmt.setString(9, scb.getIp());
			pstmt.setString(10, scb.getId());
			pstmt.setString(11, scb.getRef_nick());
			pstmt.executeUpdate();
			check = true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DBClose();
		}
		return check;
	}//replyComment
	//댓글 삭제 메소드
	public boolean deleteComment(int idx, String id){
		boolean check = false;
		try {
			con = getConnection();
			sql = "select id from sharecomment where idx = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, idx);
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(rs.getString("id").equals(id)){
					sql = "delete from sharecomment where idx = ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, idx);
					pstmt.executeUpdate();
					check = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBClose();
		}
		return check;
	}//deleteComment
	//댓글 수정 메소드
	public int updateComment(ShareCommentBean scb){
		int check = -1; //idx에 일치하는 댓글 없음
		try {
			con = getConnection();
			sql = "select * from sharecomment where idx = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, scb.getIdx());
			rs = pstmt.executeQuery();
			if(rs.next()){
				if(scb.getId().equals(rs.getString("id"))){
					sql = "update sharecomment set content = ? where idx = ?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, scb.getContent());
					pstmt.setInt(2, scb.getIdx());
					pstmt.executeUpdate();
					check = 1; //댓글 수정 성공
				}
			}else{
				check = 0; //아이디 불일치
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBClose();
		}
		return check;
	}//updateComment
	public int getComment(int ref) {
		int seq = 0;
		try {
			con=getConnection();
			sql="select max(re_seq) 're_seq' from sharecomment group by re_ref having re_ref=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, ref);
			rs=pstmt.executeQuery();
			if(rs.next()){
				seq = rs.getInt("re_seq");
				System.out.println("DAO seq : "+seq);
			}
		} catch (Exception e) {
			System.out.println("오류");
			e.printStackTrace();
		}finally{
			DBClose();
		}
		return seq;
	}
	public String getNick(int reContent) {
		try {
			con=getConnection();
			sql="select nick from sharecomment where idx=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, reContent);
			rs=pstmt.executeQuery();
			if(rs.next()){
				String nick = rs.getString(1);
				return nick;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DBClose();
		}
		return null;
	}

	
}//ShareCommentDAO
