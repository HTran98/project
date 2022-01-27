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
import com.webAdmin.entity.Products;
import com.webAdmin.repository.ProductsRepository;
import com.webAdmin.service.ProductsService;

@Service
@Transactional
public class ProductsServiceImpl implements ProductsService {

	@Autowired
	private ProductsRepository productsRePository;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<ProductsDto> getAll() {
		List<ProductsDto> listProductDto = null;

		List<Products> listProducts;
		try {
			listProducts = productsRePository.findAllOrderByCreatedAtDesc().orElseThrow(() -> new NotFound());
			listProductDto = listProducts.stream().map(product -> modelMapper.map(product, ProductsDto.class))
					.collect(Collectors.toList());
		} catch (NotFound e) {

			e.printStackTrace();
		}

		return listProductDto;
	}

	@Override
	public ProductsDto getById(int id) {
		Products products = productsRePository.findById(id).get();
		return modelMapper.map(products, ProductsDto.class);
	}

	@Override
	public ProductsDto addProduct(ProductsDto productsDto) {
		Date createdAt = new Date();
		productsDto.setCreatedAt(createdAt);
		productsDto.setUpdatedAt(createdAt);
		Products products = modelMapper.map(productsDto, Products.class);
		products = productsRePository.save(products);
		return modelMapper.map(products, ProductsDto.class);
	}

	@Override
	public void update(ProductsDto productsDto) {
		Products products = productsRePository.findById(productsDto.getId()).get();
		Date updatedAt = new Date();
		productsDto.setCreatedAt(products.getCreatedAt());
		productsDto.setUpdatedAt(updatedAt);
		if (StringUtils.isEmpty(productsDto.getThumbnail())) {
			productsDto.setThumbnail(products.getThumbnail());
		}
		products = modelMapper.map(productsDto, Products.class);

		productsRePository.save(products);

	}

	@Override
	public void deleteById(int id) {
		productsRePository.deleteById(id);

	}

	@Override
	public List<ProductsDto> getBySubCategoryId(int id) {
		List<ProductsDto> listProductDto = null;

		List<Products> listProducts;
		try {
			listProducts = productsRePository.findBySubcategoriesId(id).orElseThrow(() -> new NotFound());
			listProductDto = listProducts.stream().map(product -> modelMapper.map(product, ProductsDto.class))
					.collect(Collectors.toList());
		} catch (NotFound e) {

			e.printStackTrace();
		}

		return listProductDto;
	}

}
