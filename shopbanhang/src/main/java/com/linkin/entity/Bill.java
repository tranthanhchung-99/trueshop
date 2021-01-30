package com.linkin.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bill")
public class Bill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "buy_date")
	private Date buyDate;

	@Column(name = "price_total")
	private Long priceTotal;

	@Column(name = "discount_percent")
	private Integer discountPercent;
	
	@Column(name="total")
	private Long total;
	 
	@Column(name="buy_status")
	private String trangthai;
	
	@Column(name="transport_status")
	private String giaohang;
	
	public String getGiaohang() {
		return giaohang;
	}

	public void setGiaohang(String giaohang) {
		this.giaohang = giaohang;
	}

	public String getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	@ManyToOne
	@JoinColumn(name = "buyer_id")
	private User buyer;

	@Column(name = "status")
	private String status;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bill" )
	@Column(name = "bill_Products")
	private List<BillProduct> billProducts;
	
	@OneToOne(mappedBy = "bill",cascade = CascadeType.ALL)
	private InforBill inforBill;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public Long getPriceTotal() {
		return priceTotal;
	}

	public void setPriceTotal(Long priceTotal) {
		this.priceTotal = priceTotal;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
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

	public List<BillProduct> getBillProducts() {
		return billProducts;
	}

	public void setBillProducts(List<BillProduct> billProducts) {
		this.billProducts = billProducts;
	}

	public InforBill getInforBill() {
		return inforBill;
	}

	public void setInforBill(InforBill inforBill) {
		this.inforBill = inforBill;
	}


}
