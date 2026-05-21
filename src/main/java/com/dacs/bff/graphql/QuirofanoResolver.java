package com.dacs.bff.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.dacs.bff.dto.QuirofanoDTO;
import com.dacs.bff.service.ApiBackendQuirofanoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@PreAuthorize("hasRole('admin') or hasRole('personal_medico')")
public class QuirofanoResolver {

    private final ApiBackendQuirofanoService quirofanoService;

    @QueryMapping
    public List<QuirofanoDTO> quirofanos() {
        return quirofanoService.getQuirofanos().getBody();
    }

    @MutationMapping
    public QuirofanoDTO createQuirofano(@Argument("input") QuirofanoDTO input) throws Exception {
        return quirofanoService.saveQuirofano(input).getBody();
    }
}
