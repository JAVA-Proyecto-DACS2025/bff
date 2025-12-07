package com.dacs.bff.util;

import com.dacs.bff.dto.PacienteDto;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Period;

import org.springframework.stereotype.Component;

@Component
public class PacienteMapper {

    public static PacienteDto.FrontResponse fromApiHospitalResponse(PacienteDto.ApiHospitalResponse apiResponse) {
        if (apiResponse == null) {
            return null;
        }

        PacienteDto.FrontResponse dto = new PacienteDto.FrontResponse();

        // Mapear nombre y apellido
        if (apiResponse.getName() != null) {
            dto.setNombre(apiResponse.getName().getFirst());
            dto.setApellido(apiResponse.getName().getLast());
        }

        // Mapear DNI desde el id.value
        if (apiResponse.getId() != null && apiResponse.getId().getValue() != null) {
            dto.setDni(apiResponse.getId().getValue());
        }

        // Mapear fecha nacimiento y calcular edad
        if (apiResponse.getDob() != null && apiResponse.getDob().getDate() != null) {
            String dateStr = apiResponse.getDob().getDate();
            
            // extraer solo la fecha (yyyy-MM-dd) sin la hora
            String fechaSolo = dateStr.substring(0, 10);
            dto.setFecha_nacimiento(fechaSolo);
             
             try {
                 // parsear formato ISO 8601 con timestamp (ej: 1990-11-21T19:45:12.526Z)
                 OffsetDateTime odt = OffsetDateTime.parse(dateStr);
                 LocalDate nacimiento = odt.toLocalDate();
                 int edad = Period.between(nacimiento, LocalDate.now()).getYears();
                 dto.setEdad(edad);
             } catch (Exception e) {
                 // si falla el parseo, intentar formato simple yyyy-MM-dd
                 try {
                     LocalDate nacimiento = LocalDate.parse(fechaSolo);
                     int edad = Period.between(nacimiento, LocalDate.now()).getYears();
                     dto.setEdad(edad);
                 } catch (Exception ex) {
                     // si sigue fallando, dejar edad null
                 }
             }
         }

        // Mapear dirección (ciudad + estado)
        if (apiResponse.getLocation() != null) {
            String direccion = (apiResponse.getLocation().getCity() != null ? apiResponse.getLocation().getCity() : "")
                    + (apiResponse.getLocation().getState() != null ? ", " + apiResponse.getLocation().getState() : "");
            if (!direccion.trim().isEmpty() && !direccion.equals(",")) {
                dto.setDireccion(direccion);
            }
        }

        // Mapear teléfono (preferir phone si existe, sino cell)
        if (apiResponse.getPhone() != null && !apiResponse.getPhone().isEmpty()) {
            dto.setTelefono(apiResponse.getPhone());
        } else if (apiResponse.getCell() != null && !apiResponse.getCell().isEmpty()) {
            dto.setTelefono(apiResponse.getCell());
        }

        return dto;
    }
}