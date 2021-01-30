package com.linkin.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoryDTO {
	private Long id;
	
	 @javax.persistence.Column(name = "nic")
	@NotEmpty(message="Vui lòng nhập thể loại sản phẩm.")
	private String name;


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

	public CategoryDTO(Long id, @NotNull @Size(min = 2, max = 30) String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public CategoryDTO() {
		super();
	}

}
