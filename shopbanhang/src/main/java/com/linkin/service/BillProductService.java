package com.linkin.service;

import java.util.List;

import com.linkin.model.BillProductDTO;

public interface BillProductService {
	void insert(BillProductDTO billProductDTO);

	void update(BillProductDTO billProductDTO);

	void delete(Long id);

	List<BillProductDTO> search(String findName, int start, int length);

	List<BillProductDTO> searchByBill(Long idBill, int start, int length);
}
