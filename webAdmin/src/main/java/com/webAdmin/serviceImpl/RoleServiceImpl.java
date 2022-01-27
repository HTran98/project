package com.webAdmin.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webAdmin.dto.AccountsDto;
import com.webAdmin.dto.RoleDto;
import com.webAdmin.entity.Role;
import com.webAdmin.repository.RoleRepository;
import com.webAdmin.service.AccountsService;
import com.webAdmin.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AccountsService accountService;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<RoleDto> getAll() {
		List<RoleDto> listRoleDto = null;
		List<Role> listRole;
		try {
			listRole = roleRepository.findAllOrderByCreatedAtDesc().orElseThrow(() -> new NotFound());
			listRoleDto = listRole.stream().map(role -> modelMapper.map(role, RoleDto.class))
					.collect(Collectors.toList());
		} catch (NotFound e) {

			e.printStackTrace();
		}

		return listRoleDto;
	}

	@Override
	public RoleDto getById(int id) {

		Role role = roleRepository.findById(id).get();

		return modelMapper.map(role, RoleDto.class);
	}

	@Override
	public RoleDto addRole(RoleDto roleDto) {
		Date createdAt = new Date();
		roleDto.setCreatedAt(createdAt);
		roleDto.setUpdatedAt(createdAt);

		Role role = modelMapper.map(roleDto, Role.class);
		role = roleRepository.save(role);
		return modelMapper.map(role, RoleDto.class);
	}

	@Override
	public void update(RoleDto roleDto) {
		Date updatedAt = new Date();
		Role role = roleRepository.findById(roleDto.getId()).get();
		roleDto.setCreatedAt(role.getCreatedAt());
		roleDto.setUpdatedAt(updatedAt);
		role = modelMapper.map(roleDto, Role.class);

		roleRepository.save(role);

	}

	@Override
	public void deleteById(int id) {
		List<AccountsDto> listAccountsDto = accountService.getByRoleId(id);
		for (AccountsDto accountsDto : listAccountsDto) {
			accountService.deleteById(accountsDto.getId());
		}
		roleRepository.deleteById(id);

	}

}
