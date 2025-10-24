package com.dacs.bff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.AlumnoDto;
import com.dacs.bff.service.ApiBackendService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/alumno")
@PreAuthorize("hasRole('ROLE-A')")
public class AlumnoController {

	@Autowired
	private ApiBackendService apiBackendService;

	@GetMapping("")
	public ResponseEntity<List<AlumnoDto>> getAll() {
		log.info("Obteniendo lista de alumnos - requiere ROLE-A");
		List<AlumnoDto> data = apiBackendService.getAlumnos();
		return new ResponseEntity<List<AlumnoDto>>(data, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AlumnoDto> getById(@PathVariable(value = "id") Long id) throws Exception {
		log.info("Obteniendo alumno con ID: {} - requiere ROLE-A", id);
		AlumnoDto data = apiBackendService.getAlumnoById(id);
		return new ResponseEntity<AlumnoDto>(data, HttpStatus.OK);
	}

	@PostMapping("")
	public ResponseEntity<AlumnoDto> create(@RequestBody AlumnoDto alumnoDto) throws Exception {
		log.info("Creando nuevo alumno - requiere ROLE-A");
		AlumnoDto data = apiBackendService.savesAlumno(alumnoDto);
		return new ResponseEntity<AlumnoDto>(data, HttpStatus.OK);
	}

	@PutMapping("")
	public ResponseEntity<AlumnoDto> update(@RequestBody AlumnoDto alumnoDto) throws Exception {
		log.info("Actualizando alumno con ID: {} - requiere ROLE-A", alumnoDto.getId());
		AlumnoDto data = apiBackendService.updateAlumno(alumnoDto);
		return new ResponseEntity<AlumnoDto>(data, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<AlumnoDto> delete(@PathVariable Long id) throws Exception {
		log.info("Eliminando alumno con ID: {} - requiere ROLE-A", id);
		AlumnoDto data = apiBackendService.deleteAlumno(id);
		return new ResponseEntity<AlumnoDto>(data, HttpStatus.OK);
	}
}
