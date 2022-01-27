package com.webAdmin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webAdmin.entity.SubCategories;

public interface SubCategoriesRepository extends JpaRepository<SubCategories, Integer> {
	Optional<List<SubCategories>> findByCategoryId(int id);
	
	@Query("select  s from SubCategories s order by s.createdAt desc")
	Optional<List<SubCategories>> findAllOrderByCreatedAtDesc();
}
