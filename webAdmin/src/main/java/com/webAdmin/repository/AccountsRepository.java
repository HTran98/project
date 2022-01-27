package com.webAdmin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webAdmin.entity.Accounts;

public interface AccountsRepository extends JpaRepository<Accounts, Integer> {
	Optional<Accounts> findByName(String name);
	
	Optional<Accounts> findByEmail(String email);

	Optional<List<Accounts>> findByRoleInfoId(int id);

	Optional<List<Accounts>> findByVipsId(int id);
	
	@Query("select  a from Accounts a order by a.createdAt desc")
	Optional<List<Accounts>> findAllOrderByCreatedAtDesc();
	
	
}
