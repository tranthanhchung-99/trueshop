package com.linkin.service;

import com.linkin.entity.Bill;
import com.linkin.model.InforBillDTO;

public interface InforBillService {
	void insert(InforBillDTO inforBillDTO);

	void update(InforBillDTO inforBillDTO);

	void delete(Long id);
}
