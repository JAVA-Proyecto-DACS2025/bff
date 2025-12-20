package com.dacs.bff.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.dacs.bff.dto.PacienteDto;
import com.dacs.bff.dto.MiembroEquipoDTO;

import java.time.LocalDate;
import java.time.Period;

@Configuration
public class ModelMapperConfig {
    
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        // BackResponse -> FrontResponse (incluye cálculo de edad)
        modelMapper.typeMap(PacienteDto.BackResponse.class, PacienteDto.FrontResponse.class)
            .addMappings(mapper -> {
                // Calcular edad desde fechaNacimiento
                mapper.using(ctx -> {
                    PacienteDto.BackResponse src = (PacienteDto.BackResponse) ctx.getSource();
                    if (src == null || src.getFecha_nacimiento() == null) {
                        return null;
                    }
                    try {
                        LocalDate fechaNacimiento = LocalDate.parse(src.getFecha_nacimiento());
                        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
                    } catch (Exception e) {
                        return null;
                    }
                }).map(src -> src, PacienteDto.FrontResponse::setEdad);
            });

        // FrontResponse -> BackResponse (reverso, ignora edad)
        modelMapper.typeMap(PacienteDto.FrontResponse.class, PacienteDto.BackResponse.class)
            .addMappings(mapper -> {
                mapper.skip(PacienteDto.BackResponse::setId); // opcional: evitar sobreescribir ID
            });

        // MiembroEquipoDTO mapping (nuevo)
        modelMapper.typeMap(MiembroEquipoDTO.BackResponse.class, MiembroEquipoDTO.Response.class)
            .addMappings(mapper -> {
                mapper.map(src -> src.getPersonal().getLegajo(), MiembroEquipoDTO.Response::setLegajo);
                mapper.map(src -> src.getPersonal().getNombre(), MiembroEquipoDTO.Response::setNombre);
                mapper.map(src -> src.getPersonal().getId(), MiembroEquipoDTO.Response::setPersonalId);
                mapper.map(MiembroEquipoDTO.BackResponse::getCirugiaId, MiembroEquipoDTO.Response::setCirugiaId);
                mapper.map(MiembroEquipoDTO.BackResponse::getRol, MiembroEquipoDTO.Response::setRol);
            });

        return modelMapper;
    }
}
