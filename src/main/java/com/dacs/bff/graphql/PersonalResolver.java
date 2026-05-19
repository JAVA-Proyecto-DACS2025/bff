package com.dacs.bff.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import com.dacs.bff.client.PersonalClient;
import com.dacs.bff.dto.PersonalDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PersonalResolver {

    private final PersonalClient personalClient;

    @QueryMapping
    public PersonalDto.BackResponse personal(@Argument Long id) {
        // Call backend to retrieve full Personal entity (heavy payload)
        return personalClient.getById(id);
    }

    @QueryMapping
    public PersonalConnection personales(@Argument Integer page,
                                         @Argument Integer limit,
                                         @Argument String search,
                                         @Argument String role) {
        int p = page == null ? 0 : page;
        int l = limit == null ? 16 : limit;
        var resp = personalClient.list(p, l, search, role);
        PersonalConnection out = new PersonalConnection();
        out.setContent(resp.getContenido());
        out.setTotalElements(resp.getTotalElementos());
        out.setTotalPages(resp.getTotalPaginas());
        out.setCurrentPage(resp.getPagina());
        out.setHasNextPage(resp.getPagina() < resp.getTotalPaginas() - 1);
        out.setHasPreviousPage(resp.getPagina() > 0);
        return out;
    }

    @MutationMapping
    public PersonalDto.BackResponse createPersonal(@Argument("input") PersonalDto.Create input) {
        return personalClient.create(input);
    }

    @MutationMapping
    public PersonalDto.BackResponse updatePersonal(@Argument("input") PersonalDto.Update input) {
        if (input == null || input.getId() == null) {
            throw new IllegalArgumentException("Update input must include id");
        }
        return personalClient.update(input.getId(), input);
    }

    @MutationMapping
    public boolean deletePersonal(@Argument Long id) {
        personalClient.delete(id);
        return true;
    }
}
