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

import com.webAdmin.dto.ProductsDto;
import com.webAdmin.dto.SubCategoriesDto;
import com.webAdmin.entity.SubCategories;
import com.webAdmin.repository.SubCategoriesRepository;
import com.webAdmin.service.ProductsService;
import com.webAdmin.service.SubCategoriesService;

@Service
@Transactional
public class SubCategoriesServiceImpl implements SubCategoriesService {

	@Autowired
	private SubCategoriesRepository subCategoriesRepository;

	@Autowired
	private ProductsService productsService;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<SubCategoriesDto> getAll() {
		List<SubCategoriesDto> listSubCategoriesDto = null;
		List<SubCategories> listSubCategories;
		try {
			listSubCategories = subCategoriesRepository.findAllOrderByCreatedAtDesc().orElseThrow(() -> new NotFound());
			listSubCategoriesDto = listSubCategories.stream()
					.map(subCategory -> modelMapper.map(subCategory, SubCategoriesDto.class))
					.collect(Collectors.toList());
		} catch (NotFound e) {

			e.printStackTrace();
		}

		return listSubCategoriesDto;
	}

	@Override
	public SubCategoriesDto getById(int id) {
		SubCategories subCategories = subCategoriesRepository.findById(id).get();
		return modelMapper.map(subCategories, SubCategoriesDto.class);
	}

	@Override
	public SubCategoriesDto add(SubCategoriesDto subCategoriesDto) {
		Date createdAt = new Date();
		subCategoriesDto.setCreatedAt(createdAt);
		subCategoriesDto.setUpdatedAt(createdAt);

		SubCategories subCategories = modelMapper.map(subCategoriesDto, SubCategories.class);

		subCategories = subCategoriesRepository.save(subCategories);

		return modelMapper.map(subCategories, SubCategoriesDto.class);
	}

	@Override
	public void update(SubCategoriesDto subCategoriesDto) {
		SubCategories subCategories = subCategoriesRepository.findById(subCategoriesDto.getId()).get();
		Date updatedAt = new Date();
		subCategoriesDto.setCreatedAt(subCategories.getCreatedAt());
		subCategoriesDto.setUpdatedAt(updatedAt);
		if (StringUtils.isEmpty(subCategoriesDto.getThumbnail())) {
			subCategoriesDto.setThumbnail(subCategories.getThumbnail());
		}

		subCategories = modelMapper.map(subCategoriesDto, SubCategories.class);

		subCategoriesRepository.save(subCategories);
	}

	@Override
	public void deleteById(int id) {
		List<ProductsDto> listProduct = productsService.getBySubCategoryId(id);
		for (ProductsDto productsDto : listProduct) {
			productsService.deleteById(productsDto.getId());
		}
		subCategoriesRepository.deleteById(id);

	}

	@Override
	public List<SubCategoriesDto> getByCategoryId(int id) {
		List<SubCategoriesDto> listSubCategoriesDto = null;
		List<SubCategories> listSubCategories;
		try {
			listSubCategories = subCategoriesRepository.findByCategoryId(id).orElseThrow(() -> new NotFound());
			listSubCategoriesDto = listSubCategories.stream()
					.map(subCategory -> modelMapper.map(subCategory, SubCategoriesDto.class))
					.collect(Collectors.toList());
		} catch (NotFound e) {

			e.printStackTrace();
		}

		return listSubCategoriesDto;
	}

}
