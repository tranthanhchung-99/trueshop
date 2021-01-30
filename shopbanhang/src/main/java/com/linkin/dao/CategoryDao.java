package com.linkin.dao;

import java.util.List;

import com.linkin.entity.Category;

public interface CategoryDao {
	void insert(Category category);

	void update(Category category);

	void delete(Category category);

	Category get(Long id);

	List<Category> search(String findName);

	List<Category> search(String findName, int offset, int maxPerPage);

	int count(String findName);
}
