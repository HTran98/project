package com.webAdmin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webAdmin.entity.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
	@Query("select  c from Categories c order by c.createdAt desc")
	Optional<List<Categories>> findAllOrderByCreatedAtDesc();
}
