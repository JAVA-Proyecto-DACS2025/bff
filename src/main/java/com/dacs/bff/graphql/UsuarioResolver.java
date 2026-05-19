package com.dacs.bff.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.dacs.bff.dto.ApiResponse;
import com.dacs.bff.dto.KeycloakUserDto;
import com.dacs.bff.service.ApiConectorService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UsuarioResolver {

    private final ApiConectorService apiConectorService;

    @QueryMapping
    public KeycloakUserDto usuario(@Argument String id) {
        ApiResponse<KeycloakUserDto> response = apiConectorService.getUsuarioById(id);
        if (response == null || response.getData() == null) {
            throw new IllegalStateException("Usuario not found with id: " + id);
        }
        return response.getData();
    }

    @QueryMapping
    public UsuarioConnection usuarios(@Argument Integer page,
                                      @Argument Integer limit,
                                      @Argument String search) {
        int p = page == null ? 0 : page;
        int l = limit == null ? 16 : limit;
        var resp = apiConectorService.getUsuarios(p, l, search);
        return GraphQLConnectionMapper.from(resp, UsuarioConnection::new);
    }

    @MutationMapping
    public KeycloakUserDto createUsuario(@Argument("input") KeycloakUserDto.Create input) {
        ApiResponse<KeycloakUserDto> response = apiConectorService.createUsuario(input);
        if (response == null || response.getData() == null) {
            throw new IllegalStateException("Unable to create usuario");
        }
        return response.getData();
    }

    @MutationMapping
    public KeycloakUserDto updateUsuario(@Argument("input") KeycloakUserDto.Update input) {
        if (input == null || input.getId() == null) {
            throw new IllegalArgumentException("Update input must include id");
        }
        ApiResponse<KeycloakUserDto> response = apiConectorService.updateUsuario(input.getId(), input);
        if (response == null || response.getData() == null) {
            throw new IllegalStateException("Unable to update usuario");
        }
        return response.getData();
    }

    @MutationMapping
    public KeycloakUserDto setUsuarioStatus(@Argument Long id, @Argument boolean enabled) {
        ApiResponse<KeycloakUserDto> response = apiConectorService.toggleUsuarioStatus(String.valueOf(id), enabled);
        if (response == null || response.getData() == null) {
            throw new IllegalStateException("Unable to update usuario status");
        }
        return response.getData();
    }
}