package vo;

import java.sql.Date;

public class CommentVO {
	private int bNum, cNum;
	private Date cDate;
	private String cName;
	private String cMsg;
	public String getcMsg() {
		return cMsg == null ? "" : cMsg.trim();
	}
	public void setcMsg(String cMsg) {
		this.cMsg = cMsg;
	}
	public int getbNum() {
		return bNum;
	}
	public void setbNum(int bNum) {
		this.bNum = bNum;
	}
	public int getcNum() {
		return cNum;
	}
	public void setcNum(int cNum) {
		this.cNum = cNum;
	}
	public Date getcDate() {
		return cDate;
	}
	public void setcDate(Date cDate) {
		this.cDate = cDate;
	}
	public String getcName() {
		return cName == null ? "" : cName.trim();
	}
	public void setcName(String cName) {
		this.cName = cName;
	}
	
	
	
}
