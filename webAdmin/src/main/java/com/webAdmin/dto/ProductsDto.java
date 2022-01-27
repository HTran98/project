package com.webAdmin.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ProductsDto {
	private int id;

	private String name;
	private Date createdAt;
	private Date updatedAt;
	private String thumbnail;

	private SubCategoriesDto subcategories;
	private MultipartFile multipartFiles;
}
