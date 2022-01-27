package com.webAdmin.dto;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoriesDto {
	private int id;
	private String name;
	private Date createdAt;
	private Date updatedAt;
	private String thumbnail;

	List<SubCategoriesDto> subCategories;
	private MultipartFile multipartFiles;
}
