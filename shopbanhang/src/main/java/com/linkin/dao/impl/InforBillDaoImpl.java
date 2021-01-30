package com.linkin.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.linkin.dao.InforBillDao;
import com.linkin.entity.InforBill;
@Transactional
@Repository
public class InforBillDaoImpl implements InforBillDao {
	@PersistenceContext EntityManager entityManager;
	@Override
	public void insert(InforBill inforBill) {
		entityManager.persist(inforBill);
	}

	@Override
	public void update(InforBill inforBill) {
		entityManager.merge(inforBill);
		
	}

	@Override
	public void delete(InforBill inforBill) {
		entityManager.remove(inforBill);
		
	}

}
