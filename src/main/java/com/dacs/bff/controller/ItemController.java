package com.dacs.bff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("hasRole('ROLE-B')")
public class ItemController {

	@Autowired
	private ApiConectorService apiConectorService;

	@GetMapping(value = "")
    public List<ItemDto> items() {
		log.info("Obteniendo lista de items - requiere ROLE-B");
		return apiConectorService.items();
	}
	
	@GetMapping(value = "/{id}")
    public ItemDto getItemById(@PathVariable Integer id) throws Exception {
		log.info("Obteniendo item con ID: {} - requiere ROLE-B", id);
		return apiConectorService.getItemById(id);
	}
}
