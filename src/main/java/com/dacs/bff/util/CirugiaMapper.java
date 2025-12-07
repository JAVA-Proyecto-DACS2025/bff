package com.dacs.bff.util;

import org.springframework.stereotype.Component;

import com.dacs.bff.dto.CirugiaDTO;

@Component
public class CirugiaMapper {

    public CirugiaDTO.FrontResponse toFrontResponse(CirugiaDTO.BackResponse backResp) {
        if (backResp == null) {
            return null;
        }

        CirugiaDTO.FrontResponse front = new CirugiaDTO.FrontResponse();
        
        // copiar campos simples
        front.setId(backResp.getId());
        front.setServicio(backResp.getServicio());
        front.setPrioridad(backResp.getPrioridad());
        front.setFecha_hora_inicio(backResp.getFecha_hora_inicio());
        front.setEstado(backResp.getEstado());
        front.setAnestesia(backResp.getAnestesia());
        front.setTipo(backResp.getTipo());

        // extraer nombre del paciente (concatenar nombre + apellido o usar campo nombre)
        if (backResp.getPaciente() != null) {
            front.setDni(backResp.getPaciente().getDni());
            front.setPacienteId(backResp.getPaciente().getId());
            String nombrePaciente = backResp.getPaciente().getNombre();
            String apellidoPaciente = backResp.getPaciente().getApellido();
            if (nombrePaciente != null && apellidoPaciente != null) {
                front.setPaciente(nombrePaciente + " " + apellidoPaciente);
            } else if (nombrePaciente != null) {
                front.setPaciente(nombrePaciente);
            } else {
                front.setPaciente(apellidoPaciente);
            }
        }

        // extraer nombre del quirófano (asume que QuirofanoDto tiene getNombre())
        if (backResp.getQuirofano() != null && backResp.getQuirofano().getNombre() != null) {
            front.setQuirofano(backResp.getQuirofano().getNombre());
        }

        return front;
    }
}
