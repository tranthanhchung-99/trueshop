package com.linkin.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.linkin.dao.ReviewDao;
import com.linkin.entity.Product;
import com.linkin.entity.Review;
import com.linkin.model.SearchReviewDTO;

@Repository
@Transactional
public class ReviewDaoImpl implements ReviewDao {

	@Autowired
	private EntityManager entityManager;

	@Override
	public void add(Review review) {
		entityManager.persist(review);
	}

	@Override
	public void delete(Review review) {
		entityManager.remove(review);
	}

	@Override
	public void edit(Review review) {
		entityManager.merge(review);
	}

	@Override
	public Review getById(Long id) {
		return entityManager.find(Review.class, id);
	}

	@Override
	public List<Review> find(Long productId) {
		/*CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Review> criteriaQuery = criteriaBuilder.createQuery(Review.class);
		Root<Review> root = criteriaQuery.from(Review.class);

		List<Predicate> predicates = new ArrayList<Predicate>();


		if (searchReviewDTO.getProductId() != null) {
			Join<Review, Product> product = root.join("product");
			predicates.add(criteriaBuilder.equal(product.get("id"), searchReviewDTO.getProductId()));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Review> typedQuery = entityManager.createQuery(criteriaQuery.select(root));

		if (searchReviewDTO.getStart() != null) {
			typedQuery.setFirstResult((searchReviewDTO.getStart()));
			typedQuery.setMaxResults(searchReviewDTO.getLength());
		}

		return typedQuery.getResultList();*/
		String jql="select r from Review r join r.user u join r.product p where p.id =:pid ORDER BY Date(r.reviewDate) DESC";
		return entityManager.createQuery(jql, Review.class).setParameter("pid",productId ).getResultList();
	}

	@Override
	public Long count(SearchReviewDTO searchReviewDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Review> root = criteriaQuery.from(Review.class);

		List<Predicate> predicates = new ArrayList<Predicate>();


		if (searchReviewDTO.getProductId() != null) {
			Join<Review, Product> product = root.join("product");
			predicates.add(criteriaBuilder.equal(product.get("id"), searchReviewDTO.getProductId()));
		}

		criteriaQuery.where(predicates.toArray(new Predicate[] {}));
		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

	@Override
	public Long coutTotal(SearchReviewDTO searchReviewDTO) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
		Root<Review> root = criteriaQuery.from(Review.class);

		List<Predicate> predicates = new ArrayList<Predicate>();
		criteriaQuery.where(predicates.toArray(new Predicate[] {}));

		TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery.select(criteriaBuilder.count(root)));
		return typedQuery.getSingleResult();
	}

}
