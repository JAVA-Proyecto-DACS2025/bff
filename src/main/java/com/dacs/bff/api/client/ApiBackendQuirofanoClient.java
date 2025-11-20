package com.dacs.bff.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dacs.bff.config.FeignConfig;
import com.dacs.bff.dto.QuirofanoDTO;

@FeignClient(
		name = "apiBackendQuirofanoClient", 
		url = "${feign.client.config.apiBackendQuirofanoClient.url}",
		configuration = FeignConfig.class
		)


public interface ApiBackendQuirofanoClient {

    @GetMapping("/quirofano")
    List<QuirofanoDTO> quirofanos();

    @PostMapping("/quirofano")
    QuirofanoDTO save(@RequestBody QuirofanoDTO quirofano);
    
    @PutMapping("/quirofano")
    QuirofanoDTO update(@RequestBody QuirofanoDTO quirofano);

    @DeleteMapping("/quirofano/{id}")
    QuirofanoDTO delete(@PathVariable("id") Long id);
}

