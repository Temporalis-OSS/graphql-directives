package com.temporalis.io.directive.directives;

import static graphql.Assert.assertNull;
import static org.mockito.Mockito.mock;

import com.temporalis.io.directive.TestResources;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql.schema.idl.UnExecutableSchemaGenerator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ScopedTest {

  @BeforeAll
  public static void setUpOnce() {
    SchemaParser schemaParser = new SchemaParser();
    UnExecutableSchemaGenerator schemaGenerator = new UnExecutableSchemaGenerator();

    var scopedDirectiveSchema = TestResources.loadResource(TestResources.SCOPED_DIRECTIVE);
    var testSchema = TestResources.loadTestResource(TestResources.TEST_SCHEMA);

    var typeRegistry = new TypeDefinitionRegistry();
    typeRegistry.merge(schemaParser.parse(scopedDirectiveSchema));
    typeRegistry.merge(schemaParser.parse(testSchema));

    var graphQLSchema = schemaGenerator.makeUnExecutableSchema(typeRegistry);

    var str = "hello world";
  }

  @Test
  public void onObject() {
    var scoped = new Scoped();
    assertNull(scoped.onObject(mock(SchemaDirectiveWiringEnvironment.class)));
  }
}
