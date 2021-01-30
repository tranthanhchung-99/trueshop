package com.linkin.service;

import java.util.List;

import com.linkin.model.CommentDTO;

public interface CommentService {
	void insert(CommentDTO commentDTO);

	void update(CommentDTO commentDTO);

	void delete(Long id);

	CommentDTO get(Long id);

	List<CommentDTO> searchByProduct(Long id);
}
