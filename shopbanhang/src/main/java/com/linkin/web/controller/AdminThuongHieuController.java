package com.linkin.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.linkin.model.ThuongHieuDTO;
import com.linkin.service.ThuongHieuService;

@Controller
public class AdminThuongHieuController {

	@Autowired
	private ThuongHieuService thuongHieuService;
	
	@GetMapping(value = "/admin/thuongHieu/search")
	public String searchThuongHieu(HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page) {
		page = page == null ? 1 : page;
		keyword = keyword == null ? "" : keyword;
		// mac dinh 10 ban ghi 1 trang
		List<ThuongHieuDTO> thuongHieuDTOs = thuongHieuService.search(keyword, 0, page * 10);
		request.setAttribute("thuongHieuList", thuongHieuDTOs);
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);

		return "admin/thuonghieu/thuong-hieu-list";
	}
	
	@GetMapping(value = "/admin/thuongHieu/add")
	public String addCategory(Model model) {
		model.addAttribute("th", new ThuongHieuDTO());
		return "admin/thuonghieu/add-thuong-hieu";
	}

	@PostMapping(value = "/admin/thuongHieu/add")
	public String addCategoryPost(@ModelAttribute(name="th") @Valid ThuongHieuDTO thuongHieuDTO, BindingResult bindingResult, HttpServletRequest request) {
		
		if(bindingResult.hasErrors()) {
			return "admin/thuonghieu/add-thuong-hieu";
		}
		try {
			thuongHieuService.insert(thuongHieuDTO);
			
		}catch(Exception exception) {
			request.setAttribute("msg", "Thương hiệu đã tồn tại");
			return "admin/thuonghieu/add-thuong-hieu";
		}
		

		return "redirect:/admin/thuongHieu/search";
	}

	@GetMapping(value = "/admin/thuongHieu/update")
	public String updateCategoryGet(Model model, @RequestParam(value = "id") int id) {
		ThuongHieuDTO thuongHieuDTO = thuongHieuService.get(id);
		model.addAttribute("thuonghieu", thuongHieuDTO);

		return "admin/thuonghieu/update-thuong-hieu";
	}

	@PostMapping(value = "/admin/thuongHieu/update")
	public String updateCategoryPost(@ModelAttribute(name="thuonghieu") @Valid ThuongHieuDTO thuongHieuDTO, BindingResult bindingResult,HttpServletRequest request) {
		
		if(bindingResult.hasErrors()) {
			return "admin/thuonghieu/update-thuong-hieu";
		}
		try {
			thuongHieuService.update(thuongHieuDTO);
			
		}catch(Exception exception) {
			request.setAttribute("msg", "Thương hiệu đã tồn tại");
			return "admin/thuonghieu/update-thuong-hieu";
		}
		
		return "redirect:/admin/thuongHieu/search";
	}

	@GetMapping(value = "/admin/thuongHieu/delete")
	public String deleteCategory(@RequestParam(value = "id") int id) {
		thuongHieuService.delete(id);
		return "redirect:/admin/thuongHieu/search";
	}
	
}
