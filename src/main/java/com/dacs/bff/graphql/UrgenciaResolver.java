package com.dacs.bff.graphql;

import java.util.ArrayList;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.dacs.bff.dto.IntervencionDto;
import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.UrgenciaDTO;
import com.dacs.bff.service.ApiBackendUrgenciaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('admin') or hasRole('personal_medico')")
public class UrgenciaResolver {

    private final ApiBackendUrgenciaService urgenciaService;

    @QueryMapping
    public UrgenciaConnection urgencias(@Argument Integer pagina,
                                        @Argument Integer tamano,
                                        @Argument String fechaInicio,
                                        @Argument String fechaFin,
                                        @Argument String estado,
                                        @Argument String search,
                                        @Argument String sort,
                                        @Argument String order) {
        int p = pagina == null ? 0 : pagina;
        int t = tamano == null ? 20 : tamano;
        var resp = urgenciaService.getUrgencias(p, t, fechaInicio, fechaFin, estado, search, sort, order);
        return GraphQLConnectionMapper.from(resp, UrgenciaConnection::new);
    }

    @MutationMapping
    public UrgenciaDTO.FrontResponse createUrgencia(@Argument("input") UrgenciaDTO.FrontRequest input) throws Exception {
        return urgenciaService.createUrgencia(input).getBody();
    }

    @MutationMapping
    public UrgenciaDTO.FrontResponse updateUrgencia(@Argument Long id, @Argument("input") UrgenciaDTO.FrontRequest input) throws Exception {
        return urgenciaService.updateUrgencia(String.valueOf(id), input).getBody();
    }

    @MutationMapping
    public boolean deleteUrgencia(@Argument Long id) throws Exception {
        urgenciaService.deleteUrgencia(id);
        return true;
    }

    @MutationMapping
    public UrgenciaDTO.FrontResponse inicializarUrgencia(@Argument Long id) {
        return urgenciaService.inicializarUrgencia(id).getBody();
    }

    @MutationMapping
    public UrgenciaDTO.FrontResponse finalizarUrgencia(@Argument Long id) {
        return urgenciaService.finalizarUrgencia(id).getBody();
    }

    @QueryMapping
    public List<MiembroEquipoDTO.Response> equipoMedicoUrgencia(@Argument Long urgenciaId) {
        return urgenciaService.getEquipoMedico(urgenciaId).getBody();
    }

    @MutationMapping
    public List<MiembroEquipoDTO.Response> asignarEquipoMedicoUrgencia(@Argument Long urgenciaId,
                                                                       @Argument List<MiembroEquipoDTO.Create> miembros) {
        List<MiembroEquipoDTO.Create> payload = new ArrayList<>();
        for (MiembroEquipoDTO.Create miembro : miembros) {
            MiembroEquipoDTO.Create item = new MiembroEquipoDTO.Create();
            item.setUrgenciaId(urgenciaId);
            item.setCirugiaId(miembro.getCirugiaId());
            item.setPersonalId(miembro.getPersonalId());
            item.setRol(miembro.getRol());
            payload.add(item);
        }
        return urgenciaService.saveEquipoMedico(payload, urgenciaId).getBody();
    }

    @QueryMapping
    public List<IntervencionDto> intervencionesUrgencia(@Argument Long urgenciaId) {
        return urgenciaService.getIntervencionesByUrgenciaId(urgenciaId).getBody();
    }

    @MutationMapping
    public IntervencionDto createIntervencionUrgencia(@Argument Long urgenciaId, @Argument("input") IntervencionDto input) {
        return urgenciaService.createIntervencionForUrgencia(urgenciaId, input).getBody();
    }

    @MutationMapping
    public IntervencionDto updateIntervencionUrgencia(@Argument Long urgenciaId,
                                                      @Argument Long intervencionId,
                                                      @Argument("input") IntervencionDto input) {
        return urgenciaService.updateIntervencionForUrgencia(urgenciaId, intervencionId, input).getBody();
    }

    @MutationMapping
    public boolean deleteIntervencionUrgencia(@Argument Long urgenciaId, @Argument Long intervencionId) {
        urgenciaService.deleteIntervencionForUrgencia(urgenciaId, intervencionId);
        return true;
    }

}