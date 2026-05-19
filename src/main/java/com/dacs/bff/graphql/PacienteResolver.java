package com.dacs.bff.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.dacs.bff.client.PacienteClient;
import com.dacs.bff.dto.PacienteDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PacienteResolver {

    private final PacienteClient pacienteClient;

    @QueryMapping
    public PacienteDto.BackResponse paciente(@Argument Long id) {
        return pacienteClient.getById(id);
    }

    @QueryMapping
    public PacienteConnection pacientes(@Argument Integer page,
                                        @Argument Integer limit,
                                        @Argument String search) {
        int p = page == null ? 0 : page;
        int l = limit == null ? 16 : limit;
        var resp = pacienteClient.list(p, l, search);
        return GraphQLConnectionMapper.from(resp, PacienteConnection::new);
    }

    @MutationMapping
    public PacienteDto.BackResponse createPaciente(@Argument("input") PacienteDto.Create input) {
        return pacienteClient.create(input);
    }

    @MutationMapping
    public PacienteDto.BackResponse updatePaciente(@Argument("input") PacienteDto.Update input) {
        return pacienteClient.update(input);
    }

    @MutationMapping
    public boolean activatePaciente(@Argument Long id) {
        pacienteClient.activate(id);
        return true;
    }

    @MutationMapping
    public boolean deactivatePaciente(@Argument Long id) {
        pacienteClient.deactivate(id);
        return true;
    }
}
