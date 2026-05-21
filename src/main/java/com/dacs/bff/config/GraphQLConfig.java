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

        GraphQLScalarType longScalar = GraphQLScalarType.newScalar()
                .name("Long")
                .description("64-bit signed integer scalar")
                .coercing(new Coercing<Long, Long>() {
                    @Override
                    public Long serialize(Object dataFetcherResult) throws CoercingSerializeException {
                        if (dataFetcherResult instanceof Number number) {
                            return number.longValue();
                        }
                        throw new CoercingSerializeException("Expected a numeric value.");
                    }

                    @Override
                    public Long parseValue(Object input) throws CoercingParseValueException {
                        if (input instanceof Number number) {
                            return number.longValue();
                        }
                        if (input instanceof String stringValue) {
                            return Long.parseLong(stringValue);
                        }
                        throw new CoercingParseValueException("Expected a numeric value.");
                    }

                    @Override
                    public Long parseLiteral(Object input) throws CoercingParseLiteralException {
                        if (input instanceof graphql.language.IntValue intValue) {
                            return intValue.getValue().longValue();
                        }
                        if (input instanceof StringValue stringValue) {
                            return Long.parseLong(stringValue.getValue());
                        }
                        throw new CoercingParseLiteralException("Expected an IntValue or StringValue.");
                    }
                })
                .build();

        return wiringBuilder -> wiringBuilder.scalar(localDateTimeScalar).scalar(longScalar);
    }
}
