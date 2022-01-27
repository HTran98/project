package com.webAdmin.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webAdmin.dto.VipsDto;
import com.webAdmin.entity.Vips;
import com.webAdmin.repository.VipsRepository;
import com.webAdmin.service.VipsService;

@Service
@Transactional
public class VipsServiceImpl implements VipsService {
	@Autowired
	private VipsRepository vipsRepository;

	private ModelMapper modelmapper = new ModelMapper();

	@Override
	public VipsDto getById(int id) {
		Vips vip = vipsRepository.findById(id).get();
		return modelmapper.map(vip, VipsDto.class);
	}

	@Override
	public List<VipsDto> getAll() {
		List<VipsDto> listVipsDto = null;
		List<Vips> listVips = vipsRepository.findAll();
		listVipsDto = listVips.stream().map(vip -> modelmapper.map(vip, VipsDto.class)).collect(Collectors.toList());
		return listVipsDto;
	}

}
