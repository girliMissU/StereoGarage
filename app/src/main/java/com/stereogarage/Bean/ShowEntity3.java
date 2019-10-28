package com.stereogarage.Bean;

public class ShowEntity3 {
	private String num;
	private String machine;
//	private String machine22;
//	private String machine32;

	public ShowEntity3(String num, String machine){
		this.num = num;
		this.machine = machine;
//		this.machine22 = machine22;
//		this.machine32 = machine32;
	}
	
	public String getnum() {return num;}
	public void setnum(String num) {
		this.num = num;
	}
	public String getmachine() {return machine;}
	public void setmachine(String machine) {
		this.machine =machine;
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
