package com.linkin.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.linkin.dao.KichThuocDao;
import com.linkin.entity.KichThuoc;

@Transactional
@Repository
public class KichThuocDaoImpl implements KichThuocDao {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void insert(KichThuoc kichThuoc) {
		entityManager.persist(kichThuoc);

	}

	@Override
	public void update(KichThuoc kichThuoc) {
		entityManager.merge(kichThuoc);

	}

	@Override
	public void delete(KichThuoc kichThuoc) {
		entityManager.remove(kichThuoc);

	}

	@Override
	public KichThuoc get(Long id) {
		return entityManager.find(KichThuoc.class, id);
	}

	@Override
	public List<KichThuoc> search(String findName) {
		String jql = "select c from KichThuoc c where c.name like :name ORDER BY c.name ASC";
		return entityManager.createQuery(jql, KichThuoc.class).setParameter("name", "%" + findName + "%")
				.getResultList();
	}

	@Override
	public List<KichThuoc> search(String findName, int start, int length) {
		String jql = "select c from KichThuoc c where c.name like :name ORDER BY c.name ASC";
		return entityManager.createQuery(jql, KichThuoc.class).setParameter("name", "%" + findName + "%")
				.getResultList();
	}

	@Override
	public int count(String findName) {
		String jql = "select count(c) from KichThuoc c where c.name like :name";
		return entityManager.createQuery(jql, Integer.class).setParameter("name", "%" + findName + "%")
				.getSingleResult();
	}

}
