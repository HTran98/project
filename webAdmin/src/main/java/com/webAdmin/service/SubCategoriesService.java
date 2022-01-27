package com.webAdmin.service;

import java.util.List;

import com.webAdmin.dto.SubCategoriesDto;

public interface SubCategoriesService {
	List<SubCategoriesDto> getAll();

	SubCategoriesDto getById(int id);

	SubCategoriesDto add(SubCategoriesDto subCategoriesDto);

	void update(SubCategoriesDto subCategoriesDto);

	void deleteById(int id);
	
	List<SubCategoriesDto> getByCategoryId(int id);
}
