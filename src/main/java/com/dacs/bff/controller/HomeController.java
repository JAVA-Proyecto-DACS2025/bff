package com.dacs.bff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.ItemDto;
import com.dacs.bff.service.ApiConectorService;
import com.dacs.bff.ApplicationContextProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "")
public class HomeController {

	@Autowired
	private ApiConectorService apiConectorService;
	
	@GetMapping(value = "/")
    public Object root() {
		log.info("Ingrese a homecontroller 7");
		return "Hola desde MS bff de DACS";
	}
	
	@GetMapping(value = "/ping")
    public Object ping() {
		log.info("Ingrese a homecontroller ping");
		return "Hola desde MS bff de DACS PONG";
	}
	

	@GetMapping(value = "/version")
    public Object version() {
        return ApplicationContextProvider.getApplicationContext().getBean("buildInfo");
    }
	
	
	@GetMapping(value = "/conectorping")
    public Object conectorPing() {
		log.info("Ingrese a homecontroller conector ping");
		return apiConectorService.ping();
	}
	
	@GetMapping(value = "/items")
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
	
