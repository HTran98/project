package com.webAdmin.entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserDetailCustom implements UserDetails {

	private static final long serialVersionUID = 1L;
	private Accounts account;

	public Collection<? extends GrantedAuthority> getAuthorities() {

		return Collections.singleton(new SimpleGrantedAuthority(account.getRoleInfo().getRoleName()));
	}

	public String getPassword() {

		return account.getPassword();
	}

	
	public String getUsername() {

		return account.getName();
	}

	public boolean isAccountNonExpired() {

		return true;
	}

	public boolean isAccountNonLocked() {

		return true;
	}

	public boolean isCredentialsNonExpired() {

		return true;
	}

	public boolean isEnabled() {

		return true;
	}

}
