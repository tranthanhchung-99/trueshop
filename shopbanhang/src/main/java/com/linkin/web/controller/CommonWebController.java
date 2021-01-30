package com.linkin.web.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.linkin.model.UserPrincipal;

@Controller
public class CommonWebController {

	@RequestMapping(value = "/")
	private String index(HttpServletRequest request) {
		return "redirect:/products";
	}

	@RequestMapping(value = "/member/home")
	private String home(Model model) {
		UserPrincipal currentUser = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (currentUser.getRole().equals("ROLE_ADMIN")) {
			return "redirect:/admin/user/search";
		} else if (currentUser.getRole().equals("ROLE_MEMBER")) {
			return "redirect:/products";
		}

		return "redirect:/member/profile";
	}

	@GetMapping(value = "/download")
	public void download(HttpServletResponse response, @RequestParam("image") String image) {
		final String uploadFolder = "C:\\filetest\\";
		File file = new File(uploadFolder + File.separator + image);
		if (file.exists()) {
			try {
				Files.copy(file.toPath(), response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
