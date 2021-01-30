package com.linkin.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.linkin.dao.ThuongHieuDao;
import com.linkin.entity.ThuongHieu;

@Transactional
@Repository
public class ThuongHieuDaoImpl implements ThuongHieuDao {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void insert(ThuongHieu thuongHieu) {
		entityManager.persist(thuongHieu);
	}

	@Override
	public void update(ThuongHieu thuongHieu) {
		entityManager.merge(thuongHieu);
	}

	@Override
	public void delete(ThuongHieu thuongHieu) {
		entityManager.remove(thuongHieu);
	}

	@Override
	public ThuongHieu get(long id) {
		return entityManager.find(ThuongHieu.class, id);
	}

	@Override
	public List<ThuongHieu> search(String findName) {
		String jql = "select c from ThuongHieu c where c.name like :name ORDER BY c.name ASC";
		return entityManager.createQuery(jql, ThuongHieu.class).setParameter("name", "%" + findName + "%")
				.getResultList();
	}

	@Override
	public List<ThuongHieu> search(String findName, int offset, int maxPerPage) {
		String jql = "select c from ThuongHieu c where c.name like :name ORDER BY c.name ASC";
		return entityManager.createQuery(jql, ThuongHieu.class).setParameter("name", "%" + findName + "%")
				.getResultList();
	}

	@Override
	public int count(String findName) {
		String jql = "select count(c) from ThuongHieu c where c.name like :name";
		return entityManager.createQuery(jql, Integer.class).setParameter("name", "%" + findName + "%")
				.getSingleResult();
	}

}
