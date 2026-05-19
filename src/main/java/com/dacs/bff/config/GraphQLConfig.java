package com.dacs.bff.config;

import java.time.LocalDateTime;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQLConfig {

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        GraphQLScalarType localDateTimeScalar = GraphQLScalarType.newScalar()
                .name("LocalDateTime")
                .description("ISO-8601 LocalDateTime scalar")
                .coercing(new Coercing<LocalDateTime, String>() {
                    @Override
                    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                        if (dataFetcherResult instanceof LocalDateTime localDateTime) {
                            return localDateTime.toString();
                        }
                        throw new CoercingSerializeException("Expected a LocalDateTime object.");
                    }

                    @Override
                    public LocalDateTime parseValue(Object input) throws CoercingParseValueException {
                        if (input instanceof String stringValue) {
                            return LocalDateTime.parse(stringValue);
                        }
                        throw new CoercingParseValueException("Expected a String.");
                    }

                    @Override
                    public LocalDateTime parseLiteral(Object input) throws CoercingParseLiteralException {
                        if (input instanceof StringValue stringValue) {
                            return LocalDateTime.parse(stringValue.getValue());
                        }
                        throw new CoercingParseLiteralException("Expected a StringValue.");
                    }
                })
                .build();

        return wiringBuilder -> wiringBuilder.scalar(localDateTimeScalar);
    }
}
