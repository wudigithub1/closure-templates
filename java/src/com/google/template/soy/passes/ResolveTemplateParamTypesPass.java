/*
 * Copyright 2018 Google Inc.
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
package com.google.template.soy.passes;

import com.google.template.soy.base.internal.IdGenerator;
import com.google.template.soy.error.ErrorReporter;
import com.google.template.soy.soytree.SoyFileNode;
import com.google.template.soy.soytree.TemplateElementNode;
import com.google.template.soy.soytree.TemplateNode;
import com.google.template.soy.soytree.defn.TemplateParam;
import com.google.template.soy.soytree.defn.TemplateStateVar;
import com.google.template.soy.types.SoyTypeRegistry;
import com.google.template.soy.types.ast.TypeNodeConverter;

/** Resolve the TypeNode objects in TemplateParams to SoyTypes */
final class ResolveTemplateParamTypesPass extends CompilerFilePass {
  private final TypeNodeConverter converter;

  ResolveTemplateParamTypesPass(SoyTypeRegistry typeRegistry, ErrorReporter errorReporter) {
    this.converter = new TypeNodeConverter(errorReporter, typeRegistry);
  }

  @Override
  public void run(SoyFileNode file, IdGenerator nodeIdGen) {
    for (TemplateNode template : file.getChildren()) {
      for (TemplateParam param : template.getAllParams()) {
        if (param.getTypeNode() != null) {
          param.setType(converter.getOrCreateType(param.getTypeNode()));
        }
      }
      if (template instanceof TemplateElementNode) {
        for (TemplateStateVar state : ((TemplateElementNode) template).getStateVars()) {
          if (state.getTypeNode() != null) {
            state.setType(converter.getOrCreateType(state.getTypeNode()));
          }
        }
      }
    }
  }
}
