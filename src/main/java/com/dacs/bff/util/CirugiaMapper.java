package com.dacs.bff.util;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dacs.bff.dto.CirugiaDTO;
import com.dacs.bff.dto.PacienteDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component

public class CirugiaMapper {
    private static final Logger log = LoggerFactory.getLogger(CirugiaMapper.class);

    public CirugiaDTO.FrontResponse toFrontResponse(CirugiaDTO.BackResponse backResp) {
        if (backResp == null) {
            return null;
        }

        CirugiaDTO.FrontResponse front = new CirugiaDTO.FrontResponse();

        // copiar campos simples
        front.setId(backResp.getId());
        front.setPrioridad(backResp.getPrioridad());
        front.setEstado(backResp.getEstado());
        front.setAnestesia(backResp.getAnestesia());
        front.setTipo(backResp.getTipo());

        // extraer nombre del servicio
        if (backResp.getServicio() != null) {
            front.setServicioId(backResp.getServicio().getId());
            if (backResp.getServicio().getNombre() != null) {
                front.setServicioNombre(backResp.getServicio().getNombre());
            }
        }

        // extraer nombre del paciente (concatenar nombre + apellido o usar campo
        // nombre)
        if (backResp.getPaciente() != null) {
            front.setDni(backResp.getPaciente().getDni());
            front.setPacienteId(backResp.getPaciente().getId());
            String nombrePaciente = backResp.getPaciente().getNombre();
            String apellidoPaciente = backResp.getPaciente().getApellido();
            if (nombrePaciente != null && apellidoPaciente != null) {
                front.setPacienteNombre(nombrePaciente + " " + apellidoPaciente);
            } else if (nombrePaciente != null) {
                front.setPacienteNombre(nombrePaciente);
            } else {
                front.setPacienteNombre(apellidoPaciente);
            }
        }

        // Extraer fecha y hora
        if (backResp.getFecha_hora_inicio() != null) {
            String fechaHoraCompleta = backResp.getFecha_hora_inicio().toString();
            try {
                // parsear formato ISO 8601 (ej: 2025-12-07T14:30:45.123Z)
                java.time.OffsetDateTime odt = java.time.OffsetDateTime.parse(fechaHoraCompleta);
                String fecha = odt.toLocalDate().toString(); // yyyy-MM-dd
                String hora = odt.toLocalTime().toString().substring(0, 8); // HH:mm:ss

                front.setFechaInicio(fecha);
                front.setHoraInicio(hora);
            } catch (Exception e) {
                // si falla el parseo, intentar extraer manualmente
                try {
                    // formato: 2025-11-27T03:00:00 -> extrae fecha (antes de 'T') y hora (después
                    // de 'T')
                    String[] partes = fechaHoraCompleta.split("T");
                    if (partes.length >= 1) {
                        front.setFechaInicio(partes[0]); // 2025-11-27
                    }
                    if (partes.length >= 2) {
                        String horaConMs = partes[1]; // 03:00:00 o 03:00:00.123Z
                        String soloHora = horaConMs.split("\\.")[0]; // quita milisegundos
                        soloHora = soloHora.replace("Z", ""); // quita Z si existe
                        front.setHoraInicio(soloHora); // 03:00:00
                    }
                } catch (Exception ex) {
                    log.error("Error parseando fecha_hora_inicio: {}", fechaHoraCompleta, ex);
                }
            }
        }

        // extraer nombre del quirófano (asume que QuirofanoDto tiene getNombre())
        if (backResp.getQuirofano() != null && backResp.getQuirofano().getNombre() != null) {
            front.setQuirofanoNombre(backResp.getQuirofano().getNombre());
            front.setQuirofanoId(Long.valueOf(backResp.getQuirofano().getId()));
        }

        return front;
    }

}
