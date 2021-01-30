package com.linkin.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linkin.dao.ProductDao;
import com.linkin.entity.Category;
import com.linkin.entity.KichThuoc;
import com.linkin.entity.Product;
import com.linkin.entity.ThuongHieu;
import com.linkin.model.CategoryDTO;
import com.linkin.model.KichThuocDTO;
import com.linkin.model.ProductDTO;
import com.linkin.model.ThuongHieuDTO;
import com.linkin.service.ProductService;

@Transactional
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductDao productDao;

	@Override
	public void insert(ProductDTO productDTO) {
		Product product = new Product();
		product.setName(productDTO.getName());
		product.setPriceIn(productDTO.getPriceIn());
		product.setPriceOut(productDTO.getPriceOut());
		product.setDescription(productDTO.getDescription());
		product.setImage(productDTO.getImage());
		product.setSoLuong(productDTO.getSoLuong());

		Category category = new Category();
		category.setId(productDTO.getCategory().getId());
		category.setName(productDTO.getCategory().getName());
		product.setCategory(category);

		ThuongHieu thuongHieu = new ThuongHieu();
		thuongHieu.setId(productDTO.getThuongHieuDTO().getId());
		thuongHieu.setName(productDTO.getThuongHieuDTO().getName());
		product.setThuongHieu(thuongHieu);

		KichThuoc kichThuoc = new KichThuoc();
		kichThuoc.setId(productDTO.getKichThuocDTO().getId());
		kichThuoc.setName(productDTO.getKichThuocDTO().getName());
		product.setKichThuoc(kichThuoc);

		productDao.insert(product);
	}

	@Override
	public void update(ProductDTO productDTO) {
		Product product = productDao.get(productDTO.getId());
		if (product != null) {
			product.setId(productDTO.getId());
			product.setName(productDTO.getName());
			product.setPriceIn(productDTO.getPriceIn());
			product.setPriceOut(productDTO.getPriceOut());
			product.setDescription(productDTO.getDescription());
			product.setImage(productDTO.getImage());
			product.setSoLuong(productDTO.getSoLuong());

			Category category = new Category();
			category.setId(productDTO.getCategory().getId());
			category.setName(productDTO.getCategory().getName());
			product.setCategory(category);

			ThuongHieu thuongHieu = new ThuongHieu();
			thuongHieu.setId(productDTO.getThuongHieuDTO().getId());
			thuongHieu.setName(productDTO.getThuongHieuDTO().getName());
			product.setThuongHieu(thuongHieu);

			KichThuoc kichThuoc = new KichThuoc();
			kichThuoc.setId(productDTO.getKichThuocDTO().getId());
			kichThuoc.setName(productDTO.getKichThuocDTO().getName());
			product.setKichThuoc(kichThuoc);

			productDao.update(product);
		}
	}

	@Override
	public void delete(Long id) {
		Product product = productDao.get(id);
		if (product != null) {
			productDao.delete(product);
		}
	}

	@Override
	public List<ProductDTO> search(String findName, String categoryName, String thuongHieuName, String kichThuocName,
			Long priceStart, Long priceEnd, int start, int length) {

		List<Product> listProducts = productDao.search(findName, categoryName, thuongHieuName, kichThuocName,
				priceStart, priceEnd, start, length);
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		for (Product product : listProducts) {
			ProductDTO dto = new ProductDTO();
			dto.setId(product.getId());
			dto.setName(product.getName());
			dto.setPriceIn(product.getPriceIn());
			dto.setPriceOut(product.getPriceOut());
			dto.setImage(product.getImage());
			dto.setDescription(product.getDescription());
			dto.setSoLuong(product.getSoLuong());

			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(product.getCategory().getId());
			categoryDTO.setName(product.getCategory().getName());
			dto.setCategory(categoryDTO);

			ThuongHieuDTO thuongHieu = new ThuongHieuDTO();
			thuongHieu.setId(product.getThuongHieu().getId());
			thuongHieu.setName(product.getThuongHieu().getName());
			dto.setThuongHieuDTO(thuongHieu);

			KichThuocDTO kichThuocDTO = new KichThuocDTO();
			kichThuocDTO.setId(product.getKichThuoc().getId());
			kichThuocDTO.setName(product.getKichThuoc().getName());
			dto.setKichThuocDTO(kichThuocDTO);

			productDTOs.add(dto);
		}
		return productDTOs;
	}

	@Override
	public ProductDTO get(Long id) {
		Product product = productDao.get(id);
		ProductDTO dto = new ProductDTO();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setImage(product.getImage());
		dto.setPriceIn(product.getPriceIn());
		dto.setPriceOut(product.getPriceOut());
		dto.setSoLuong(product.getSoLuong());

		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(product.getCategory().getId());
		categoryDTO.setName(product.getCategory().getName());
		dto.setCategory(categoryDTO);

		ThuongHieuDTO thuongHieuDTO = new ThuongHieuDTO();
		thuongHieuDTO.setId(product.getThuongHieu().getId());
		thuongHieuDTO.setName(product.getThuongHieu().getName());
		dto.setThuongHieuDTO(thuongHieuDTO);

		KichThuocDTO kichThuocDTO = new KichThuocDTO();
		kichThuocDTO.setId(product.getKichThuoc().getId());
		kichThuocDTO.setName(product.getKichThuoc().getName());
		dto.setKichThuocDTO(kichThuocDTO);

		return dto;
	}

	@Override
	public List<ProductDTO> searchByCategory(String findName, String thuongHieuName, String kichThuocName,
			Long priceStart, Long priceEnd, Long categoryId, int start, int length) {

		List<Product> listProducts = productDao.searchByCategory(findName, thuongHieuName, kichThuocName, priceStart,
				priceEnd, categoryId, start, length);
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		for (Product product : listProducts) {
			ProductDTO dto = new ProductDTO();
			dto.setId(product.getId());
			dto.setName(product.getName());
			dto.setPriceIn(product.getPriceIn());
			dto.setPriceOut(product.getPriceOut());
			dto.setImage(product.getImage());
			dto.setDescription(product.getDescription());

			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(product.getCategory().getId());
			categoryDTO.setName(product.getCategory().getName());
			dto.setCategory(categoryDTO);

			ThuongHieuDTO thuongHieu = new ThuongHieuDTO();
			thuongHieu.setId(product.getThuongHieu().getId());
			thuongHieu.setName(product.getThuongHieu().getName());
			dto.setThuongHieuDTO(thuongHieu);

			KichThuocDTO kichThuocDTO = new KichThuocDTO();
			kichThuocDTO.setId(product.getKichThuoc().getId());
			kichThuocDTO.setName(product.getKichThuoc().getName());
			dto.setKichThuocDTO(kichThuocDTO);

			productDTOs.add(dto);
		}
		return productDTOs;
	}

	@Override
	public void updateQuantity(ProductDTO productDTO) {
		Product product = productDao.get(productDTO.getId());
		if (product != null) {
			product.setSoLuong(productDTO.getSoLuong());
		}

	}

	@Override
	public List<ProductDTO> searchName(String findName, String categoryName, String thuongHieuName,
			String kichThuocName, Long priceStart, Long priceEnd, int start, int length) {
		List<Product> listProducts = productDao.search(findName, categoryName, thuongHieuName, kichThuocName,
				priceStart, priceEnd, start, length);
		List<ProductDTO> productDTOs = new ArrayList<ProductDTO>();
		for (Product product : listProducts) {
			ProductDTO dto = new ProductDTO();
			dto.setId(product.getId());
			dto.setName(product.getName());
			dto.setPriceIn(product.getPriceIn());
			dto.setPriceOut(product.getPriceOut());
			dto.setImage(product.getImage());
			dto.setDescription(product.getDescription());
			dto.setSoLuong(product.getSoLuong());

			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setId(product.getCategory().getId());
			categoryDTO.setName(product.getCategory().getName());
			dto.setCategory(categoryDTO);

			ThuongHieuDTO thuongHieu = new ThuongHieuDTO();
			thuongHieu.setId(product.getThuongHieu().getId());
			thuongHieu.setName(product.getThuongHieu().getName());
			dto.setThuongHieuDTO(thuongHieu);

			KichThuocDTO kichThuocDTO = new KichThuocDTO();
			kichThuocDTO.setId(product.getKichThuoc().getId());
			kichThuocDTO.setName(product.getKichThuoc().getName());
			dto.setKichThuocDTO(kichThuocDTO);

			productDTOs.add(dto);
		}
		return productDTOs;
	}

}
