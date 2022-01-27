package com.webAdmin.service;

import java.util.List;

import com.webAdmin.dto.VipsDto;

public interface VipsService {
	VipsDto getById(int id);

	List<VipsDto> getAll();

}
