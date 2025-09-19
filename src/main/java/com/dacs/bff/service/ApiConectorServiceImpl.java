package com.dacs.bff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiConectorClient;
import com.dacs.bff.dto.ItemDto;

@Service
public class ApiConectorServiceImpl implements ApiConectorService {

	@Autowired
	private ApiConectorClient apiConectorClient;
	
	@Override
	public String ping() {
		return apiConectorClient.ping();
	}

	@Override
	public List<ItemDto> items() {
		return apiConectorClient.items();
	}

	@Override
	public ItemDto getItemById(Integer id) throws Exception {
		
		List<ItemDto> items = apiConectorClient.items();
		ItemDto item = null;
		for (ItemDto itemDto : items) {
			if(itemDto.getId() == id) {
				item = itemDto;
				break;
			}
			
		}
		if(item == null) {
			throw new Exception("Item no encontrado");
		}	

		return item;
	}

}
