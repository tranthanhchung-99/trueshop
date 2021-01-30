package com.linkin.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.linkin.dao.BillProductDao;
import com.linkin.entity.BillProduct;

@Transactional
@Repository
public class BillProductDaoImpl implements BillProductDao {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void insert(BillProduct billProduct) {
		entityManager.persist(billProduct);
	}

	@Override
	public void update(BillProduct billProduct) {
		entityManager.merge(billProduct);
	}

	@Override
	public void delete(BillProduct billProduct) {
		entityManager.remove(billProduct);
	}

	@Override
	public BillProduct get(Long id) {
		return entityManager.find(BillProduct.class, id);
	}

	@Override
	public List<BillProduct> search(String findName , int start, int length) {
		String jql = "select bp from BillProduct bp join bp.product p join bp.bill b where p.name like :pname ORDER BY bp.id ASC";
		return entityManager.createQuery(jql, BillProduct.class).setParameter("pname","%"+ findName+"%").getResultList();
	}

	@Override
	public List<BillProduct> searchByBill(Long idBill, int start, int length) {
		String jql = "select bp from BillProduct bp join bp.product p join bp.bill b where b.id =:billId ORDER BY bp.id ASC";
		return entityManager.createQuery(jql, BillProduct.class).setParameter("billId", idBill).getResultList();
	}

}
