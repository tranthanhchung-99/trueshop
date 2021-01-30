package com.linkin.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.linkin.dao.BillDao;
import com.linkin.entity.Bill;

@Transactional
@Repository
public class BillDaoImpl implements BillDao {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void insert(Bill bill) {
		entityManager.persist(bill);
	}

	@Override
	public void update(Bill bill) {
		entityManager.merge(bill);
	}

	@Override
	public void delete(Bill bill) {
		entityManager.remove(bill);

	}

	@Override
	public Bill get(Long id) {
		return entityManager.find(Bill.class, id);
	}

	@Override
	public List<Bill> search(String findName, int start, int length) {
		String jql = "select b from Bill b join b.buyer u join b.inforBill ib"
				+ " where u.name like :uname ORDER BY Date(b.buyDate) DESC";
		return entityManager.createQuery(jql, Bill.class).setParameter("uname", "%" + findName + "%").getResultList();
	}

	@Override
	public List<Bill> searchByBuyerId(Long buyerId, int start, int length) {
		String jql = "select b from Bill b join b.buyer u join b.inforBill ib where u.id =:buyerId ORDER BY Date(b.buyDate) DESC";
		return entityManager.createQuery(jql, Bill.class).setParameter("buyerId", buyerId).getResultList();
	}

	@Override
	public List<Bill> searchByTrangThai(String trangThaiName, String giaoHangName, String findName, int start,
			int length) {
		String jql = "select b from Bill b join b.buyer u join b.inforBill ib where (b.trangthai like:bTrang and b.giaohang like:bGiao and u.name like :uname) ORDER BY Date(b.buyDate) DESC";
		return entityManager.createQuery(jql, Bill.class).setParameter("bTrang", "%" + trangThaiName + "%")
				.setParameter("bGiao", "%" + giaoHangName + "%").setParameter("uname", "%" + findName + "%")
				.setFirstResult(start).setMaxResults(length).getResultList();
	}

	@Override
	public List<Bill> searchByLaixuat(Date thoigian) {
		String jql = "select b from Bill b join b.buyer u join b.inforBill ib"
				+ " where b.buyDate like:uname ORDER BY Date(b.buyDate) DESC";
		return entityManager.createQuery(jql, Bill.class).setParameter("uname", "%" + thoigian + "%").getResultList();
	}

}
