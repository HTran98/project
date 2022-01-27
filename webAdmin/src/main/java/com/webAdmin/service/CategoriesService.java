package com.webAdmin.service;

import java.util.List;

import com.webAdmin.dto.CategoriesDto;

public interface CategoriesService {
	List<CategoriesDto> getAll();

	CategoriesDto getById(int id);

	CategoriesDto addCategory(CategoriesDto categoriesDto);

	void update(CategoriesDto categoriesDto);

	void deleteById(int id);
}
