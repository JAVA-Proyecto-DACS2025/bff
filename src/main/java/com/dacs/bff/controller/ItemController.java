package com.dacs.bff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.ItemDto;
import com.dacs.bff.service.ApiConectorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/items")
public class ItemController {

	@Autowired
	private ApiConectorService apiConectorService;

	
	@GetMapping(value = "")
    public List<ItemDto> items() {
		log.info("Ingrese a homecontroller conector ping");
		return apiConectorService.items();
	}
	
	
	@GetMapping(value = "/items/{id}")
    public  ItemDto getItems(@PathVariable Integer id) {
		log.info("Ingrese a homecontroller getItems");	
		try {
			return apiConectorService.getItemById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	
	}
}
	
