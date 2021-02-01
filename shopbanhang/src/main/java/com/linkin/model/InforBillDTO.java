package com.linkin.model;

public class InforBillDTO {
	private Long id;
	private String address;
	private String phoneNumber;
	private String method;
	private String note;
	private String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	private BillDTO billDTO;
	public BillDTO getBillDTO() {
		return billDTO;
	}
	public void setBillDTO(BillDTO billDTO) {
		this.billDTO = billDTO;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public InforBillDTO(Long id, String address, String phoneNumber, String method, String note) {
		super();
		this.id = id;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.method = method;
		this.note = note;
	}
	public InforBillDTO() {
		super();
	}
	
}
