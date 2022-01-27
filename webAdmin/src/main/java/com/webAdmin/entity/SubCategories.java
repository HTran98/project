package com.webAdmin.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "subcategories")
public class SubCategories {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private Date createdAt;
	private Date updatedAt;
	private String thumbnail;
	@ManyToOne
	@JoinColumn(name = "CategoryId")
	private Categories category;

	@OneToMany(mappedBy = "subcategories")
	List<Products> products;

}
