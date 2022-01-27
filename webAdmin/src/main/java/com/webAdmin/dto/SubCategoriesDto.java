package com.webAdmin.dto;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SubCategoriesDto {
	private int id;

	private String name;
	private Date createdAt;
	private Date updatedAt;
	private String thumbnail;

	private CategoriesDto category;

	List<ProductsDto> products;
	private MultipartFile multipartFiles;
}
