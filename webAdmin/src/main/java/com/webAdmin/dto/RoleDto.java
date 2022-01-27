package com.webAdmin.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RoleDto {
	private int id;
	private String roleName;
	private String description;
	private Date createdAt;
	private Date updatedAt;

	List<AccountsDto> accountInfos;
	
}
