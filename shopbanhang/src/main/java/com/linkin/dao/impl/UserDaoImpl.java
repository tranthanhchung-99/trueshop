package com.linkin.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.linkin.dao.UserDao;
import com.linkin.entity.User;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void insert(User user) {
		entityManager.persist(user);
	}

	@Override
	public void update(User user) {
		entityManager.merge(user);
	}

	@Override
	public void delete(User user) {
		entityManager.remove(user);
	}

	@Override
	public User get(Long id) {
		return entityManager.find(User.class, id);
	}

	@Override
	public User getByUserName(String userName) {
		String jql = " select u from User u where u.username = :name ";
		return entityManager.createQuery(jql, User.class).setParameter("name", userName).getSingleResult();
	}

	@Override
	public List<User> search(String findName, int start, int length) {
		String jql = "select u from User u where name like :name ORDER BY u.id ASC";
		Query query = entityManager.createQuery(jql, User.class);
		query.setParameter("name", "%" + findName + "%");
		return query.getResultList();
	}

	@Override
	public User getByEmail(String email) {
		String jql = " select u from User u where u.email = :mail ";
		return entityManager.createQuery(jql, User.class).setParameter("mail", email).getSingleResult();
	}

}
