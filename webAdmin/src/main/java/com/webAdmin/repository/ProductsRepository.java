package com.webAdmin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webAdmin.entity.Products;

public interface ProductsRepository extends JpaRepository<Products, Integer> {
	Optional<List<Products>> findBySubcategoriesId(int id);
	
	@Query("select  p from Products p order by p.createdAt desc")
	Optional<List<Products>> findAllOrderByCreatedAtDesc();
}
