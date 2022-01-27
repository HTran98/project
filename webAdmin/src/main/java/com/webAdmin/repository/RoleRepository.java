package com.webAdmin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webAdmin.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	@Query("select  r from Role r order by r.createdAt desc")
	Optional<List<Role>> findAllOrderByCreatedAtDesc();

}
