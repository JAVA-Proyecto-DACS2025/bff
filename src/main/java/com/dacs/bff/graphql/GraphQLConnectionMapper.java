package com.dacs.bff.graphql;

import java.util.function.Supplier;

import com.dacs.bff.dto.PaginacionDto;

public final class GraphQLConnectionMapper {

    private GraphQLConnectionMapper() {
    }

    public static <T, C extends GraphQLConnection<T>> C from(
            PaginacionDto.Response<T> response,
            Supplier<C> factory) {
        C connection = factory.get();
        connection.setContent(response.getContenido());
        connection.setTotalElements(response.getTotalElementos());
        connection.setTotalPages(response.getTotalPaginas());
        connection.setCurrentPage(response.getPagina());
        connection.setHasNextPage(response.getPagina() < response.getTotalPaginas() - 1);
        connection.setHasPreviousPage(response.getPagina() > 0);
        return connection;
    }
}