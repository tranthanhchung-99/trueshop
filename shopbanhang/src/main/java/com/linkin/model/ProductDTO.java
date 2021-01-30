package com.linkin.model;

import org.springframework.web.multipart.MultipartFile;

public class ProductDTO {
	private Long id;
	private String name;
	private Long priceIn;
	private Long priceOut;

	private MultipartFile multipartFile;
	private String image;
	private String description;
	private CategoryDTO category;
	private ThuongHieuDTO thuongHieuDTO;
	private KichThuocDTO kichThuocDTO;
	private GioiTinhDTO gioiTinhDTO;
	private Long soLuong;

	public Long getPriceIn() {
		return priceIn;
	}

	public void setPriceIn(Long priceIn) {
		this.priceIn = priceIn;
	}

	public Long getPriceOut() {
		return priceOut;
	}

	public void setPriceOut(Long priceOut) {
		this.priceOut = priceOut;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CategoryDTO getCategory() {
		return category;
	}

	public void setCategory(CategoryDTO category) {
		this.category = category;
	}

	public GioiTinhDTO getGioiTinhDTO() {
		return gioiTinhDTO;
	}

	public void setGioiTinhDTO(GioiTinhDTO gioiTinhDTO) {
		this.gioiTinhDTO = gioiTinhDTO;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public ThuongHieuDTO getThuongHieuDTO() {
		return thuongHieuDTO;
	}

	public void setThuongHieuDTO(ThuongHieuDTO thuongHieuDTO) {
		this.thuongHieuDTO = thuongHieuDTO;
	}

	public KichThuocDTO getKichThuocDTO() {
		return kichThuocDTO;
	}

	public void setKichThuocDTO(KichThuocDTO kichThuocDTO) {
		this.kichThuocDTO = kichThuocDTO;
	}

	public Long getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(Long soLuong) {
		this.soLuong = soLuong;
	}

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

}
