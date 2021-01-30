package com.linkin.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class GioiTinhDTO {

	private Long id;
	@NotEmpty(message="Hãy nhập giới tính")
	private String name;

	public GioiTinhDTO() {
		super();
	}

	public GioiTinhDTO(String name) {
		super();
		this.name = name;
	}

	public GioiTinhDTO(Long id,@NotEmpty @Size(min = 2, max = 30) String name) {
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
