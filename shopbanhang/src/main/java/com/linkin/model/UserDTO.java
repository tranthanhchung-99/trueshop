package com.linkin.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class UserDTO {
	private Long id;
	@NotEmpty(message="không được để trống tên")
	private String name;
	@NotEmpty(message="không được để trống tuổi.")
	@Min(value = 12, message = "tuổi không được nhỏ hơn 12.")
	private String age;
	@NotEmpty(message="không được để trống tuổi.")
	private String role;
	
	private Boolean enabled;
	@NotEmpty(message="không được để trống tài khoản.")
	private String username;
	@NotEmpty(message="không được để trống password.")
	private String password;
	@NotEmpty(message="không được để trống địa chỉ.")
	private String address;
	
	private String gender;
	@NotEmpty(message="không được để trống SDT.")
	
	private String phone;
	@NotEmpty(message="không được để trống Email.")
	@Email(message = "Không đúng định dạng Email")
	private String email;

	
	

	public UserDTO(Long id, @NotEmpty(message = "không được để trông name.") String name,
			@NotEmpty(message = "không được để trống tuổi.") @Min(value = 0, message = "tuổi không được nhỏ hơn 0.") String age,
			@NotEmpty(message = "không được để trống tuổi.") String role, Boolean enabled,
			@NotEmpty(message = "không được để trống tài khoản.") String username,
			@NotEmpty(message = "không được để trống password.") String password,
			@NotEmpty(message = "không được để trống địa chỉ.") String address, String gender,
			@NotEmpty(message = "không được để trống SDT.") String phone,
			@NotEmpty(message = "không được để trống Email.") @Email(message = "Không đúng định dạng Email") String email) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.role = role;
		this.enabled = enabled;
		this.username = username;
		this.password = password;
		this.address = address;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
	}

	public UserDTO() {
		super();
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
