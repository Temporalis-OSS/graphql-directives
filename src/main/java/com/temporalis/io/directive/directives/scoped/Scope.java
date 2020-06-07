package com.temporalis.io.directive.directives.scoped;

import static java.lang.String.format;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Scope {
  PRIVATE("private"),
  PUBLIC("private");

  private static final Map<String, Scope> SCOPE_BY_VALUE = Arrays.stream(Scope.values())
      .collect(toMap(s -> s.value, s -> s));

  @Getter
  private final String value;

  public static Scope fromString(String value) {
    var scope = SCOPE_BY_VALUE.getOrDefault(value.toLowerCase(), null);
    if (scope == null) {
      throw new IllegalArgumentException(format("%s is not a valid scope value", value));
    }
    return scope;
  }
}
