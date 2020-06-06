package com.temporalis.io.directive;

import static java.util.stream.Collectors.toList;

import graphql.language.FieldDefinition;
import graphql.schema.GraphQLDirectiveContainer;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphQLSchema;
import graphql.schema.transform.FieldVisibilitySchemaTransformation;
import java.util.List;
import java.util.stream.Collectors;

public class DirectiveSchemaTransformer {

  public GraphQLSchema transform(GraphQLSchema schema){

  }

  private GraphQLObjectType removePrivateFieldDefinitions(GraphQLObjectType graphQLObjectType){

  }

  private List<GraphQLFieldDefinition> getPublicFieldDefinitions(GraphQLObjectType graphQLObjectType){
    return graphQLObjectType.getFieldDefinitions().stream()
        .filter(field -> field.getDirective("scoped") != null)
        .collect(toList());
  }
}
