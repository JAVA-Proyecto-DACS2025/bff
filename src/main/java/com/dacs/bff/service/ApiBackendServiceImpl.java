package com.dacs.bff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendClient;
import com.dacs.bff.dto.AlumnoDto;

@Service
public class ApiBackendServiceImpl implements ApiBackendService{

	@Autowired
	private ApiBackendClient apiBackendClient;
	
	@Override
	public String ping() {
		return apiBackendClient.ping();
	}

	@Override
	public AlumnoDto getAlumnoById(Long id) throws Exception {
		//TODO validar parametro y lanzar exepcion
		return apiBackendClient.alumnoById(id);
	}

	@Override
	public List<AlumnoDto> getAlumnos() {
		// TODO Auto-generated method stub
		return apiBackendClient.alumnos();
	}

	@Override
	public AlumnoDto savesAlumno(AlumnoDto alumno) throws Exception {
		//TODO validar parametro y lanzar exepcion
		return apiBackendClient.save(alumno);
	}

	@Override
	public AlumnoDto updateAlumno(AlumnoDto alumno) throws Exception {
		//TODO validar parametro y lanzar exepcion
		return apiBackendClient.update(alumno);
	}

	@Override
	public AlumnoDto deleteAlumno(Long id) throws Exception {
		//TODO validar parametro y lanzar exepcion
		return apiBackendClient.delete(id);
	}



}
