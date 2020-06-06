package com.temporalis.io.directive.directives;

import com.temporalis.io.directive.NamedDirective;
import graphql.schema.GraphQLEnumType;
import graphql.schema.GraphQLEnumValueDefinition;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import graphql.schema.transform.FieldVisibilitySchemaTransformation;
import graphql.schema.transform.VisibleFieldPredicate;
import graphql.schema.transform.VisibleFieldPredicateEnvironment;

public class Scoped implements SchemaDirectiveWiring, NamedDirective, VisibleFieldPredicate {

    private static final String SCOPE_ARGUMENT_NAME = "scope";

    @Override
    public GraphQLObjectType onObject(SchemaDirectiveWiringEnvironment<GraphQLObjectType> environment) {

//        var directive = environment.getDirective(getName());
//        var argument = directive.getArgument(SCOPE_ARGUMENT_NAME);
//        //argument.getValue()
//        var f = new FieldVisibilitySchemaTransformation(env -> {
//            // check the env directive
//            env.getSchemaElement().getDefinition().
//                return true;
//        })
//
//            f.apply(schema);


            // for each child.. if implicitly private, if child has public, expose.
            // if implicitly public, if child is not explicitly private, expose
            // else, remove


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
        return "scoped";
    }

    @Override
    public boolean isVisible(VisibleFieldPredicateEnvironment environment) {
        return false;
    }
}
