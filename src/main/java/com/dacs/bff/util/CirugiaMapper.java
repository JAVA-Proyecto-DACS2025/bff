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

    /**
     * Toma un FrontRequest con fechaInicio y horaInicio y devuelve uno igual pero con fecha_hora_inicio armado para el backend.
     */
    public CirugiaDTO.FrontRequest toFrontRequest(CirugiaDTO.FrontResponse front) {
        if (front == null) return null;
        CirugiaDTO.FrontRequest nuevo = new CirugiaDTO.FrontRequest();
        // Copiar todos los campos simples
        nuevo.setId(front.getId());
        nuevo.setPrioridad(front.getPrioridad());
        nuevo.setEstado(front.getEstado());
        nuevo.setAnestesia(front.getAnestesia());
        nuevo.setTipo(front.getTipo());
        nuevo.setServicioId(front.getServicioId());
        nuevo.setPacienteId(front.getPacienteId());
        nuevo.setQuirofanoId(front.getQuirofanoId());
        // ...agrega aquí cualquier otro campo que tenga tu FrontRequest...

        // Combinar fechaInicio y horaInicio soportando formatos dd/MM/yyyy y HH:mm HS
        if (front.getFechaInicio() != null && front.getHoraInicio() != null) {
            try {
                // Normalizar fecha: de dd/MM/yyyy a yyyy-MM-dd
                String fechaStr = front.getFechaInicio().trim();
                LocalDate fecha;
                if (fechaStr.contains("/")) {
                    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    fecha = LocalDate.parse(fechaStr, inputFormatter);
                } else {
                    fecha = LocalDate.parse(fechaStr); // yyyy-MM-dd
                }

                // Normalizar hora: quitar " HS", asegurar formato HH:mm
                String horaStr = front.getHoraInicio().replace(" HS", "").trim();
                // Si viene como H:mm, agregar cero a la izquierda
                String[] partes = horaStr.split(":");
                if (partes.length == 2 && partes[0].length() == 1) {
                    horaStr = "0" + horaStr;
                }
                // Si no tiene segundos, agregar ":00"
                if (horaStr.length() == 5) {
                    horaStr = horaStr + ":00";
                }
                DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalTime hora = LocalTime.parse(horaStr, horaFormatter);
                nuevo.setFecha_hora_inicio(LocalDateTime.of(fecha, hora));
            } catch (Exception e) {
                log.error("Error combinando fechaInicio y horaInicio: {} {}", front.getFechaInicio(), front.getHoraInicio(), e);
            }
        }

        return nuevo;
    }

}
