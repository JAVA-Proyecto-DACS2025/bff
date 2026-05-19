package com.dacs.bff.graphql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.dacs.bff.dto.CirugiaDTO;
import com.dacs.bff.dto.IntervencionDto;
import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.ServicioDto;
import com.dacs.bff.service.ApiBackendCirugiaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('admin') or hasRole('personal_medico')")
public class CirugiaResolver {

    private final ApiBackendCirugiaService cirugiaService;

    @QueryMapping
    public CirugiaConnection cirugias(@Argument Integer pagina,
                                      @Argument Integer tamano,
                                      @Argument String fechaInicio,
                                      @Argument String fechaFin,
                                      @Argument String estado,
                                      @Argument String search,
                                      @Argument String sort,
                                      @Argument String order) {
        int p = pagina == null ? 0 : pagina;
        int t = tamano == null ? 20 : tamano;
        var resp = cirugiaService.getCirugias(p, t, fechaInicio, fechaFin, estado, search, sort, order);
        return GraphQLConnectionMapper.from(resp, CirugiaConnection::new);
    }

    @MutationMapping
    public CirugiaDTO.FrontResponse createCirugia(@Argument("input") CirugiaDTO.FrontRequest input) throws Exception {
        return cirugiaService.createCirugia(input).getBody();
    }

    @MutationMapping
    public CirugiaDTO.FrontResponse updateCirugia(@Argument Long id, @Argument("input") CirugiaDTO.FrontRequest input) throws Exception {
        return cirugiaService.updateCirugia(String.valueOf(id), input).getBody();
    }

    @MutationMapping
    public boolean deleteCirugia(@Argument Long id) throws Exception {
        cirugiaService.deleteCirugia(id);
        return true;
    }

    @MutationMapping
    public CirugiaDTO.FrontResponse inicializarCirugia(@Argument Long id) {
        return cirugiaService.inicializarCirugia(id).getBody();
    }

    @MutationMapping
    public CirugiaDTO.FrontResponse finalizarCirugia(@Argument Long id) {
        return cirugiaService.finalizarCirugia(id).getBody();
    }

    @QueryMapping
    public List<MiembroEquipoDTO.Response> equipoMedico(@Argument Long cirugiaId) {
        return cirugiaService.getEquipoMedico(cirugiaId).getBody();
    }

    @MutationMapping
    public List<MiembroEquipoDTO.Response> asignarEquipoMedico(@Argument Long cirugiaId,
                                                                @Argument List<MiembroEquipoDTO.Create> miembros) {
        List<MiembroEquipoDTO.Create> payload = new ArrayList<>();
        for (MiembroEquipoDTO.Create miembro : miembros) {
            MiembroEquipoDTO.Create item = new MiembroEquipoDTO.Create();
            item.setCirugiaId(cirugiaId);
            item.setUrgenciaId(miembro.getUrgenciaId());
            item.setPersonalId(miembro.getPersonalId());
            item.setRol(miembro.getRol());
            payload.add(item);
        }
        return cirugiaService.saveEquipoMedico(payload, cirugiaId).getBody();
    }

    @QueryMapping
    public List<ServicioDto> serviciosCirugia(@Argument Integer pagina, @Argument Integer tamano) {
        int p = pagina == null ? 0 : pagina;
        int t = tamano == null ? 10 : tamano;
        return cirugiaService.getServicios(t, p).getBody();
    }

    @QueryMapping
    public List<IntervencionDto> intervenciones(@Argument Long cirugiaId) {
        return cirugiaService.getIntervencionesByCirugiaId(cirugiaId).getBody();
    }

    @MutationMapping
    public IntervencionDto createIntervencion(@Argument Long cirugiaId, @Argument("input") IntervencionDto input) {
        return cirugiaService.createIntervencion(cirugiaId, input).getBody();
    }

    @MutationMapping
    public IntervencionDto updateIntervencion(@Argument Long cirugiaId,
                                              @Argument Long intervencionId,
                                              @Argument("input") IntervencionDto input) {
        return cirugiaService.updateIntervencion(cirugiaId, intervencionId, input).getBody();
    }

    @MutationMapping
    public boolean deleteIntervencion(@Argument Long cirugiaId, @Argument Long intervencionId) {
        cirugiaService.deleteIntervencion(cirugiaId, intervencionId);
        return true;
    }
}