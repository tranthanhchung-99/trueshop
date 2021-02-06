package com.linkin.web.controller;

import com.linkin.model.BillDTO;
import com.linkin.model.BillProductDTO;
import com.linkin.model.ProductDTO;
import com.linkin.service.BillProductService;
import com.linkin.service.BillService;
import com.linkin.service.InforBillService;
import com.linkin.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminBillController {
	@Autowired
	BillService billService;
	@Autowired
	BillProductService billProductService;
	@Autowired
	ProductService productService;
	InforBillService inforBillService;

	@GetMapping(value = "/admin/bill/search") // cho ra tat ca cac bill trong database cua cac khach hang.
	public String AdminBillSearch(HttpServletRequest request,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "page", required = false) Integer page, HttpSession session,
			@RequestParam(value = "userName", required = false) String userName) {
		page = page == null ? 1 : page;
		keyword = keyword == null ? "NEW" : keyword;
		userName = userName == null ? "" : userName;
		
		
		List<BillDTO> listBill = billService.searchByTrangThai("", "",userName,0,page*10);// danh sach bill theo trang thai va giao hang 
		List<BillDTO> billDTOs= new ArrayList<>();
		List<BillDTO> billDTOs1= new ArrayList<>();
		List<BillDTO> billDTOs2= new ArrayList<>();
		List<BillDTO> billDTOs3= new ArrayList<>();
		List<BillDTO> billDTOs4= new ArrayList<>();
		for(BillDTO dto:listBill) {
			if(dto.getTrangThai().equals("NEW")||dto.getTrangThai().equals("NEWS")){// neu la don hnag moi them vao list billDTOs
				billDTOs.add(dto);
			}
			if(dto.getTrangThai().equals("DA XAC NHAN")){// neu la don hang da dc admin xac nhan them vao list billDTOs1
				billDTOs1.add(dto);
			}
			if(dto.getTrangThai().equals("HUY")){// neu la don hang da dc admin huy them vao list billDTOs2
				billDTOs2.add(dto);
			}
			if(dto.getGiaoHang().equals("DANG VAN CHUYEN")){// neu la don hang da dc admin giao hang them vao list billDTOs3
				billDTOs3.add(dto);
			}
			if(dto.getGiaoHang().equals("DA NHAN HANG")){// neu la don hang da dc khach hang xac nhan da nhan hang  them vao list billDTOs4
				billDTOs4.add(dto);
			}
		}

		request.setAttribute("bills1", billDTOs1);//da xac nhan
		request.setAttribute("bills3", billDTOs3);//dang van chuyen
		request.setAttribute("bills4", billDTOs4);//da nhan hang
		request.setAttribute("bills2", billDTOs2);//huy
		request.setAttribute("bills", billDTOs);// don hang moi cho xac nhan
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);
		request.setAttribute("userName", userName);

		return "admin/bill/bills";
	}

	@GetMapping(value = "/admin/billproduct/search") // chi tiet cac san pham da mua co trong 1 bill
	public String AdminBillProductSearch(HttpServletRequest request, @RequestParam(name = "idBill") Long idBill,
			@RequestParam(value = "keyword", required = false) Long keyword,
			@RequestParam(value = "page", required = false) Integer page) {
		page = page == null ? 1 : page;
		keyword = idBill;
		List<BillProductDTO> listBillProduct = billProductService.searchByBill(keyword, 0, 1000 * 10);
		request.setAttribute("billProducts", listBillProduct);
		request.setAttribute("page", page);
		request.setAttribute("keyword", keyword);
		return "admin/bill/bill";
	}

	@GetMapping(value = "/admin/delete/bills")
	public String deleteBill(@RequestParam(name = "idBill", required = true) Long billId) {
		billService.delete(billId);
		return "redirect:/admin/bill/search";
	}

	// xóa item in list bill detail
	@GetMapping(value = "/admin/delete/bill")
	public String deleteBillProduct(@RequestParam(name = "idBill", required = true) Long billId) {
		System.out.println(billId);
		System.out.println();
		billProductService.delete(billId);
		return "redirect:/admin/billproduct/search?idBill=" + billId;
	}
	//  update trangj thais bill thanhf DA XAC NHAN sau khi bill dc admin xacs nhan
	@GetMapping(value = "/admin/updateMua/bill")
	public String updateMuaBill(@RequestParam(name = "billId") Long billId) {
		BillDTO billDTO = billService.get(billId);
		billDTO.setTrangThai("DA XAC NHAN");
		billService.update(billDTO);
		return "redirect:/admin/bill/search";
	}

	@GetMapping(value = "/admin/updateHuy/bill")//update trangj thais bill thanhf HUY sau khi bill dc admin HUY
	public String updateHuyBill(@RequestParam(name = "billId") Long billId) {
		int page = 1;
		BillDTO billDTO = billService.get(billId);
		billDTO.setTrangThai("HUY");
		billDTO.setGiaoHang("HUY VAN CHUYEN");
		billService.update(billDTO);
		List<BillProductDTO> listBillProduct = billProductService.searchByBill(billId, 0, 1000 * 10);
		for (BillProductDTO billProductDTO : listBillProduct) {
			ProductDTO productDTO = productService.get(billProductDTO.getProduct().getId());
			productDTO.setSoLuong(productDTO.getSoLuong() + billProductDTO.getQuantity());
			productService.update(productDTO);

		}
		return "redirect:/admin/bill/search";
	}
	// update trang thai giao hnag sau khi admin an GIAO 
	@GetMapping(value = "/admin/updateGiao/bill")
	public String updateGiaoHangBill(@RequestParam(name = "billId") Long billId, HttpSession session,
			HttpServletRequest req) {
		session = req.getSession();
		BillDTO billDTO = billService.get(billId);
		billDTO.setTrangThai("DA CHUYEN HANG");
		billDTO.setGiaoHang("DANG VAN CHUYEN");
		billService.update(billDTO);
		return "redirect:/admin/bill/search?keyword=DA+XAC+NHAN";
	}
	@GetMapping(value = "/admin/updateSuccess/bill")
	public String updateGiaoHangSuccess(@RequestParam(name = "billId") Long billId, HttpSession session,
									 HttpServletRequest req) {
		session = req.getSession();
		BillDTO billDTO = billService.get(billId);
		billDTO.setTrangThai("DA CHUYEN HANG");
		billDTO.setGiaoHang("DANG VAN CHUYEN");
		billService.update(billDTO);
		return "redirect:/admin/bill/search?keyword=DA+XAC+NHAN";
	}

}
