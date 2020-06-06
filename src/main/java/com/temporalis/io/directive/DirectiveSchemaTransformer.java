package com.temporalis.io.directive;

import graphql.language.FieldDefinition;
import graphql.schema.GraphQLDirectiveContainer;
import graphql.schema.GraphQLSchema;
import graphql.schema.transform.FieldVisibilitySchemaTransformation;

public class DirectiveSchemaTransformer {

//  public GraphQLSchema transform(GraphQLSchema schema){
//    var f = new FieldVisibilitySchemaTransformation(env -> {
//      // check the env directive
//      var parent = env.getParentElement();
//      var implicitlyPublic = parent
//      var definition = env.getSchemaElement().getDefinition();
//      if(definition instanceof GraphQLDirectiveContainer){
//        var field = (GraphQLDirectiveContainer)definition;
//        field.getDirective("Scoped");
//
//      }
//      return true;
//    })
//
//    f.apply(schema);
//  }
}
