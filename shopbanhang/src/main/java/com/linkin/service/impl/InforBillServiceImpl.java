package com.linkin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkin.dao.InforBillDao;
import com.linkin.entity.Bill;
import com.linkin.entity.InforBill;
import com.linkin.model.InforBillDTO;
import com.linkin.service.InforBillService;
@Transactional
@Service
public class InforBillServiceImpl implements InforBillService{
	@Autowired InforBillDao inforBillDao;
	@Override
	public void insert(InforBillDTO inforBillDTO) {
		InforBill inforBill= new InforBill();
		inforBill.setPhoneNumber(inforBillDTO.getPhoneNumber());
		inforBill.setAddress(inforBillDTO.getAddress());
		inforBill.setMethod(inforBillDTO.getMethod());
		inforBill.setNote(inforBillDTO.getNote());
		Bill bill= new Bill();
		bill.setId(inforBillDTO.getBillDTO().getId());
		inforBill.setBill(bill);
		inforBillDao.insert(inforBill);
		
	}

	@Override
	public void update(InforBillDTO inforBillDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
