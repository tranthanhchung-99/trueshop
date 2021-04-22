package com.linkin.model;

public class BillDTO {
	private Long id;
	private UserDTO user;
	private String buyDate;
	private Long priceTotal;
	private Long total;
	private Integer discountPercent;
	private String status;
	private InforBillDTO inforBillDTO;
	private String trangThai;
	private String giaoHang;

	public String getGiaoHang() {
		return giaoHang;
	}

	public void setGiaoHang(String giaoHang) {
		this.giaoHang = giaoHang;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}

	public Long getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(Long d) {
		this.priceTotal = d;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(Integer discountPercent) {
		this.discountPercent = discountPercent;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public InforBillDTO getInforBillDTO() {
		return inforBillDTO;
	}

	public void setInforBillDTO(InforBillDTO inforBillDTO) {
		this.inforBillDTO = inforBillDTO;
	}

	public BillDTO(Long id) {
		super();
		this.id = id;
	}
	public BillDTO() {
		super();
		
	}

}
