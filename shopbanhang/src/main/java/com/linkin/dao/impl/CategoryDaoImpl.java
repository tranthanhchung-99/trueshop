package com.linkin.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.linkin.dao.CategoryDao;
import com.linkin.entity.Category;

@Transactional
@Repository
public class CategoryDaoImpl implements CategoryDao {
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void insert(Category category) {
		entityManager.persist(category);

	}

	@Override
	public void update(Category category) {
		entityManager.merge(category);

	}

	@Override
	public void delete(Category category) {
		entityManager.remove(category);

	}

	@Override
	public Category get(Long id) {
		return entityManager.find(Category.class, id);
	}

	@Override
	public List<Category> search(String findName) {
		String jql = "select c from Category c where c.name like :name ORDER BY c.name ASC";
		return entityManager.createQuery(jql, Category.class).setParameter("name", "%" + findName + "%")
				.getResultList();
	}

	@Override
	public List<Category> search(String findName, int start, int length) {
		String jql = "select c from Category c where c.name like :name ORDER BY c.name ASC";
		return entityManager.createQuery(jql, Category.class).setParameter("name", "%" + findName + "%")
				.getResultList();
	}

	@Override
	public int count(String findName) {
		String jql = "select count(c) from Category c where c.name like :name";
		return entityManager.createQuery(jql, Integer.class).setParameter("name", "%" + findName + "%")
				.getSingleResult();
	}

}
