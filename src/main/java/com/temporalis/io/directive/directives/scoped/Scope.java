package com.temporalis.io.directive.directives.scoped;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
public enum Scope {
  PRIVATE("private"),
  PUBLIC("private");

  private static final Map<String, Scope> SCOPE_BY_VALUE = Arrays.stream(Scope.values())
      .collect(toMap(s -> s.value, s -> s));

  @Getter
  private final String value;

  public static Scope fromString(String value) {
    return ofNullable(SCOPE_BY_VALUE.get(value.toLowerCase())).orElseThrow(
            () -> new IllegalArgumentException(format("%s is not a valid scope value", value)));
  }
}
