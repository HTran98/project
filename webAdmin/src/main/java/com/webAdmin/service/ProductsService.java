package com.webAdmin.service;

import java.util.List;

import com.webAdmin.dto.ProductsDto;

public interface ProductsService {
	List<ProductsDto> getAll();

	ProductsDto getById(int id);

	ProductsDto addProduct(ProductsDto productsDto);

	void update(ProductsDto productsDto);

	void deleteById(int id);
	
	List<ProductsDto> getBySubCategoryId(int id);
}
