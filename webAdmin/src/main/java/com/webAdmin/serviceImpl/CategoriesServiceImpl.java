package com.webAdmin.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.webAdmin.dto.CategoriesDto;
import com.webAdmin.dto.SubCategoriesDto;
import com.webAdmin.entity.Categories;
import com.webAdmin.repository.CategoriesRepository;
import com.webAdmin.service.CategoriesService;
import com.webAdmin.service.SubCategoriesService;

@Service
@Transactional
public class CategoriesServiceImpl implements CategoriesService {

	@Autowired
	private CategoriesRepository categoriesRepository;

	@Autowired
	private SubCategoriesService subCategoriesService;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<CategoriesDto> getAll() {
		List<CategoriesDto> listCategoriesDto = null;
		List<Categories> listCategories;
		try {
			listCategories = categoriesRepository.findAllOrderByCreatedAtDesc().orElseThrow(()-> new NotFound());
			listCategoriesDto = listCategories.stream().map(category -> modelMapper.map(category, CategoriesDto.class))
					.collect(Collectors.toList());
		} catch (NotFound e) {
			
			e.printStackTrace();
		}
		
		return listCategoriesDto;
	}

	@Override
	public CategoriesDto getById(int id) {
		Categories categories = categoriesRepository.findById(id).get();

		return modelMapper.map(categories, CategoriesDto.class);
	}

	@Override
	public CategoriesDto addCategory(CategoriesDto categoriesDto) {
		Date createdAt = new Date();
		categoriesDto.setCreatedAt(createdAt);
		categoriesDto.setUpdatedAt(createdAt);

		Categories categories = modelMapper.map(categoriesDto, Categories.class);
		categories = categoriesRepository.save(categories);
		return modelMapper.map(categories, CategoriesDto.class);
	}

	@Override
	public void update(CategoriesDto categoriesDto) {
		Categories categories = categoriesRepository.findById(categoriesDto.getId()).get();
		Date updatedAt = new Date();
		categoriesDto.setCreatedAt(categories.getCreatedAt());
		categoriesDto.setUpdatedAt(updatedAt);
		if(StringUtils.isEmpty(categoriesDto.getThumbnail()))
		{
			categoriesDto.setThumbnail(categories.getThumbnail());
		}
		categories = modelMapper.map(categoriesDto, Categories.class);

		categoriesRepository.save(categories);
	}

	@Override
	public void deleteById(int id) {
		List<SubCategoriesDto> listSubCategoriesDto = subCategoriesService.getByCategoryId(id);
		for (SubCategoriesDto subCategoriesDto : listSubCategoriesDto) {
			subCategoriesService.deleteById(subCategoriesDto.getId());
		}
		categoriesRepository.deleteById(id);

	}

}
