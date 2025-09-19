package com.dacs.bff.service;

import java.util.List;

import com.dacs.bff.dto.AlumnoDto;

public interface ApiBackendService {

	public String ping();

	public AlumnoDto getAlumnoById(Long id) throws Exception;

	public List<AlumnoDto> getAlumnos();

	public AlumnoDto savesAlumno(AlumnoDto alumno) throws Exception;

	public AlumnoDto updateAlumno(AlumnoDto alumno) throws Exception;

	public AlumnoDto deleteAlumno(Long id) throws Exception;
}
