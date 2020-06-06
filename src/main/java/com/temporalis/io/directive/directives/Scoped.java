package com.temporalis.io.directive.directives;

import com.temporalis.io.directive.NamedDirective;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLDirectiveContainer;
import graphql.schema.GraphQLEnumType;
import graphql.schema.GraphQLEnumValueDefinition;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import java.util.ArrayList;
import java.util.List;

public class Scoped implements SchemaDirectiveWiring, NamedDirective {

  private static final String SCOPE_ARGUMENT_NAME = "scope";

  @Override
  public GraphQLObjectType onObject(SchemaDirectiveWiringEnvironment<GraphQLObjectType> environment) {

    var isImplicitPrivate = isImplicitPrivate(environment);
    var publicFields = getPublicFields(environment.getElement().getFieldDefinitions(), !isImplicitPrivate);

    return environment.getElement().transform(builder -> {
      builder.clearFields();
      builder.fields(publicFields);
    });
  }

  @Override
  public GraphQLEnumType onEnum(SchemaDirectiveWiringEnvironment<GraphQLEnumType> environment) {
    var isImplicitPrivate = isImplicitPrivate(environment);
    var publicFields = getPublicFields(environment.getElement().getValues(), !isImplicitPrivate);

    return environment.getElement().transform(builder -> {
      builder.clearValues();
      builder.values(publicFields);
    });
  }

  @Override
  public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
    return environment.getElement();
  }

  @Override
  public GraphQLEnumValueDefinition onEnumValue(
      SchemaDirectiveWiringEnvironment<GraphQLEnumValueDefinition> environment) {
    return environment.getElement();
  }

  @Override
  public GraphQLArgument onArgument(SchemaDirectiveWiringEnvironment<GraphQLArgument> environment) {
    return environment.getElement();
  }


  @Override
  public String getName() {
    return "scoped";
  }

  private <T extends GraphQLDirectiveContainer> List<T> getPublicFields(List<T> fields, boolean implicitlyPublic) {
    var publicFields = new ArrayList<T>();
    for (var field : fields) {
      var fieldIsExplicitlyPrivate =
          field.getDirective("scoped") != null && field.getDirective("scoped").getArgument("scope").getValue()
              .equals("PRIVATE");
      var fieldIsExplicitlyPublic =
          field.getDirective("scoped") != null && field.getDirective("scoped").getArgument("scope").getValue()
              .equals("PUBLIC");

      if (implicitlyPublic && !fieldIsExplicitlyPrivate) {
        publicFields.add(field);
      } else if (!implicitlyPublic && fieldIsExplicitlyPublic) {
        publicFields.add(field);
      }
    }
    return publicFields;
  }

  private <T extends GraphQLDirectiveContainer> boolean isImplicitPrivate(
      SchemaDirectiveWiringEnvironment<T> environment) {
    return environment.getDirective("scoped") == null ||
        environment.getDirective("scoped").getArgument("scope").getValue().equals("PRIVATE");
  }
}
