/*
 * Copyright 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.template.soy.shared.internal;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.template.soy.shared.restricted.SoyFunction;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/**
 * Enum of built-in functions supported in Soy expressions.
 *
 * <p>Important: Do not use outside of Soy code (treat as superpackage-private).
 *
 */
public enum BuiltinFunction implements SoyFunction {
  IS_FIRST("isFirst"),
  IS_LAST("isLast"),
  INDEX("index"),
  QUOTE_KEYS_IF_JS("quoteKeysIfJs"),
  CHECK_NOT_NULL("checkNotNull"),
  FLOAT("_soy_private_do_not_use_float"), // TODO(user): fix name
  ;

  /** Map of NonpluginFunctions by function name. */
  private static final Map<String, BuiltinFunction> NONPLUGIN_FUNCTIONS_BY_NAME;

  static {
    ImmutableMap.Builder<String, BuiltinFunction> mapBuilder = ImmutableMap.builder();
    for (BuiltinFunction nonpluginFn : values()) {
      mapBuilder.put(nonpluginFn.functionName, nonpluginFn);
    }
    NONPLUGIN_FUNCTIONS_BY_NAME = mapBuilder.build();
  }

  /**
   * Returns the NonpluginFunction for the given function name, or null if not found.
   *
   * @param functionName The function name to retrieve.
   */
  @Nullable
  public static BuiltinFunction forFunctionName(String functionName) {
    return NONPLUGIN_FUNCTIONS_BY_NAME.get(functionName);
  }

  /** The function name. */
  private final String functionName;

  BuiltinFunction(String name) {
    this.functionName = name;
  }

  @Override
  public String getName() {
    return functionName;
  }

  @Override
  public Set<Integer> getValidArgsSizes() {
    return ImmutableSet.of(1); // All built-in functions are unary.
  }
}
