package tourpage.chat.db;

public class ChatDTO {
	
//	CREATE TABLE CHAT (
//
//			chatID int primary key auto_increment,
//			fromID varchar(20),
//			toID varchar(20),
//			chatContent varchar(100),
//			chatTime dateTime,
//			chatRead int
//	);


	
	int chatID;
    String fromID;
    String toID;
    String chatContent;
    String chatTime;
    
	public int getChatID() {
		return chatID;
	}
	public void setChatID(int chatID) {
		this.chatID = chatID;
	}
	public String getFromID() {
		return fromID;
	}
	public void setFromID(String fromID) {
		this.fromID = fromID;
	}
	public String getToID() {
		return toID;
	}
	public void setToID(String toID) {
		this.toID = toID;
	}
	public String getChatContent() {
		return chatContent;
	}
	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}
	public String getChatTime() {
		return chatTime;
	}
	public void setChatTime(String chatTime) {
		this.chatTime = chatTime;
	}
    
    
}