package com.dacs.bff.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.TurnoDto;
import com.dacs.bff.service.ApiBackendTurnoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('admin') or hasRole('personal_medico')")
public class TurnoResolver {

    private final ApiBackendTurnoService turnoService;

    @QueryMapping
    public TurnoConnection turnosDisponibles(
            @Argument Integer pagina,
            @Argument Integer tamano,
            @Argument String fechaInicio,
            @Argument String fechaFin,
            @Argument Integer quirofanoId,
            @Argument String estado,
            @Argument Integer duracionMinutos,
            @Argument Long servicioId) {
        
        int p = pagina == null ? 0 : pagina;
        int t = tamano == null ? 10 : tamano;
        int qId = quirofanoId == null ? 0 : quirofanoId;
        
        var resp = turnoService.getTurnosDisponibles(p, t, fechaInicio, fechaFin, qId, estado, duracionMinutos, servicioId);
        return GraphQLConnectionMapper.from(resp, TurnoConnection::new);
    }
}
