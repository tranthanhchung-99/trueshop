package com.linkin.web.controller;

import com.linkin.entity.Bill;
import com.linkin.model.BillDTO;
import com.linkin.model.BillProductDTO;
import com.linkin.model.CategoryDTO;
import com.linkin.model.ProductDTO;
import com.linkin.service.BillProductService;
import com.linkin.service.BillService;
import com.linkin.service.InforBillService;
import com.linkin.service.ProductService;

import javassist.expr.NewArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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

		List<BillDTO> listBill = billService.searchByTrangThai("", "", userName, 0, page * 10);// danh sach bill theo
																								// trang thai va giao
																								// hang
		List<BillDTO> billDTOs = new ArrayList<>();
		List<BillDTO> billDTOs1 = new ArrayList<>();
		List<BillDTO> billDTOs2 = new ArrayList<>();
		List<BillDTO> billDTOs3 = new ArrayList<>();
		List<BillDTO> billDTOs4 = new ArrayList<>();
		for (BillDTO dto : listBill) {
			if (dto.getTrangThai().equals("NEW") || dto.getTrangThai().equals("NEWS")) {// neu la don hnag moi them vao
																						// list billDTOs
				billDTOs.add(dto);
			}
			if (dto.getTrangThai().equals("DA XAC NHAN")) {// neu la don hang da dc admin xac nhan them vao list
															// billDTOs1
				billDTOs1.add(dto);
			}
			if (dto.getTrangThai().equals("HUY")) {// neu la don hang da dc admin huy them vao list billDTOs2
				billDTOs2.add(dto);
			}
			if (dto.getGiaoHang().equals("DANG VAN CHUYEN")) {// neu la don hang da dc admin giao hang them vao list
																// billDTOs3
				billDTOs3.add(dto);
			}
			if (dto.getGiaoHang().equals("DA NHAN HANG")) {// neu la don hang da dc khach hang xac nhan da nhan hang
															// them vao list billDTOs4
				billDTOs4.add(dto);
			}
		}

		request.setAttribute("bills1", billDTOs1);// da xac nhan
		request.setAttribute("bills3", billDTOs3);// dang van chuyen
		request.setAttribute("bills4", billDTOs4);// da nhan hang
		request.setAttribute("bills2", billDTOs2);// huy
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
		request.setAttribute("billId", idBill);
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

	// update trangj thais bill thanhf DA XAC NHAN sau khi bill dc admin xacs nhan
	@GetMapping(value = "/admin/updateMua/bill")
	public String updateMuaBill(@RequestParam(name = "billId") Long billId) {
		BillDTO billDTO = billService.get(billId);
		billDTO.setTrangThai("DA XAC NHAN");
		billService.update(billDTO);
		return "redirect:/admin/bill/search";
	}

	@GetMapping(value = "/admin/updateHuy/bill") // update trangj thais bill thanhf HUY sau khi bill dc admin HUY
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

	@GetMapping(value = "/admin/updateNhan/bill")
	public String updateGiaoHangBillAdmin(@RequestParam(name = "billId") Long billId, HttpSession httpSession,
			HttpServletRequest req) {
		httpSession = req.getSession();
		BillDTO billDTO = billService.get(billId);
		billDTO.setGiaoHang("DA NHAN HANG");
		billService.update(billDTO);
		return "redirect:/admin/bill/search";
	}

	@GetMapping(value = "/admin/add/bill-product")
	public String updateAdmin(@RequestParam(name = "billId") Long billId, HttpSession httpSession,
			HttpServletRequest req) {
		System.out.println("oknnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
		List<ProductDTO> listPros = productService.search("", "", "", "", 0L, 100000000000000L, 0, 10 * 100);
		// mooi sp lay 1 sp vs 1 size show ra trang chu
		for (int i = 0; i < listPros.size() - 1; i++) {// tao vong lap theo bien i bat dau tu 0 dau cua listPro
			for (int j = listPros.size() - 1; j > i; j--) {// tao vong lap theo bien j bat dau tu phan tu cuoi listPro
				if (listPros.get(i).getName().equals(listPros.get(j).getName())) {// neu ten cua 2 sp thu i va thu j
																					// giong
																					// nhau thi xoa ptu thu j trong
																					// listPro
					listPros.remove(listPros.get(j));
				}
			}
		}
		req.setAttribute("productList", listPros);
		req.setAttribute("billId", billId);
		return "admin/bill/addproduct";
	}

	@GetMapping(value = "/admin/product")
	public String updateproductAdmin(@RequestParam(name = "id") Long id, HttpSession httpSession,
			@RequestParam(name = "billId") Long billId, HttpServletRequest req, Model model) {
		model.addAttribute("billproduct", new BillProductDTO());
		ProductDTO product = productService.get(id);
		List<ProductDTO> list = productService.searchName(product.getName(), product.getCategory().getName(),
				product.getThuongHieuDTO().getName(), "", 1L, 100000000000L, 0, 10000000);// toan bo sp trong db cung
		req.setAttribute("billId", billId); // ten vs sp o tren
		req.setAttribute("size", list);
		req.setAttribute("product", product);
		return "admin/bill/product";
	}

	@GetMapping(value = "/admin/buy")
	public String buyAdmin() {

		return "admin/bill/product";
	}

	@PostMapping(value = "/admin/buy")
	public String buyproductAdmin(@ModelAttribute(name = "billproduct") BillProductDTO billProductDTOm,
			@RequestParam(name = "productId") Long idP, HttpSession httpSession, HttpServletRequest req,
			@RequestParam(name = "billId") Long billId) {
		ProductDTO product = productService.get(idP);
		BillProductDTO billProductDTO = new BillProductDTO();
		billProductDTO.setBill(new BillDTO(billId));
		billProductDTO.setProduct(new ProductDTO(product.getId()));
		billProductDTO.setQuantity(billProductDTOm.getQuantity());
		billProductDTO.setUnitPrice(product.getPriceOut());
		billProductService.insert(billProductDTO);
		List<BillProductDTO> listBillProduct = billProductService.searchByBill(billId, 0, 1000 * 10);
		BillDTO billDTO = billService.get(billId);
		Long newPrice = 0L;
		Long newPriceTotal = 0L;
		for (BillProductDTO billProductDTO2 : listBillProduct) {
			newPrice += billProductDTO2.getUnitPrice() * billProductDTO2.getQuantity();
		}
		List<BillDTO> list = billService.searchByBuyerId(billDTO.getUser().getId(), 0, 1000 * 10); // search trong bang
		// bill
// update so luong sp sau khi mua hang thanh cong.
		
		product.setSoLuong(product.getSoLuong() -billProductDTOm.getQuantity() );
		productService.update(product);

		if (list.size() == 1) { // lan dau mua giam 5% tong don hang
			newPriceTotal = (newPrice - ((newPrice * 5) / 100));
			billDTO.setPriceTotal(newPriceTotal);
			billDTO.setTotal(newPrice);
			billDTO.setDiscountPercent(5);

		} else {

			billDTO.setPriceTotal(newPrice);
			billDTO.setDiscountPercent(0);
			billDTO.setTotal(newPrice);
			billDTO.setStatus("OLD");
		}
		
		billService.update(billDTO);
		return "redirect:/admin/billproduct/search?idBill=" + billId;
	}
}
