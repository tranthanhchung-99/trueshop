package com.linkin.dao;

import java.util.List;

import com.linkin.entity.Review;
import com.linkin.model.SearchReviewDTO;

public interface ReviewDao {

	void add(Review review);

	void delete(Review review);

	void edit(Review review);

	Review getById(Long id);

	List<Review> find(Long productId);

	Long count(SearchReviewDTO searchReviewDTO);

	Long coutTotal(SearchReviewDTO searchReviewDTO);
}
