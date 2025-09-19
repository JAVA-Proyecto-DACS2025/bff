package com.dacs.bff.service;

import java.util.List;

import com.dacs.bff.dto.ItemDto;

public interface ApiConectorService {
	
	public String ping();
	
	public List<ItemDto> items();
	
	public ItemDto getItemById(Integer id) throws Exception;
}
