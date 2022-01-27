package com.webAdmin.service;

import java.util.List;

import com.webAdmin.dto.AccountsDto;

public interface AccountsService {
	List<AccountsDto> getAll();

	AccountsDto getById(int id);
	
	AccountsDto addAccount(AccountsDto accountsDto);
	
	List<AccountsDto> getByRoleId(int id);
	
	List<AccountsDto> getByVipsId(int id);

	void deleteById(int id);

	void updateAccount(AccountsDto accountsDto);
}
