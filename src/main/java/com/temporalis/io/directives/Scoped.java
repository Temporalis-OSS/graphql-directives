package com.temporalis.io.directives;

import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLEnumType;
import graphql.schema.GraphQLEnumValueDefinition;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;

public class Scoped implements SchemaDirectiveWiring {

    @Override
    public GraphQLObjectType onObject(SchemaDirectiveWiringEnvironment<GraphQLObjectType> environment) {
        return null;
    }

    @Override
    public GraphQLEnumType onEnum(SchemaDirectiveWiringEnvironment<GraphQLEnumType> environment) {
        return null;
    }

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        return null;
    }

    @Override
    public GraphQLArgument onArgument(SchemaDirectiveWiringEnvironment<GraphQLArgument> environment) {
        return null;
    }

    @Override
    public GraphQLEnumValueDefinition onEnumValue(SchemaDirectiveWiringEnvironment<GraphQLEnumValueDefinition> environment) {
        return null;
    }
}
