package com.realtime.project.config;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Locale;

@Configuration
public class InstantScalarConfig {

    @Bean
    public GraphQLScalarType scalarType() {
        return GraphQLScalarType.newScalar()
                .name("Instant")
                .description("GRAPHQL INSTANT SCALAR")
                .coercing(new Coercing<Instant, String>() {
                    @Override
                    public String serialize(@NotNull Object dataFetcherResult, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingSerializeException {
                        if(dataFetcherResult instanceof Instant) {
                            return dataFetcherResult.toString();
                        }
                        throw new CoercingSerializeException("Expected Instant Object");
                    }

                    @Override
                    public @Nullable Instant parseValue(@NotNull Object input, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseValueException {
                        try {
                            if(input instanceof String)
                                return LocalDateTime.parse((String)input).atZone(
                                        ZoneId.of("America/Toronto")
                                ) .toInstant();
                            throw new CoercingParseValueException("Invalid Input");
                        }catch (DateTimeParseException ex) {
                            throw new CoercingParseValueException("Not a valid Instant Type, EXCEPTION: " + ex.getMessage());
                        }
                    }

                    @Override
                    public @Nullable Instant parseLiteral(@NotNull Value<?> input, @NotNull CoercedVariables variables, @NotNull GraphQLContext graphQLContext, @NotNull Locale locale) throws CoercingParseLiteralException {
                            if(input instanceof StringValue) {
                                try {
                                    return LocalDateTime.parse(((StringValue) input).getValue()).atZone(
                                            ZoneId.of("America/Toronto")
                                    ) .toInstant();
                                }catch (DateTimeParseException ex) {
                                    throw new CoercingParseLiteralException("Not a valid Instant Type, EXCEPTION: " + ex.getMessage());
                                }
                            }
                            throw new CoercingParseLiteralException("Not a valid Instant Type");
                    }
                }).build();
    }

    @Bean
    public RuntimeWiringConfigurer configure() {
        return (builder) -> builder.scalar(scalarType());
    }

}
