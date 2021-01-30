package com.linkin.model;

public class GiaoHang {
	String trangthai;
	String giaohang;
	String text;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTrangthai() {
		return trangthai;
	}
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}
	public String getGiaohang() {
		return giaohang;
	}
	public void setGiaohang(String giaohang) {
		this.giaohang = giaohang;
	}
	public GiaoHang(String trangthai, String giaohang, String text) {
		super();
		this.trangthai = trangthai;
		this.giaohang = giaohang;
		this.text = text;
	}
	public GiaoHang() {
		super();
	}
	
	
}
