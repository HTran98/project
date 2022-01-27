package com.webAdmin.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "accounts")
public class Accounts {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@ManyToOne
	@JoinColumn(name = "roleId")
	private Role roleInfo;
	@ManyToOne
	@JoinColumn(name = "VipId")
	private Vips vips;
}
