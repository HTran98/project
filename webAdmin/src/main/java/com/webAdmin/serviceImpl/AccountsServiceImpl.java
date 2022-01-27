package com.webAdmin.serviceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.webAdmin.dto.AccountsDto;
import com.webAdmin.entity.Accounts;
import com.webAdmin.repository.AccountsRepository;
import com.webAdmin.service.AccountsService;

@Service
@Transactional
public class AccountsServiceImpl implements AccountsService {

	@Autowired
	private AccountsRepository accountsRepository;
	private ModelMapper modelMapper = new ModelMapper();

	private BCryptPasswordEncoder brBCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Override
	public List<AccountsDto> getAll() {
		List<AccountsDto> listAccountsDto = null;
		List<Accounts> listAccounts;
		try {
			listAccounts = accountsRepository.findAllOrderByCreatedAtDesc().orElseThrow(() -> new NotFound());
			listAccountsDto = listAccounts.stream().map(account -> modelMapper.map(account, AccountsDto.class))
					.collect(Collectors.toList());
		} catch (NotFound e) {

			e.printStackTrace();
		}

		return listAccountsDto;
	}

	@Override
	public AccountsDto getById(int id) {
		Accounts account = accountsRepository.findById(id).get();

		return modelMapper.map(account, AccountsDto.class);
	}

	@Override
	public AccountsDto addAccount(AccountsDto accountsDto) {
		Date createAt = new Date();
		accountsDto.setCreatedAt(createAt);
		accountsDto.setUpdatedAt(createAt);
		accountsDto.setPassword(brBCryptPasswordEncoder.encode(accountsDto.getPassword()));

		Accounts accounts = modelMapper.map(accountsDto, Accounts.class);
		accounts = accountsRepository.save(accounts);

		return modelMapper.map(accounts, AccountsDto.class);
	}

	@Override
	public void deleteById(int id) {
		accountsRepository.deleteById(id);

	}

	@Override
	public void updateAccount(AccountsDto accountsDto) {
		Accounts accounts = accountsRepository.findById(accountsDto.getId()).get();
		Date updatedAt = new Date();
		accountsDto.setCreatedAt(accounts.getCreatedAt());
		accountsDto.setUpdatedAt(updatedAt);

		if (StringUtils.isEmpty(accountsDto.getAvatar())) {
			accountsDto.setAvatar(accounts.getAvatar());
		}
		if (StringUtils.isEmptyOrWhitespace(accountsDto.getPassword())) {
			accountsDto.setPassword(accounts.getPassword());
		} else {
			accountsDto.setPassword(brBCryptPasswordEncoder.encode(accountsDto.getPassword()));
		}
		accounts = modelMapper.map(accountsDto, Accounts.class);

		accountsRepository.save(accounts);
	}

	@Override
	public List<AccountsDto> getByRoleId(int id) {

		List<AccountsDto> listAccountsDto = null;
		List<Accounts> listAccounts;
		try {
			listAccounts = accountsRepository.findByRoleInfoId(id).orElseThrow(() -> new NotFound());
			listAccountsDto = listAccounts.stream().map(account -> modelMapper.map(account, AccountsDto.class))
					.collect(Collectors.toList());
		} catch (NotFound e) {

			e.printStackTrace();
		}

		return listAccountsDto;
	}

	@Override
	public List<AccountsDto> getByVipsId(int id) {
		List<AccountsDto> listAccountsDto = null;
		List<Accounts> listAccounts;
		try {
			listAccounts = accountsRepository.findByVipsId(id).orElseThrow(() -> new NotFound());
			listAccountsDto = listAccounts.stream().map(account -> modelMapper.map(account, AccountsDto.class))
					.collect(Collectors.toList());
		} catch (NotFound e) {

			e.printStackTrace();
		}

		return listAccountsDto;
	}

}
