package com.temporalis.io.directive.directives.scoped;

import static com.temporalis.io.directive.directives.scoped.Scope.PRIVATE;
import static com.temporalis.io.directive.directives.scoped.Scope.PUBLIC;
import static com.temporalis.io.directive.directives.scoped.Scope.fromString;
import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

import com.temporalis.io.directive.NamedDirective;
import com.temporalis.io.directive.exceptions.DirectiveConstraintException;
import graphql.language.NonNullType;
import graphql.schema.GraphQLArgument;
import graphql.schema.GraphQLDirectiveContainer;
import graphql.schema.GraphQLEnumType;
import graphql.schema.GraphQLEnumValueDefinition;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLObjectType;
import graphql.schema.GraphqlElementParentTree;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Scoped implements SchemaDirectiveWiring, NamedDirective {

  public static final String SCOPED_DIRECTIVE_NAME = "scoped";
  private static final String SCOPE_ARGUMENT_NAME = "scope";
  private static final String MISSING_SCOPED_PARENT_EXCEPTION_MESSAGE_FORMAT = "Missing @scoped directive on parent %s";
  private static final String CAN_NOT_REMOVE_REQUIRED_ARGUMENT_EXCEPTION_MESSAGE_FORMAT = "@scoped private is invalid on argument %s as the argument is not nullable";

  private final ScopeProvider scopeProvider;

  @Override
  public GraphQLObjectType onObject(SchemaDirectiveWiringEnvironment<GraphQLObjectType> environment) {
    if(scopeProvider.getActiveScope() == PRIVATE){
      return environment.getElement();
    }

    var isImplicitlyPrivate = isImplicitlyPrivate(environment);
    var publicFields = getPublicChildren(environment.getElement().getFieldDefinitions(), isImplicitlyPrivate).stream()
        .map(field -> field.transform(fieldBuilder -> {
          if (field.getArguments() != null) {
            fieldBuilder.clearArguments();
            fieldBuilder.arguments(getPublicArguments(field.getArguments()));
          }
        }))
        .collect(toList());

    return environment.getElement().transform(builder -> {
      builder.clearFields();
      builder.fields(publicFields);
    });
  }

  @Override
  public GraphQLEnumType onEnum(SchemaDirectiveWiringEnvironment<GraphQLEnumType> environment) {
    if(scopeProvider.getActiveScope() == PRIVATE){
      return environment.getElement();
    }

    var isImplicitlyPrivate = isImplicitlyPrivate(environment);
    var publicValues = getPublicChildren(environment.getElement().getValues(), isImplicitlyPrivate);

    return environment.getElement().transform(builder -> {
      builder.clearValues();
      builder.values(publicValues);
    });
  }

  @Override
  public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
    return validateEnvironment(environment);
  }

  @Override
  public GraphQLEnumValueDefinition onEnumValue(
      SchemaDirectiveWiringEnvironment<GraphQLEnumValueDefinition> environment) {
    var parent = environment.getElementParentTree().getParentInfo()
        .map(GraphqlElementParentTree::getElement)
        .orElse(null);
    if (parent instanceof GraphQLEnumType) {
      if (getFieldScope((GraphQLEnumType) parent) != null) {
        return environment.getElement();
      }
    }
    throw new DirectiveConstraintException(format(MISSING_SCOPED_PARENT_EXCEPTION_MESSAGE_FORMAT, parent));
  }

  @Override
  public GraphQLArgument onArgument(SchemaDirectiveWiringEnvironment<GraphQLArgument> environment) {
    if (environment.getElement().getDefinition().getType() instanceof NonNullType) {
      if (getFieldScope(environment.getElement()) == PRIVATE) {
        throw new DirectiveConstraintException(
            format(CAN_NOT_REMOVE_REQUIRED_ARGUMENT_EXCEPTION_MESSAGE_FORMAT, environment.getElement().getName()));
      }
    }
    return validateEnvironment(environment);
  }


  @Override
  public String getName() {
    return SCOPED_DIRECTIVE_NAME;
  }

  private <T extends GraphQLDirectiveContainer> List<T> getPublicChildren(List<T> fields, boolean implicitlyPrivate) {
    var publicFields = new ArrayList<T>();
    for (var field : fields) {
      if (getFieldScope(field) == PRIVATE) {
        continue;
      }
      if (getFieldScope(field) == null && implicitlyPrivate) {
        continue;
      }
      if (getFieldScope(field) == null && !implicitlyPrivate) {
        publicFields.add(field);
      } else if (getFieldScope(field) == PUBLIC) {
        publicFields.add(field);
      }
    }
    return publicFields;
  }

  private List<GraphQLArgument> getPublicArguments(List<GraphQLArgument> arguments) {
    return arguments.stream()
        .filter(argument -> getFieldScope(argument) != PRIVATE)
        .collect(toList());
  }

  private <T extends GraphQLDirectiveContainer> boolean isImplicitlyPrivate(
      SchemaDirectiveWiringEnvironment<T> environment) {
    var scope = getFieldScope(environment.getElement());
    return scope == null || scope == PRIVATE;
  }

  private <T extends GraphQLDirectiveContainer> Scope getFieldScope(T field) {
    return field.getDirective(SCOPED_DIRECTIVE_NAME) != null ?
        fromString((String) field.getDirective(SCOPED_DIRECTIVE_NAME)
            .getArgument(SCOPE_ARGUMENT_NAME).getValue()) : null;
  }

  private <T extends GraphQLDirectiveContainer> T validateEnvironment(SchemaDirectiveWiringEnvironment<T> environment) {
    var parent = environment.getFieldsContainer();
    if (getFieldScope((GraphQLObjectType) parent) != null) {
      return environment.getElement();
    }
    throw new DirectiveConstraintException(format(MISSING_SCOPED_PARENT_EXCEPTION_MESSAGE_FORMAT, parent.getName()));
  }
}
