package com.linkin.dao;

import java.util.Date;
import java.util.List;

import com.linkin.entity.Bill;

public interface BillDao {
	void insert(Bill bill);

	void update(Bill bill);

	void delete(Bill bill);

	Bill get(Long id);

	List<Bill> search(String findName, int start, int length);

	List<Bill> searchByBuyerId(Long buyerId, int start, int length);
	
	List<Bill> searchByTrangThai(String trangThaiName, String giaoHangName ,String findName, int start, int length);
	
	List<Bill> searchByLaixuat(Date thoiGian);
}
