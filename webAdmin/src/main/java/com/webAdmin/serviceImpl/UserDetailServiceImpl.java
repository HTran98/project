package com.webAdmin.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.webAdmin.entity.Accounts;
import com.webAdmin.entity.UserDetailCustom;
import com.webAdmin.repository.AccountsRepository;


@Service
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private AccountsRepository accountsRepository;

	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		Accounts accounts = null;

		accounts = accountsRepository.findByEmail(userName)
				.orElseThrow(() -> new UsernameNotFoundException("Not found account"));

		if (accounts == null) {
			throw new UsernameNotFoundException(userName);
		}
		return new UserDetailCustom(accounts);
	}

	@Transactional
	public UserDetails loadUserById(int id) throws NotFoundException {
		Accounts accounts = accountsRepository.findById(id).get();

		return new UserDetailCustom(accounts);
	}

}
