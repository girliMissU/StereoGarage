package com.stereogarage.Bean;

public class ShowEntity5 {
	private String user;
	private String num;
	private String mail;

//	private String machine32;

	public ShowEntity5(String user,String num, String mail){
		this.user = user;
		this.num = num;
		this.mail = mail;
//		this.machine22 = machine22;
//		this.machine32 = machine32;
	}

	public String getUser() {return user;}
	public void setUser(String user) {
		this.user = user;
	}
	public String getNum() {return num;}
	public void setNum(String num) {
		this.num = num;
	}
	public String getMail() {return mail;}
	public void setMail(String mail) {
		this.mail =mail;
	}
//	public String getmachine22() {
//		return machine22;
//	}
//	public void setmachine22(String machine22) {this.machine22 = machine22;}
//	public String getmachine32() {
//		return machine32;
//	}
//	public void setmachine32(String machine32) {this.machine32 = machine32;}

}
