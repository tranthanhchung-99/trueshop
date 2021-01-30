package com.linkin.web.controller;

import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.linkin.model.BillDTO;
import com.linkin.model.BillProductDTO;
import com.linkin.model.ProductDTO;
import com.linkin.service.BillProductService;
import com.linkin.service.BillService;
import com.linkin.service.InforBillService;
import com.linkin.service.ProductService;
import com.linkin.utils.DateTimeUtils;

@Controller
public class AdminThongKeLaiXuat {
	@Autowired
	BillService billService;
	@Autowired
	BillProductService billProductService;
	@Autowired
	ProductService productService;
	InforBillService inforBillService;

	@GetMapping(value = "/admin/thongkelaixuat")
	public String AdminBillSearch(HttpServletRequest request,
			@RequestParam(value = "theo", required = false) String theo,
			@RequestParam(value = "thoigian", required = false) String thoigian) {
		theo = theo == null ? "DAY" : theo;// tuy chon de hien thi lai xuat theo ngay tuan thang nam,mac dinh ban dau la
											// ngay
		thoigian = thoigian == null ? DateTimeUtils.formatDate(new Date(), DateTimeUtils.DD_MM_YYYY) : thoigian;
		// laays ngay nhap vao tu giao dien hoawc ngay hien tai neu chua nhap.
		String thoigianmonth = thoigian.substring(3, 10);// lay gia tri thang va nam
		String thoigianyear = thoigian.substring(6, 10);// lay gia tri nam
		String daoweek = thoigian.substring(6, 10) + "-" + thoigian.substring(3, 5) + "-" + thoigian.substring(0, 2);
		// dao nguoc thoi gian ve dang nam thang ngay vi
		// java.time.LocalDate.parse("nam-thang-ngay").get(IsoFields.WEEK_OF_WEEK_BASED_YEAR)
		int week = java.time.LocalDate.parse(daoweek).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
		// tim ra tuan so n trong nam theo ngay nhap vao hoac mac dinh la ngay hien tai
		// neu chua nhap ngay ben giao dien
		String day = "";
		String month = "";
		String year = "";
		int weekcheck = 0;
		List<String> list = new ArrayList<String>();

		List<BillDTO> listBill = billService.searchByTrangThai("", "DA NHAN HANG","",0,1000);// danh sach bill da nhan hang

		List<BillDTO> billDTOs = new ArrayList<>();
		List<BillDTO> billDTOsmonth = new ArrayList<>();
		List<BillDTO> billDTOsyear = new ArrayList<>();
		List<BillDTO> billDTOsweek = new ArrayList<>();

		for (BillDTO billDTO : listBill) {// lay tung don hang 1 trong tat ca don hang
			day = billDTO.getBuyDate().substring(0, 10);// lay ngay thang nam cua 1 bill trong csdl
			month = billDTO.getBuyDate().substring(3, 10);// lay thang nam cua 1 bill trong csdl
			year = billDTO.getBuyDate().substring(6, 10);// lay nam cua 1 bill trong csdl

			String weekiput = billDTO.getBuyDate().substring(6, 10) + "-" + billDTO.getBuyDate().substring(3, 5) + "-"
					+ billDTO.getBuyDate().substring(0, 2);// dao ngay thang nam thanh nam thang ngay cua 1 bill trong
															// csdl
			weekcheck = java.time.LocalDate.parse(weekiput).get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
			// xac dinh tuan thu n trong nam dc tao cua 1bill trong csdl
			if (thoigian.equals(day)) {// neu ngay nhap vao hoac mac dinh la ngay hien tai = ngay tao cua bill
				billDTOs.add(billDTO);// them bill vao list billDTOs
			}
			if (thoigianmonth.equals(month)) {// neu thang va nam cua ngay nhap vao hoac mac dinh la cua ngay hien tai =
												// ngay tao cua bill
				billDTOsmonth.add(billDTO);// them bill vao list billDTOsmonth
			}
			if (thoigianyear.equals(year)) {// nam cua ngay nhap vao hoac mac dinh la cua ngay hien tai
				billDTOsyear.add(billDTO);// them bill vao list billDTOsmonth
			}
			if (week == weekcheck) {// neu ngay nhap vao hoac mac dinh la ngay hien tai va ngay tao bill cung nam
									// trong tuan thu n cua nam
				if (billDTO.getBuyDate().substring(6, 10).equals(thoigian.substring(6, 10))) {// kiem tra xem co cung
																								// nam khong
					billDTOsweek.add(billDTO);// them bill vao list billDTOsweek
				}
			}
		}

		double tongthuday = 0;
		double tongchiday = 0;
		double tongthuweek = 0;
		double tongchiweek = 0;
		double tongthumonth = 0;
		double tongchimonth = 0;
		double tongthuyear = 0;
		double tongchiyear = 0;

		double loinhuanday = 0;
		double loinhuanmonth = 0;
		double loinhuanweek = 0;
		double loinhuanyear = 0;
		List<BillProductDTO> billProductDTOs = new ArrayList<BillProductDTO>();// tao 1 list cac BillProductDTO moi
		for (BillDTO dto : billDTOs) {/// duyet tung bill trong list bill theo ngay
			billProductDTOs = billProductService.searchByBill(dto.getId(), 0, 10000);// lay tat ca cac mat hang va so
																						// luong dc mua tuong ung trong
																						// 1 billproduct
			for (BillProductDTO billProductDTO : billProductDTOs) {// duyet tung mat hang
				ProductDTO productDTO = productService.get(billProductDTO.getProduct().getId());// lay thong tin cua mat
																								// hang dang dc duyet
				tongchiday = tongchiday + (productDTO.getPriceIn() * billProductDTO.getQuantity());// tinh tong chi phi
				// lay gia nhap cua 1 mat hang nhan voi so luong ban ra trong 1 billproduct dang
				// dc duyeet.
			}
			tongthuday = tongthuday + dto.getPriceTotal();// tong thu nhan dc sau khi ban cua 1 bill dang dc duyet
			// bang gia tri pricetoTal cua 1 bill sau khi tru khuyen mai(5% voi don hnag dau
			// tien cua khach hnag moi)

		}
		loinhuanday = tongthuday - tongchiday;// loi nhuan theo ngay
		for (BillDTO dto : billDTOsmonth) {// tuong tu nhu tinh loi nhuan ngay , nhung day la tinh loi nhuaan thang
			billProductDTOs = billProductService.searchByBill(dto.getId(), 0, 10000);
			for (BillProductDTO billProductDTO : billProductDTOs) {
				ProductDTO productDTO = productService.get(billProductDTO.getProduct().getId());
				tongchimonth = tongchimonth + (productDTO.getPriceIn() * billProductDTO.getQuantity());
			}
			tongthumonth = tongthumonth + dto.getPriceTotal();

		}
		loinhuanmonth = tongthumonth - tongchimonth;
		for (BillDTO dto : billDTOsyear) {// tuong tu voi loi nhuan nam
			System.out.println("Don hang so" + dto.getId());
			billProductDTOs = billProductService.searchByBill(dto.getId(), 0, 10000);
			for (BillProductDTO billProductDTO : billProductDTOs) {
				ProductDTO productDTO = productService.get(billProductDTO.getProduct().getId());
				System.out.println("mã sp" + billProductDTO.getProduct().getId());
				tongchiyear = tongchiyear + (productDTO.getPriceIn() * billProductDTO.getQuantity());

			}
			tongthuyear = tongthuyear + dto.getPriceTotal();

		}
		loinhuanyear = tongthuyear - tongchiyear;
		for (BillDTO dto : billDTOsweek) {// tuong tu tinh loi nhuan cho 1 tuan
			billProductDTOs = billProductService.searchByBill(dto.getId(), 0, 10000);
			for (BillProductDTO billProductDTO : billProductDTOs) {
				ProductDTO productDTO = productService.get(billProductDTO.getProduct().getId());
				tongchiweek = tongchiweek + (productDTO.getPriceIn() * billProductDTO.getQuantity());
			}
			tongthuweek = tongthuweek + dto.getPriceTotal();
		}

		loinhuanweek = tongthuweek - tongchiweek;

		//if (theo.equals("DAY")) {// neu thoigian la DAY truyen vao tu nut bam DAY thi se do du lieu cac bill theo
									// ngay sang giao dien
			request.setAttribute("billsd", billDTOs);// danh sach bill
			request.setAttribute("laixuatd", loinhuanday);// loi nhuan theo ngay do sang giao dien
		//}
		//if (theo.equals("MONTH")) {
			request.setAttribute("billsm", billDTOsmonth);// tuong tu la do danh sach bill theo thang
			request.setAttribute("laixuatm", loinhuanmonth);
		//}
		//if (theo.equals("YEAR")) {
			request.setAttribute("billsy", billDTOsyear);// theo nam
			request.setAttribute("laixuaty", loinhuanyear);
		//}
		//if (theo.equals("WEEK")) {
			request.setAttribute("billsw", billDTOsweek);// theo tuan
			request.setAttribute("laixuatw", loinhuanweek);
		//}

		request.setAttribute("thoigian", thoigian);

		return "admin/bill/laixuat";
	}

}
