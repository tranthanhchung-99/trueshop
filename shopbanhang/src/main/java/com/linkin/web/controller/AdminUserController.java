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

import com.linkin.model.UserDTO;
import com.linkin.service.UserService;

@Controller
public class AdminUserController {
	@Autowired
	private UserService userService;

	@GetMapping(value = "/admin/user/search")
	public String searchUser(HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page) {
		page = page == null ? 1 : page;
		keyword = keyword == null ? "" : keyword;
		// mac dinh 10 ban ghi 1 trang
		List<UserDTO> userList = userService.search(keyword, 0, page* 10);
		request.setAttribute("userList", userList);
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);
		return "admin/user/users";
	}

	@GetMapping(value = "/admin/user/add")
	public String AdminAddUserGet(Model model) {
		model.addAttribute("adduser", new UserDTO());
		return "admin/user/add-user";
	}

	@PostMapping(value = "/admin/user/add")
	public String AdminAddUserPost(@ModelAttribute(name = "adduser") @Valid UserDTO user, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return "admin/user/add-user";
		}
		user.setEnabled(true);
		try {
			userService.insert(user);
			
		} catch (Exception exception) {
			request.setAttribute("msg", "Thông tin nhập đã tồn tại");
			return "admin/user/add-user";
		}

		return "redirect:/admin/user/search";

	}

	@GetMapping(value = "/admin/user/update")
	public String AdminUpdateUserGet(Model model, @RequestParam(name = "id") Long id) {
		UserDTO user = userService.get(id);
		user.setPassword("abc");
		model.addAttribute("user", user);
		return "admin/user/update-user";

	}

	@PostMapping(value = "/admin/user/update")
	public String changePassword(@ModelAttribute(name = "user") @Valid UserDTO user, BindingResult bindingResult, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return "admin/user/update-user";
		}
		try {
			user.setEnabled(true);
			userService.update(user);
			
		} catch (Exception exception) {
			request.setAttribute("msg", "Thông tin nhập đã tồn tại");
			return "admin/user/update-user";
		}
	
		return "redirect:/admin/user/search";
	}

	@GetMapping(value = "/admin/user/change-password")
	public String changePassword(Model model, @RequestParam(name = "id") Long id) {
		UserDTO user = userService.get(id);
		model.addAttribute("user", user);
		return "admin/user/change-password";
	}

	@PostMapping(value = "/admin/user/change-password")
	public String AdminUpdateUserPost(@ModelAttribute(name = "user") UserDTO user) {
		userService.setupUserPassword(user);
		return "redirect:/admin/user/search";
	}

	@GetMapping(value = "/admin/user/delete")
	public String deleteUser(Long id) {
		userService.delete(id);
		return "redirect:/admin/user/search";
	}

}