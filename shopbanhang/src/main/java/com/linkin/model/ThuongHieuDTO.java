package com.linkin.model;

import javax.validation.constraints.NotEmpty;

public class ThuongHieuDTO {

	private Long id;
	@NotEmpty(message="Hãy nhập tên thương hiệu")
	private String name;

	public ThuongHieuDTO() {
		super();
	}

	public ThuongHieuDTO(String name) {
		super();
		this.name = name;
	}

	public ThuongHieuDTO(Long id,@NotEmpty String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
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
