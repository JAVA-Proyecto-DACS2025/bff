package com.dacs.bff.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.dacs.bff.api.client.ApiBackendTipoIntervenciones;
import com.dacs.bff.dto.TipoIntervencionDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('admin') or hasRole('personal_medico')")
public class TipoIntervenciónResolver {

    private final ApiBackendTipoIntervenciones tipoIntervenciónService;

    @QueryMapping(name = "tiposIntervencion")
    public List<TipoIntervencionDto> tiposIntervencion() {
        return tipoIntervenciónService.getTipoIntervenciones().getBody();
    }
}
