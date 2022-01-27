package com.webAdmin.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class AccountsDto {
	private int id;
	private String name;
	private String phone;
	private String address;
	private String email;
	private String password;
	private String gender;
	private String type;
	private Date createdAt;
	private Date updatedAt;
	private int rating;
	private String otpCode;
	private String otpCodeExpired;
	private String avatar;
	
	private RoleDto roleInfo;
	
	private VipsDto vips;
	
	private MultipartFile multipartFiles;
}
