package com.linkin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linkin.dao.ReviewDao;
import com.linkin.entity.Product;
import com.linkin.entity.Review;
import com.linkin.entity.User;
import com.linkin.model.ProductDTO;
import com.linkin.model.ReviewDTO;
import com.linkin.model.SearchReviewDTO;
import com.linkin.model.UserDTO;
import com.linkin.service.ReviewService;

@Transactional
@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewDao reviewDao;

	@Override
	public void add(ReviewDTO reviewDTO) {
		Review review = new Review();
		review.setStarNumBer(reviewDTO.getStarNumber());
		review.setProduct(new Product(reviewDTO.getProductDTO().getId()));
		User user= new User();
		user.setName(reviewDTO.getUserDTO().getName());
		user.setId(reviewDTO.getUserDTO().getId());
		review.setUser(user);
		reviewDao.add(review);

	}

	@Override
	public void delete(Long id) {
		Review review = reviewDao.getById(id);
		if (review != null) {
			reviewDao.delete(review);
		}

	}

	@Override
	public void edit(ReviewDTO reviewDTO) {
		Review review = reviewDao.getById(reviewDTO.getId());
		if (review != null) {
			review.setStarNumBer(reviewDTO.getStarNumber());
			review.setProduct(new Product(reviewDTO.getProductDTO().getId()));
			User user= new User();
			user.setName(reviewDTO.getUserDTO().getName());
			review.setUser(user);
		}
		reviewDao.edit(review);
	}

	@Override
	public ReviewDTO getById(Long id) {
		Review review = reviewDao.getById(id);
		if (review != null) {
			convert(review);
		}
		return null;
	}

	private ReviewDTO convert(Review review) {
		ReviewDTO reviewDTO = new ReviewDTO();
		reviewDTO.setId(review.getId());
		reviewDTO.setStarNumber(review.getStarNumBer());
		ProductDTO productDTO= new ProductDTO();
		productDTO.setId(review.getProduct().getId());
		reviewDTO.setProductDTO(productDTO);
		UserDTO userDTO= new UserDTO();
		userDTO.setName(review.getUser().getName());
		reviewDTO.setUserDTO(userDTO);
		return reviewDTO;
	}

	@Override
	public List<ReviewDTO> find(Long productId) {
		List<Review> reviews = reviewDao.find(productId);
		List<ReviewDTO> reviewDTOs = new ArrayList<ReviewDTO>();
		reviews.forEach(rev -> {
			reviewDTOs.add(convert(rev));
		});
		return reviewDTOs;
	}

	@Override
	public Long count(SearchReviewDTO searchReviewDTO) {

		return reviewDao.count(searchReviewDTO);
	}

	@Override
	public Long coutTotal(SearchReviewDTO searchReviewDTO) {

		return reviewDao.count(searchReviewDTO);
	}

}
