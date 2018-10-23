package tourpage.share.db;

import java.sql.Date;

public class ShareCommentBean {
//	create table sharecomment(
//			idx int primary key,
//			board_idx int not null,
//			nick varchar(10) not null,
//			profile varchar(50),
//			date date,
//			re_ref int not null,
//			re_lev int not null,
//			re_seq int not null,
//			content varchar(300) not null,
//			ip varchar(30) not null,
//			id varchar(100) not null
//			);
//  위의 내용 복사하셔서 그대로 테이블 만드시면됩니다.
	private int idx; //코멘트의 고유 번호
	private int board_idx; //게시글의 번호
	private String nick; //닉네임
	private String profile; //프로필 이미지
	private Date date; //코멘트작성 날짜
	private int re_ref; //댓글 그룹
	private int re_lev; //댓글 들여쓰기 레벨
	private int re_seq; //댓글 순서
	private String content; //댓글 내용
	private String ip; //ip
	private String id; //id
	private String ref_nick;
	private boolean chk;
	public boolean isChk() {
		return chk;
	}
	public void setChk(boolean chk) {
		this.chk = chk;
	}
	public String getRef_nick() {
		return ref_nick;
	}
	public void setRef_nick(String ref_nick) {
		this.ref_nick = ref_nick;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public int getBoard_idx() {
		return board_idx;
	}
	public void setBoard_idx(int board_idx) {
		this.board_idx = board_idx;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getRe_ref() {
		return re_ref;
	}
	public void setRe_ref(int re_ref) {
		this.re_ref = re_ref;
	}
	public int getRe_lev() {
		return re_lev;
	}
	public void setRe_lev(int re_lev) {
		this.re_lev = re_lev;
	}
	public int getRe_seq() {
		return re_seq;
	}
	public void setRe_seq(int re_seq) {
		this.re_seq = re_seq;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
}//ShareCommentBean
