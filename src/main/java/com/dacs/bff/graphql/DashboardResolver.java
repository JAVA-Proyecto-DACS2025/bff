package com.dacs.bff.graphql;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.dacs.bff.dto.EstadisticasGeneralesDto;
import com.dacs.bff.service.ApiBackendDashboardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('admin') or hasRole('personal_medico')")
public class DashboardResolver {

    private final ApiBackendDashboardService dashboardService;

    @QueryMapping
    public EstadisticasGeneralesDto estadisticasGenerales() {
        return dashboardService.getEstadisticasGenerales().getBody();
    }
}
