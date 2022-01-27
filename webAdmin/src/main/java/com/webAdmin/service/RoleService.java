package com.webAdmin.service;

import java.util.List;

import com.webAdmin.dto.RoleDto;

public interface RoleService {
	List<RoleDto> getAll();

	RoleDto getById(int id);

	RoleDto addRole(RoleDto roleDto);

	void update(RoleDto roleDto);

	void deleteById(int id);
}
