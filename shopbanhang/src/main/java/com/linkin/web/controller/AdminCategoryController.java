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

import com.linkin.model.CategoryDTO;
import com.linkin.service.CategoryService;

@Controller
public class AdminCategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping(value = "/admin/category/search")// cho ra tat car category trong db
	public String searchCategory(HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page) {
		page = page == null ? 1 : page;
		keyword = keyword == null ? "" : keyword;
		// mac dinh 10 ban ghi 1 trang
		List<CategoryDTO> categoryList = categoryService.search(keyword, 0, page* 10);
		request.setAttribute("categoryList", categoryList);// day du lieu sang giao dien
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);

		return "admin/category/categories";
	}
	// goi ra form add cate
	@GetMapping(value = "/admin/category/add")
	public String addCategory( Model model) {
		
		model.addAttribute("category", new CategoryDTO());
		return "admin/category/add-category";
	}
	//gui du lieu tu form va day xuong db
	@PostMapping(value = "/admin/category/add")
	public String addCategoryPost(@ModelAttribute(name="category") @Valid CategoryDTO category, BindingResult bindingResult, HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			return "admin/category/add-category";
		}
		try {
			categoryService.insert(category);// neu trung thi cho ra tb 
			
		}catch(Exception exception) {
			request.setAttribute("msg", "Thể loại đã tồn tại");
			return "admin/category/add-category";
		}
		

		return "redirect:/admin/category/search";
	}
	// tuong tu add goi den form update
	@GetMapping(value = "/admin/category/update")
	public String updateCategoryGet(Model model, @RequestParam(value = "id") Long id) {
		CategoryDTO category = categoryService.get(id);
		model.addAttribute("cate", category);

		return "admin/category/update-category";
	}
	// chuyen du lieu tu form sang de luu xuong db tuong tu add 
	@PostMapping(value = "/admin/category/update")
	public String updateCategoryPost(@ModelAttribute(name="cate") @Valid CategoryDTO category,BindingResult bindingResult,HttpServletRequest request) {
		if(bindingResult.hasErrors()) {
			return "admin/category/update-category";
		}
		try {
			categoryService.update(category);
			
		}catch(Exception exception) {
			request.setAttribute("msg", "Thể loại đã tồn tại");
			return "admin/category/update-category";
		}
		
		
		return "redirect:/admin/category/search";
	}

	@GetMapping(value = "/admin/category/delete")
	public String deleteCategory(@RequestParam(value = "id") Long id) {
		categoryService.delete(id);
		return "redirect:/admin/category/search";
	}

}