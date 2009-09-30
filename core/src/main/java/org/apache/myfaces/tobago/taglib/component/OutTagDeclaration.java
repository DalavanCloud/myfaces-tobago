package org.apache.myfaces.tobago.taglib.component;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.myfaces.tobago.apt.annotation.BodyContentDescription;
import org.apache.myfaces.tobago.apt.annotation.Tag;
import org.apache.myfaces.tobago.apt.annotation.TagAttribute;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTag;
import org.apache.myfaces.tobago.apt.annotation.UIComponentTagAttribute;
import org.apache.myfaces.tobago.component.ComponentTypes;
import org.apache.myfaces.tobago.component.RendererTypes;
import org.apache.myfaces.tobago.taglib.decl.HasConverter;
import org.apache.myfaces.tobago.taglib.decl.HasIdBindingAndRendered;
import org.apache.myfaces.tobago.taglib.decl.HasTip;
import org.apache.myfaces.tobago.taglib.decl.HasValue;
import org.apache.myfaces.tobago.taglib.decl.IsGridLayoutComponent;
import org.apache.myfaces.tobago.taglib.decl.IsInline;

/**
 * Renders a text
 */
@Tag(name = "out")
@BodyContentDescription(anyTagOf = "f:converter|f:convertNumber|f:convertDateTime|...")
@UIComponentTag(
    uiComponent = "org.apache.myfaces.tobago.component.UIOut",
    uiComponentBaseClass = "org.apache.myfaces.tobago.component.AbstractUIOut",
    componentType = ComponentTypes.OUT,
    rendererType = RendererTypes.OUT,
    allowedChildComponenents = "NONE")

public interface OutTagDeclaration
    extends HasIdBindingAndRendered, HasConverter, IsInline, HasTip, HasValue, IsGridLayoutComponent {

  /**
   * Flag indicating that characters that are
   * sensitive in HTML and XML markup must be escaped.
   */
  @TagAttribute
  @UIComponentTagAttribute(type = "java.lang.Boolean", defaultValue = "Boolean.TRUE")
  void setEscape(String escape);

  /**
   * Indicate markup of this component.
   * Possible values are 'none', 'strong' and 'deleted'
   */
  @TagAttribute
  @UIComponentTagAttribute(defaultValue = "none", type = "java.lang.String[]",
      allowedValues = {"none", "strong", "deleted"})
  void setMarkup(String markup);
  
  @UIComponentTagAttribute(type = "java.lang.Boolean", defaultValue = "Boolean.TRUE")
  void setCreateSpan(String createSpan);
}
