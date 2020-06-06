package com.temporalis.io.directive;

import java.io.File;

public class TestResources {
  private static final String TEST_RESOURCES = "src/test/resources/";
  private static final String RESOURCES = "src/main/resources/";

  public static final String TEST_SCHEMA = "schemas/valid_scope_schema.graphqls";
  public static final String SCOPED_DIRECTIVE = "schemas/scoped_directive.graphqls";

  public static File loadTestResource(String relativePath){
    return new File(TEST_RESOURCES + relativePath);
  }
  public static File loadResource(String relativePath){
    return new File(RESOURCES + relativePath);
  }
}
