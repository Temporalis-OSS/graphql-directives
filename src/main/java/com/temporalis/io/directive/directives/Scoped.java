package com.temporalis.io.directive.directives;

import com.temporalis.io.directive.NamedDirective;
import graphql.schema.GraphQLEnumType;
import graphql.schema.GraphQLEnumValueDefinition;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import graphql.schema.transform.VisibleFieldPredicate;
import graphql.schema.transform.VisibleFieldPredicateEnvironment;

public class Scoped implements SchemaDirectiveWiring, NamedDirective, VisibleFieldPredicate {

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
    public GraphQLEnumValueDefinition onEnumValue(SchemaDirectiveWiringEnvironment<GraphQLEnumValueDefinition> environment) {
        return null;
    }

    @Override
    public String getName() {
        return "Scope";
    }

    @Override
    public boolean isVisible(VisibleFieldPredicateEnvironment environment) {
        return false;
    }
}
