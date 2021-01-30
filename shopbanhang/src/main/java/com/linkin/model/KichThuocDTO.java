package com.linkin.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class KichThuocDTO {
	private Long id;
	@NotEmpty(message="Hãy nhập tên kích thước")
	@Min(value = 36, message = "Kích thước không được nhỏ hơn 36.")
	@Max(value = 45, message = "Kích thước không được lớn hơn 45.")
	private String name;
	



	public KichThuocDTO(Long id,
			@NotEmpty(message = "nhap ten kich thuoc.") @Min(value = 36, message = "Kích thước không được nhỏ hơn 36.") @Max(value = 45, message = "Kích thước không được lớn hơn 45.") String name) {
		super();
		this.id = id;
		this.name = name;
	}


	public Long getId() {
		return id;
	}


	public KichThuocDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
