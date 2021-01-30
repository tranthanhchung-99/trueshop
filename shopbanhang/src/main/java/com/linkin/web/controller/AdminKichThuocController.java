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

import com.linkin.model.KichThuocDTO;
import com.linkin.service.KichThuocService;

@Controller
public class AdminKichThuocController {

	@Autowired
	private KichThuocService kichThuocService;

	@GetMapping(value = "/admin/kichThuoc/search")
	public String searchKichThuoc(HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page) {
		page = page == null ? 1 : page;
		keyword = keyword == null ? "" : keyword;
		// mac dinh 10 ban ghi 1 trang
		List<KichThuocDTO> kichThuocList = kichThuocService.search(keyword, 0, page * 10);
		request.setAttribute("kichThuocList", kichThuocList);
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);

		return "admin/kichthuoc/kich-thuoc-list";
	}

	@GetMapping(value = "/admin/kichThuoc/add")
	public String addKichThuoc(Model model) {
		model.addAttribute("kt", new KichThuocDTO());
		return "admin/kichthuoc/add-kich-thuoc";
	}

	@PostMapping(value = "/admin/kichThuoc/add")
	public String addKichThuocPost(@ModelAttribute(name="kt") @Valid   KichThuocDTO kichThuocDTO , BindingResult bindingResult ,HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			return "admin/kichthuoc/add-kich-thuoc";
		}
		try {
			kichThuocService.insert(kichThuocDTO);
			
		}catch(Exception exception) {
			request.setAttribute("msg", "Kích thước đã tồn tại");
			return "admin/kichthuoc/add-kich-thuoc";
		}
		

		return "redirect:/admin/kichThuoc/search";
	}


	@GetMapping(value = "/admin/kichThuoc/update")
	public String updateKichThuocGet(Model model, @RequestParam(value = "id") Long id) {
		KichThuocDTO kichThuocDTO = kichThuocService.get(id);
		model.addAttribute("kichThuocDTO", kichThuocDTO);

		return "admin/kichthuoc/update-kich-thuoc";
	}

	@PostMapping(value = "/admin/kichThuoc/update")
	public String updateKichThuocPost(@ModelAttribute(name="kichThuocDTO") @Valid   KichThuocDTO kichThuocDTO , BindingResult bindingResult,HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			return "admin/kichthuoc/update-kich-thuoc";
		}
		try {
			kichThuocService.update(kichThuocDTO);
			
		}catch(Exception exception) {
			request.setAttribute("msg", "Kích thước đã tồn tại");
			return "admin/kichthuoc/update-kich-thuoc";
		}
		
		
		return "redirect:/admin/kichThuoc/search";
	}

	@GetMapping(value = "/admin/kichThuoc/delete")
	public String deleteKichThuoc(@RequestParam(value = "id") Long id) {
		kichThuocService.delete(id);
		return "redirect:/admin/kichThuoc/search";
	}

}