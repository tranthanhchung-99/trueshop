package com.linkin.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.linkin.dao.CommentDao;
import com.linkin.entity.Comment;

@Transactional
@Repository
public class CommentDaoImpl implements CommentDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void insert(Comment comment) {
		entityManager.persist(comment);
	}

	@Override
	public void update(Comment comment) {
		entityManager.merge(comment);
	}

	@Override
	public void delete(Long id) {
		entityManager.remove(get(id));
	}

	@Override
	public Comment get(Long id) {
		return entityManager.find(Comment.class, id);
	}

	@Override
	public List<Comment> searchByProduct(Long id) {
		String jql = "select c from Comment c join c.product p join c.user u where p.id = :pId ORDER BY Date(c.commentDate) DESC";
		return entityManager.createQuery(jql, Comment.class).setParameter("pId", id).getResultList();
	}

}
