package com.linkin.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.linkin.model.CategoryDTO;
import com.linkin.model.KichThuocDTO;
import com.linkin.model.ProductDTO;
import com.linkin.model.ProductValid;
import com.linkin.model.ThuongHieuDTO;
import com.linkin.service.CategoryService;
import com.linkin.service.KichThuocService;
import com.linkin.service.ProductService;
import com.linkin.service.ThuongHieuService;

@Controller
public class AdminProductController {
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ThuongHieuService thuongHieuService;
	@Autowired
	private KichThuocService kichThuocService;

	@Autowired
	ProductValid productValid;

	// goi den form add product
	@GetMapping(value = "/admin/product/add")
	public String AdminProductAddGet(HttpServletRequest request, Model model) {
		model.addAttribute("product", new ProductDTO());
		List<CategoryDTO> list = categoryService.search("", 0, 1000);
		List<ThuongHieuDTO> thuongHieuDTOs = thuongHieuService.search("", 0, 1000);
		List<KichThuocDTO> kichThuocDTOs = kichThuocService.search("", 0, 1000);

		request.setAttribute("categoryList", list);
		request.setAttribute("thuongHieuList", thuongHieuDTOs);
		request.setAttribute("kichThuocList", kichThuocDTOs);

		return "admin/product/add";
	}

	// lay du lieu tu form add de luu xuong db
	@PostMapping(value = "/admin/product/add")
	public String AdminProductAddPost(@ModelAttribute(name = "product") ProductDTO product,
			/* @RequestParam(name = "imageFile") MultipartFile multipartFile */ BindingResult bindingResult,
			HttpServletRequest request) {
		productValid.validate(product, bindingResult);
		if (bindingResult.hasErrors()) {
			List<CategoryDTO> list = categoryService.search("", 0, 1000);
			List<ThuongHieuDTO> thuongHieuDTOs = thuongHieuService.search("", 0, 1000);
			List<KichThuocDTO> kichThuocDTOs = kichThuocService.search("", 0, 1000);

			request.setAttribute("categoryList", list);
			request.setAttribute("thuongHieuList", thuongHieuDTOs);
			request.setAttribute("kichThuocList", kichThuocDTOs);
			return "admin/product/add";
		}
		String originalFilename = product.getMultipartFile().getOriginalFilename();// ten file anh
		int lastIndex = originalFilename.lastIndexOf(".");// lay so ptu sau dau. tu ten file anh
		String ext = originalFilename.substring(lastIndex);// lay cac ki tu tu sau dau .

		String avatarFilename = System.currentTimeMillis() + ext;// cho gen ra ten moi theo thoi gian + voi duoi file
																	// anh
		File newfile = new File("C:\\filetest\\" + avatarFilename);
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(newfile);
			fileOutputStream.write(product.getMultipartFile().getBytes());// luu anh voi ten moi xuong thu muc
																			// C:\\filetest\\" trong o dia
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		product.setImage(avatarFilename);

		KichThuocDTO kichThuocDTO = kichThuocService.get(product.getKichThuocDTO().getId());
		int check = 0;
		List<ProductDTO> listPro = productService.search(product.getName(), "", "", "", 1L,
				1000000L, 0, 10 * 10);// lay ra 1 list product co cung ten cung kich thuoc vs product ddinhj theem
										// vao.
	
		if (listPro != null) {// kiem tra list tren co null hay k , neu null bieens check=2,
			// con khong null tien hanh kiem tra xem sp them vao co ten , kich thuoc trung
			// voi sp nao trong db k.neu trung check =1.
			for (ProductDTO dto : listPro) {
				if (product.getName().equals(dto.getName()) && kichThuocDTO.getId() == dto.getKichThuocDTO().getId()) {
					check = 1;
					
					break;
				}
			}

		} else {
			check = 2;
		}
		// voi check= 1 dua ra thong bao "sp da ton tai" , check!=1 thi them sp xuong
		// db.
		if (check == 1) {
			List<CategoryDTO> list = categoryService.search("", 0, 1000);
			List<ThuongHieuDTO> thuongHieuDTOs = thuongHieuService.search("", 0, 1000);
			List<KichThuocDTO> kichThuocDTOs = kichThuocService.search("", 0, 1000);

			request.setAttribute("categoryList", list);
			request.setAttribute("thuongHieuList", thuongHieuDTOs);
			request.setAttribute("kichThuocList", kichThuocDTOs);
			request.setAttribute("msg", "Sản phẩm đã tồn tại");
			return "admin/product/add";
		} else {
			productService.insert(product);
			return "redirect:/admin/product/search";
		}

	}
	// tuong tu add
	@GetMapping(value = "/admin/product/update")
	public String AdminProductUpdate(HttpServletRequest request, Model model, Long id) {
		ProductDTO product = productService.get(id);
		List<CategoryDTO> list = categoryService.search("", 0, 1000);
		List<ThuongHieuDTO> thuongHieuDTOs = thuongHieuService.search("", 0, 1000);
		List<KichThuocDTO> kichThuocDTOs = kichThuocService.search("", 0, 1000);

		model.addAttribute("product", product);

		request.setAttribute("categoryList", list);
		request.setAttribute("thuongHieuList", thuongHieuDTOs);
		request.setAttribute("kichThuocList", kichThuocDTOs);
		return "admin/product/update";
	}

	@PostMapping(value = "/admin/product/update")
	public String AdminProductUpdatePost(@ModelAttribute(name = "product") ProductDTO product,
			/* @RequestParam(name = "imageFile") MultipartFile multipartFile */ BindingResult bindingResult,
			HttpServletRequest request) {
		productValid.validate(product, bindingResult);
		if (bindingResult.hasErrors()) {
			List<CategoryDTO> list = categoryService.search("", 0, 1000);
			List<ThuongHieuDTO> thuongHieuDTOs = thuongHieuService.search("", 0, 1000);
			List<KichThuocDTO> kichThuocDTOs = kichThuocService.search("", 0, 1000);

			request.setAttribute("categoryList", list);
			request.setAttribute("thuongHieuList", thuongHieuDTOs);
			request.setAttribute("kichThuocList", kichThuocDTOs);
			return "admin/product/update";

		}
		String originalFilename = product.getMultipartFile().getOriginalFilename();
		int lastIndex = originalFilename.lastIndexOf(".");
		String ext = originalFilename.substring(lastIndex);

		String avatarFilename = System.currentTimeMillis() + ext;
		File newfile = new File("C:\\filetest\\" + avatarFilename);
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(newfile);
			fileOutputStream.write(product.getMultipartFile().getBytes());
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		product.setImage(avatarFilename);
		productService.update(product);
		return "redirect:/admin/product/search";
	}

	@GetMapping(value = "/admin/product/delete")
	public String AdminDeleteProduct(@RequestParam(name = "id") Long id) {
		productService.delete(id);
		return "redirect:/admin/product/search";
	}

	@GetMapping(value = "/admin/product/search")
	public String AdminProductSearch(HttpServletRequest request) {

		String thuongHieuName = request.getParameter("thuongHieuName") == null ? ""
				: request.getParameter("thuongHieuName");

		String categoryName = request.getParameter("categoryName") == null ? "" : request.getParameter("categoryName");

		String kichThuocName = request.getParameter("kichThuocName") == null ? ""
				: request.getParameter("kichThuocName");

		String keyword = request.getParameter("keyword") == null ? "" : request.getParameter("keyword");

		Integer page = request.getParameter("page") == null ? 1 : Integer.valueOf(request.getParameter("page"));

		Long priceStart = (request.getParameter("priceStart") == null || request.getParameter("priceStart") == "") ? 1
				: Long.valueOf(request.getParameter("priceStart"));

		Long priceEnd = (request.getParameter("priceEnd") == null || request.getParameter("priceEnd") == "") ? 100000000
				: Long.valueOf(request.getParameter("priceEnd"));
		// danh sach sp loc theo dieu kien , mac dinh ban dau la tat ca cac sp trong db
		List<ProductDTO> listPro = productService.search(keyword, categoryName, thuongHieuName, kichThuocName,
				priceStart, priceEnd, 0, page * 10);
		List<CategoryDTO> categoryDTOs = categoryService.search("", 0, 1000 * 10);
		List<ThuongHieuDTO> thuongHieuDTOs = thuongHieuService.search("", 0, 1000 * 10);
		List<KichThuocDTO> kichThuocDTOs = kichThuocService.search("", 0, 1000 * 10);

		request.setAttribute("kichThuocList", kichThuocDTOs);
		request.setAttribute("thuongHieuList", thuongHieuDTOs);
		request.setAttribute("productList", listPro);
		request.setAttribute("categoryList", categoryDTOs);

		request.setAttribute("kichThuocName", kichThuocName);
		request.setAttribute("categoryName", categoryName);
		request.setAttribute("thuongHieuName", thuongHieuName);
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);
		return "admin/product/search";
	}
}
