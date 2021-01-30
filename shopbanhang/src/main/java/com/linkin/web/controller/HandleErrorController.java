package com.linkin.web.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class HandleErrorController implements org.springframework.boot.web.servlet.error.ErrorController{
	// xu li loi.
	@RequestMapping("/error")
	  public String handleError(HttpServletRequest request) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);// lay status loi server tra ve khi gap loi
	    if (status != null) {
	      Integer statusCode = Integer.valueOf(status.toString());
	      if (statusCode == HttpStatus.NOT_FOUND.value()) {
	        return "error/404";
	      } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	        return "error/500";
	      }
	    }
	    return "error/error";
	  }


	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}

}
